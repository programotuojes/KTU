import numpy as np
from sklearn.linear_model import LinearRegression

import plots
from linear_neuron import AdaptiveLinearNeuron

INPUT_FILE = 'input/sunspot.txt'
N = 10
LEARNING_RATE = 0.000000085
EPOCH_COUNT = 1000
ERROR_GOAL = 200


def read_file():
    file = open(INPUT_FILE, 'r')
    years = []
    spots = []

    for line in file.readlines():
        [year, spot] = line.rstrip('\n').split('\t')
        years.append(int(year))
        spots.append(int(spot))

    file.close()
    return years, spots


def get_input_output(spots):
    p = []
    t = []

    for i in range(len(spots) - N):
        p.append(spots[i: i + N])
        t.append(spots[i + N])

    return p, t


def mse(errors):
    return sum(map(lambda x: x ** 2, errors)) / len(errors)


def mad(errors):
    return np.median(np.abs(errors))


def main():
    years, spots = read_file()
    plots.activity(years, spots)

    p, t = get_input_output(spots)
    plots.plot_3d(p, t)

    pu = p[:200]
    tu = t[:200]
    model = LinearRegression().fit(pu, tu)

    print('--- Model values ---')
    print('Weights:', model.coef_)
    print('b:', model.intercept_)

    tsu = model.predict(pu)
    plots.verify(tu, tsu, years[N:200 + N], 'Model verification with used data')

    tsu = model.predict(p[200:])
    plots.verify(t[200:], tsu, years[200 + N:], 'Model verification with unused data')

    error = np.subtract(t, model.predict(p))
    plots.error(error, years[N:])
    plots.error_hist(error)

    print('\n--- Errors ---')
    print('MSE: {:.2f}'.format(mse(error)))
    print('MAD: {:.2f}'.format(mad(error)))

    aln = AdaptiveLinearNeuron(LEARNING_RATE, EPOCH_COUNT, ERROR_GOAL).fit(np.array(p[:200]), np.array(t[:200]))
    prediction = aln.predict(np.array(p[:200]))
    plots.verify(tu, prediction, years[N:200 + N], 'ALN verification with used data')

    print('\n--- ALN values ---')
    print('Weights:', aln.weight[1:])
    print('b:', aln.weight[0])

    print('\n--- ALN errors ---')
    print('MSE: {:.2f}'.format(mse(aln.errors2)))
    print('MAD: {:.2f}'.format(mad(aln.errors2)))

    print('\n--- Params ---')
    print('N:', N)
    print('Learning rate:', LEARNING_RATE)

    plots.show()


if __name__ == '__main__':
    main()
