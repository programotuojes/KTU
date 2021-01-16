#ifndef L1A_RESCAR_H
#define L1A_RESCAR_H

#include <string>
#include "Car.h"

using namespace std;

class ResCar {
public:
    void fun(const Car &car);

    Car car;
    string result;
};

#endif // L1A_RESCAR_H
