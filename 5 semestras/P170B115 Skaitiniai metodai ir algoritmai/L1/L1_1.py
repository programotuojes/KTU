import matplotlib.pyplot as plt
import solve
from functions import *
from intervals import *


# ---- Plotting f(x) ---- #
plt.figure(0)
plt.title('Polynomial f(x)')

# Calculating x interval to plot
x_from = min(acc_lower(COEFF), -r(COEFF)) - 1
x_to = max(acc_upper(COEFF), r(COEFF)) + 1

# Plot setup
plt.ylim(x_from, x_to)
plt.axvline(linewidth=0.5, color='k')
plt.axhline(linewidth=0.5, color='k')
plt.grid(linewidth=0.2)

# Plotting rough interval estimate
plt.plot(-r(COEFF), 0, color='r', marker='>', markersize=7)
plt.plot(r(COEFF), 0, color='r', marker='<', markersize=7)

# Plotting accurate interval estimate
plt.plot(acc_lower(COEFF), 0, color='g', marker='>', markersize=5)
plt.plot(acc_upper(COEFF), 0, color='g', marker='<', markersize=5)

x1 = np.arange(x_from, x_to, 0.01)
y1 = f(x1)
plt.plot(x1, y1)

intervals = solve.get_all_intervals(f, acc_lower(COEFF), acc_upper(COEFF), 0.1)
for i, interval in enumerate(intervals):
    cor, it_cor = solve.chord(f, interval[0], interval[1])
    ntn, it_ntn = solve.newton(f, f_prime, interval[0])
    scn, it_scn = solve.scan(f, interval[0], interval[1])

    print('----- f(x) root #{} -----'.format(i + 1))
    print('Chord  = ', cor, ' iterations = ', it_cor)
    print('Newton = ', ntn, ' iterations = ', it_ntn)
    print('Scan   = ', scn, ' iterations = ', it_scn)
    print()

    plt.plot(ntn, 0, 'ro')

print('f(x) np.roots = {}'.format(np.roots(COEFF)), end='\n\n\n')


# ---- Plotting g(x) ---- #
plt.figure(1)
plt.title('Function g(x)')

x_from = -6 - 1
x_to = 6 + 1

# Plot setup
plt.ylim(-1, 1)
plt.axvline(linewidth=0.5, color='k')
plt.axhline(linewidth=0.5, color='k')
plt.grid(linewidth=0.2)

x1 = np.arange(x_from, x_to, 0.01)
y1 = g(x1)
plt.plot(x1, y1)

intervals = solve.get_all_intervals(g, x_from, x_to, 0.1)
for i, interval in enumerate(intervals):
    cor, it_cor = solve.chord(g, interval[0], interval[1])
    ntn, it_ntn = solve.newton(g, g_prime, interval[0])
    scn, it_scn = solve.scan(g, interval[0], interval[1])

    print('----- g(x) root #{} -----'.format(i + 1))
    print('Chord  = ', cor, ' iterations = ', it_cor)
    print('Newton = ', ntn, ' iterations = ', it_ntn)
    print('Scan   = ', scn, ' iterations = ', it_scn)
    print()

    plt.plot(ntn, 0, 'ro')


plt.show()
