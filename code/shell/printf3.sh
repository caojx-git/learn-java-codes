#!/bin/bash
 
# format-string为双引号
printf "%d %s\n" 1 "abc";

# 单引号与双引号效果一样 
printf '%d %s\n' 1 "abc"; 

# 没有引号也可以输出
printf %s abcdef;

# 格式只指定了一个参数，但多出的参数仍然会按照该格式输出，format-string 被重用
printf %s abc def;

printf "%s\n" abc def;

printf "%s %s %s\n" a b c d e f g h i j;

# 如果没有 arguments，那么 %s 用NULL代替，%d 用 0 代替
printf "%s and %d \n";
