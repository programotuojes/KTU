#include <sstream>
#include "ResCar.h"

void ResCar::fun(const Car &car) {
    ostringstream ss;
    ss.precision(2);
    ss << fixed;

    int stringSum = 0;

    for (char c : car.model)
        stringSum += c;

    ss << (float) car.mileage * car.fuelEcon + (float) stringSum;

    this->car = car;
    this->result = ss.str();
}
