#!/bin/bash
#bash script for 327 Assn3

LOG_FILE="myLogFile.log"
exePath="/Users/stuartbourne/documents/school/cmpe327/Assn3/Assignment3/src/out/"
testingPath="/Users/stuartbourne/documents/school/cmpe327/Assn3/Assignment3/test/unit_testing/"

rm logFile.log
touch logFile.log
COUNTER=0
COUNTER2=0
DIFF1="FOOBAR"
DIFF2="FOOBAR"
#loop through directories to access files
for parent in */ ; do
    cd $parent
    for child in */ ; do
        cd $child
        COUNTER=$((COUNTER+1)) 
        echo "Running Test $COUNTER: ${PWD##*/}\n" >> "../../logFile.log"
        for file in *_etsf.txt ; do
            TSFFILENAME=${file%?????????}
            SECONDDIR=$(pwd)
            cd ${exePath}tsf 
            
            shopt -s nullglob
            for another in *"${TSFFILENAME}_tsf.txt"
            do
                TSFFILE="$exePath/tsf/$another"
                
            done
            
            cd $SECONDDIR 
            DIFF1=$(diff -u --ignore-all-space $file $TSFFILE)
            
            if ["$DIFF1" = ""]
            then
                echo "Transaction Summary File Test: Passed. \nNo differences for test ${file%????}\n\n" >> "../../logFile.log"
            else
            
                echo "Transaction Summary File Test: Failed. \nDiff file for test ${file%????}: \n $DIFF\n\n" >> "../../logFile.log"
            fi
        done

        for file in *_etermout.txt ; do
            TERMFILENAME=${file%?????????????}
            TERMFILE="${exePath}termout/${TERMFILENAME}_termout.txt"
            DIFF2=$(diff -u --ignore-all-space $file $TERMFILE)

            if ["$DIFF2" = ""]
            then
                echo "Terminal Output Test: Passed. \nNo differences for test ${file%????}\n\n" >> "../../logFile.log"
            else
                echo "Terminal Output Test: Failed. \nTerminal diff file for test ${file%????}: \n $DIFF\n\n" >> "../../logFile.log"
            fi
        done
        if [ $DIFF1 = $DIFF2 ]
        then
            COUNTER2=$((COUNTER2+1)) 
        fi
        cd "${testingPath}/$parent"
        echo ""
    done
    cd ..
done

cd $testingPath
echo "----------------------------------" >> "logfile.log"
if [ $COUNTER = $COUNTER2 ] 
then
    echo "TEST SUITE PASSED" >> "logFile.log"
else
    echo "TEST SUITE FAILED" >> "logFile.log"
fi
echo "$COUNTER2 of $COUNTER Tests Passed" >> "logFile.log"