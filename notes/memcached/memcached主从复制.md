# Memcached主从复制实现

原文：https://www.linuxidc.com/Linux/2014-08/106021.htm

​    由于 Memcached 自己没有防止单点的措施，因为为了保障 Memcached 服务的高可用，我们需要借助外部的工具来实现高可用的功能。本文引入 Repcached 这个工具，通过使用该工具我们可以完成 Memcached 服务的主从功能。

​      Repcached 它是由日本人开发的，用来实现 Memcached 复制功能的一个工具。它所构建的主从方案是一个**单主单从**的方案，不支持多主多从。但是，**它的特点是，主从两个节点可以互相读写**，从而可以达到互相同步的效果。

​      假设主节点坏掉，从节点会很快侦测到连接断开，然后它会自动切换到监听状态( listen)从而成为主节点，并且等待新的从节点加入。

​      假设原来挂掉的主节点恢复之后，我们只能人工手动以从节点的方式去启动。原来的主节点并不能抢占成为新的主节点，除非新的主节点挂掉。这也就意味着，基于 Repcached 实现的 Memcached 主从，针对主节点并不具备抢占功能。

​      假设从节点坏掉，主节点也会很快侦测到连接断开，然后它就会重新切换到监听状态(listen),并且等待新的从节点加入。

​      假设主从节点都挂掉，则数据就丢失了！因此，这是 Repcached 的一个短板，不过后期我们可以通过结合其它的工具来弥补这个缺点。

​      OK，简单介绍到这里。下面我们通过实验来看，基于 Repcached 的 Memcached 主从架构是如何部署，以后如何测试和管理的。

## 一、环境准备

### 1. 环境

