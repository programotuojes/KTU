#!/bin/bash

if [ $# -eq 0 ]
then
    echo Error: no arguments provided
    exit 1
else
    echo -----
    echo $# arguments:
    echo $*
    echo -----

    if [ $# -eq 1 ]
    then
        if [ -d "$1" ]
        then
            echo $1 contains:
            ls "$1"
            echo -----
        elif [ -r "$1" ]
        then
            echo -n "Print file? [Y/n] "
            read yno

            case $yno in
                [Yy] | "" )
                    cat "$1";;
                * )
                    echo Ok, not printing file
            esac
        fi
    fi
fi
