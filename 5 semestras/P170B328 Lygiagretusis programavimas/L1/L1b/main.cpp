#include <vector>
#include <thread>
#include <algorithm>
#include <functional>
#include "Car.h"
#include "Monitor.h"
#include "ResultMonitor.h"
#include "ResCar.h"

using namespace std;

#define INPUT_FILE "IFF-8-7_KlevinskasG_L1b_dat_1.txt"
#define OUTPUT_FILE "IFF-8-7_KlevinskasG_L1b_dat_1.txt"

void work(Monitor<Car> &src, ResultMonitor<ResCar> &dest) {
    while (!src.done()) {
        Car car;
        ResCar resultCar;

        if (!src.pop(car))
            continue;

        resultCar.fun(car);

        int firstDigit = resultCar.result[0] - '0';
        if (firstDigit % 2 == 0)
            dest.push(resultCar);
    }
}

int main() {
    vector<Car> cars;
    ifstream in;
    in.open(INPUT_FILE);

    int n;
    in >> n;

    for (int i = 0; i < n; i++) {
        Car car;
        in >> car.mileage >> car.fuelEcon >> car.model;
        cars.push_back(car);
    }

    in.close();

    Monitor<Car> monitor(n / 2);
    ResultMonitor<ResCar> resultMonitor(n);

    #pragma omp parallel num_threads(n / 4 + 1)
    {
        if (omp_get_thread_num() != 0) {
            work(monitor, resultMonitor);
        }
        else {
            int i = 0;
            while (i < cars.size()) {
                if (monitor.push(cars[i]))
                    i++;
            }
            monitor.finished = true;
        }
    }

    resultMonitor.printResults(OUTPUT_FILE);

    return 0;
}
