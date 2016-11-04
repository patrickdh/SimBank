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
        done
        
        for file in *_vaf.txt ; do
            vaf=${testingPath}${parent}${child}$file
        done
        
        #cd to the executable directory, run the file then pipe the output to an output directory
        
        tsf="tsf.txt"
        cd ${exePath}
        output=${output%???????}
        
        echo "output : $output"
        
        javac SimBank_UI.java
        java SimBank_UI $vaf $tsf $input > "out/termout/${output}_termout.txt"
        
        #get the copy of the tsf and save it
        cd "$exePath/out/tsf"
        cp $tsf "$output.txt" 
        cd "${testingPath}/$parent"
        echo ""
    done
    cd ..
done
