import numpy as np

COEFF = [2.19, -5.17, -7.17, 15.14, 1.21]


def _derivative(coefficients):
    _coeff = coefficients[:-1]
    exp = len(coefficients) - 1

    for i in range(len(_coeff)):
        _coeff[i] *= exp
        exp -= 1

    return _coeff


def _f(x, coeff):
    y = 0.0
    i = len(coeff) - 1

    for coefficient in coeff:
        y += coefficient * x ** i
        i -= 1

    return y


def f(x):
    return _f(x, COEFF)


def f_prime(x):
    return _f(x, _derivative(COEFF))


def g(x):
    return np.exp(-((x / 2) ** 2)) * np.sin(2 * x)


def g_prime(x):
    return 0.5 * np.exp(-((x / 2) ** 2)) * (4 * np.cos(2 * x) - x * np.sin(2 * x))
