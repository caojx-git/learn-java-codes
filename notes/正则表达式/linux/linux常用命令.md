[toc]

#linux 常用命令
***

##一、查看系统语言环境
1.1 显示目前所支持的语言
>echo $LANG
[root@localhost ~]# echo $LANG
zh_CN.UTF-8

上边的意思是说，目前语言为zh_CN.UTF-8

1.2 修改语言为英文语系
>LANG=en_US

**提示**：上边的命令中没有空格符号

##二、基础操作命令

###2.1日期

####日期命令

2.1.1 显示日期与时间命令
>[root@localhost ~]# date
2016年 12月 21日 星期三 13:03:13 CST

2.1.2 格式化显示日期与时间
>[root@localhost ~]# date +%y/%m/%d
16/12/21
[root@localhost ~]# date +%Y/%m/%d
2016/12/21
[root@localhost ~]# date +%H:%m
13:12

####日历命令

> cal [选项] [[[日] 月] 年]

选项：
 -1, --one        只显示当前月份(默认)
 -3, --three      显示上个月、当月和下个月
 -s, --sunday     周日作为一周第一天
 -m, --monday     周一用为一周第一天
 -j, --julian     输出儒略日
 -y, --year       输出整年
 -V, --version    显示版本信息并退出
 -h, --help       显示此帮助并退出


![cal](/home/caojx/learn/notes/images/linux/cal.png)

默认显示本月的日期

###2.2计算器

####简单好用的计算器

linux中提供了一个计算程序，bc，在命令中输入bc后，就可以进行简单的运算
支持 +、-、*、/、^、%, quit离开

![bc](/home/caojx/learn/notes/images/linux/bc.png)


bc进入计算器后，就可以进行运行输出结果，但是上边的10/100结果是0，因为bc默认只输出整数部分，如果要输出小数，在bc中执行命令scale=number，number表示要输出小数点后的位数。

![bc](/home/caojx/learn/notes/images/linux/bc2.png)

###2.3 Linux系统的在线求助man page 与info page

####2.3.1 man page

案例
>man date

![](/home/caojx/learn/notes/images/linux/man-date.png)

上边的只是部分截图，一般来说man page的内容分为如下几个部分
代号 --------------------------- 内容说明
MAME---------------------------简短的命令，数据名称说明
SYNOPSIS-----------------------简短的命令执行语法（syntax）简介
DESCRIPTION-------------------较为完成的说明，这部分最好仔细看
OPTIONS------------------------针对SYNOPSIS部分，列举所有可用的选项说明
COMMANDS---------------------软件程序（软件）在执行的时候，可以在程序（软件）中执行的命令
FILES------------------------这个程序或数据所使用的参考或连接到的某些文件
SELL ALSO--------------------这个命令或数据的有关的其他说明
EXAMPLE----------------------简单的案例
BUGS-------------------------是否有相关的错误

#####2.3.2 man page 常用操作

空格健 ----- --------向下翻页
[Page Down] -------   向下翻页
[Page Up] ----------  向上翻页
[Home]  ------------- 回到第一页
[End]   ----------------回到最后一页
/string -------------- 向下查询string字符串
？string -------------向上查询string字符串
n/N ------------------在查询字符串的时候，用来下/上一个查询
q --------------------- 结束man page
#####2.2.3 man page 说明文件
查询系统中还有那些跟 “man” 这个命令相关的说明文件
>[root@localhost ~]# man -f man
man (1)              - an interface to the on-line reference manuals
man (7)              - macros to format man pages
man (1p)             - display system documentation

查看指定等级的命令
>[root@localhost ~]# man 1 man
[root@localhost ~]# man 7 man
[root@localhost ~]# man 1p man

查询包含某个关键字的命令的说明文件
>man -k man
[caojx@localhost ~]$ man -k man
bsd_signal (3)       - signal handling with BSD semantics
cproj (3)            - project into Riemann Sphere
。。。。。

####2.3.4其他帮助命令
info page
命令 - -help

###2.4查看系统的使用状态,以及关机命令
#####2.4.1查看系统状态
>who 查看有谁在线
netstate -a 查看网络联机状态
ps -aux 查看后台可执行程序

#####2.4.2关机命令
a. shutdown
作用如下：
可以自由选择关机模式：关机，重启，进入单用户模式均可
可以设置关机时间：可以设置某个特定的时候关机
可以自定义关机消息：在关机之前，可以将消息传递给所有的在线用户
可以仅仅发出警告消息：某些时候，不想关机，只是告知用户某断时间需要注意下，这个时候可以使用shutdown来通知用户
可以选择是否要用fsck检查文件系统
> [root@localhost ~]# /sbin/shutdown [-t 秒] [-arkhncfF] 时间 [警告消息]
参数：
-t sec:-t 后加秒数，表示“过几秒后关机”
-k：不要真的关机，只是发送警告消息
-r: 在系统服务停止后就重启
-h： 在系统服务停止后就关机
-n: 不经过init程序，直接以shutdown的功能来关机
-f： 关机之后强制略过fsck的磁盘检查
-F：关机之后强制进行fsck的磁盘检查
-c: 取消已经进行的shutdonw命令内容
时间： 这个一定需要加入的参数，比如now表示立刻。

