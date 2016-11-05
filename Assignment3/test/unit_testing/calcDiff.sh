#!/bin/bash
#bash script for 327 Assn3

LOG_FILE="myLogFile.log"
exePath="/Users/stuartbourne/documents/school/cmpe327/Assn3/Assignment3/src/out/"
testingPath="/Users/stuartbourne/documents/school/cmpe327/Assn3/Assignment3/test/unit_testing/"

rm logFile.log
touch logFile.log

#loop through directories to access files
for parent in */ ; do
    cd $parent
    for child in */ ; do
        cd $child
        
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
            DIFF=$(diff -u --ignore-all-space $file $TSFFILE)
            
            if ["$DIFF" = ""]
            then
                echo "no differences for file   $file\n\n" >> "../logFile.log"
            else
                echo "Diff file for file $file: \n $DIFF\n\n" >> "../logFile.log"
            fi
        done

        for file in *_etermout.txt ; do
            TERMFILENAME=${file%?????????????}
            TERMFILE="${exePath}termout/${TERMFILENAME}_termout.txt"
            DIFF=$(diff -u --ignore-all-space $file $TERMFILE)

            if ["$DIFF" = ""]
            then
                echo "no differences for file $file\n\n" >> "../logFile.log"
            else
                echo "Terminal diff file for file $file: \n $DIFF\n\n" >> "../../logFile.log"
            fi
        done
        
        cd "${testingPath}/$parent"
        echo ""
    done
    cd ..
done
