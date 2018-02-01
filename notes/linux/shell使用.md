#shell的使用

[TOC]

Shell 是一个用 C 语言编写的程序，它是用户使用 Linux 的桥梁。Shell 既是一种命令语言，又是一种程序设计语言。  
Shell 是指一种应用程序，这个应用程序提供了一个界面，用户通过这个界面访问操作系统内核的服务。    
提示：文章主要参考自[http://www.runoob.com/linux/linux-shell.html](http://www.runoob.com/linux/linux-shell.html) 

## 一、shell 环境
Shell 编程跟 java、php 编程一样，只要有一个能编写代码的文本编辑器和一个能解释执行的脚本解释器就可以了。
- Linux 的 Shell 种类众多，常见的有：
- Bourne Shell（/usr/bin/sh或/bin/sh）
- Bourne Again Shell（/bin/bash）
- C Shell（/usr/bin/csh）
- K Shell（/usr/bin/ksh）
- Shell for Root（/sbin/sh

我们这里主要使用Bourne Again Shell（bash）


## 二、开始编写shell
### 2.1 helloworld.sh
注意：编写shell的时候，shell程序的第一行必须有如下声明
 - \#!/bin/sh   
  符号#!用来告诉系统它后面的参数是用来执行该文件的程序。在这个例子中我们使用/bin/sh来执行程序。

1 helloworl.sh
```shell
#!bin/sh
echo 'hello world'
```

\#! 是一个约定的标记，它告诉系统这个脚本需要什么解释器来执行，即使用哪一种 shell。
echo 命令用于向窗口输出文本

运行结果:
```text
caojx$ chmod +x helloworld.sh  --添加可执行权限
caojx$ ./helloworld.sh  --执行helloworld.sh脚本
hello world!
```
### 2.2 shell 变量

>变量类型

运行shell时，会同时存在三种变量：  
- 局部变量 局部变量在脚本或命令中定义，仅在当前shell实例中有效，其他shell启动的程序不能访问局部变量。
- 环境变量 所有的程序，包括shell启动的程序，都能访问环境变量，有些程序需要环境变量来保证其正常运行。必要的时候shell脚本也可以定义环境变量。
- shell变量 shell变量是由shell程序设置的特殊变量。shell变量中有一部分是环境变量，有一部分是局部变量，这些变量保证了shell的正常运行

>定义变量

定义变量，定义变量时，变量名不加美元符号（$）
```text
your_name="runoob.com"
```
注意，变量名和等号之间不能有空格，这可能和你熟悉的所有编程语言都不一样。同时，变量名的命名须遵循如下规则：
- 首个字符必须为字母（a-z，A-Z）。
- 中间不能有空格，可以使用下划线（_）。
- 不能使用标点符号。
- 不能使用bash里的关键字（可用help命令查看保留关键字）
- 变量内容若有空格符可以使用双引号『"』或单引号『'』将变量内容结合起来，但是
  - 双引号内的特殊字符如$等，可以保持原本的特性，如下：  
    var="lang is $LANG" 则echo $var可得lang is zh_TW.UTF-8  
  - 单引号内的特俗字符则仅为一般字符（纯文本），如下：  
    var='lang is $LANG' 则echo $var可得lang is $LANG  

除了显式地直接赋值，还可以用语句给变量赋值，如：
```text
for file in `ls /etc`
```
以上语句将 /etc 下目录的文件名循环出来。

>使用shell变量

使用一个定义过的变量，只要在变量名前面加美元符号即可，如：  
```text
your_name="qinjx"
echo $your_name
echo ${your_name} 
```
变量名外面的花括号是可选的，加不加都行，加花括号是为了帮助解释器识别变量的边界，比如下面这种情况：
```text
for skill in Ada Coffe Action Java; do
    echo "I am good at ${skill}Script"
done
```

如果不给skill变量加花括号，写成echo "I am good at $skillScript"，解释器就会把$skillScript当成一个变量（其值为空），代码执行结果就不是我们期望的样子了

>只读变量

使用 readonly 命令可以将变量定义为只读变量，只读变量的值不能被改变。
下面的例子尝试更改只读变量，结果报错：  
1.readonly.sh
```shell
#!/bin/bash
myUrl="http://www.w3cschool.cc"
readonly myUrl
myUrl="http://www.runoob.com"
```
运行结果：
```text
caojx$ chmod +x readonly.sh 
caojx$ ./readonly.sh 
./readonly.sh: line 4: myUrl: readonly variable
```

>删除变量

使用 unset 命令可以删除变量。语法：  
unset variable_name  
变量被删除后不能再次使用。unset 命令不能删除只读变量。  
1.unset.sh
```shell
#!/bin/sh
myUrl="http://www.runoob.com"
unset myUrl
echo $myUrl
```

以上实例执行将没有任何输出。

### 2.3 shell字符串

 字符串是shell编程中最常用最有用的数据类型（除了数字和字符串，也没啥其它类型好用了），字符串可以用单引号，也可以用双引号，也可以不用引号。
 >单引号
 ```shell
 str='this is a string'
 ```
 单引号字符串的限制：
 - 单引号里的任何字符都会原样输出，单引号字符串中的变量是无效的；
 - 单引号字串中不能出现单引号（对单引号使用转义符后也不行）。

 >双引号

 ```shell
 your_name='qinjx'
 str="Hello, I know your are \"$your_name\"! \n"
 ```
 双引号的优点：
 - 双引号里可以有变量
 - 双引号里可以出现转义字符

 >拼接字符串

 ```shell
 your_name="qinjx"
 greeting="hello, "$your_name" !"
 greeting_1="hello, ${your_name} !"
 echo $greeting $greeting_1
 ```

 >获取字符串长度
 ```shell
 string="abcd"
 echo ${#string} #输出 4
 ```

 >提取子字符串

 以下实例从字符串第 2 个字符开始截取 4 个字符：
 ```shell
  string="runoob is a great site"
  echo ${string:1:4} # 输出 unoo
 ```

 1.string.sh
 ```shell
 #!/bin/bash
 string="runoob is great company"
 #输出变量string的值
 echo $string
 echo ${string}
 #获取变量string的长度
 echo ${#string}
 #提取子字符串的长度
 echo ${string:1:4}
 ```
运行结果
```text
runoob is great company
runoob is great company
23
unoo
```

### 2.4 shell数组
bash支持一维数组（不支持多维数组），并且没有限定数组的大小。  
与大部分编程语言类似，数组元素的下标由0开始。    
类似与C语言，数组元素的下标由0开始编号。获取数组中的元素要利用下标，下标可以是整数或算术表达式，其值应大于或等于0。  

>定义数组

在Shell中，用括号来表示数组，数组元素用"空格"符号分割开。定义数组的一般形式为：
```shell
数组名=(值1 值2 ... 值n)
比如：
array_name=(value0 value1 value2 value3)
或单独定义数组的各个分量
array_name[0]=value0
array_name[1]=value1
array_name[n]=valuen
```
注意：可以不使用连续的下标，而且下标的范围没有限制。

>读取数组

读取数组元素值的一般格式是：
```shell
 ${数组名[下标]}
 例如：
 valuen=${array_name[n]}
 使用@符号可以获取数组中的所有元素，例如：
 echo ${array_name[@]}
```

>获取数组的长度

获取数组长度的方法与获取字符串长度的方法相同，例如：
```shell
 # 取得数组元素的个数
 length=${#array_name[@]}
 # 或者
 length=${#array_name[*]}
 # 取得数组单个元素的长度
 lengthn=${#array_name[n]}
```

1.array.sh
```shell
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
```

运行结果：
```text
1
a
1 2 3 4 5 6
1 2 3 4 5 6
6
6
```

### 2.5 shell注释
以"#"开头的行就是注释，会被解释器忽略，像如下所示
```shell
#--------------------------------------------
# 这是一个注释
# author：菜鸟教程
# site：www.runoob.com
# slogan：学的不仅是技术，更是梦想！
#--------------------------------------------
##### 用户配置区 开始 #####
#
#
# 这里可以添加脚本描述信息
# 
#
##### 用户配置区 结束  #####
```

## 三、shell传递参数

我们可以在执行 Shell 脚本时，向脚本传递参数，脚本内获取参数的格式为：$n。n 代表一个数字，1 为执行脚本的第一个参数，2 为执行脚本的第二个参数，以此类推……  
实例  
以下实例我们向脚本传递三个参数，并分别输出，其中 $0 为执行的文件名：  
1.param.sh
```shell
#!/bin/bash
echo "shell 传递参数实例！";
echo "执行的文件名：$0";
echo "第一个参数为：$1";
echo "第二个参数为：$2";
echo "第三个参数为：$3";
```
运行结果
```text
hellox:shell caojx$ ./param.sh 1 2 3
shell 传递参数实例!
执行的文件名:./param.sh
第一个参数为:1
第二个参数为:2
第三个参数为:3
```

其他参数说明：

| 参数处理 | 说明                                       |
| ---- | :--------------------------------------- |
| $#   | 传递到脚本的参数个数                               |
| $*   | 以一个单字符串显示所有向脚本传递的参数。<br>如"$*"用「"」括起来的情况、以"$1 $2 … $n"的形式输出所有参数。 |
| $$   | 脚本运行的当前进程ID号                             |
| $!   | 后台运行的最后一个进程的ID号                          |
| $@   | 与$*相同，但是使用时加引号，并在引号中返回每个参数。<br>如"$@"用「"」括起来的情况、以"$1" "$2" … "$n" 的形式输出所有参数。 |
| $-   | 显示Shell使用的当前选项，与set命令功能相同。               |
| $?   | 显示最后命令的退出状态。0表示没有错误，其他任何值表明有错误。          |

2.param2.sh
```shell
#!/bin/bash

echo "shell 参数传递实例！";
echo "第一个参数为：$1";
echo "参数格式为：$#"；
echo "传递的参数作为一个字符串显示：$*";
```

运行结果:
```text
hellox:shell caojx$ ./param2.sh 1 2 3
shell 参数传递实例！
第一个参数为：1
参数格式为：3；
传递的参数作为一个字符串显示：1 2 3
```

注意：$* 与 $@ 区别：
- 相同点：都是引用所有参数。
- 不同点：只有在双引号中体现出来。假设在脚本运行时写了三个参数 1、2、3，，则 " * " 等价于 "1 2 3"（传递了一个参数），而 "@" 等价于 "1" "2" "3"（传递了三个参数）。

## 四、shell基本运算符

Shell 和其他编程语言一样，支持多种运算符，包括：
- 算数运算符
- 关系运算符
- 布尔运算符
- 字符串运算符
- 文件测试运算符  
  原生bash不支持简单的数学运算，但是可以通过其他命令来实现，例如 awk 和 expr，expr 最常用。 
  expr 是一款表达式计算工具，使用它能完成表达式的求值操作。 

1.add.sh
```shell
#!/bin/bash

val=`expr 2 + 2`;
echo "两数之和为 : $val";
```

运行结果:
```text
caojx$ ./add.sh 
两个数之和为:4
```
注意：
- 表达式和运算符之间要有空格，例如 2+2 是不对的，必须写成 2 + 2，这与我们熟悉的大多数编程语言不一样。
- 完整的表达式要被 ` ` 包含，注意这个字符不是常用的单引号，在 Esc 键下边

>1.算术运算符


下表列出了常用的算术运算符，假定变量 a 为 10，变量 b 为 20：

| 运算符  | 说明                        | 举例                               |
| ---- | :------------------------ | :------------------------------- |
| +    | 加法                        | `expr $a + $b` 结果为 30。           |
| -    | 减法                        | `expr $a - $b` 结果为 -10。          |
| *    | 乘法                        | `expr $a \* $b` 结果为  200，乘法需要\转意 |
| /    | 除法                        | `expr $b / $a` 结果为 2。            |
| %    | 取余                        | `expr $b % $a` 结果为 0             |
| =    | 赋值                        | a=$b 将把变量 b 的值赋给 a。              |
| ==   | 相等。用于比较两个数字，相同则返回 true    | [ $a == $b ] 返回 false。           |
| !=   | 不相等。用于比较两个数字，不相同则返回 true。 | [ $a != $b ] 返回 true。            |
注意：条件表达式要放在方括号之间，并且要有空格，例如: [$a==$b] 是错误的，必须写成 [ $a == $b ]，即[空格 变量 空格==空格 变量 空格]

1.yunsuan.sh
```shell

val=`expr $b % $a`
echo "b % a : $val"

if [ $a == $b ]  # 注意 then需要写在if的下边一行
then
   echo "a 等于 b"
fi
if [ $a != $b ]
then
   echo "a 不等于 b"
fi
```

运行结果：
```text
a + b : 30
a - b : -10
a * b : 200
b / a : 2
b % a : 0
a 不等于 b
```

注意：
乘号(*)前边必须加反斜杠(\)才能实现乘法运算；
if...then...fi 是条件语句，后续将会讲解。

>2.关系运算符

关系运算符只支持数字，不支持字符串，除非字符串的值是数字。
下表列出了常用的关系运算符，假定变量 a 为 10，变量 b 为 20：

| 运算符  | 说明                            | 举例                      |
| ---- | :---------------------------- | :---------------------- |
| -eq  | 检测两个数是否相等，相等返回 true。          | [ $a -eq $b ] 返回 false  |
| -ne  | 检测两个数是否相等，不相等返回 true。         | [ $a -ne $b ] 返回 true。  |
| -gt  | 检测左边的数是否大于右边的，如果是，则返回 true。   | [ $a -gt $b ] 返回 false。 |
| -lt  | 检测左边的数是否小于右边的，如果是，则返回 true。   | [ $a -lt $b ] 返回 true。  |
| -ge  | 检测左边的数是否大于等于右边的，如果是，则返回 true。 | $a -ge $b ] 返回 false    |
| -le  | 检测左边的数是否小于等于右边的，如果是，则返回 true。 | [ $a -le $b ] 返回 true。  |

