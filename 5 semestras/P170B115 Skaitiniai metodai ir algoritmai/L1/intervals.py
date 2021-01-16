import numpy as np


def _b(coefficients):
    only_negatives = []

    for i in coefficients[1:]:
        if i < 0:
            only_negatives.append(i)

    if len(only_negatives) == 0:
        return 0

    return max(list(map(lambda num: abs(num), only_negatives)))


def _k(coefficients):
    n = len(coefficients) - 1
    max_index = -1

    for i, v in enumerate(coefficients[1:]):
        if v < 0:
            max_index = n - (i + 1)
            break

    if max_index == -1:
        return 0

    return n - max_index


def r(coefficients):
    coefficients = coefficients[:]

    if coefficients[0] < 0:
        coefficients = np.negative(coefficients)

    return 1 + max(coefficients[1:]) / coefficients[0]


def r_pos(coefficients):
    __b = _b(coefficients)
    __k = _k(coefficients)

    if __b == 0 or __k == 0:
        return 0

    return 1 + (__b / coefficients[0]) ** (1. / __k)


def r_neg(coefficients):
    _coefficients = coefficients[:]
    n = len(_coefficients) - 1

    for i in range(n + 1):
        if ((n - i) % 2) != 0:
            _coefficients[i] = -_coefficients[i]

    if _coefficients[0] < 0:
        _coefficients = np.negative(_coefficients)

    __b = _b(_coefficients)
    __k = _k(_coefficients)

    if __b == 0 or __k == 0:
        return 0

    return 1 + (__b / _coefficients[0]) ** (1. / __k)


def acc_lower(coefficients):
    return -min(r(coefficients), r_neg(coefficients))


def acc_upper(coefficients):
    return min(r(coefficients), r_pos(coefficients))
