#!/bin/sh
# 1. Darydamas uzduotis naudojuosi tik LD1 ir LD2 aprasais,
#    sistemos pagalbos sistema (man ir pan.), savo darytomis LD uzduotimis
# 2. Užduoti darau savarankiškai be treciuju asmenu pagalbos
# 3. Uzduoti koreguoju naudodamasis vienu kompiuteriu
#
# Gustas Klevinskas, sutinku

if [ $# -lt 1 ]
then
    echo '1a. Netinkamas argumentu kiekis'
    exit 254
fi

if [ ! -d $1 ]
then
    echo '1b. Pirmas argumentas – turi buti egzistuojantis katalogas'
    exit 254
fi

if [ $# -lt 2 ]
then
    echo '1c. Neivestas nei vienas failo pletinys'
    exit 254
fi

function unique_ext {
    ext=""

    for i
    do
        ext="$i\n$ext"
    done

    echo -en $ext | sort | uniq > tmp.txt   # TODO check if exists
    echo `cat tmp.txt`
}

function Pletiniai {
    echo `echo $(unique_ext $@) | wc -w`
}

folder=$1
shift
extensions=`unique_ext $@`
unique_count=`Pletiniai $@`
bad_count=`awk 'BEGIN { count = 0 } { if ($1 !~ /\.(.*)/) count++; } END {print count;}' tmp.txt`
good_ext=`awk '{ if ($1 ~ /\.(.*)/) print $1; }' tmp.txt`

echo '2. Argumentai pateikti teisingai'
echo "3a. Analizuojamas katalogas: $folder"
echo "3a. Analizuojami pletiniai: $extensions"
echo "3b. Skirtingi pletiniai: $unique_count"
echo "3c. Nekorektiski pletiniai: $bad_count"

rm tmp.txt
