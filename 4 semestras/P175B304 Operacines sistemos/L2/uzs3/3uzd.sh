#!/bin/bash

if [ $# -ne 2 ]
then
    echo Error: 2 arguments required
    exit 1
fi

if [ ! -d "$1" ]
then
    echo Error: first argument must be a directory
    exit 1
fi

if [ ! -f "$2" ]
then
    echo Error: second argument must be a directory
    exit 2
fi

mv "$2" "$1" || cp "$2" "$1"