b.其他关机命令
reboot
poweroff
halt
init 0 （init表示切换执行等级，0关机，1单用户模式，2不完全多用户，不含NFS 3，纯命令模式，4	未分配 5图形界面，6重启）

###2.4.5Linux忘记root密码
有些朋友在设置完成linux之后，就忘记了root密码，修改root密码并不需要重新安装
这里使用grup引导程序来作为案例
2.5.1.重启系统，进入到grub界面
重启系统按e就可以进入到grub编辑模式，有点像下边的
>root(hd0,0)
kernel /vmlinuz-2.6.18-128.el5 ro root=LABEL=- /rhgb quiet
initrd /initrd-2.6.18-128.elg.img

2.5.2.光标移动到kernel哪一行，按e，进入kernel该行编辑界面中，让后在最后输入single
> kernel //vmlinuz-2.6.18-128.el5 ro root=LABEL=- /rhgb quiet single
按下enter确定之后，按下b就可以开机进入但用户模式维护模式，在这个模式下，你会在tty1的地方不许药输入密码就可以取得终端控制权限（root身份），之后就可以修改密码了。

2.5.3.修改root密码
>[root@localhost ~]# passwd
接下来，系统会要求你输入两次新的root密码。

##三、linux的文件权限与目录配置

提示：对于权限来说root用户是天神，权限对root用户是不受影响的，但时root用户的权限可以影响到其他用户。

###3.1用户与用户组
Liunx最优秀的地方之一，就在于他的多用户、多任务环境。为了让各个用户具有较保密的文件数据，因此文件的权限管理就变得很重要了。Linux一般将文件的访问权限分为3个类别，分别是owner，group,others，而且3中身份各有read，write，execute等权限，如果管理不当，你的Linux主机会变得非常不舒服.

linux用户身份与用户组记录的文件
>/etc/passwd - -记录系统上的账号和一般用户，和root用户相关的信息
/etc/shadow - -记录个人的密码
/etc/group  - -记录linux主机中所有的组名 

###3.2Linux文件权限的管理
####3.2.1linux文件的属性
-rw-rw-r--. 1 test  test 0 11月 20 17:24 a.txt

其中 -rw------- 
\-  :第一个符号表示文件类型，Linux中有7中文件类型
d  目录文件。
l  符号链接(指向另一个文件,类似于瘟下的快捷方式)。
s  套接字文件。
b  块设备文件,二进制文件。
c  字符设备文件,如键盘，鼠标
p  命名管道文件。
\-  普通文件，或更准确地说，不属于以上几种类型的文件

rw------- 每三位一组，对组(u, g, o)的权限进行说明
 rw- 		rw--			r--
U所有者		g所属组		o其他人
r可读		w可写		x可执行

r:read,读权限  4
w:write,写权限  2
x:可执行权限  1

![文件权限](/home/caojx/learn/notes/images/linux/file-qx.png)

提示：显示完整的时间格式ls -l --full-time

####3.2.2修改文件的属性与权限
* chgrp:改变文件所属用户组（change group）
* chown:改变文件所有者 (change owner)
* chmod:改变文件权限(change mod)

#####3.2.2.1 chgrp 改变文件所属用户组
命令格式：：chgrp [选项] 用户组 文件或目录
	选项：
    -R：递归（recursive）的持续的修改，即连同目录下的所有文件，目录
    用户组：修改为目标用户组的组名，这个组名需要在/etc/group文件中存在
    
案例：在test的目录下有一个文件a.txt将所属用户组从root修改为caojx用户组
>[caojx@localhost test]$ ls -l
总用量 4
-rw-r--r-- 1 caojx root 3 12月 30 13:00 a.txt
[caojx@localhost test]$ chgrp caojx a.txt 
[caojx@localhost test]$ ls -l
总用量 4
-rw-r--r-- 1 caojx caojx 3 12月 30 13:00 a.txt

#####3.2.2.2 chown 改变文件所有者
命令格式：chown [选项]  [所有者][:[组]] 文件或目录
选项：
	-R：递归（recursive）的持续的修改，即连同目录下的所有文件，目录
    
所有者：修改为目标用户的用户名，用户名必须是已经存在系统中的账号，这个用户账号需要在/etc/passwd中存在
组：修改为目标用户组的组名，这个组名需要在/etc/group文件中存在

案例：在test的目录下有一个文件b.txt,将其所有者修改为root用户
>[root@localhost test]# ls -l
总用量 4
-rw-r--r-- 1 caojx caojx 3 12月 30 13:00 a.txt
[root@localhost test]# chown root a.txt 
[root@localhost test]# ls -l
总用量 4
-rw-r--r-- 1 root caojx 3 12月 30 13:00 a.txt

**提示cp命令会复制执行者的属性和权限，所以有时候我们将文件复制给其他用户之后需要修改文件的权限和属性在可以使用**

