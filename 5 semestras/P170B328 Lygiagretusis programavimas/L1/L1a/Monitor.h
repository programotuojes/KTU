#ifndef L1A_MONITOR_H
#define L1A_MONITOR_H

#include <mutex>
#include <condition_variable>

using namespace std;

template<class T>
class Monitor {
public:
    explicit Monitor(int maxSize);

    ~Monitor();

    virtual void push(T src);

    bool pop(T &dest);

    bool done();

    bool finished = false;

protected:
    T *arr;
    int length = 0;
    int maxSize = 0;

    mutex lock;
    condition_variable cv;
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
void Monitor<T>::push(T src) {
    unique_lock<mutex> guard(lock);
    cv.wait(guard, [&] { return length < maxSize; });

    arr[length++] = src;
    cv.notify_all();
}

template<class T>
bool Monitor<T>::pop(T &dest) {
    unique_lock<mutex> guard(lock);
    cv.wait(guard, [&] { return length > 0 || done(); });

    if (done())
        return false;

    dest = arr[--length];
    cv.notify_all();

    return true;
}

template<class T>
bool Monitor<T>::done() {
    return finished && length == 0;
}

#endif // L1A_MONITOR_H
