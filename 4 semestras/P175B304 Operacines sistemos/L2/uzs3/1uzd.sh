#!/bin/bash

if [ $# -eq 2 ]
then
    if [ -f $1 -a -f $2 ]
    then
        user1=`stat -c %U $1`
        user2=`stat -c %U $2`

        if [ $user1 = $user2 ]
        then
            echo Owners match
            exit 0
        else
            echo Owners don\'t match
            exit 1
        fi
    else
        echo Error: arguments must be files
        exit 2
    fi
else
    echo Error: need two arguments
    exit 2
fi
