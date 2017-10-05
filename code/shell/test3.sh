#!/bin/bash

#test 文件测试

cd /Users/caojx/code/learn/code/shell;

if test -e test3.sh
then 
 echo "test3.sh文件存在";
else
 echo "test3.sh文件不存在";
fi
