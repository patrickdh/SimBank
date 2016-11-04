#!/bin/bash
#bash script for 327 Assn3

LOG_FILE="myLogFile.log"
exePath="/Users/stuartbourne/documents/school/cmpe327/Assn3/Assignment3/src"
testingPath="/Users/stuartbourne/documents/school/cmpe327/Assn3/Assignment3/test/unit_testing/"

#loop through directories to access files
counter=0
echo "" > logFile.log
for parent in */ ; do
    cd $parent
    for child in */ ; do
        cd $child
        for file in *_etsfout.txt ; do
            #so we're going to run the input
            counter=$((counter+1))
            FileName=${file%????????????}
            echo $FileName
            DIFF=$(diff $file $file)
            if ["$DIFF" == ""]
            then
                echo "\nNo errors found in unit test ${counter}\n\n" > diffFile.diff
            else
                echo "Diff File: \n"$DIFF"\n\n" > diffFile.diff
            fi
            cat diffFile.diff >> fileName.txt
            
            rm -rf diffFile.diff
            
            #now write to log
            cat fileName.txt >> "${testingPath}/logFile.log"
            
            rm -rf fileName.txt
            rm -rf logFile.log
            
        done
        cd "${testingPath}$parent"
    done
    cd ..
done
