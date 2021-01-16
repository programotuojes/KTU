import multiprocessing as mp
import time
import numpy as np
import utils
from calculations import M, N, psi, psi_prime_point

PROCESS_COUNT = 20
calc_times_mp = []
calc_times = []


def gradient_mp(stat_arr, new_arr, pool):
    pool_results = []

    tic = time.perf_counter()
    for i in range(M):
        pool_results.append(pool.apply_async(psi_prime_point, args=(stat_arr, new_arr, i)))

    g = [res.get() for res in pool_results]

    toc = time.perf_counter()
    calc_times_mp.append(toc - tic)

    return np.array(g) / np.linalg.norm(g)


def gradient(stat_arr, new_arr, _):
    g = []

    tic = time.perf_counter()
    for i in range(M):
        g.append(psi_prime_point(stat_arr, new_arr, i))
    toc = time.perf_counter()

    calc_times.append(toc - tic)

    return np.array(g) / np.linalg.norm(g)


def solve(gradient_function):
    pool = mp.Pool(processes=PROCESS_COUNT)
    stat_points = utils.read_points('data/points1.txt', N)
    new_points = utils.read_points('data/points2.txt', M)

    tic = time.perf_counter()

    initial_points = new_points
    step = 0.1
    iteration_count = 0
    old_psi = psi(stat_points, new_points)

    while step > 1e-3:
        iteration_count += 1

        old_points = new_points
        new_points = new_points - step * gradient_function(stat_points, new_points, pool)

        if utils.is_beyond_10(new_points):
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

    toc = time.perf_counter()
    pool.close()
    pool.join()

    # print(iteration_count, "iterations")
    return stat_points, initial_points, new_points, toc - tic


if __name__ == '__main__':
    coords = []

    for pc in range(1, 20):
        PROCESS_COUNT = pc

        given_points, initial_guess, result, total_time = solve(gradient)
        _, _, result_mp, total_time_mp = solve(gradient_mp)
        coords = [given_points, initial_guess, result]

        MULTIPLIER = 1000  # ms
        total_time *= MULTIPLIER
        total_time_mp *= MULTIPLIER
        gradient_avg = np.average(calc_times) * MULTIPLIER
        gradient_avg_mp = np.average(calc_times_mp) * MULTIPLIER

        print("{}, {:>10.2f}, {:>10.2f}, {:>10.2f}, {:>10.2f}".format(
            PROCESS_COUNT, total_time, total_time_mp, gradient_avg, gradient_avg_mp
        ))

        # print("Initial guess")
        # print(initial_guess)
        # print("Result (mp)")
        # print(result)
        # print("Result (non-mp)")

        # print("--- Non-MP Results ---")
        # print("{:>10.2f} s (total time)".format(total_time))
        # print("{:>10.2f} ms (avg gradient time)".format(gradient_avg))
        # print("--- Multiprocessing results ({} processes) ---".format(PROCESS_COUNT))
        # print("{:>10.2f} s (total time)".format(total_time_mp))
        # print("{:>10.2f} ms (avg gradient time)".format(gradient_avg_mp))
        # print(result_mp)

    utils.plot_results(coords[0], coords[1], coords[2])
