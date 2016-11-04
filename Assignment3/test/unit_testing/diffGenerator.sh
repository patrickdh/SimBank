#!/bin/bash
#bash script for 327 Assn3

LOG_FILE="myLogFile.log"
exePath="/Users/stuartbourne/documents/school/cmpe327/Assn3/Assignment3/src"
testingPath="/Users/stuartbourne/documents/school/cmpe327/Assn3/Assignment3/test/unit_testing/"

#loop through directories to access files
counter=0
echo "" > logFile.log
cd $exePath
cd "out/tsf"
for file in *.txt ; do
    NEWFILE=${exePath:????}
    diff $file "$exePath/out/etsf/${NEWFILE}_etsf.txt" > "${file}_diff"
done