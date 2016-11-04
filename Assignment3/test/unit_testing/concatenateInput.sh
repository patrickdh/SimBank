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
        pwd
        echo ""
        for file in *_in.txt ; do
            #so we're going to run the inputs
            touch temp.txt
            echo 'exit' > temp.txt
            cat temp.txt >> $file
        done
    
        cd "${testingPath}$parent"
    done
    cd ..
done
