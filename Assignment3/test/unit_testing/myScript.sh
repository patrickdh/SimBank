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
            #echo "input: ${input}"
            #echo""
        done
        for file in *_vaf.txt ; do
            vaf=${testingPath}${parent}${child}$file
            #echo "vaf: ${vaf}"
            #echo""
        done
        tsf=${input%???????}
        tsf="${tsf}_tsf.txt"
        #echo "tsf: ${tsf}"
        #echo ""
        #cd to the executable directory, run the file then pipe the output to an output directory
        
        cd ${exePath}
        output=${output%???????} output="${output}_generatedOut.txt"
        
        echo ${output}
        
        java SimBank_UI $vaf $tsf $input >> "output"/$output
        
        cd "${testingPath}/$parent"
    done
    cd ..
    break
done
