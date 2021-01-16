from sys import exit
import numpy as np


# Row 2 moved to row 1
A = [[6, -2, 3, 4],
     [0, 1, 2, 1],
     [0, 3, 4, -3],
     [0, -4, 3, 1]]
b = [-15, 2, 10, -2]

A_old = A
b_old = b

n = len(A)

for i in range(n):
    r1 = A[i][i]

    if abs(r1) < 1e-6:
        print('Leading element = 0, i = ', i)
        exit(1)

    for j in range(n):
        A[i][j] /= r1

    b[i] /= r1

    for j in range(n):
        if i != j:
            r2 = A[j][i]

            for k in range(i, n):
                A[j][k] -= r2 * A[i][k]

            b[j] -= r2 * b[i]


print("----- Calculated results -----")
print(b)

print("\n----- Check -----")
print("A * x =", np.matmul(A_old, b))
print("b =", b_old)

print("\n----- numpy solve() -----")
print(np.linalg.solve(A_old, b_old))
