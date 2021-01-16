import matplotlib.pyplot as plt
import numpy as np
import sympy

v0 = 50.0
m = 2.0
t1 = 3.0
v1 = 14.0
BETA = 1


def newton(x0):
    iterations = 0

    plt.plot([x0, x0], [0, fun(x0)], linewidth=2, c='r')

    while abs(fun(x0)) > 1e-10:
        x0old = x0
        fold = fun(x0)
        x0 = x0 - BETA * (fun(x0) / fun_prime(x0))
        plt.plot([x0old, x0], [fold, 0], linewidth=2, c='r')
        plt.plot([x0, x0], [0, fun(x0)], linewidth=2, c='r')
        iterations += 1

    return x0, iterations


c = sympy.Symbol('c')
f = v0 * sympy.exp(-(c * t1) / m) + (m * 9.8 * (sympy.exp(-(c * t1) / m) - 1)) / c - v1
f_prime = f.diff(c)

fun = sympy.lambdify(c, f)
fun_prime = sympy.lambdify(c, f_prime)

root, iterations = newton(0.15)
print('Root = ', root)
print('Iterations = ', iterations)

x1 = np.arange(0, 0.2, 0.001)
y1 = fun(x1)

plt.axhline(linewidth=0.5, color='k')
plt.grid(linewidth=0.2)
plt.plot(x1, y1)
plt.plot(root, 0, 'o')

plt.show()
