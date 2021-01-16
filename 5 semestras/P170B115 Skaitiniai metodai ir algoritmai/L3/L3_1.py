import numpy as np
import matplotlib.pyplot as plt


FROM = -3
TO = 3
POINTS = 10
DETAILED_STEP = 0.001


def f(x):
    return np.exp(-x ** 2) * np.cos(x ** 2) * (x - 3) * (x ** 2 + 3)


def fi(a, x):
    y = 0
    power = 0

    for i in a:
        y += i * x ** power
        power += 1

    return y


def x_matrix(x_val):
    matrix = []

    for i in x_val:
        x_row = []

        for j in range(POINTS):
            x_row.append(i ** j)

        matrix.append(x_row)

    return matrix


def chebyshev_x():
    x = []

    for i in range(POINTS):
        x.append((TO - FROM) / 2 * np.cos(np.pi * (2 * i + 1) / (2 * POINTS)) + (TO + FROM) / 2)

    return np.array(x)


def plot_functions(x_values, axis, title):
    # Continuous function x_file and y_file values
    x_fun = np.arange(FROM, TO, DETAILED_STEP)
    y_fun = f(x_fun)

    y_values = f(x_values)
    a_values = np.linalg.solve(x_matrix(x_values), y_values)

    # Interpolated function x_file and y_file values
    x_interpolated = np.arange(FROM, TO, DETAILED_STEP)
    y_interpolated = fi(a_values, x_interpolated)

    # Remove large y_file values
    inter_filter = y_interpolated < 10
    y_interpolated = y_interpolated[inter_filter]
    x_interpolated = x_interpolated[:len(y_interpolated)]

    # Calculating function differences
    x_diff = x_fun[:len(x_interpolated)]
    y_diff = abs(y_interpolated - y_fun[:len(y_interpolated)])

    continuous_function, = axis.plot(x_fun, y_fun)
    interpolated_function, = axis.plot(x_interpolated, y_interpolated)
    difference_function, = axis.plot(x_diff, y_diff, linewidth=0.2)

    for i in range(len(x_values)):
        axis.plot(x_values[i], y_values[i], 'ko', markersize=3)

    axis.set_title(title)
    axis.axvline(linewidth=0.5, color='k')
    axis.axhline(linewidth=0.5, color='k')
    axis.grid(linewidth=0.2)
    axis.legend(
        [continuous_function, interpolated_function, difference_function],
        ['Continuous function', 'Interpolated function', 'Difference']
    )


fig, axs = plt.subplots(1, 2, constrained_layout=True)
fig.suptitle('Polynomial interpolation', fontsize=16)

plot_functions(np.arange(FROM, TO, (TO - FROM) / POINTS), axs[0], 'Constant step')
plot_functions(chebyshev_x(), axs[1], 'Chebyshev step')

plt.show()
