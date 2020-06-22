#!/bin/bash

# Files
failedAttempts='failed_attempts.txt'
ips='ip_addresses.txt'
countedIps='counted_ips.txt'
output1='uzdA.txt'
output2='uzdB.txt'
output3='uzdC.txt'

if [ $# -ne 2 ]
then
	echo "Two arguments required. Exiting..."
	exit 255
fi

echo -n "Filtering failed attemps... "
grep "Failed password" /data/ld/ld2/studlog > $failedAttempts
echo "Done"

echo -n "Getting IP addresses... "
awk '{ if ($15 ~ /port/) print $14; else print $15; }' $failedAttempts > $ips
echo "Done"

echo "Sorting... "
sort $ips | uniq -c | sort -n > $countedIps
awk '{ print $1 " attempts from ip " $2 }' $countedIps > $output1
echo "First task printed to $output1"
awk -v min=$1 max=$2'{ if ($1 > min && $1 < max) print $1 " attempts from ip " $2 }' $countedIps > $output2
echo "Second task printed to $output2"

rm $failedAttempts $ips $countedIps
