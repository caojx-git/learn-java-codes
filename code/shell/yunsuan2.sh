#!/bin/bash
#关系运算，关系运算只支持数组，不支持字符串，除非字符串的值是数组

a=10;
b=20;

if [ $a -eq $b ]
then
 echo "$a -eq $b: a 等于 b";
else
 echo "$a -eq $b: a 不等于 b";
fi

if [ $a -ne $b ]
then
 echo "$a -ne $b : a 不等于 b";
else
 echo "$a -ne $b : a 等于 b";
fi

if [ $a -gt $b ]
then
 echo "$a -gt $b : a 大于 b";
else
 echo "$a -gt $b : a 不大于 b";
fi

if [ $a -lt $b ]
then
   echo "$a -lt $b: a 小于 b"
else
   echo "$a -lt $b: a 不小于 b"
fi

if [ $a -ge $b ]
then
   echo "$a -ge $b: a 大于或等于 b"
else
   echo "$a -ge $b: a 小于 b"
fi

if [ $a -le $b ]
then
   echo "$a -le $b: a 小于或等于 b"
else
   echo "$a -le $b: a 大于 b"
fi
