#注释
#!/bin/bash
#-------------------
#shell 数组
#------------------
#定义数组,数组元素用空格隔开
array=(1 2 3 4 5 6)
#使用如下这种方式定义数组，下标可以不连续
array2[0]="a"
array2[1]="b"
array2[4]="c"

#读取数组
echo ${array[0]}
echo ${array2[0]}

#获取数组中的所有元素
echo ${array[@]}
echo ${array[*]}

#获取数组长度
echo ${#array[@]}
echo ${#array[*]}

