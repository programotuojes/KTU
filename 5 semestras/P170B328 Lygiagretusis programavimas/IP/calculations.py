from sys import exit
import numpy as np

N = 3   # Given points
M = 15  # Chosen points
S = 1


# Average distance
def avg_dist(stat_arr, new_arr):
    total = 0
    amount = 0

    for point in np.concatenate((stat_arr, new_arr)):
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


def psi_prime_point(stat_arr, new_arr, index):
    return [
        psi_prime(stat_arr, new_arr, index, 0),
        psi_prime(stat_arr, new_arr, index, 1)
    ]