1.yunsuan2.sh
```shell
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
```

运算结果:
```text
caojx$ ./yunsuan2.sh 
10 -eq 20: a 不等于 b
10 -ne 20 : a 不等于 b
10 -gt 20 : a 不大于 b
10 -lt 20: a 小于 b
10 -ge 20: a 小于 b
10 -le 20: a 小于或等于 b
```

>3.布尔运算符

下表列出了常用的布尔运算符，假定变量 a 为 10，变量 b 为 20：

| 运算符  | 说明                                 | 举例                                    |
| ---- | :--------------------------------- | :------------------------------------ |
| !    | 非运算，表达式为 true 则返回 false，否则返回 true。 | [ ! false ] 返回 true。                  |
| -o   | 或运算，有一个表达式为 true 则返回 true。         | [ $a -lt 20 -o $b -gt 100 ] 返回 true。  |
| -a   | 与运算，两个表达式都为 true 才返回 true。         | [ $a -lt 20 -a $b -gt 100 ] 返回 false。 |

1.yunsuan3.sh
```shell
#!/bin/bash
#布尔运算
a=10
b=20

if [ $a != $b ]
then
   echo "$a != $b : a 不等于 b"
else
   echo "$a != $b: a 等于 b"
fi
if [ $a -lt 100 -a $b -gt 15 ]
then
   echo "$a 小于 100 且 $b 大于 15 : 返回 true"
else
   echo "$a 小于 100 且 $b 大于 15 : 返回 false"
fi
if [ $a -lt 100 -o $b -gt 100 ]
then
   echo "$a 小于 100 或 $b 大于 100 : 返回 true"
else
   echo "$a 小于 100 或 $b 大于 100 : 返回 false"
fi
if [ $a -lt 5 -o $b -gt 100 ]
then
   echo "$a 小于 5 或 $b 大于 100 : 返回 true"
else
   echo "$a 小于 5 或 $b 大于 100 : 返回 false"
fi
```

