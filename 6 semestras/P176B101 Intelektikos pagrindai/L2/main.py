import numpy as np
from matplotlib import pyplot as plt

STEP = 0.01

LOW_PROB = [0, 0, 20, 40]
MID_PROB = [20, 40, 60, 80]
HIGH_PROB = [60, 80, 100, 100]


def trap_y(x, a, b, c, d):
    if a <= x < b:
        return (x - a) / (b - a)
    elif b <= x < c:
        return 1
    elif c <= x < d:
        return (d - x) / (d - c)
    else:
        return 0


def trap_x(y, a, b, c, d):
    return a + (b - a) * y, d - (d - c) * y


def price_activation(x):
    points = [
        [0, 0, 1500, 2500],  # Cheap
        [1000, 3000, 6000, 8000],  # Average
        [5000, 8000, 10000, 10000]  # Expensive
    ]
    return [trap_y(x, *p) for p in points]


def mileage_activation(x):
    points = [
        [0, 0, 10000, 25000],  # Low
        [10000, 25000, 50000, 70000],  # Medium
        [50000, 70000, 100001, 100001]  # High
    ]
    return [trap_y(x, *p) for p in points]


def age_activation(x):
    points = [
        [2000, 2000, 2007, 2010],  # Old
        [2007, 2010, 2015, 2018],  # Average
        [2015, 2018, 2022, 2022]  # New
    ]
    return [trap_y(x, *p) for p in points]


def low_prob_rules(pw, mw, aw):
    weights = [min(pw[2], mw[2], aw[0]),
               max(pw[2], mw[2]),
               min(pw[2], mw[1], aw[1]),
               min(pw[2], 1 - aw[1]),
               min(pw[0], mw[0], aw[2])]

    return max(weights)


def mid_prob_rules(pw, mw, aw):
    weights = [min(pw[1], mw[1], aw[1]),
               min(pw[0], mw[2], aw[0]),
               min(pw[2], aw[2]),
               min(pw[2], 1 - mw[2])]

    return max(weights)


def high_prob_rules(pw, mw, aw):
    weights = [min(pw[1], mw[0], aw[2]),
               min(pw[0], aw[1]),
               min(1 - pw[2], 1 - mw[2], aw[1]),
               min(mw[1], aw[2])]

    return max(weights)


def result_implication(price, mileage, age):
    pw = price_activation(price)
    mw = mileage_activation(mileage)
    aw = age_activation(age)

    print('-----')
    print('price activation: {}'.format(pw))
    print('mileage activation: {}'.format(mw))
    print('age activation: {}'.format(aw))

    low = low_prob_rules(pw, mw, aw)
    med = mid_prob_rules(pw, mw, aw)
    high = high_prob_rules(pw, mw, aw)

    print('-----')
    print('low activation: {}'.format(low))
    print('med activation: {}'.format(med))
    print('high activation: {}'.format(high))

    return low, med, high


def get_aggregate(low, med, high):
    x = np.arange(0, 100, STEP)
    aggregate = np.zeros_like(x)

    xi = 0.0
    for i in range(len(aggregate)):
        low_step = min(low, trap_y(xi, *LOW_PROB))
        med_step = min(med, trap_y(xi, *MID_PROB))
        high_step = min(high, trap_y(xi, *HIGH_PROB))
        xi += STEP

        aggregate[i] = max(low_step, med_step, high_step)

    return x, aggregate


def centroid(low, med, high):
    x, aggregate = get_aggregate(low, med, high)

    sum_moment_area = 0.0
    sum_area = 0.0

    x = np.arange(0, 100, STEP)
    for i in range(1, len(aggregate)):
        y1 = aggregate[i - 1]
        y2 = aggregate[i]

        if not (y1 == y2 == 0.0):
            moment = STEP * (2 * y1 + y2) / (3 * (y1 + y2)) + x[i - 1]
            area = STEP * (y1 + y2) / 2

            sum_moment_area += moment * area
            sum_area += area

    return sum_moment_area / sum_area


def mom(low, med, high):
    x, aggregate = get_aggregate(low, med, high)
    return np.mean(x[aggregate == aggregate.max()])


def main():
    price = 1506
    mileage = 19880
    age = 2013

    low, med, high = result_implication(price, mileage, age)
    centroid_res = centroid(low, med, high)
    mom_res = mom(low, med, high)

    print('-----')
    print('Purchase probability (centroid) = {:.1f}'.format(centroid_res))
    print('Purchase probability (MOM) = {:.1f}'.format(mom_res))

    # Outline
    plt.plot(LOW_PROB, [0, 1, 1, 0], 'k--', linewidth=0.5)
    plt.plot(MID_PROB, [0, 1, 1, 0], 'k--', linewidth=0.5)
    plt.plot(HIGH_PROB, [0, 1, 1, 0], 'k--', linewidth=0.5)

    # Activated purchase probabilities
    plt.fill([0, *trap_x(low, *LOW_PROB), 40], [0, low, low, 0], 'y')
    plt.fill([20, *trap_x(med, *MID_PROB), 80], [0, med, med, 0], 'y')
    plt.fill([60, *trap_x(high, *HIGH_PROB), 100], [0, high, high, 0], 'y')

    # Result with centroid defuzzification
    cen_ax, = plt.plot([centroid_res, centroid_res], [0, 1])
    # Result with MOM defuzzification
    mom_ax, = plt.plot([mom_res, mom_res], [0, 1])

    plt.xlim(0, 100)
    plt.ylim(0, 1.05)
    plt.legend([cen_ax, mom_ax], ['Centroid', 'MOM'])
    plt.show()


if __name__ == '__main__':
    main()