#####3.2.2.3 chmod修改文件的权限
文件的权限有3种：
r:4 --》可读:权值
w:2 --》可写:权值
x:1 --》可执行:权值
文件3身份权限，每种身份权限通过各自的（r,w,x）累加，假如文件的权限[-rw-r- -r- - ]
owner = rw- = 4+2+0 = 6
group = r- - = 1+0+0=1
others = r - - = 1+0+0=1

方式一：通过数字方式修改文件的权限
>命令格式：chmod [选项]... 八进制模式  文件或目录...

选项：
	-R：递归（recursive）的持续的修改，即连同目录下的所有文件，目录
    
案例：修改a.txt的文件权限为764 （所有者权限rwx,组权限rw-,其他用户权限r- -）
>[root@localhost test]# ls -l
总用量 4
-rw-r--r-- 1 root caojx 3 12月 30 13:00 a.txt
[root@localhost test]# chmod 764 a.txt 
[root@localhost test]# ls -l
总用量 4
-rwxrw-r-- 1 root caojx 3 12月 30 13:00 a.txt

方式二：通过符号类型修改文件的权限
>命令格式：chmod [选项]... 模式[,模式]... 文件...

选项：
	-R：递归（recursive）的持续的修改，即连同目录下的所有文件，目录

**身份权限标识：**
u --->表示用户
g --->表示组
o --->表示其他
a --->表示所有包括（u，g,o）
**权限设置符号：**
通过+，-，= 添加，删除，设置权限

**权限 r,w,x:**
r 可读
w 可写
x 可执行

案例：修改a.txt的文件权限为[-rwxrw-r--]
>[root@localhost test]# chmod u=rwx,g=rw,o=r a.txt
 [root@localhost test]# ls -l
总用量 4
-rwxrw-r-- 1 root caojx 3 12月 30 13:00 a.txt

####3.2.3 目录与文件权限的意义
* 权限对文件的重要性
文件是存放数据的地方，包括文本文件，数据库内容文件，二进制文件，因此权限对于文件来说，它的意义如下：

**r(read):**可以读取此文件的实际内容，如读取文件文件的文字内容等。
**w(write):**可以编辑，新增或修改文件的内容(不包括删除权限，删除文件的权限是由目录权限决定的)
**x(excute):**该文件具有被系统执行的权限。

* 权限对目录的重要性

**r(read contents in directory):**表示具有读取目录结构列表的权限，拥有此权限表示你可以使用ls查看该目录下的文件列表了
**w(modify contents of directory):**表示你具有更改目录结构列表的权限
有该权限你可在目录下做如下操作：

1. 新建文件与目录
2. 删除依存在的文件与目录（不论该文件的权限为何）
3. 将已经存在文件或目录进行重命名
4. 转移该目录内的文件，目录位置。

**x(access directory):**目录是不能被执行的，目录的**x**代表的是用户能否进入该目录成功工作目录的用途。加入你没有该权限，你就无法cd 进入到该目录。



###3.3 CenOS的查看
>[caojx@localhost ~]$ uname -r    - -》(查看实际的内核版本)
3.10.0-514.2.2.el7.x86_64
[caojx@localhost ~]$ lsb_release -a
LSB Version:	:core-4.1-amd64:core-4.1-noarch:cxx-4.1-amd64:cxx-4.1-noarch:desktop-4.1-amd64:desktop-4.1-noarch:languages-4.1-amd64:languages-4.1-noarch:printing-4.1-amd64:printing-4.1-noarch  ---》LSB（Linux Standard Base）的版本
Distributor ID:	CentOS
Description:	CentOS Linux release 7.3.1611 (Core) ----》distribution版本
Release:	7.3.1611
Codename:	Core


##四、Linux文件与目录管理

###7.1.1目录的相关操作
>cd:切换目录
pwd：显示当前目录
mkdir：新建一个新的目录
rmdir：删除一个空的目录

###7.1.2关于文件路径变量：$PATH
我们知道，在查看文件的属性的命令为ls的完整文件名为:/bin/ls(这个是绝对路径)，“为什么我们可以在任何地方执行/bin/ls命令呢？”为什么我们在任何目录下输入ls就可以显示出一些信息，而不会说是，/bin/ls命令呢?，这是因为我们定义了环境变量$PATH的作用。。
当我们执行一个命令的时候，如ls，系统会依照PATH变量的设置去每个PATH定义的目录下查询文件名为ls的可执行文件，如果PATH定义目录中含有多个名为ls的可执行文件，那么就执行先查询到的。

>查看那些目录被定义出来了：echo $PATH
>[caojx@localhost ~]$ echo $PATH
/usr/local/apache-tomcat-7.0.67/bin:/usr/local/apache-maven-3.3.9/bin:/usr/local/nginx-1.8.0/sbin:/u01/app/oracle/product/11.2.0/xe/bin:/usr/local/idea-IU-163.7743.44/bin:/usr/java/jdk1.8.0_112/bin:/usr/java/jdk1.8.0_112/jre/bin:/usr/lib64/qt-3.3/bin:/usr/local/bin:/usr/local/sbin:/usr/bin:/usr/sbin:/bin:/sbin:/home/caojx/.local/bin:/home/caojx/bin