运算结果:
```text
caojx$ ./yunsuan3.sh 
10 != 20 : a 不等于 b
10 小于 100 且 20 大于 15 : 返回 true
10 小于 100 或 20 大于 100 : 返回 true
10 小于 5 或 20 大于 100 : 返回 false
```

>4.逻辑运算符

以下介绍 Shell 的逻辑运算符，假定变量 a 为 10，变量 b 为 20:

| 运算符  | 说明      | 举例                                      |
| ---- | :------ | --------------------------------------- |
| &&   | 逻辑的 AND | [[ $a -lt 100 && $b -gt 100 ]] 返回 false |
| 双竖线  | 逻辑的 OR  | [[ $a -lt 100 双竖线 $b -gt 100 ]] 返回 true |

注意：做逻辑运算的时候，需要双方括号[[]]

1.yunsuan4.sh
```shell
#!/bin/bash

#逻辑运算符

a=10;
b=20;

if [[ $a -lt 100 && $b -gt 100 ]] 
then
 echo "返回true";
else
 echo "返回false";
fi

if [[ $a -lt 100 || $b -gt 100 ]]
then
 echo "返回true";
else
 echo "返回false";
fi
```
运算结果:
```text
caojx$ ./yunsuan4.sh 
返回false
返回true
```
>5.字符串运算符

