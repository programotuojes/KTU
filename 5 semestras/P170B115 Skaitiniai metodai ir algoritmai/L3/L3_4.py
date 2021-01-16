import numpy as np
import matplotlib.pyplot as plt


MONTHS = range(12)
TEMPERATURE = [-0.9, 6.0, 7.1, 12.6, 15.8, 20.6, 22.0, 20.5, 18.0, 9.9, 5.1, -0.6]


def g(num_approx):
    matrix = []

    for x in MONTHS:
        row = []

        for j in range(num_approx):
            row.append(x ** j)

        matrix.append(row)

    return matrix


def c(num_approx):
    g_matrix = g(num_approx)
    g_t = np.transpose(g_matrix)

    left = np.matmul(g_t, g_matrix)
    right = np.matmul(g_t, TEMPERATURE)

    return np.linalg.solve(left, right)


def approximate(coeff):
    x = np.arange(0, 11, 0.01)
    y = []

    for i in x:
        val = 0

        for p, j in enumerate(coeff):
            val += j * i ** p

        y.append(val)

    return x, y


def plot(num_approx):
    coeff = c(num_approx)
    print("------")
    print("Approximation order =", num_approx)
    print("c =", coeff)

    x_approx, y_approx = approximate(coeff)

    line, = plt.plot(x_approx, y_approx, linewidth=0.5)
    return line


plt.axhline(linewidth=0.5, color='k')
plt.grid(linewidth=0.2)
lines = []

for i in range(2, 6):
    line = plot(i)
    lines.append(line)

plt.plot(MONTHS, TEMPERATURE, 'ko', markersize=3)
plt.legend(lines, [
    "2nd order approximation",
    "3rd order approximation",
    "4th order approximation",
    "5th order approximation",
])
plt.show()
