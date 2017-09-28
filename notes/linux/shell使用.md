#shell的使用

Shell 是一个用 C 语言编写的程序，它是用户使用 Linux 的桥梁。Shell 既是一种命令语言，又是一种程序设计语言。  
Shell 是指一种应用程序，这个应用程序提供了一个界面，用户通过这个界面访问操作系统内核的服务。  

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

参考：  
[http://www.jb51.net/article/28514.htm](http://www.jb51.net/article/28514.htm)  
[http://www.runoob.com/linux/linux-shell.html](http://www.runoob.com/linux/linux-shell.html)