下表列出了常用的字符串运算符，假定变量 a 为 "abc"，变量 b 为 "efg"：

| 运算符  | 说明                      | 举例                    |
| ---- | :---------------------- | :-------------------- |
| =    | 检测两个字符串是否相等，相等返回 true。  | [ $a = $b ] 返回 false。 |
| !=   | 检测两个字符串是否相等，不相等返回 true。 | [ $a != $b ] 返回 true。 |
| -z   | 检测字符串长度是否为0，为0返回 true。  | [ -z $a ] 返回 false。   |
| -n   | 检测字符串长度是否为0，不为0返回 true。 | [ -n $a ] 返回 true。    |
| -str | 检测字符串是否为空，不为空返回 true。   | [ $a ] 返回 true。       |

1.yunsuan5.sh
```shell
#!/bin/bash

#字符串运算符

a="abc"
b="efg"

if [ $a = $b ]
then
   echo "$a = $b : a 等于 b"
else
   echo "$a = $b: a 不等于 b"
fi
if [ $a != $b ]
then
   echo "$a != $b : a 不等于 b"
else
   echo "$a != $b: a 等于 b"
fi
if [ -z $a ]
then
   echo "-z $a : 字符串长度为 0"
else
   echo "-z $a : 字符串长度不为 0"
fi
if [ -n $a ]
then
   echo "-n $a : 字符串长度不为 0"
else
   echo "-n $a : 字符串长度为 0"
fi
if [ $a ]
then
   echo "$a : 字符串不为空"
else
   echo "$a : 字符串为空"
fi
```
运算结果:
```text
caojx$ ./yunsuan5.sh 
abc = efg: a 不等于 b
abc != efg : a 不等于 b
-z abc : 字符串长度不为 0
-n abc : 字符串长度不为 0
abc : 字符串不为空
```

>6.文件测试运算符

文件测试运算符用于检测 Unix 文件的各种属性。
属性检测描述如下：

