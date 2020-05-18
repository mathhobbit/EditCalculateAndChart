#!/bin/sh

HOME=/Users/sergey_nikitin/work/ECalcAndChart/build/bin

for file in `ls -1 $1`
do
 echo $file
 if [ -d $1/$file ]; then
   $HOME/clean_class.sh $1/$file
 else
     sfx=`echo $file|awk -F \. '{print $NF}'`
     if [ "$sfx"!="$file" ]; then
     if [ "$sfx" == "class" ]; then
         echo "removing $1/$file"
         rm $1/$file
     fi
     fi
 fi
done

