#!/bin/bash

IP="$1"

for i in {1..254}
do 
        ping -c 1 $IP.$i | grep ^64 | awk '{print $4}' | cut -d ":" -f 1 &
done

