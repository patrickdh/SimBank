#!/bin/sh

FRONTENDDIR="/Users/stuartbourne/Documents/school/CMPE327/Project/Assignment6/Frontend/src"

BACKENDDIR="/Users/stuartbourne/Documents/school/CMPE327/Project/Assignment6/327BackOffice/src"

INPUTSDIR="/Users/stuartbourne/Documents/school/CMPE327/Project/Assignment6/Inputs"

cd $INPUTSDIR/$1
for sesh in Session*; do
    cd $FRONTENDDIR
    #vaf tsf commands
    echo ""
    echo ""
    echo ""
    echo ""
    java SimBank_UI ../../shared/validAccountsFile.txt _tsf.txt $INPUTSDIR/$1/$sesh
    echo "$INPUTSDIR/$1/$sesh"
done

#creat mtsf

cd $FRONTENDDIR/tsf
rm mstf.txt
for file in *; do 
    cat file >> mtsf.txt    
done

cd $BACKENDDIR
java BackOffice masterAccountsFile.txt ../../shared/mergedTransactionSummaryFile.txt
