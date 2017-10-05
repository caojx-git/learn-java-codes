#!/bin/bash

#文件测试

file="/Users/caojx/code/learn/code/shell/test.sh";

if [ -r $file ]
then 
 echo "文件可读";
else
 echo "文件不可读";
fi

if [ -w $file ]
then 
 echo "文件可写";
else
 echo "文件不可写";
fi

if [ -x $file ]
then
   echo "文件可执行"
else
   echo "文件不可执行"
fi

if [ -f $file ]
then
 echo "文件为普通文件";
else
 echo "文件为特殊文件";
fi

if [ -d $file ]
then 
 echo "文件是个目录";
else
 echo "文件不是目录";
fi

if [ -s $file ]
then 
 echo "文件不为空";
else
 echo "文件为空";
fi

if [ -e $file ]
then 
 echo "文件存在";
else 
 echo "文件不存在";
fi

