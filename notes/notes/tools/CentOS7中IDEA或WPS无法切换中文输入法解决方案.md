>问题描述

CentOS7的WPS或在IntelliJ IDEA系列产品中经常不能切换中文输入法，通过下边的方法一般可以解决。

>声明

输入法：CentOS7自带ibus，如果你用的是fcitx请在对应的地方进行修改
系统：CentOS7，这个方案应该适用于大多数Linux发行版本

>IntelliJ IDEA中文输入法问题解决

首先进入解压该软件的根目录下，进入bin文件夹中，找到启动该软件的idae.sh文件，使用gedit打开，或者使用vi命令打开，进入文档模式：
在注释之后的首行添加：
exportXMODIFIERS="@im=ibus"
exportGTK_IM_MODULE="ibus"
export QT_IM_MODULE="ibus"
保存退出，重新执行.sh文件可以发现已经可以输入中文了


>WPS中文输入法问题解决

1）为了解决问题需要配置两个文件：
打开命令行模式，输入如下：
gedit/usr/bin/wps
在#!/bin/bash下面添加如下配置：
exportXMODIFIERS="@im=ibus"
export QT_IM_MODULE="ibus"
gedit/usr/bin/et
在#!/bin/bash下面添加如下配置：
exportXMODIFIERS="@im=ibus"
export QT_IM_MODULE="ibus"
重新启动WPS可以发现中文问题解决！


>推荐设置

上述步骤设置好后，推荐设置一下环境变量
vi /etc/profile
在末尾添加：
export XIM="ibus"
export XIM_PROGRAM="ibus"
export XMODIFIERS="@im=ibus"
export GTK_IM_MODULE="ibus"
export QT_IM_MODULE="ibus"



参考文章：http://www.linuxdiyf.com/linux/20404.html




