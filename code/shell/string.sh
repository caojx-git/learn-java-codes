#!/bin/bash
string="runoob is great company"
#输出变量string的值
echo $string
echo ${string}
#获取变量string的长度
echo ${#string}
#提取子字符串的长度
echo ${string:1:4}
