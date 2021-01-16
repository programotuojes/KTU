import numpy as np

ACCURACY = 1e-13
BETA = 1


def get_interval(f, x_from, x_to, step):
    start = x_from

    for x in np.arange(x_from, x_to, step):
        if np.sign(f(start)) != np.sign(f(x)):
            return start, x

        start = x

    return None, None


def get_all_intervals(f, x_from, x_to, step):
    x_from -= step * 1.1
    x_to -= step * 1.1

    start, end = get_interval(f, x_from, x_to, step)
    intervals = []

    while start is not None and end is not None:
        intervals.append([start, end])
        start, end = get_interval(f, end, x_to, step)

    return intervals


def chord(f, x_from, x_to):
    iterations = 1

    k = abs(f(x_from) / f(x_to))
    x_mid = (x_from + k * x_to) / (1 + k)

    while abs(f(x_mid)) > ACCURACY:
        iterations += 1

        if np.sign(f(x_mid)) == np.sign(f(x_to)):
            x_to = x_mid
        else:
            x_from = x_mid

        k = abs(f(x_from) / f(x_to))
        x_mid = (x_from + k * x_to) / (1 + k)

    return x_mid, iterations


def newton(f, f_prime, x0):
    iterations = 0

    while abs(f(x0)) > ACCURACY:
        x0 = x0 - BETA * (f(x0) / f_prime(x0))
        iterations += 1

    return x0, iterations


def scan(f, x_from, x_to):
    iterations = 0
    step = (x_to - x_from) / 10
    start = x_from

    while abs(x_to - x_from) > ACCURACY:
        for x in np.arange(x_from, x_to, step):
            if np.sign(f(start)) != np.sign(f(x)):
                x_from = start
                x_to = x
                break

            start = x

        step /= 10
        iterations += 1

    return (x_from + x_to) / 2, iterations
