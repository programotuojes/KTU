cmake_minimum_required(VERSION 3.18)
project(L1a)

set(CMAKE_CXX_STANDARD 14)

add_executable(L1a main.cpp Monitor.h Car.cpp Car.h ResCar.cpp ResCar.h ResultMonitor.h)

TARGET_LINK_LIBRARIES(L1a pthread)
