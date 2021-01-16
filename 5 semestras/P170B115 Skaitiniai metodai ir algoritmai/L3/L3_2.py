import matplotlib.pyplot as plt
import numpy as np


TEMPERATURE = [-0.9, 6.0, 7.1, 12.6, 15.8, 20.6, 22.0, 20.5, 18.0, 9.9, 5.1, -0.6]


fig, axs = plt.subplots(1, 2, constrained_layout=True)
fig.suptitle('Temperature interpolation', fontsize=16)


def plot_functions(x_arr, y_arr, axis, title):
    axis.set_title(title)
    axis.axhline(linewidth=0.5, color='k')
    axis.grid(linewidth=0.2)

    axis.plot(x_arr, y_arr)
    axis.plot(range(12), TEMPERATURE, 'ko', markersize=3)


# ----- Part 1 -----
def x_matrix():
    matrix = []

    for i in range(12):
        x_row = []

        for j in range(12):
            x_row.append(i ** j)

        matrix.append(x_row)

    return matrix


def interpolate(a, x_arr):
    y_arr = 0
    power = 0

    for i in a:
        y_arr += i * x_arr ** power
        power += 1

    return y_arr


a_values = np.linalg.solve(x_matrix(), TEMPERATURE)
x_interpolated = np.arange(0, 12, 0.01)
y_interpolated = interpolate(a_values, x_interpolated)

inter_filter = y_interpolated > min(TEMPERATURE) - 1
y_interpolated = y_interpolated[inter_filter]
x_interpolated = x_interpolated[:len(y_interpolated)]

plot_functions(x_interpolated, y_interpolated, axs[0], 'Polynomial interpolation')


# ----- Part 2 -----
def d_matrix():
    temp_len = len(TEMPERATURE)
    matrix = np.zeros((temp_len - 2, temp_len))

    for i in range(temp_len - 2):
        matrix[i][i] = 1 / 6
        matrix[i][i + 1] = 2 / 3
        matrix[i][i + 2] = 1 / 6

    # Remove first and last column to make the matrix square
    square = []
    for i in matrix:
        square.append(i[1:len(i) - 1])

    return square


def y_vector():
    vector = []

    for i in range(len(TEMPERATURE) - 2):
        vector.append(TEMPERATURE[i + 2] - 2 * TEMPERATURE[i + 1] + TEMPERATURE[i])

    return vector


def calc_y(f_prime0, f_prime1, y0, y1, step):
    return (f_prime0 * step ** 2 / 2 -
            f_prime0 * step ** 3 / 6 +
            f_prime1 * step ** 3 / 6 +
            (y1 - y0) * step -
            f_prime0 * step / 3 -
            f_prime1 * step / 6 +
            y0)


def global_spline_interpolation(f_primes):
    x_interpol = []
    y_interpol = []
    month = 0

    for step in np.arange(0, 1, 0.01):
        x_interpol.append(month + step)
        y_interpol.append(calc_y(0, f_primes[0], TEMPERATURE[0], TEMPERATURE[1], step))

    month += 1

    for i in range(len(f_primes) - 1):
        for step in np.arange(0, 1, 0.01):
            x_interpol.append(month + step)
            y_interpol.append(
                f_primes[i] * step ** 2 / 2 -
                f_primes[i] * step ** 3 / 6 +
                f_primes[i + 1] * step ** 3 / 6 +
                (TEMPERATURE[i + 1 + 1] - TEMPERATURE[i + 1]) * step -
                f_primes[i] * step / 3 -
                f_primes[i + 1] * step / 6 +
                TEMPERATURE[i + 1]
            )

        month += 1

    for step in np.arange(0, 1, 0.01):
        x_interpol.append(month + step)
        y_interpol.append(calc_y(f_primes[len(f_primes) - 1], 0, TEMPERATURE[10], TEMPERATURE[11], step))

    return x_interpol, y_interpol


f_primes2 = np.linalg.solve(d_matrix(), y_vector())
x, y = global_spline_interpolation(f_primes2)

plot_functions(x, y, axs[1], 'Global spline interpolation')


plt.show()
