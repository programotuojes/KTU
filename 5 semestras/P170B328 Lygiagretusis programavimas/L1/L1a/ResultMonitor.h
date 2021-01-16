#ifndef L1A_RESULT_MONITOR_H
#define L1A_RESULT_MONITOR_H

#include <fstream>
#include <iomanip>
#include "Monitor.h"

using namespace std;

template<class T>
class ResultMonitor : public Monitor<T> {
public:
    explicit ResultMonitor(int maxSize) : Monitor<T>(maxSize) {};

    void push(T src);

    void printResults(const string &filename);
};

template<class T>
void ResultMonitor<T>::push(T src) {
    unique_lock<mutex> guard(this->lock);

    if (this->length == this->maxSize)
        return;

    int insertIndex = this->length;

    for (int i = 0; i < this->length; i++) {
        if (stof(src.result) < stof(this->arr[i].result)) {
            insertIndex = i;
            break;
        }
    }

    for (int i = this->length; i > insertIndex; i--) {
        this->arr[i] = this->arr[i - 1];
    }

    this->arr[insertIndex] = src;
    this->length++;
}

template<class T>
void ResultMonitor<T>::printResults(const string &filename) {
    ofstream out;
    out.open(filename);

    if (this->length == 0) {
        out << "Table is empty" << endl;
        return;
    }

    out << "+-----------+----------+-------------+------------------+" << endl;
    out << "|    ODO    |   Econ   |    Model    |    Calculated    |" << endl;
    out << "+-----------+----------+-------------+------------------+" << endl;

    for (int i = 0; i < this->length; i++) {
        out << setprecision(1);
        out << fixed;
        // TODO decouple from ResCar
        out << "| " << setw(9) << this->arr[i].car.mileage
            << " | " << setw(8) << this->arr[i].car.fuelEcon
            << " | " << left << setw(11) << this->arr[i].car.model << right
            << " | " << setw(16) << this->arr[i].result << " |" << endl;
    }

    out << "+-----------+----------+-------------+------------------+" << endl;

    out.close();
}

#endif // L1A_RESULT_MONITOR_H
