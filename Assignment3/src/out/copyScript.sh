#!/bin/bash
#bash script for copying files

mkdir etermout
cd termout

for file in *_termout.txt ; do
    FILENAME=${file:????????????}
    cp $file "../etermout/${FILENAME}_etermout.txt"
done
