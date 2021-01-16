import matplotlib.pyplot as plt
import numpy as np


m = 5
v0 = 80
h0 = 5
k1 = 0.15
k2 = 0.6
g = 9.8

dt = 0.1


def f(v, k):
    return k / m * v * abs(v) + g


def euler_method(v, k):
    return dt * f(v, k)


def runge_kutta_method(v, k):
    v1 = v - dt / 2 * f(v, k)
    v2 = v - dt / 2 * f(v1, k)
    v3 = v - dt * f(v2, k)

    return dt / 6 * (f(v, k) + 2 * f(v1, k) + 2 * f(v2, k) + f(v3, k))


def solve(method):
    t_arr = [0]
    h_arr = [h0]
    v_arr = [v0]

    t = 0
    h = h0
    v = v0
    k = k1

    while h > 0:
        prev_v = v

        v -= method(v, k)
        h += dt * v
        t += dt

        v_arr.append(v)
        h_arr.append(h)
        t_arr.append(t)

        if np.sign(prev_v) != np.sign(v):
            k = k2

    return t_arr, h_arr, v_arr


def plot(axis, y_label, *plots):
    axis.axvline(linewidth=0.5, color='k')
    axis.axhline(linewidth=0.5, color='k')
    axis.grid(linewidth=0.2)
    axis.set_xlabel('t, s', fontsize=16)
    axis.set_ylabel(y_label, fontsize=16)

    lines = []
    names = []

    for i in plots:
        line, = axis.plot(i[0], i[1])
        lines.append(line)
        names.append(i[2])

    axis.legend(lines, names)


def print_result(title, t_arr, h_arr):
    print('----- {} -----'.format(title))
    max_h = max(h_arr)
    time = t_arr[h_arr.index(max_h)]
    print('Max height = {:.2f} m, time = {:.2f} s'.format(max_h, time))
    print('Landing time = {:.2f} s'.format(t_arr[-1]))


fig, axs = plt.subplots(1, 2)
fig.suptitle('Height and speed over time (dt = {})'.format(dt), fontsize=16)

euler_time, euler_h, euler_v = solve(euler_method)
runge_time, runge_h, runge_v = solve(runge_kutta_method)

dt = 0.0001
true_time, true_h, true_v = solve(runge_kutta_method)

plot(
    axs[0],
    'h, m',
    (euler_time, euler_h, 'Euler method'),
    (runge_time, runge_h, 'Runge-Kutta method'),
    (true_time, true_h, 'Small step'),
)
plot(
    axs[1],
    'v, m/s',
    (euler_time, euler_v, 'Euler method'),
    (runge_time, runge_v, 'Runge-Kutta method'),
    (true_time, true_v, 'Small step'),
)

print_result('Euler method', euler_time, euler_h)
print_result('Runge-Kutta method', runge_time, runge_h)
print_result('Runge-Kutta with dt = {}'.format(dt), true_time, true_h)

plt.show()
