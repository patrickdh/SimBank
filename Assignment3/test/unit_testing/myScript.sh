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
        for file in *_in.txt ; do
            #so we're going to run the inputs
            output=$file
            input=${testingPath}${parent}${child}$file
            echo""
        done
        
        for file in *_vaf.txt ; do
            vaf=${testingPath}${parent}${child}$file
        done
        tsf=${input%???????}
        tsf="${tsf}_tsf.txt"
        #cd to the executable directory, run the file then pipe the output to an output directory
        
        cd ${exePath}
        output=${output%???????} output="${output}_expectedOut.txt"
        
        echo ${output}
        
        java SimBank_UI $vaf $tsf $input >> "${testingPath}"${parent}${child}/$output
        
        cd "${testingPath}/$parent"
    done
    cd ..
done
