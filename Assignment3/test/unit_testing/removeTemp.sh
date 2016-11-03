#!/bin/bash
#bash script for 327 Assn3

LOG_FILE="myLogFile.log"
exePath="/Users/stuartbourne/documents/school/cmpe327/Assn3/Assignment3/src"
testingPath="/Users/stuartbourne/documents/school/cmpe327/Assn3/Assignment3/test/unit_testing/"

#loop through directories to access files
for parent in */ ; do
    cd $parent
    for child in */ ; do
        cd $child
        for file in temp.txt ; do
            rm -rf temp.txt
        done
    
        cd "${testingPath}$parent"
    done
    cd ..
done
