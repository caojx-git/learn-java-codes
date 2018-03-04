# Magent搭建Memcached集群

[TOC]

原文：http://blog.51cto.com/ultrasql/1636374

## 一、Memcached集群介绍

由于Memcached服务器与服务器之间没有任何通讯，并且不进行任何数据复制备份，所以当任何服务器节点出现故障时，会出现单点故障，如果需要实现HA，则需要通过另外的方式来解决。

通过Magent缓存代理，防止单点现象，缓存代理也可以做备份，通过客户端连接到缓存代理服务器，缓存代理服务器连接缓存连接服务器，缓存代理服务器可以连接多台Memcached机器可以将每台Memcached机器进行数据同步。如果其中一台缓存服务器down机，系统依然可以继续工作，如果其中一台Memcached机器down掉，数据不会丢失并且可以保证数据的完整性。

 

## 二、Memcached集群搭建

Magent的架构方案已经在上一篇博文[《Magent介绍》](http://ultrasql.blog.51cto.com/9591438/1633897)中有详细描述。现以如下图示例架构方案说明Magent如何搭建Memcached集群，而在生产环境需要根据自身业务特点设计健壮的架构方案。

![](../images/memcached/memcached_magent_1.jpg)  

现有测试机：192.168.11.51/52/68

先在三台测试机上安装好libevent和memcached，启动memcached实例；

然后在51和52上安装好magent，启动magent实例。



### 2.1 安装和启动Memcached实例

详细步骤，请参见之前的博文[《Memcached 1.4.22安装和配置》](http://ultrasql.blog.51cto.com/9591438/1632179)，分别启动如下实例：

```shell
/usr/local/bin/memcached -d -m 256 -u memcached -l 192.168.11.51 -p 11211 -c 1024 -P /var/run/memcached/memcached.pid
/usr/local/bin/memcached -d -m 256 -u memcached -l 192.168.11.52 -p 11211 -c 1024 -P /var/run/memcached/memcached.pid
/usr/local/bin/memcached -d -m 256 -u memcached -l 192.168.11.68 -p 11211 -c 1024 -P /var/run/memcached/memcached.pid
```

### 2.2 安装和启动Magent实例

笔者在测试magent-0.6.tar.gz时，该版本在与最新版memcached运行下不够稳定，如下配置以magent-0.5.tar.gz为示例。

**1. 安装magent到/usr/local下**
```shell
cd /usr/local    
mkdir magent    
cd magent    
wget http://memagent.googlecode.com/files/magent-0.5.tar.gz    
（若无法直接访问，可先下载安装包后上传到服务器上）    
tar zxvf magent-0.5.tar.gz
```

**2.修改配置**

在ketama.h文件开头添加    

```shell
#ifndef SSIZE_MAX    
#define SSIZE_MAX 32767    
#endif
```

```shell
ln -s /usr/lib64/libm.so /usr/lib64/libm.a
/sbin/ldconfig
sed -i "s#LIBS = -levent#LIBS = -levent -lm#g" Makefile    
vi Makefile
```

将    

```shell
CFLAGS = -Wall -O2 -g
```

修改为：    

```shell
CFLAGS = -lrt -Wall -O2 -g
```
**3. 编译 **

```shell
make
```

输出如下信息：  

```shell
gcc -lrt -Wall -O2 -g -c -o magent.o magent.c    
gcc -lrt -Wall -O2 -g -c -o ketama.o ketama.c    
gcc -lrt -Wall -O2 -g -o magent magent.o ketama.o -levent –lm
```

**4. 查看命令帮助**

```shell
#./magent –h
memcached agent v0.4 Build-Date: Apr 21 2015 09:21:10    
Usage:    
-h this message    
-u uid    
-g gid    
-p port, default is 11211. (0 to disable tcp support)    
-s ip:port, set memcached server ip and port    
-b ip:port, set backup memcached server ip and port    
-l ip, local bind ip address, default is 0.0.0.0    
-n number, set max connections, default is 4096    
-D don't go to background    
-k use ketama key allocation algorithm    
-f file, unix socket path to listen on. default is off    
-i number, set max keep alive connections for one memcached server, default is 20    
-v verbose

```

**5. 启动magent实例**

```shell
/usr/local/magent/magent -u root -n 4096 -l 192.168.11.51 -p 11200 -s 192.168.11.51:11211 -s 192.168.11.52:11211 -b 192.168.11.68:11211    
/usr/local/magent/magent -u root -n 4096 -l 192.168.11.52 -p 11200 -s 192.168.11.51:11211 -s 192.168.11.52:11211 -b 192.168.11.68:11211
```



## 三、测试流程

登录51上的magent，存储key1到key5：

```shell
[root@mongo01 ~]# telnet 192.168.11.51 11200
Trying 192.168.11.51...
Connected to 192.168.11.51.
Escape character is '^]'.
stats
memcached agent v0.4
matrix 1 -> 192.168.11.51:11211, pool size 0
matrix 2 -> 192.168.11.52:11211, pool size 0
END
set key1 0 0 1
1
STORED
set key2 0 0 2
22
STORED
set key3 0 0 3
333
STORED
set key4 0 0 4
4444
STORED
set key5 0 0 5
55555
STORED
quit
Connection closed by foreign host.
```

登录到51上的memcached，获取到了key2和key4：

```shell
[root@mongo01 ~]# telnet 192.168.11.51 11211
Trying 192.168.11.51...
Connected to 192.168.11.51.
Escape character is '^]'.
get key1
END
get key2
VALUE key2 0 2
22
END
get key3
END
get key4
VALUE key4 0 4
4444
END
get key5
END
quit
Connection closed by foreign host.
```

登录到52上的memcached，获取到了key1、key3和key5：

```shell
[root@mongo02 ~]# telnet 192.168.11.52 11211
Trying 192.168.11.52...
Connected to 192.168.11.52.
Escape character is '^]'.
get key1
VALUE key1 0 1
1
END
get key2
END
get key3
VALUE key3 0 3
333
END
get key4
END
get key5
VALUE key5 0 5
55555
END
quit
Connection closed by foreign host.
```

登录到68上的memcached，获取到了key1到key5:

```shell
[root@szlnmp01 ~]# telnet 192.168.11.68 11211
Trying 192.168.11.68...
Connected to 192.168.11.68.
Escape character is '^]'.
get key1
VALUE key1 0 1
1
END
get key2
VALUE key2 0 2
22
END
get key3
VALUE key3 0 3
333
END
get key4
VALUE key4 0 4
4444
END
get key5
VALUE key5 0 5
55555
END
quit
Connection closed by foreign host.
```

停掉52的memcached进程，通过51上的magent获取到了key1到key5：

```shell
kill `cat /var/run/memcached/memcached.pid`
[root@mongo01 magent]# telnet 192.168.11.51 11200
Trying 192.168.11.51...
Connected to 192.168.11.51.
Escape character is '^]'.
get key1
VALUE key1 0 1
1
END
get key2
VALUE key2 0 2
22
END
get key3
VALUE key3 0 3
333
END
get key4
VALUE key4 0 4
4444
END
get key5
VALUE key5 0 5
55555
END
quit
Connection closed by foreign host.
```

恢复52的memcached进程，通过51上的magent，只获取到了key2和key4：

```shell
[root@mongo01 ~]# telnet 192.168.11.51 11200
Trying 192.168.11.51...
Connected to 192.168.11.51.
Escape character is '^]'.
get key1
END
get key2
VALUE key2 0 2
22
END
get key3
END
get key4
VALUE key4 0 4
4444
END
get key5
END
quit
Connection closed by foreign host.
```

通过以上测试可以得出结论：

1. 通过magent的连接池存放的值会分别存在magent代理的所有memcached上去。
2. 如果有一个memcached宕机通过magent代理方式还能取到值。
3. 如果memcached修复重启后通过magent代理方式取到的值就会为Null，这是由于memcache重启后里边的值随着memcache服务的停止就消失了（因为在内存中），但是magent是通过key进行哈希计算分配到某台机器上的，memcache重启后会还从这台机器上取值，所有取到的值就为空。

解决办法：

1. 在每次memcache宕机修复后可以写一个程序把集群中的其他memcache的所有信息全给拷贝到当前宕机修复后的memcache中。
2. 自己写代理，当从一个memcached服务上取到的值为Null时再去其他memcached上取值。

注意事项：

magent的调用方式同memcached一样，客户端可以不用改代码即可实现切换到magent模式下。



## 四、缓存与DB的同步

比较保险的做法是：查询的时候从缓存中取，add、updae、delete的时候同时操作缓存与DB。

当然你也可以定时同步缓存与DB的数据，不同的业务应该有不同的选择。

## 五、magent-0.6版本相关的错误汇总

1. 产生如下错误：   

```shell
gcc -Wall -g -O2 -I/usr/local/include -m64 -c -o magent.o magent.c    
magent.c: In function ‘writev_list’:    
magent.c:729: error: ‘SSIZE_MAX’ undeclared (first use in this function)    
magent.c:729: error: (Each undeclared identifier is reported only once    
magent.c:729: error: for each function it appears in.)    
make: *** [magent.o] Error 1
```

解决方法：    
在ketama.h文件开头添加    

```shell
#ifndef SSIZE_MAX    
#define SSIZE_MAX 32767    
#endif
```

再次make    

2. 产生如下错误:

```shell
gcc -Wall -g -O2 -I/usr/local/include -m64 -c -o magent.o magent.c    
gcc -Wall -g -O2 -I/usr/local/include -m64 -c -o ketama.o ketama.c    
gcc -Wall -g -O2 -I/usr/local/include -m64 -o magent magent.o ketama.o     
usr/lib64/libevent.a /usr/lib64/libm.a     
gcc: /usr/lib64/libevent.a: No such file or directory    
gcc: /usr/lib64/libm.a: No such file or directory    
make: *** [magent] Error 1
```

解决方法：    

```shell
ln -s /usr/lib64/libm.so /usr/lib64/libm.a    
vi Makefile
```

找到LIBS = /usr/lib64/libevent.a /usr/lib64/libm.a    
按照如下格式修改：    
LIBS = /usr/<libevent的安装路径>/libevent.a /usr/lib64/libm.a    
如：LIBS = /usr/lib/libevent.a /usr/lib64/libm.a    
保存    

再次make    

3. 产生如下错误：    

```shell
gcc -Wall -g -O2 -I/usr/local/include -m64 -o magent magent.o ketama.o /usr/lib/libevent.a /usr/lib64/libm.a     
/usr/lib/libevent.a(event.o): In function `gettime':    
/tmp/libevent-2.0.22-stable/event.c:370: undefined reference to `clock_gettime'    
/usr/lib/libevent.a(event.o): In function `detect_monotonic':    
/tmp/libevent-2.0.22-stable/event.c:340: undefined reference to `clock_gettime'    
collect2: ld returned 1 exit status    
make: *** [magent] Error 1
```

解决方法：    

```shell
vi Makefile
```

将 

```shell
CFLAGS = -Wall -g -O2 -I/usr/local/include $(M64)
```

修改为：    

```shell
CFLAGS = -lrt -Wall -g -O2 -I/usr/local/include $(M64)
```

保存    

再次make    

输出为：    

```shell
gcc -lrt -Wall -g -O2 -I/usr/local/include -m64 -o magent magent.o ketama.o /usr/lib/libevent.a /usr/lib64/libm.a
```



原文：

http://blog.51cto.com/ultrasql/1636374

http://blog.51cto.com/ultrasql/1633897