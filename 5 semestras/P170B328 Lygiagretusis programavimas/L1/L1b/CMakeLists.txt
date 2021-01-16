cmake_minimum_required(VERSION 3.18)
project(L1b)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++14 -fopenmp")

add_executable(L1b main.cpp Monitor.h Car.cpp Car.h ResCar.cpp ResCar.h ResultMonitor.h)
