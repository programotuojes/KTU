import numpy as np
import random
from sys import exit
import matplotlib.pyplot as plt


N = 3
M = 3
S = 1


def generate_points(amount):
    points = []

    for i in range(amount):
        points.append([random.uniform(-10, 10), random.uniform(-10, 10)])

    return np.array(points)


# Average distance
def avg_dist(stat_arr, new_arr):
    total = 0
    amount = 0

    for point in stat_arr + new_arr:
        total += np.sqrt(point[0] ** 2 + point[1] ** 2)
        amount += 1

    return total / amount


# Distance between stationary and new points
def v1(stat_arr, new_arr, d):
    total = 0

    for new_point in new_arr:
        for stat_point in stat_arr:
            total += abs(np.sqrt((new_point[0] - stat_point[0]) ** 2 + (new_point[1] - stat_point[1]) ** 2) - d)

    return total


# Distance between new points
def v2(new_arr, d):
    total = 0

    for i in range(M):
        for j in range(i + 1):
            total += abs(np.sqrt((new_arr[i][0] - new_arr[j][0]) ** 2 + (new_arr[i][1] - new_arr[j][1]) ** 2) - d)

    return total


# Distance from center
def v3(new_arr):
    total = 0

    for point in new_arr:
        total += abs(np.sqrt(point[0] ** 2 + point[1] ** 2) - S)

    return total


def psi(stat_arr, new_arr):
    d = avg_dist(stat_arr, new_arr)

    return v1(stat_arr, new_arr, d) + v2(new_arr, d) + v3(new_arr)


def psi_prime(stat_arr, new_arr, i, j):
    h = 0.001
    increased_arr = np.copy(new_arr)
    increased_arr[i][j] += h

    psi1 = psi(stat_arr, new_arr)
    psi2 = psi(stat_arr, increased_arr)

    if psi2 - psi1 == 0:
        print("Derivative is 0")
        exit(1)

    return (psi2 - psi1) / h


def gradient(stat_arr, new_arr):
    g = []

    for i in range(M):
        g.append([
            psi_prime(stat_arr, new_arr, i, 0),
            psi_prime(stat_arr, new_arr, i, 1)
        ])

    return np.array(g) / np.linalg.norm(g)


def is_beyond_10(points):
    for i in points:
        if abs(i[0]) > 10 or abs(i[1]) > 10:
            return True

    return False


def solve():
    stat_points = generate_points(N)
    new_points = generate_points(M)

    initial_points = new_points
    step = 0.1
    iteration_count = 0
    old_psi = psi(stat_points, new_points)

    while step > 1e-3:
        iteration_count += 1

        old_points = new_points
        new_points = new_points - step * gradient(stat_points, new_points)

        if is_beyond_10(new_points):
            print("New points beyond 10")
            print(iteration_count, "iterations")
            return stat_points, initial_points, old_points

        new_psi = psi(stat_points, new_points)

        if new_psi > old_psi:
            step /= 5
            new_points = old_points
        else:
            step += step * 0.05

        old_psi = new_psi

    print(iteration_count, "iterations")
    return stat_points, initial_points, new_points


given_points, initial_guess, result = solve()

print("Initial guess")
print(initial_guess)
print("Result")
print(result)

plt.xlim(-11, 11)
plt.ylim(-11, 11)
plt.axvline(linewidth=0.5, color='k')
plt.axhline(linewidth=0.5, color='k')
plt.grid(linewidth=0.2)

given_point = None
initial_point = None
result_point = None

for point in given_points:
    given_point, = plt.plot(point[0], point[1], 'bo')

for point in initial_guess:
    initial_point, = plt.plot(point[0], point[1], 'ko', markersize=3)

for point in result:
    result_point, = plt.plot(point[0], point[1], 'ro')


plt.legend(
    [given_point, initial_point, result_point],
    ["Given points", "Initial guess", "Result"]
)

plt.show()
