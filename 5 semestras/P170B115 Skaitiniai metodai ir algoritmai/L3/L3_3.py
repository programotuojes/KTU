import numpy as np
import matplotlib.pyplot as plt


def read_file(point_num):
    x_arr = []
    y_arr = []

    file = open('country/coords.txt', 'r')
    lines = file.readlines()

    i = 1
    skip = int(len(lines) / point_num)

    for line in lines:
        if i % skip == 0:
            coords = line.split(' ')
            x_arr.append(int(coords[0]))
            y_arr.append(int(coords[1]))

        i += 1

    x_arr.append(x_arr[0])
    y_arr.append(y_arr[0])

    file.close()

    return x_arr, y_arr


def d_matrix(arr):
    arr_len = len(arr)
    matrix = np.zeros((arr_len, arr_len))

    for i in range(arr_len - 2):
        matrix[i][i] = 1 / 6
        matrix[i][i + 1] = 2 / 3
        matrix[i][i + 2] = 1 / 6

    matrix[arr_len - 2][0] = 1 / 3
    matrix[arr_len - 2][1] = 1 / 6
    matrix[arr_len - 2][arr_len - 2] = 1 / 6
    matrix[arr_len - 2][arr_len - 1] = 1 / 3

    matrix[arr_len - 1][0] = 1
    matrix[arr_len - 1][arr_len - 1] = -1

    return matrix


def y_vector(arr):
    n = len(arr)
    vector = []

    for i in range(n - 2):
        vector.append(arr[i] - 2 * arr[i + 1] + arr[i + 2])

    vector.append(arr[1] - arr[0] - arr[n - 1] + arr[n - 2])
    vector.append(0.0)

    return vector


def calc_y(f_prime0, f_prime1, y0, y1, step):
    return (f_prime0 * step ** 2 / 2 -
            f_prime0 * step ** 3 / 6 +
            f_prime1 * step ** 3 / 6 +
            (y1 - y0) * step -
            f_prime0 * step / 3 -
            f_prime1 * step / 6 +
            y0)


def global_spline_interpolation(arr, f_primes):
    interpol_values = []

    for i in range(len(arr) - 1):
        for step in np.arange(0, 1, 0.01):
            interpol_values.append(calc_y(
                f_primes[i],
                f_primes[i + 1],
                arr[i],
                arr[i + 1],
                step,
            ))

    return interpol_values


x_full, y_full = read_file(847)
full_outline, = plt.plot(x_full, y_full, 'r', linewidth=0.5)


x, y = read_file(100)
x_primes = np.linalg.solve(d_matrix(x), y_vector(x))
y_primes = np.linalg.solve(d_matrix(y), y_vector(y))
interpolated_x = global_spline_interpolation(x, x_primes)
interpolated_y = global_spline_interpolation(y, y_primes)

interpolated_outline, = plt.plot(interpolated_x, interpolated_y)
interpolation_points, = plt.plot(x, y, 'ko', markersize=3)

plt.legend(
    [full_outline, interpolated_outline, interpolation_points],
    ['Original outline', 'Interpolated outline', 'Interpolation points']
)

plt.axis('off')
plt.show()