| 运算符     | 说明                                       | 举例                     |
| ------- | :--------------------------------------- | :--------------------- |
| -b file | 检测文件是否是块设备文件，如果是，则返回 true。               | [ -b $file ] 返回 false。 |
| -c file | 检测文件是否是字符设备文件，如果是，则返回 true。              | [ -c $file ] 返回 false。 |
| -d file | 检测文件是否是目录，如果是，则返回 true。                  | [ -d $file ] 返回 false。 |
| -f file | 检测文件是否是普通文件（既不是目录，也不是设备文件），如果是，则返回 true。 | [ -f $file ] 返回 true。  |
| -g file | 检测文件是否设置了 SGID 位，如果是，则返回 true。           | [ -g $file ] 返回 false。 |
| -k file | 检测文件是否设置了粘着位(Sticky Bit)，如果是，则返回 true。   | [ -k $file ] 返回 false。 |
| -p file | 检测文件是否是有名管道，如果是，则返回 true。                | [ -p $file ] 返回 false。 |
| -u file | 检测文件是否设置了 SUID 位，如果是，则返回 true。           | [ -u $file ] 返回 false。 |
| -r file | 检测文件是否可读，如果是，则返回 true。                   | [ -r $file ] 返回 true。  |
| -w file | 检测文件是否可写，如果是，则返回 true。                   | [ -w $file ] 返回 true。  |
| -x file | 检测文件是否可执行，如果是，则返回 true。                  | [ -x $file ] 返回 true。  |
| -s file | 检测文件是否为空（文件大小是否大于0），不为空返回 true。          | [ -s $file ] 返回 true。  |
| -e file | 检测文件（包括目录）是否存在，如果是，则返回 true。             | [ -e $file ] 返回 true。  |

变量 file 表示文件"test.sh"，它的大小为100字节，具有 rwx 权限。下面的代码，将检测该文件的各种属性：

1.testfile.sh
```shell
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
```

运行结果:
```text
caojx$ ./testfile.sh 
文件可读
文件可写
文件可执行
文件为普通文件
文件不是目录
文件不为空
文件存在
```

## 五、shell echo命令

shell 的 echo 指令与 PHP 的 echo 指令类似，都是用于字符串的输出。命令格式：
```shell
echo string
```
>1.显示普通字符串:

```shell
echo "It is a test"
```
结果：
```text
echo It is a test
```
这里的双引号完全可以省略，但是不提倡。

>2.显示转义字符
```shell
echo "\"It is a test\""
```
结果将是:
```text
"It is a test"
```

>3.显示变量

read 命令从标准输入中读取一行,并把输入行的每个字段的值指定给 shell 变量
```shell
#!/bin/sh
read name;
echo "$name It is a test";
```
以上代码保存为 test.sh，name 接收标准输入的变量，结果将是:
```text
caojx$ ./test.sh
OK                     #标准输入
OK It is a test        #输出
```
>4.显示换行
```shell
echo -e "OK! \n"； # -e 开启转义，开启转义后\n才会生效
echo "It it a test"；
```

输出结果：
```text
OK!

It it a test
```

>5.显示不换行
```shell
#!/bin/sh
echo -e "OK! \c"； # -e 开启转义 \c 不换行
echo "It is a test"；
```
输出结果：
```text
OK! It is a test
```

>6.显示结果定向至文件
```shell
echo "It is a test" > myfile；
```

>7.原样输出字符串，不进行转义或取变量(用单引号)
```shell
echo '$name\"'；
输出结果：
$name\"
```

>8.显示命令执行结果
```shell
echo `date`；
```

注意： 这里使用的是反引号 `, 而不是单引号 '。
结果将显示当前日期
```text
Thu Jul 24 10:08:46 CST 2014
```

## 六、shell printf 命令
printf 命令模仿 C 程序库（library）里的 printf() 程序。  
标准所定义，因此使用printf的脚本比使用echo移植性好。  
printf 使用引用文本或空格分隔的参数，外面可以在printf中使用格式化字符串，还可以制定字符串的宽度、左右对齐方式等。默认printf不会像
echo 自动添加换行符，我们可以手动添加 \n。  

>1.printf 命令的语法
```text
printf  format-string  [arguments...]
```
参数说明：
- format-string: 为格式控制字符串
- arguments: 为参数列表。

1.printf1.sh
```shell
#!/bin/bash

#printf

echo "hello,shell";
printf "hello,shell\n";
```
运行结果:
```text
caojx$ ./printf1.sh 
hello,shell
hello,shell
```

2.printf2.sh
```shell
#!/bin/bash

printf "%-10s %-8s %-4s\n" 姓名 性别 体重kg;
printf "%-10s %-8s %-4.2f\n" 郭靖 男 66.1234;
printf "%-10s %-8s %-4.2f\n" 杨过 男 48.6543;
```
运行结果:
```text
caojx$ ./printf2.sh 
姓名     性别   体重kg
郭靖     男      66.12
杨过     男      48.65
```
注意：
%s %c %d %f都是格式替代符  
%-10s 指一个宽度为10个字符（-表示左对齐，没有则表示右对齐），任何字符都会被显示在10个字符宽的字符内，如果不足则自动以空格填充，  
超过也会将内容全部显示出来。  
%-4.2f 指格式化为小数，其中.2指保留2位小数。  

%d %s %c %f 格式替代符详解:
- d: Decimal 十进制整数 -- 对应位置参数必须是十进制整数，否则报错！
- s: String 字符串 -- 对应位置参数必须是字符串或者字符型，否则报错！
- c: Char 字符 -- 对应位置参数必须是字符串或者字符型，否则报错！
- f: Float 浮点 -- 对应位置参数必须是数字型，否则报错！

3.printf3.sh
```shell
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
```

运行结果:
```text
caojx$ ./printf3.sh 
1 abc
1 abc
abcdefabcdefabc
def
a b c
d e f
g h i
j  
 and 0 
