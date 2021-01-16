#ifndef L1A_MONITOR_H
#define L1A_MONITOR_H

#include <omp.h>

using namespace std;

template<class T>
class Monitor {
public:
    explicit Monitor(int maxSize);

    ~Monitor();

    bool push(T src);

    bool pop(T &dest);

    bool done();

    bool finished = false;

protected:
    T *arr;
    int maxSize = 0;
    int length = 0;
};


template<class T>
Monitor<T>::Monitor(int maxSize) {
    this->maxSize = maxSize;
    arr = new T[maxSize];
}

template<class T>
Monitor<T>::~Monitor() {
    delete[] arr;
}

template<class T>
bool Monitor<T>::push(T src) {
    bool ret = true;

    #pragma omp critical (src_monitor)
    {
        if (length == maxSize)
            ret = false;
        else
            arr[length++] = src;
    }

    return ret;
}

template<class T>
bool Monitor<T>::pop(T &dest) {
    bool ret = true;

    #pragma omp critical (src_monitor)
    {
        if (length == 0)
            ret = false;
        else
            dest = arr[--length];
    }

    return ret;
}

template<class T>
bool Monitor<T>::done() {
    return finished && length == 0;
}

#endif // L1A_MONITOR_H
