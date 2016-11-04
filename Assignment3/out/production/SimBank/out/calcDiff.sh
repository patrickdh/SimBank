#!/bin/bash
#bash script to calculate the difference between etsf and tsf and etermout and termout
rm logFile.log
touch logFile.log

#Check the etsf and tsf files
cd etsf
for file in *_etsf.txt ; do
    TSFFILENAME=${file%?????????}
    TSFFILE="../tsf/${TSFFILENAME}_tsf.txt"
    DIFF=$(diff $file $TSFFILE)
    
    if ["$DIFF" = ""]
    then
        echo "no differences for file $file\n\n" >> "../logFile.log"
    else
        echo "Diff file for file $file: \n $DIFF\n\n" >> "../logFile.log"
    fi
    
done