```

>2.printf的转义序列

| 序列    | 说明                                       |
| ----- | ---------------------------------------- |
| \a    | 警告字符，通常为ASCII的BEL字符                      |
| \b    | 后退                                       |
| \c    | 抑制（不显示）输出结果中任何结尾的换行字符（只在%b格式指示符控制下的参数字符串中有效）,<br>而且，任何留在参数里的字符、任何接下来的参数以及任何留在格式字符串中的字符，都被忽略 |
| \f    | 换页（formfeed）                             |
| \n    | 换行                                       |
| \r    | 回车（Carriage return）                      |
| \t    | 水平制表符                                    |
| \v    | 垂直制表符                                    |
| \\    | 一个字面上的反斜杠字符                              |
| \ddd  | 表示1到3位数八进制值的字符。仅在格式字符串中有效                |
| \0ddd | 表示1到3位的八进制值字符                            |

## 七、shell test 命令
shell中的 test 命令用于检查某个条件是否成立，它可以进行数值、字符和文件三个方面的测试。

>1.数值测试

| 参数   | 说明      |
| ---- | ------- |
| -eq  | 等于则为真   |
| -ne  | 不等于则为真  |
| -gt  | 大于则为真   |
| -ge  | 大于等于则为真 |
| -lt  | 小于则为真   |
| -le  | 小于等于则为真 |

1.test1.sh
```shell
#!/bin/bash

#test 命令

num1=100;
num2=100;

if test $[num1] -eq $[num2]  #这里也可以不用方括号
then
 echo "两个数相等!";
else
 echo "两个数不相等!";
fi 
```
运行结果:
```text
caojx$ ./test1.sh 
两个数相等!
```
>2.代码中的 [] 执行基本的算数运算
```shell

#!/bin/bash

a=5;
b=6;

result=$[a+b]; # 注意等号两边不能有空格
echo "result 为： $result";
```

运行结果:
```text
result 为： 11
```
>3.字符串测试

| 参数     | 说明          |
| ------ | ----------- |
| =      | 等于则为真       |
| !=     | 不相等则为真      |
| -z 字符串 | 字符串长度为零则为真  |
| -n 字符串 | 字符串长度不为零则为真 |

1.test2.sh
```shell
caojx$ cat test2.sh 
#!/bin/bash

str1="hello";
str2="shell";

if test $str1 = $str2
then
 echo "两个字符串相等";
else
 echo "两个字符串不相等";
fi
```

运行结果:
```text
caojx$ ./test2.sh 
两个字符串不相等
```
>3.文件测试

| 参数     | 说明                 |
| ------ | ------------------ |
| -e 文件名 | 如果文件存在则为真          |
| -r 文件名 | 如果文件存在且可读则为真       |
| -w 文件名 | 如果文件存在且可写则为真       |
| -x 文件名 | 如果文件存在且可执行则为真      |
| -s 文件名 | 如果文件存在且至少有一个字符则为真  |
| -d 文件名 | 如果文件存在且为目录则为真      |
| -f 文件名 | 如果文件存在且为普通文件则为真    |
| -c 文件名 | 如果文件存在且为字符型特殊文件则为真 |
| -b 文件名 | 如果文件存在且为块特殊文件则为真   |

1.test3.sh
```shell
#!/bin/bash

#test 文件测试

cd /Users/caojx/code/learn/code/shell;

if test -e test3.sh
then 
 echo "test3.sh文件存在";
else
 echo "test3.sh文件不存在";
fi
```
运行结果:
```text
caojx$ ./test3.sh 
test3.sh文件存在
```

另外，Shell还提供了与( -a )、或( -o )、非( ! )三个逻辑操作符用于将测试条件连接起来，其优先级为："!"最高，"-a"次之，"-o"最低。例如：
```shell
cd /bin
if test -e ./notFile -o -e ./bash
then
    echo '有一个文件存在!'
else
    echo '两个文件都不存在'
fi
```
输出结果：
```text
有一个文件存在!
```
## 八、shell流程控制

和Java、PHP等语言不一样，sh的流程控制不可为空，如(以下为PHP流程控制写法)：
```phh
<?php
if (isset($_GET["q"])) {
    search(q);
}
else {
    // 不做任何事情
}
```
注意：在sh/bash里可不能这么写，如果else分支没有语句执行，就不要写这个else。

>1.if语句

1.if
```text
if 条件
then
    command1; 
    command2;
    ...;
    commandN;
