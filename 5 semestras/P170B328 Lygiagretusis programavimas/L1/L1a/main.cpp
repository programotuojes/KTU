#include <vector>
#include <thread>
#include <algorithm>
#include <functional>
#include "Car.h"
#include "Monitor.h"
#include "ResultMonitor.h"
#include "ResCar.h"

using namespace std;

#define INPUT_FILE "IFF-8-7_KlevinskasG_L1a_dat_1.txt"
#define OUTPUT_FILE "IFF-8-7_KlevinskasG_L1a_rez_1.txt"

void work(Monitor<Car> &src, ResultMonitor<ResCar> &dest) {
    while (!src.done()) {
        Car car;
        ResCar resultCar;

        if (!src.pop(car))
            return;

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

    vector<thread> threads;
    threads.reserve(n / 4);

    Monitor<Car> monitor(n / 2);
    ResultMonitor<ResCar> resultMonitor(n);

    for (int i = 0; i < n / 4; i++) {
        threads.emplace_back(work, ref(monitor), ref(resultMonitor));
    }

    for (const Car &car : cars) {
        monitor.push(car);
    }
    monitor.finished = true;


    for_each(threads.begin(), threads.end(), mem_fn(&thread::join));
    resultMonitor.printResults(OUTPUT_FILE);

    return 0;
}
