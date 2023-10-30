from copy import deepcopy

import calc


def __is_team_value_bad(value):
    return value != 'blue' and value != 'red'


def __is_numerical_value_bad(value):
    try:
        if int(value) < 0:
            return True
    except ValueError:
        return True

    return False


def is_value_bad(key, value):
    if key == 'won' or key == 'first_blood':
        return __is_team_value_bad(value)
    else:
        return __is_numerical_value_bad(value)


def get_frequencies(column):
    freq = {}

    for cell in column:
        freq[cell] = freq.get(cell, 0) + 1

    return freq


def get_fixed_columns(columns):
    result = deepcopy(columns)
    bad_rows = set()

    for key in result:
        for row_id, cell in enumerate(result[key]):
            if is_value_bad(key, cell):
                bad_rows.add(row_id)
            elif key != 'won' and key != 'first_blood':
                result[key][row_id] = int(result[key][row_id])

    for key in result:
        for row_id in reversed(range(len(result[key]))):
            if row_id in bad_rows:
                del result[key][row_id]

    return result


def get_fixed_column(columns, key):
    result = []

    for cell in columns[key]:
        if not is_value_bad(key, cell):
            if key != 'won' and key != 'first_blood':
                result.append(int(cell))
            else:
                result.append(cell)

    return result


def remove_outliers(columns, numerical):
    result = {}

    for key in columns:
        if key in numerical:
            median = calc.median(columns[key])
            q1 = calc.first_quartile(columns[key])
            q3 = calc.third_quartile(columns[key])
            iqr = q3 - q1

            result[key] = []

            for cell in columns[key]:
                if 1.5 * iqr + q3 >= cell >= 1.5 * iqr - q1:
                    result[key].append(cell)
                else:
                    result[key].append(median)
        else:
            result[key] = columns[key]

    return result
