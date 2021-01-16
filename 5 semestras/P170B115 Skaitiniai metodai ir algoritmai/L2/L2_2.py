import matplotlib.pyplot as plt
import numpy as np
import sympy
from matplotlib import cm


# ----- Solving -----
def newton(f, jacobian, xi):
    iterations = 0
    _xi = np.copy(xi)

    while max(np.abs(f(_xi))) > 1e-6:
        delta_x = np.linalg.solve(jacobian(_xi), np.negative(f(_xi)))
        _xi = np.add(_xi, delta_x)
        iterations += 1

    return _xi, iterations


# ----- Part 1 -----
x1, x2 = sympy.symbols('x1 x2')

fun1 = sympy.exp(-((x1 + 2) ** 2 + 2 * x2 ** 2) / 4) - 0.1
fun2 = x1 ** 2 * x2 ** 2 + x1 - 8

f1_diff_x1 = sympy.lambdify([x1, x2], fun1.diff(x1))
f1_diff_x2 = sympy.lambdify([x1, x2], fun1.diff(x2))
f2_diff_x1 = sympy.lambdify([x1, x2], fun2.diff(x1))
f2_diff_x2 = sympy.lambdify([x1, x2], fun2.diff(x2))

f1 = sympy.lambdify([x1, x2], fun1)
f2 = sympy.lambdify([x1, x2], fun2)


def f_arr(xi):
    return [
        f1(xi[0], xi[1]),
        f2(xi[0], xi[1])
    ]


def jacobian(xi):
    substitutions = [(x1, xi[0]), (x2, xi[1])]
    j = sympy.Matrix([[fun1, fun2]]).jacobian([x1, x2]).subs(substitutions)
    return np.array(j).astype(np.float64)


xi = [-6.0, 1.0]
newton_root1, iterations = newton(f_arr, jacobian, xi)
nsolve_root = sympy.nsolve((fun1, fun2), (x1, x2), xi)
print("----- Part 1 -----")
print("Iterations: ", iterations)
print("Root:       ", newton_root1)
print("nsolve root: [{}, {}]".format(nsolve_root[0], nsolve_root[1]))


# ----- Part 2 -----
x1, x2, x3, x4 = sympy.symbols('x1 x2 x3 x4')

fun1 = 5 * x1 + 4 * x2 - 2 * x3 + 22
fun2 = -5 * x3 ** 2 + 4 * x1 * x3 + 5
fun3 = -x3 ** 2 + x4 ** 3 + 2 * x2 * x4 + 1
fun4 = 3 * x1 - 12 * x2 + 3 * x3 - 3 * x4 - 63

f1 = sympy.lambdify([x1, x2, x3, x4], fun1)
f2 = sympy.lambdify([x1, x2, x3, x4], fun2)
f3 = sympy.lambdify([x1, x2, x3, x4], fun3)
f4 = sympy.lambdify([x1, x2, x3, x4], fun4)


def f_arr(xi):
    return [
        f1(xi[0], xi[1], xi[2], xi[3]),
        f2(xi[0], xi[1], xi[2], xi[3]),
        f3(xi[0], xi[1], xi[2], xi[3]),
        f4(xi[0], xi[1], xi[2], xi[3]),
    ]


def jacobian(xi):
    substitutions = [(x1, xi[0]), (x2, xi[1]), (x3, xi[2]), (x4, xi[3])]
    j = sympy.Matrix([[fun1, fun2, fun3, fun4]]).jacobian([x1, x2, x3, x4]).subs(substitutions)
    return np.array(j).astype(np.float64)


xi = [1.0, 1.0, 1.0, 1.0]
newton_root2, iterations = newton(f_arr, jacobian, xi)
nsolve_root = sympy.nsolve((fun1, fun2, fun3, fun4), (x1, x2, x3, x4), xi)
print("----- Part 2 -----")
print("Iterations: ", iterations)
print("Root:       ", newton_root2)
print("nsolve root: [{}, {}, {}, {}]".format(nsolve_root[0], nsolve_root[1], nsolve_root[2], nsolve_root[3]))


# ----- Plotting -----
X1 = np.arange(-7, 7, 0.1)
X2 = np.arange(-7, 7, 0.1)
XX1, XX2 = np.meshgrid(X1, X2)

Z1 = np.exp(-((XX1 + 2) ** 2 + 2 * XX2 ** 2) / 4) - 0.1
Z2 = XX1 ** 2 * XX2 ** 2 + XX1 - 8

fig = plt.figure()
ax = fig.gca(projection='3d')
ax.plot_surface(XX1, XX2, Z1, cmap=cm.coolwarm, alpha=0.5)
ax.plot_surface(XX1, XX2, np.zeros(np.shape(Z1)), antialiased=False, alpha=0.2)
ax.contour(X1, X2, Z1, levels=0, colors='red')

fig = plt.figure()
ax = fig.gca(projection='3d')
ax.plot_surface(XX1, XX2, Z2, cmap=cm.summer, antialiased=False, alpha=0.5)
ax.plot_surface(XX1, XX2, np.zeros(np.shape(Z1)), antialiased=False, alpha=0.2)
ax.contour(X1, X2, Z2, levels=0, colors='green')

fig = plt.figure()
ax = fig.gca()
ax.grid(color='#C0C0C0', linestyle='-', linewidth=0.5)
ax.contour(X1, X2, Z1, levels=0, colors='red')
ax.contour(X1, X2, Z2, levels=0, colors='green')
plt.plot(newton_root1[0], newton_root1[1], 'o')

plt.show()
