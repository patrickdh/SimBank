#!/bin/sh

INPUTSDIR="/Users/stuartbourne/Documents/school/CMPE327/Project/Assignment6/Inputs"

cd $INPUTSDIR
for day in */ do
    dayScript $day
done