#linux 常用命令
***

##一、查看系统语言环境
1. 显示目前所支持的语言
>echo $LANG
[root@localhost ~]# echo $LANG
zh_CN.UTF-8

上边的意思是说，目前语言为zh_CN.UTF-8

2. 修改语言为英文语系
>LANG=en_US

**提示**：上边的命令中没有空格符号

##二、基础操作命令

###1.日期

####日期命令

1. 显示日期与时间命令
>[root@localhost ~]# date
2016年 12月 21日 星期三 13:03:13 CST

2. 格式化显示日期与时间
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

###2.计算器

####简单好用的计算器

linux中提供了一个计算程序，bc，在命令中输入bc后，就可以进行简单的运算
支持 +、-、*、/、^、%, quit离开

![bc](/home/caojx/learn/notes/images/linux/bc.png)


bc进入计算器后，就可以进行运行输出结果，但是上边的10/100结果是0，因为bc默认只输出整数部分，如果要输出小数，在bc中执行命令scale=number，number表示要输出小数点后的位数。

![bc](/home/caojx/learn/notes/images/linux/bc2.png)

###3.Linux系统的在线求助man page 与info page

####1. man page

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

#####man page 常用操作

空格健 ----- --------向下翻页
[Page Down] -------   向下翻页
[Page Up] ----------  向上翻页
[Home]  ------------- 回到第一页
[End]   ----------------回到最后一页
/string -------------- 向下查询string字符串
？string -------------向上查询string字符串
n/N ------------------在查询字符串的时候，用来下/上一个查询
q --------------------- 结束man page
##### man page 说明文件
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

####2.其他帮助命令
info page
命令 - -help

###4查看系统的使用状态,以及关机命令
#####1.查看系统状态
>who 查看有谁在线
netstate -a 查看网络联机状态
ps -aux 查看后台可执行程序

#####2.关机命令
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

###5.Linux忘记root密码
有些朋友在设置完成linux之后，就忘记了root密码，修改root密码并不需要重新安装
这里使用grup引导程序来作为案例
1.重启系统，进入到grub界面
重启系统按e就可以进入到grub编辑模式，有点像下边的
>root(hd0,0)
kernel /vmlinuz-2.6.18-128.el5 ro root=LABEL=- /rhgb quiet
initrd /initrd-2.6.18-128.elg.img

2.光标移动到kernel哪一行，按e，进入kernel该行编辑界面中，让后在最后输入single
> kernel //vmlinuz-2.6.18-128.el5 ro root=LABEL=- /rhgb quiet single
按下enter确定之后，按下b就可以开机进入但用户模式维护模式，在这个模式下，你会在tty1的地方不许药输入密码就可以取得终端控制权限（root身份），之后就可以修改密码了。

3.修改root密码
>[root@localhost ~]# passwd
接下来，系统会要求你输入两次新的root密码。


