#!/bin/sh

dir=`pwd`

function list_dirs {
    for file in *
    do
        echo *
        if [ -d "$dir/$file" ]
        then
            echo "--- $file is a directory"
            cd "$dir/$file"
            list_dirs
        fi
        echo $file
    done
}

list_dirs