fi
```
2.if else
```text
if 条件
then
    command1;
    command2;
    ...
    commandN;
else
    command;
fi
```
3.if else-if else
```text
if 条件
then
    command1;
elif 条件2 
then 
    command2;
else
    commandN;
fi
```

案例1:
```shell
#!/bin/bash

a=10;
b=20;
if [ $a == $b ]
then
   echo "a 等于 b";
elif [ $a -gt $b ]
then
   echo "a 大于 b";
elif [ $a -lt $b ]
then
   echo "a 小于 b";
else
   echo "没有符合的条件";
fi
```
运行结果：
```text
a 小于 b
```

if else语句经常与test命令结合使用，如下所示：  
案例2：
```shell
#!/bin/bash

num1=$[2*3];
num2=$[1+5];
if test $[num1] -eq $[num2]
then
    echo '两个数字相等!';
else
    echo '两个数字不相等!';
fi
```
运行结果：
```text
两个数字相等!
```
>2.循环语句

1.for循环
```text
for var in item1 item2 ... itemN
do
    command1;
    command2;
    ...;
    commandN;
done
```

案例1：
```shell
for num in 1 2 3 4 5
do
    echo "The value is: $num";
done
```
运行结果:
```text
The value is: 1
The value is: 2
The value is: 3
The value is: 4
The value is: 5
```

2.while循环
```text
while 条件
do
    command;
done
```

案例1：
```shell
#!/bin/sh
int=1
while(( $int<=5 ))
do
    echo $int
    let "int++"
done
```

运行结果：
```text
1
2
3
4
5
```


案例2:while循环可用于读取键盘信息。下面的例子中，输入信息被设置为变量FILM，按<Ctrl-D>结束循环。
```shell
#!/bin/bash

echo '按下 <CTRL-D> 退出'
echo -n '输入你最喜欢的网站名: '
while read FILM
do
    echo "是的！$FILM 是一个好网站";
done
```

运行结果:
```text
按下 <CTRL-D> 退出
输入你最喜欢的网站名:菜鸟教程
是的！菜鸟教程 是一个好网站
```

3.无限循环
无限循环语法格式：
```text
while :
do
    command;
done
```
或
```text
while true
do
    command;
done
```
或
```text
for (( ; ; ));
```
4.until 循环
until循环执行一系列命令直至条件为真时停止。  
until循环与while循环在处理方式上刚好相反。  
一般while循环优于until循环，但在某些时候—也只是极少数情况下，until循环更加有用。  
until 语法格式:
```text
until 条件
do
    command;
done
```
>3.case语句
```text
case 值 in
模式1)
    command1;
    command2;
    ...
    commandN;
    ;;
模式2）
    command1;
    command2;
    ...
    commandN
    ;;
esac
```

case工作方式如上所示。取值后面必须为单词in，每一模式必须以右括号结束。取值可以为变量或常数。匹配发现取值符合某一模式后，  
其间所有命令开始执行直至 ;;。取值将检测匹配的每一个模式。一旦模式匹配，则执行完匹配模式相应命令后不再继续其他模式。  
如果无一匹配模式，使用星号 * 捕获该值，再执行后面的命令。  

案例:下面的脚本提示输入1到4，与每一种模式进行匹配：
```shell
#!/bin/bash

echo '输入 1 到 4 之间的数字:';
echo '你输入的数字为:';
read aNum
case $aNum in
    1)  echo '你选择了 1';
    ;;
    2)  echo '你选择了 2';
    ;;
    3)  echo '你选择了 3';
    ;;
    4)  echo '你选择了 4';
    ;;
    *)  echo '你没有输入 1 到 4 之间的数字';
    ;;
esac
```
输入不同的内容，会有不同的结果，例如：
```text
输入 1 到 4 之间的数字:
你输入的数字为:
3
你选择了 3
```

>4.break

break命令允许跳出所有循环（终止执行后面的所有循环）。  
下面的例子中，脚本进入死循环直至用户输入数字大于5。要跳出这个循环，返回到shell提示符下，需要使用break命令。
```shell
#!/bin/bash
while :
do
    echo -n "输入 1 到 5 之间的数字:"
    read aNum
    case $aNum in
        1|2|3|4|5) echo "你输入的数字为 $aNum!"
        ;;
        *) echo "你输入的数字不是 1 到 5 之间的! 游戏结束"
            break
        ;;
    esac
done
```

>5.continue

continue命令与break命令类似，只有一点差别，它不会跳出所有循环，仅仅跳出当前循环。
```shell
#!/bin/bash
while :
do
    echo -n "输入 1 到 5 之间的数字: "
    read aNum
    case $aNum in
        1|2|3|4|5) echo "你输入的数字为 $aNum!"
        ;;
        *) echo "你输入的数字不是 1 到 5 之间的!"
            continue
            echo "游戏结束"
        ;;
    esac
