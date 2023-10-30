from math import sqrt

from helper import is_value_bad, get_frequencies


def get_missing_percent(columns, key):
    bad_count = 0

    for cell in columns[key]:
        if is_value_bad(key, cell):
            bad_count += 1

    return bad_count / len(columns[key]) * 100


def cardinality(column):
    freq = get_frequencies(column)
    return len(freq)


def first_quartile(column):
    column = sorted(column)
    n = len(column)
    return median(column[:n // 2])


def third_quartile(column):
    column = sorted(column)
    n = len(column)

    if n % 2 != 0:
        return median(column[n // 2 + 1:])
    else:
        return median(column[n // 2:])


def median(column):
    n = len(column)

    if n % 2 != 0:
        return column[n // 2]
    else:
        return (column[n // 2 - 1] + column[n // 2]) / 2


def avg(column):
    return sum(column) / len(column)


def std_dev(column):
    mean = avg(column)
    total = 0

    for i in column:
        total += (i - mean) ** 2

    return sqrt(total / (len(column) - 1))


def mode(column):
    freq = get_frequencies(column)
    max_key = max(freq, key=freq.get)

    return max_key, freq[max_key], freq[max_key] / len(column) * 100


def mode2(column):
    freq = get_frequencies(column)
    max_cell = max(freq, key=freq.get)
    second_cell = 0

    for cell in column:
        if cell != max_cell:
            second_cell = cell
            break

    for cell in freq:
        if freq[second_cell] < freq[cell] and cell != max_cell:
            second_cell = cell

    return second_cell, freq[second_cell], freq[second_cell] / len(column) * 100


def covariance(columns, key1, key2):
    if len(columns[key1]) != len(columns[key2]):
        raise ValueError('columns are not equal length')

    n = len(columns[key1])
    key1_avg = avg(columns[key1])
    key2_avg = avg(columns[key2])
    total = 0

    for i in range(n):
        total += (columns[key1][i] - key1_avg) * (columns[key2][i] - key2_avg)

    return total / (n - 1)


def correlation(columns, key1, key2):
    return covariance(columns, key1, key2) / (std_dev(columns[key1]) * std_dev(columns[key2]))
