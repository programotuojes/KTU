import matplotlib.pyplot as plt
import numpy as np


def is_beyond_10(points):
    for i in points:
        if abs(i[0]) > 10 or abs(i[1]) > 10:
            return True

    return False


def read_points(filename, amount):
    file = open(filename, 'r')
    points = []

    for i in range(amount):
        coords = file.readline().split(' ')
        points.append([
            float(coords[0]),
            float(coords[1]),
        ])

    return np.array(points)


def plot_results(given_points, initial_guess, result):
    plt.xlim(-11, 11)
    plt.ylim(-11, 11)
    plt.axvline(linewidth=0.5, color='k')
    plt.axhline(linewidth=0.5, color='k')
    plt.grid(linewidth=0.2)

    given_point = None
    initial_point = None
    result_point = None

    for p in given_points:
        given_point, = plt.plot(p[0], p[1], 'bo')

    for p in initial_guess:
        initial_point, = plt.plot(p[0], p[1], 'ko', markersize=3)

    for p in result:
        result_point, = plt.plot(p[0], p[1], 'ro')

    plt.legend(
        [given_point, initial_point, result_point],
        ["Given points", "Initial guess", "Result"]
    )

    plt.show()