done
```
运行代码发现，当输入大于5的数字时，该例中的循环不会结束，语句 echo "Game is over!" 永远不会被执行。

## 九、shell函数
linux shell 可以用户定义函数，然后在shell脚本中可以随便调用。
shell中函数的定义格式如下：
```text
[ function ] funname [()]
{
    action;
    [return int;]
}
```
说明：
- 可以带function fun() 定义，也可以直接fun() 定义,不带任何参数。
- 参数返回，可以显示加：return 返回，如果不加，将以最后一条命令运行结果，作为返回值。 return后跟数值n(0-255），不能返回其他值，比如字符串

>1.无参数函数
>1.function1.sh
```shell
#!/bin/bash

#shell 函数

domoFun(){
 echo "hello,shell";
}

echo "-----函数开始执行-----";
 domoFun
echo "-----函数执行完毕-----";
```
运行结果:
```text
./function1.sh 
-----函数开始执行-----
hello,shell
-----函数执行完毕-----
```

下面定义一个带有return语句的函数：

2.function2.sh
```shell
#!/bin/bash

funWithReturn(){
    echo "这个函数会对输入的两个数字进行相加运算...";
    echo "输入第一个数字: ";
    read aNum;
    echo "输入第二个数字: ";
    read anotherNum;
    echo "两个数字分别为 $aNum 和 $anotherNum !";
    return $(($aNum+$anotherNum));
}
funWithReturn
echo "输入的两个数字之和为 $? !"; #函数返回值在调用该函数后通过$?来获得
```

运行结果:
```text
caojx$ ./function2.sh 
这个函数会对输入的两个数字进行相加运算...
输入第一个数字: 
1
输入第二个数字: 
2
两个数字分别为 1 和 2 !
输入的两个数字之和为 3 !
```
函数返回值在调用该函数后通过 $? 来获得。  
注意：所有函数在使用前必须定义。这意味着必须将函数放在脚本开始部分，直至shell解释器首次发现它时，才可以使用。调用函数仅使用其函数名即可。  

>2.有参数函数

在Shell中，调用函数时可以向其传递参数。在函数体内部，通过 $n 的形式来获取参数的值，例如，$1表示第一个参数，$2表示第二个参数...
带参数的函数示例：
```shell
#!/bin/bash

funWithParam(){
    echo "第一个参数为 $1 !"
    echo "第二个参数为 $2 !"
    echo "第十个参数为 $10 !"
    echo "第十个参数为 ${10} !"
    echo "第十一个参数为 ${11} !"
    echo "参数总数有 $# 个!"
    echo "作为一个字符串输出所有参数 $* !"
}
funWithParam 1 2 3 4 5 6 7 8 9 34 73
```

输出结果：
```text
第一个参数为 1 !
第二个参数为 2 !
第十个参数为 10 !
第十个参数为 34 !
第十一个参数为 73 !
参数总数有 11 个!
作为一个字符串输出所有参数 1 2 3 4 5 6 7 8 9 34 73 !
```
注意，$10 不能获取第十个参数，获取第十个参数需要${10}。当n>=10时，需要使用${n}来获取参数。
另外，还有几个特殊字符用来处理参数：

| 处理参数 | 说明                              |
| ---- | :------------------------------ |
| $#   | 传递到脚本的参数个数                      |
| $*   | 以一个单字符串显示所有向脚本传递的参数             |
| $$   | 脚本运行的当前进程ID号                    |
| $!   | 后台运行的最后一个进程的ID号                 |
| $@   | 与$*相同，但是使用时加引号，并在引号中返回每个参数。     |
| $-   | 显示Shell使用的当前选项，与set命令功能相同。      |
| $?   | 显示最后命令的退出状态。0表示没有错误，其他任何值表明有错误。 |


## 十、shell文件包含

和其他语言一样，Shell 也可以包含外部脚本。这样可以很方便的封装一些公用的代码作为一个独立的文件。
Shell 文件包含的语法格式如下：
```text
. filename   # 注意点号(.)和文件名中间有一空格
或
source filename
```

实例:

创建两个 shell 脚本文件。
fileinclude1.sh 代码如下：
```shell
#!/bin/bash

url="http://www.runoob.com"
```

fileinclude2.sh 代码如下：
```shell
#!/bin/bash

#使用 . 号来引用fileinclude1.sh 文件
# 或者使用以下包含文件代码
# source ./fileinclude1.sh
. ./fileinclude1.sh
echo "菜鸟教程官网地址：$url"
```
运行结果:
```text
caojx$ chmod +x fileinclude2.sh 
caojx$ ./fileinclude2.sh 
菜鸟教程官方网址: http://www.runoob.com
```

参考：  
[http://www.jb51.net/article/28514.htm](http://www.jb51.net/article/28514.htm)  
[http://www.runoob.com/linux/linux-shell.html](http://www.runoob.com/linux/linux-shell.html)