- [CentOS](https://www.centos.org/download/) 7.0 x86_64位 采用最小化安装，系统经过了基本优化
- selinux 为关闭状态，iptables 为无限制模式
- 主机名：nolinux
- 源码包存放位置：/usr/local/src
- libevent版本：2.0.21
- memcached版本：1.4.20 



Memcached 安装及启动脚本 [http://www.linuxidc.com/Linux/2013-07/87641.htm](https://www.linuxidc.com/Linux/2013-07/87641.htm)

PHP中使用Memcached的性能问题 [http://www.linuxidc.com/Linux/2013-06/85883.htm](https://www.linuxidc.com/Linux/2013-06/85883.htm)

[Ubuntu](http://www.linuxidc.com/topicnews.aspx?tid=2)下安装Memcached及命令解释 [http://www.linuxidc.com/Linux/2013-06/85832.htm](https://www.linuxidc.com/Linux/2013-06/85832.htm)

Memcached的安装和应用 [http://www.linuxidc.com/Linux/2013-08/89165.htm](https://www.linuxidc.com/Linux/2013-08/89165.htm)

使用Nginx+Memcached的小图片存储方案 [http://www.linuxidc.com/Linux/2013-11/92390.htm](https://www.linuxidc.com/Linux/2013-11/92390.htm)

Memcached使用入门 [http://www.linuxidc.com/Linux/2011-12/49516p2.htm](https://www.linuxidc.com/Linux/2011-12/49516p2.htm)



### 2. 安装Memcached

**1、安装libevent**

Libevent 是一个用C语言[编写](https://baike.baidu.com/item/%E7%BC%96%E5%86%99)的、轻量级的开源高性能事件通知库，主要有以下几个亮点：事件驱动（ event-driven），高性能;轻量级，专注于网络，不如 ACE 那么臃肿庞大；源代码相当精炼、易读；跨平台，支持 Windows、 Linux、 *BSD 和 Mac Os；支持多种 I/O 多路复用技术， epoll、 poll、 dev/poll、 select 和 kqueue 等；支持 I/O，定时器和信号等事件；注册事件优先级。

Libevent 已经被广泛的应用，作为底层的网络库；比如 memcached、 Vomit、 Nylon、 Netchat等等

libevent是一个事件通知库，适用于windows、linux、bsd等多种平台，内部使用select、epoll、kqueue、IOCP等系统调用管理事件机制。著名分布式缓存软件memcached也是基于libevent，而且libevent在使用上可以做到跨平台，而且根据libevent官方网站上公布的数据统计，似乎也有着非凡的性能。

```shell
[root@master ~]#cd /usr/local/src
[root@master src]# wget http://code.taobao.org/p/nolinux/src/memcached/src/libevent-2.0.21-stable.tar.gz?orig
[root@master src]# tar zxvf libevent-2.0.21-stable.tar.gz
[root@master src]# cd libevent-2.0.21-stable
[root@master libevent-2.0.21-stable]#  ./configure --prefix=/usr
[root@master libevent-2.0.21-stable]#  make
[root@master libevent-2.0.21-stable]#  make install
[root@master libevent-2.0.21-stable]# ll /usr/lib/libevent*  # libevent安装完后，会在/usr/lib目录下出现如下内容

lrwxrwxrwx 1 root root 21 Aug 11 13:49 /usr/lib/libevent-2.0.so.5 -> libevent-2.0.so.5.1.9
-rwxr-xr-x 1 root root 968690 Aug 11 13:49 /usr/lib/libevent-2.0.so.5.1.9
-rw-r--r-- 1 root root 1571802 Aug 11 13:49 /usr/lib/libevent.a
lrwxrwxrwx 1 root root 26 Aug 11 13:49 /usr/lib/libevent_core-2.0.so.5 -> libevent_core-2.0.so.5.1.9
-rwxr-xr-x 1 root root 585225 Aug 11 13:49 /usr/lib/libevent_core-2.0.so.5.1.9
-rw-r--r-- 1 root root 978482 Aug 11 13:49 /usr/lib/libevent_core.a
-rwxr-xr-x 1 root root 970 Aug 11 13:49 /usr/lib/libevent_core.la
lrwxrwxrwx 1 root root 26 Aug 11 13:49 /usr/lib/libevent_core.so -> libevent_core-2.0.so.5.1.9
lrwxrwxrwx 1 root root 27 Aug 11 13:49 /usr/lib/libevent_extra-2.0.so.5 -> libevent_extra-2.0.so.5.1.9
-rwxr-xr-x 1 root root 404852 Aug 11 13:49 /usr/lib/libevent_extra-2.0.so.5.1.9
-rw-r--r-- 1 root root 593392 Aug 11 13:49 /usr/lib/libevent_extra.a
-rwxr-xr-x 1 root root 977 Aug 11 13:49 /usr/lib/libevent_extra.la
lrwxrwxrwx 1 root root 27 Aug 11 13:49 /usr/lib/libevent_extra.so -> libevent_extra-2.0.so.5.1.9
-rwxr-xr-x 1 root root 935 Aug 11 13:49 /usr/lib/libevent.la
lrwxrwxrwx 1 root root 30 Aug 11 13:49 /usr/lib/libevent_pthreads-2.0.so.5 -> libevent_pthreads-2.0.so.5.1.9
-rwxr-xr-x 1 root root 18430 Aug 11 13:49 /usr/lib/libevent_pthreads-2.0.so.5.1.9
-rw-r--r-- 1 root root 18670 Aug 11 13:49 /usr/lib/libevent_pthreads.a
-rwxr-xr-x 1 root root 998 Aug 11 13:49 /usr/lib/libevent_pthreads.la
lrwxrwxrwx 1 root root 30 Aug 11 13:49 /usr/lib/libevent_pthreads.so -> libevent_pthreads-2.0.so.5.1.9
lrwxrwxrwx 1 root root 21 Aug 11 13:49 /usr/lib/libevent.so -> libevent-2.0.so.5.1.9
[root@master libevent-2.0.21-stable]# cd ..
```

**2、安装memcached**

```shell
[root@master src]# tar zxvf memcached-1.4.20.tar.gz
[root@master src]# cd memcached-1.4.20
[root@master memcached-1.4.20]# ./configure --with-libevent=/usr
[root@master memcached-1.4.20]# wget http://code.taobao.org/p/nolinux/src/memcached/src/memcached-1.4.20.tar.gz?orig
[root@master memcached-1.4.20]# make
[root@master memcached-1.4.20]# make install
[root@master memcached-1.4.20]# cd ..
[root@master src]# ll /usr/local/bin/memcached    # 安装完成后会把memcached 放到 /usr/local/bin/memcached
-rwxr-xr-x 1 root root 341907 Aug 11 13:52 /usr/local/bin/memcached
```

注意：如果中间出现报错，请仔细检查错误信息，按照错误信息来配置或者增加相应的库或者路径

### 3. 安装Repcached安装

**方式一：使用repcached版本**

```shell
[root@master src]# wget http://downloads.sourceforge.net/repcached/memcached-1.2.8-repcached-2.2.tar.gz
[root@master src]# tar zxf memcached-1.2.8-repcached-2.2.tar.gz
[root@master src]# cd memcached-1.2.8-repcached-2.2
```

**方式二：使用patch版本**

```shell
[root@master memcached-1.2.8-repcached-2.2]# wget http://downloads.sourceforge.net/repcached/repcached-2.2-1.2.8.patch.gz
[root@master memcached-1.2.8-repcached-2.2]# gzip -cd ../repcached-2.2-1.2.8.patch.gz | patch -p1
[root@master memcached-1.2.8-repcached-2.2]# ./configure --enable-replication
[root@master memcached-1.2.8-repcached-2.2]#  make
[root@master memcached-1.2.8-repcached-2.2]#  make install
[root@master memcached-1.2.8-repcached-2.2]# cd ..
```

**以上操作，我们需要针对主节点和备节点都操作！这里我仅仅以主节点的部署为例！切记！**

## 二、启动配置

**1、启动master**

```shell
[root@master ~]# memcached -v -d -p 11211 -l 192.168.0.102 -u root -P /tmp/memcached1.pid
[root@master ~]# replication: listen 
[root@master ~]# replication: accept
```

**2、启动salve**

```shell
[root@slave src]# memcached -v -d -p 11211 -l 192.168.0.103 -u root -x 192.168.0.102 -P /tmp/memcached1.pid
[root@slave src]# replication: connect (peer=192.168.0.102:11212)
replication: marugoto copying
replication: start
[root@slave src]#
```

**3、回到master节点**

```shell
[root@master ~]# replication: accept  # 启动正常后，master 将 accept
```

## 三、测试

由于我们主节点和从节点的memcached服务都启动了，并且监听也都正常，所以以下的测试操作全部放到master节点进行。

```shell
[root@master ~]# telnet 192.168.0.102 11211    # 连接到我们主节点，添加一个记录
Trying 192.168.0.102...
Connected to 192.168.0.102.
Escape character is '^]'.
set key 0 0 6
sunsky
STORED
quit
Connection closed by foreign host.


[root@master ~]# telnet 192.168.0.103 11211    # 连接到我们的从节点，查看主节点的记录是否同步过来
Trying 192.168.0.103...
Connected to 192.168.0.103.
Escape character is '^]'.
get key
VALUE key 0 6
sunsky
END
quit
Connection closed by foreign host.


[root@master ~]# pkill memcached    # 现在，杀掉我们主节点的memcached进程
replication: cleanup start
replication: close
replication: cleanup complete


[root@slave src]# replication: close  # 备节点此时变为监听状态，即变成了主节点
replication: listen


[root@master ~]# telnet 192.168.0.103 11211  # 查看从节点上面的数据是否还存在
Trying 192.168.0.103...
Connected to 192.168.0.103.
Escape character is '^]'.
get key
VALUE key 0 6
sunsky
END
quit
Connection closed by foreign host.


[root@master ~]# memcached -v -d -p 11211 -l 192.168.0.102 -u root -x 192.168.0.103 -P /tmp/memcached.pid  
#  由于memcached的主/从没有抢占功能，因此主恢复之后，只能作为现有主节点的从节点
[root@master ~]# replication: connect (peer=192.168.0.103:11212)
replication: marugoto copying
replication: start


[root@slave src]# replication: accept    # 在上面加入之后，下面之前的从节点就会蹦出如下输入，表示开启同步
replication: marugoto start
replication: marugoto 1
replication: marugoto owari


[root@master ~]# telnet 192.168.0.102 11211  # 我们连接到刚刚恢复的节点，可以看到数据又回来了
Trying 192.168.0.102...
Connected to 192.168.0.102.
Escape character is '^]'.
get key
VALUE key 0 6
sunsky
END
quit
Connection closed by foreign host.
```

 	以上就是我们做的关于memcached基于repcached的主从复制实验了。通过实验，我们可以看出，通过他我们实现了主从中任何一个宕机，都不会影响另外一台机器上的数据。



## 四、总结

在文章最后，我们再来总结以下基于 Repcached 的 Memcached 主从的优缺点：

**优点：**

1、能够实现 cache 的冗余功能

2、主从之间可以互相读写

**缺点：**

1、只可以一主一从，单对单