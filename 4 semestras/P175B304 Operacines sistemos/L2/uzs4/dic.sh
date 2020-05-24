#!/bin/sh

dictionary='dict.txt'

if [ ! -e "$dictionary" ]
then
	echo "--- Dictionary ---" > "$dictionary"
fi

for word
do
	insert_word='true'

	while read -r line
	do
		if [ "$line" = "$word" ]
		then
			echo "Word $word already exists"
			insert_word='false'
			break
		fi
	done < "$dictionary"

	if [ $insert_word = 'true' ]
	then
		echo "$word" >> "$dictionary"
		echo "Added $word to the dictionary"
	fi
done
