#include <iostream>
#include <fstream>
#include <cstdlib>

using namespace std;


#define THREAD_COUNT 11
#define RESULT_STR_SIZE 30
#define INPUT_FILE "IFF-8-7_KlevinskasG_L3_dat.txt"
#define OUTPUT_FILE "IFF-8-7_KlevinskasG_L3_rez.txt"

typedef struct Car_t {
    char model[10];
    int mileage;
    float fuelEcon;
} Car;


__device__ void mileageToStr(char *dest, int src) {
    int i = 0;
    src /= 1000;

    while (src != 0) {
        int digit = src % 10;
        dest[i++] = '0' + digit;
        src /= 10;
    }

    int start = 0;
    int end = i - 1;

    while (start < end) {
        char temp = dest[start];
        dest[start] = dest[end];
        dest[end] = temp;

        start++;
        end--;
    }

    dest[i++] = 'k';
    dest[i] = '\0';
}


__device__ int slen(const char *str) {
    int len = 0;

    while (str[len] != '\0')
        len++;

    return len;
}


__device__ char *getResultString(Car car) {
    char *result = (char *) malloc(RESULT_STR_SIZE);
    char mileage[10];
    char fuelEconRating;

    mileageToStr(mileage, car.mileage);

    if (car.fuelEcon > 10)
        fuelEconRating = 'E';
    else if (car.fuelEcon > 8)
        fuelEconRating = 'D';
    else if (car.fuelEcon > 6)
        fuelEconRating = 'C';
    else if (car.fuelEcon > 4)
        fuelEconRating = 'B';
    else
        fuelEconRating = 'A';

    int i = slen(car.model);

    memcpy(result, car.model, i);
    result[i++] = '-';
    memcpy(result + i, mileage, slen(mileage));
    i += slen(mileage);
    result[i++] = '-';
    result[i++] = fuelEconRating;
    result[i] = '\0';

    return result;
}


__global__ void getResult(Car *cars, int n, char *results, unsigned int *resultSize) {
    for (int i = threadIdx.x; i < n; i += THREAD_COUNT) {
        char *result = getResultString(cars[i]);

        if (result[0] > 'F') {
            unsigned int insertIndex = atomicInc_system(resultSize, n) * RESULT_STR_SIZE;
            memcpy(results + insertIndex, result, RESULT_STR_SIZE);
        }

        free(result);
    }
}


void writeToFile(char *result, unsigned int n) {
    ofstream out;
    out.open(OUTPUT_FILE);

    if (n == 0) {
        out << "Result array is empty" << endl;
        return;
    }

    for (int i = 0; i < n; i++) {
        out << result + (i * RESULT_STR_SIZE) << endl;
    }

    out << "Size: " << n << endl;
    out.close();
}


int main() {
    ifstream in;
    in.open(INPUT_FILE);

    int n;
    in >> n;

    Car* cars = new Car[n];

    for (int i = 0; i < n; i++) {
        Car car;
        in >> car.mileage >> car.fuelEcon >> car.model;
        cars[i] = car;
    }

    in.close();
    
    Car *deviceCars;
    char *deviceResults;
    unsigned int *deviceResultSize;

    cudaMalloc(&deviceCars, n * sizeof(Car));
    cudaMalloc(&deviceResults, RESULT_STR_SIZE * n * sizeof(char));
    cudaMalloc(&deviceResultSize, sizeof(unsigned int));

    cudaMemcpy(deviceCars, cars, n * sizeof(Car), cudaMemcpyHostToDevice);
    cudaMemset(deviceResultSize, 0, sizeof(unsigned int));

    getResult<<<1, THREAD_COUNT>>>(deviceCars, n, deviceResults, deviceResultSize);
    cudaDeviceSynchronize();

    unsigned int resultSize;
    cudaMemcpy(&resultSize, deviceResultSize, sizeof(unsigned int), cudaMemcpyDeviceToHost);

    if (resultSize == 0) {
        cout << "Result array is empty" << endl;
        writeToFile(nullptr, resultSize);
        return 0;
    }

    char* results = (char*) malloc(RESULT_STR_SIZE * resultSize * sizeof(char));

    for (int i = 0; i < resultSize; i++) {
        cudaMemcpy(
            results + (i * RESULT_STR_SIZE),
            deviceResults + (i * RESULT_STR_SIZE),
            RESULT_STR_SIZE * sizeof(char),
            cudaMemcpyDeviceToHost
        );
    }

    for (int i = 0; i < resultSize; i++) {
        cout << results + (i * RESULT_STR_SIZE) << endl;
    }

    cout << "Size: " << resultSize << endl;

    writeToFile(results, resultSize);

    delete[] cars;
    cudaFree(&deviceCars);
    cudaFree(&deviceResults);
    cudaFree(&deviceResultSize);
    free(results);

    return 0;
}
