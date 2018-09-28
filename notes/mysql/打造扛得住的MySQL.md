# 打造扛得住的MySQL

[TOC]

**说明：**



​	主要看：2.1 MySQL常见的存储引擎，六、数据库索引优化，其他的可以忽略

​	文档整理自打造扛得住的MySQL数据库架构，有些内容记录的比较浅特别是案例性的操作，由于没有这么多环境，我这边只是粗略的记录了大致过程，不过大部分内容是完整的**非常感谢老师的视频**。

​	看完本文档不会成为MySQL大神，不过相信可以在很多方面提升你使用MySQL的姿势或优化MySQL手段。



时间：2018/9/16

# 一、实例和故事

## 1.1 在双11大促中的数据库服务器

原来老师所在的公司的数据库架构比较简单，没有任何主从复制的高可用组件

![](../images/mysql/mysql_h_1.png)

![](../images/mysql/mysql_h_2.png)  



如果主服务器出现故障，很难实现故障的自动切换，必须从众多的从服务器中选择出一台新的从服务器作为主服务器，并且其他从服务器需要对新主服务器进行同步，这个操作过程是相当耗时的，由于从服务器较多，完成主从切换需要耗费半个小时左右，而且这种从服务器很多，当访问量很大的时候，对主服务器的网卡也是很大的挑战，容易引起故障。  

![](../images/mysql/mysql_h_3.png)  

## 1.2 在大促中什么影响了数据库性能

我们可以通过监控信息了解什么影响了数据库性能

> QPS & TPS

**QPS: **每秒钟处理完请求的次数；注意这里是处理完。具体是指发出请求到服务器处理完成功返回结果。

**TPS：**每秒钟处理完的事务次数。

 并发量：系统能同时处理的请求数。

 RT：响应时间，处理一次请求所需要的平均处理时间。

 计算关系：

  	QPS = 并发量 / 平均响应时间

  	并发量 = QPS * 平均响应时间

![](../images/mysql/mysql_h_4.png) 

> 并发量 & CPU 使用率

![](../images/mysql/mysql_h_5.png)  

> 磁盘IO用的是fashion IO 

老师遇到过一次IO监控过高的情况

![](../images/mysql/mysql_h_6.png)   

峰值发生在凌晨2点半左右，这个峰值可能会使得服务器性能急剧下降，造成大量堵塞，对服务器进行检查后发现是由于数据库备份远程同步计划任务造成的。

为了避免出现这种情况，建议：

![](../images/mysql/mysql_h_7.png)   



前面我们了解到了一些可能会对数据性能造成影响的因素，主要有如下：

![](../images/mysql/mysql_h_8.png)  



**并发量：同一时间处理的请求的数量** 
TPS - Transactions Per Second（每秒传输的事物处理个数），这是指服务器每秒处理的事务数，支持事务的存储引擎如InnoDB等特有的一个性能指标。 

> 计算方法： TPS = (COM_COMMIT + COM_ROLLBACK)/UPTIME

```mysql
mysql > show  global  status like 'COM_COMMIT%'; 
mysql > show  global  status like 'COM_ROLLBACK%'; 
mysql > show  global  status like 'UPTIME%'; 
```

QPS - Queries Per Second（每秒查询处理量）同时适用与InnoDB和MyISAM 引擎 

> 计算方法： QPS=QUESTIONS/UPTIME

```mysql
mysql > show global status like 'QUESTIONS%'; 
mysql > show global status like 'UPTIME%'; 
```

参考文章：

[mysql状态查看 QPS/TPS/缓存命中率查看](https://blog.csdn.net/wwytwen/article/details/42006849)

[系统吞吐量、TPS（QPS）、用户并发量、性能测试概念和公式](http://blog.csdn.net/wind19/article/details/8600083)

**超高的QPS和TPS**

超高的QPS或超高TPS可会引起SQL效率低下，在大促环境下网站的访问量会大幅上升，随着访问量上升必然会有超高的QPS和TPS，这时候每处里一个SQL所需要的时间就显得很重要了。

还有MySQL5.5版本之前并不支持多CPU的并发运算，只支持单CPU可能以后会支持

![](../images/mysql/mysql_h_9.png)  

大促来袭，访问量急速增加，对于超高的QPS和TPS，效率低下的SQL是有很大的风险的， 大部分数据库问题都是由于慢查询造成的，也就是说可以优化SQL来解决问题。

**大量的并发和超高的CPU使用率**

风险：

大量并发：数据库连接数被占满（mysql连接数限制 max_connection默认100）

超高的cpu使用率：因cpu资源耗尽而出现宕机



**磁盘IO**

风险：

磁盘IO性能突然下降（可以使用更快的磁盘设备解决）
​	
对于消耗磁盘性能的计划任务（调整计划任务，做好磁盘维护）

这种问题常常发生于：热数据远远大于服务器可用内存的情况下



**网卡流量**

风险：网卡IO被占满（1000MB/8  约等于 100MB）

如何避免无法连接数据库的情况：

1. 减少从服务器的数量（因为每一个从服务器都需要从主服务器去复制日志，从服务器越多流量越大）
2. 进行缓存分级 （避免缓存大量失效直接对数据库进行冲击）
3. 避免使用 『select *』 进行查询（查询出没有必要的列也会浪费网络流量）
4. 分离业务网络和服务器网络（避免由于主从同步或者网络备份等作业的影响我们的网络性能）

## 1.3 大表带来的问题

> 什么表可以称之为大表？

 MySQL并没有限制说每个表最大行数是多少，存储数据量是多少，至少可以说只要存储空间允许就可以把数据存到MySQL中。

我们大致可以从两个维度来说明什么大表：

- 记录行数巨大，超过千万行
- 表数据文件巨大，表数据文件超过10G

1. 大表对查询的影响

**案例：**

比如老师之前所在公司遇到过一个表，该表记录有上亿条数据

![](../images/mysql/mysql_h_10.png) 



订单来源只有四个，区分度很低，要从上亿条数据中查找一小部分数据，将会产生大量的磁盘IO。如果我们需要在页面上显示的渠道来源的订单，以促进用户购买那么这个查询就会在用户每次访问商品时都会被执行，这样就会产生大量的慢查询从而严重的拖慢了网站的访问，从而很容易将数据库拖垮。这样的SQL是需要优化的。

![](../images/mysql/mysql_h_11.png)    



2. 大表对DDL的影响

![](../images/mysql/mysql_h_12.png)    

5.5 之前的版本

![](../images/mysql/mysql_h_13.png)  



3. 如何处理数据库中的大表

比如频繁更新的订单日志表，如果在更新数据时修改表结构，会导致数据连接数突然猛增，连接数被沾满，500错误。
解决思路：传说中的分库分表 

![](../images/mysql/mysql_h_14.png)  



![](../images/mysql/mysql_h_15.png)  



## 1.4 大事务带来的问题

> 什么是大事务：运行时间比较长，操作的数据比较多的事务

风险：

- 锁定太多的数据

- 造成大量的阻塞和锁超时，回滚时所需要的时间比较长
- 执行时间长，容易造成主从延迟	



![](../images/mysql/mysql_h_16.png)  

> 事务的ACID特性

**事务的原子性（ATOMICITY）**

定义：一个事务必须被视为一个不可分割的最小工作单元，整个事务中的所有操作要么全部提交成功，要么全部失败，对于一个事务来说，不可能只执行其中的一部分操作。

**事务的一致性(CONSISTENCY)**

定义：一致性是指事务将数据从一种一致性状态转换到另外一种一致性状态，在事务开始之前和事务结束之后，数据库中的数据的完整性没有被破坏。

**事务的隔离性（ISOLATION）**

隔离性要求一个事务对数据库中的数据的修改，在未提交完成前对其他事务不可见的。

SQL标准中定义的四种隔离级别：

- 未提交读（READ UNCOMMITED）
- 已提交读（READ COMMITED）
- 可重复读（REPEATABLE READ）
- 可串行化（SERIALIZABLE）

已提交读（不重复读）和可重复读的区别是 :
  可重复读（一个用户在其事务没有结束时不可以得到另一个用户已经执行完的事务的结果），但是已提交读是可以的。 
 注意：隔离性最高，并发性最低，串行化会在读取的每一行上多加锁，所以可能会出现比较多的锁超时事件 

**事务的持久性（DURABILITY）**

定义：一旦事务提交，则其所做的修改就会永久保存到数据库中，此时即使系统崩溃，已经提交的数据也不会丢失。

```sql
#查看事务的结构
show variables like '%iso%';
更改事务结构
#set session tx_isolation='事务类型';
```

**如何处理大事务：**

1. 避免一次处理太多的数据
2. 移除不需要事务的SELECT

## 1.5 总结

直观的展示了数据库在繁忙时的系统状态，简单了解了对性能有影响的一些因素。



# 二、什么影响了MySQL性能

## 2.1 影响性能的几个方面

1. 服务器硬件

2. 操作系统

3. 数据库存储引擎的选择

  MyISAM：不支持事务，表级锁。

  InnoDB: 事务级存储引擎，完美支持行级锁，事务ACID特性。

4. **数据库参数配置影响最大**

5. **数据库表结构设计和SQL语句**

## 2.2 CPU资源和可用内存大小

系统繁忙的时候我们可以监控到系统cpu资源是比较紧张的，特别是对于一些计算密集型的应用。所首先对MySQL性能有影响的是CPU资源和可用内存大小。当我们的热数据的大小远远超过我们可用内存大小时I/O就会成为我们的性能瓶颈，所以当出现I/O系统出现瓶颈时我们可以通过升级I/O子系统解决。网络和I/O资源也是对数据库有影响的第二个因素。

>**服务器硬件对性能的影响**

**cpu:**

1. 如何选择CPU 更快或更多? 如果是CPU密集型的应用我们应该选择更快的CPU，因为目前MySQL（5.5版本之前）不支持多CPU对同一SQL的并发处理。
2. 系统的并发量：如果需要提高系统的吞吐量，那么建议CPU越多越好。
3. 64位CPU应该使用64位的服务器版本不要使用32位的服务器版本

**内存：**

1. 在MyISAM存储引擎中会将索引保存到内存中，而数据通过操作系统进行缓存
2. InnoDB会同时在内存中缓存数据和索引从而提高数据库的运行效率
3. 内存越多越好，但是对性能影响是有限的，并不能无限增加性能，比如只有64G的数据，却有128G的内存，最多也就使用到64G的内存。
4. 选择内存时应该选择服务器支持的最大内存主频
5. 服务器内存尽量每个通道的内存是相同的品牌、颗粒、频率、电压、校验技术和型号
6. 单条容量要尽可能大
7. 内存适合当前数据量，和未来增加量

## 2.3 使用RAID增加传统机器硬盘的性能

什么是RAID:

RAID是磁盘冗余队列的简称（Redundant Arrays of Independent Disks）简单来说RAID的作用就是可以把多个容量较小的磁盘组成一个更大的磁盘，并提供数据冗余来保证数据的完整性的技术。



RAID级别：

参考：[RAID各级别特性]() https://blog.csdn.net/song0156/article/details/51694613)

常见的RAID级别：

![](../images/mysql/mysql_h_18.png)    

![](../images/mysql/mysql_h_17.png)



![](../images/mysql/mysql_h_19.png)  

## 2.4 使用固态存储SSD或PCIe卡

**优点：**

相比机械磁盘固态磁盘有更好的随机读写性能
​	
相比机械磁盘固态磁盘更好的支持并发

**缺点：**

相比机械磁盘固态硬盘更容易损坏

## 2.5 使用网络存储SAN和NAS

NAS设备使用网络连接，通过基于文件的协如NFS或SMB来访问，由于是通过网络来访问的，通常存在一定的网络延迟。还有对于大多数开发人员来说，对于网络不是很熟悉，如果出现故障，排查时间过长。但是网络存储适用于数据备份。 



网络设备对于性能的限制存主要在网络延迟和吞吐量（带宽）两个方面影响。

**网络对性能的影响：**

- 网络带宽对性能的影响
- 网络质量对性能的影响

**建议：**

- 采用高性能和高带宽的网络接口设备和交换机
- 对多个网卡进行绑定，增强可用性和带宽
- 尽可能的进行网络隔离 ，避免外网直接冲击数据库，将数据库部署在内网中

## 2.7 总结：服务器硬件对性能的影响

服务器硬件对性能的影响：

**CPU**

- 64位的CPU一定要工作在64位的系统下
- 对于高并发的场景CPU的数量比频率更重要
- 对于CPU密集型的场景和复杂SQL则频率越高越好

**内存：**

- 选择主板所能使用的最高频率的内存
- 内存的大小对性能很重要，所以尽可能的大

**I/O子系统：**

- PCIe->SSD->Raid10->磁盘->SAN

## 2.8 CentOS系统参数优化

CentOS是一款非常优秀的Linux操作系统，具有非常多的可以优化的参数，这里老师只是介绍了其中很少很重要的一部分。其他可以参见书本《Linux性能优化大师》

1.内核相关参数（/etc/sysctl.conf）

**网络相关参数(/etc/sysctl.conf)：**

- net.core.somaxconn=65535  每个端口最大连接数
- net.core.netdev_max_backlog=65535 接收数据包的速率比内核处理快的时候，允许发送到队列中的数据包的最大数量
- net.ipv4.tcp_max_syn_backlog=65535  允许还未获得连接的请求可以保存到队列中的最大数目
- net.ipv4.tcp_fin_timeout=10 用于控制tcp连接处理的等待状态的时间，对于连接比较频繁的系统通常会有大量的连接处于等待状态，该参数用于减少这种time_out的等待时间，将会提升tcp的回收速度。
- net.ipv4.tcp_tw_reuse=1 加快tcp连接的回收
- net.ipv4.tcp_tw_recycle=1 加快tcp连接的回收




以下4个参数决定tcp连接接收和发送缓存区大小的默认值和最大值，应该调整稍大一些。
- net.core.wmem_default=87380
- net.core.wmem_max=16777216
- net.core.rmem_default=87380	
- net.core.rmem_max=16777216

以下3个参数用于减少失效连接占用tcp资源的数量，加快资源回收数量，不应该太大
- net.ipv4.tcp_keepalive_time=120 发送tcp探测的时间间隔用于探测tcp连接是否有效，单位s
- net.ipv4.tcp_keepalive_intvl=30 用于当探测tcp消息未获得响应时重发该消息的时间间隔，单位s
- net.ipv4.tcp_keepalive_probes=3 表示在鉴定tcp消息失效之前最多发送多少个探测消息



**内存相关参数(/etc/sysctl.conf)：**

- kernel.shmmax=4294967295   Linux内核中最重要的参数之一，用于定义单个共享内存段的最大值，这个参数应该设置足够大，以便（尽可能）能在一个共享内存段下容纳下整个的Innodb缓冲池大小。
- vm.swappiness=0  在Linux系统安装时都会有一个特殊的磁盘分区，称为系统交换分区。这个参数当内存不足时会对性能产生比较明显的影响。free -m  命令可以查看交换分区的大小

如果没有交换分区可能会存在以下两个问题：

  		1. 降低操作系统的性能
  		2. 容易造成内存溢出，崩溃，或都被操作系统kill掉

结论：在MySQL服务器上保留交换分区还是很有必要的，但是要控制什么时候使用交换分区

vm.swappiness=0 就是告诉Linux内核除非虚拟内存完全满了，否则不要使用交换分区。



**增加资源限制(/ect/security/limit.conf)**

添加到limit.conf文件末尾，增加打开文件数量限制

- **\* soft nofile 65535**

- **\* hard nofile 65535**

注意：

\* 表示对所有用户有效

soft 指的是当前操作系统生效的设置

hard 表明系统中所能设定的最大值

nofile 表示所限制的资源时打开文件的最大数目

65535 就是限制的数量

结论：可以打开的文件数量增加到了65535个以保证可以打开足够多的文件句柄

注意：这个文件的修改需要重启系统才可以生效。



**磁盘调度策略（/sys/block/devname/scheduler）**

```shell
#查看目前磁盘所使用的调度策略
#cat /sys/block/devname/scheduler
noop anticipatory deadline [cfq]
```

如上所示，目前使用的调度策略是cfq，这个策略用于桌面级别的系统是没有问题的，但是用于MySQL数据库服务器就不太合适。



其他几种磁盘调度策略：

**noop(电梯式调度策略)**：NOOP实现了一个FIFO队列，它像电梯的工作方法一样I/O请求进行组织，当有一个新的请求到来时，它将请求合并到最近的请求之后，以此来保证请求同一介质。NOOP倾向饿死读而利于写，因此NOOP对于闪存设备、RAM以及嵌入式系统是最好的选择。

**deadline(截止时间调度策略)**：Deadline确保了在一个截止时间内服务请求，这个介质时间是可以调整，而默认读期限短于写期限。这样就防止了写操作因为不不能被读取而出现饿死的现象，Deadline对于数据库类应用是最好的选择。

**anticipatory(预料I/O调度策略)**：本质上与Deadline一样，但在最后一次读操作后要等待6ms，才能继续进行其对其他I/O请求进行调度。他会在每个6ms中插入一个新的I/O操作，而会将一些小写入流合并成一个大写入流，用写入延时换取最大的写入吞吐量。适合于写入较多的环境，比如文件服务器，对数据库环境表现很差。



> 修改磁盘调度策略为deadline：

```shell
echo deadline > /sys/block/sda/queue/scheduler
```



# 三、MySQL性能管理以及架构设计

## 2.1 MySQL常见存储引擎

### 1. MySQL常见存储引擎MyISAM

**MySQL5.5之前的版本默认存储引擎是MyISAM**，由于这个原因现在还有大量的服务器使用MyISAM存储引擎。MyISAM也是MySQL大部分系统表和临时表所使用的一种存储引擎。

**注意：这里所说的临时表不是我们create table那种临时表，是指在排序、分组等操作中，当数量超过一定的大小之后，由优化查询器构建的临时表。**

 

MyISAM存储引擎会将数据存储在两个系统文件中，数据文件（MYD ）和索引文件（MYI）。

> 建立MyISAM存储引擎的表

```mysql
create table myIsam(
	id int(11) default null,
    c1 varchar(10) default null
) ENGINE=MyISAM DEFAULT CHARSET=utf8
```

> 下边是MyISAM存储引擎的存储方式的文件

```shell
#ls -l
myIsam.frm  #MySQL所有的存储引擎都会生成这个文件，用于记录表的结构
myIsam.MYD  #MyISAM存储引擎特有的文件，数据文件
myIsam.MYI  #MyISAM存储引擎特有的文件，索引文件
```

**MyISAM特性：**

 - 并发性与锁级别

   MyISAM使用的是表级锁，不是行级锁，如果对表中的数据进行修改时，需要对整个表进行加锁，而对表中的数据进行读取时也需要加共享锁，由此可见MyISAM对于读写的混合操作并不会太好。

   如果对于只读的操作来说，并发性还是可以接受的，因为共享锁不会阻塞共享锁。

- 表损坏修复

  MyISAM支持对由于任意关闭而损坏的MyISAM进行检查和修复操作（注意：这里说的修复不是事务恢复，因为MyISAM并不是一种事务型的存储引擎，所以不可能进行事务恢复所需要的相关日志），所以对MyISAM型的表进行修复可能会造成数据的丢失，我们可以通过如下命令对表进行检查和修复。

  ```mysql
  mysql>check table tablename; #检查表
  mysql>repair table tablename; #恢复表
  ```

- MyISAM表支持的索引类型

  MyISAM支持全文索引，是MySQL5.7版本之前唯一官方支持全文索引的存储引擎，MyISAM还支持对text以及Blob等类型字段的前500个字符的前缀索引。

- MyISAM表支持数据压缩

  如果MyISAM是一张很大的只读表，即在表创建完导入数据后就不会对表进行任何修改操作那么我们就可以对这样的表进行压缩操作，可以节约磁盘空间，不过压缩后的表只能读。

  可以使用：**myisampack** 命令压缩表中的数据，由于myisampack压缩是独立单行压缩的，所以读取数据时不用对整个表进行解压。



**MyISAM引擎的限制：**

- MySQL5.0之前单表默认表大小为4G
- 如存储大表则要修改MAX_Rows和AVG_ROW_LENGTH，最大容量=MAX_Rows * AVG_ROW_LENGTH
- MySQL5.0之后默认支持为256TB

**使用场景：**

 - 非事务型应用
 - 适合只读类应用
 - 空间类应用，MySQL版本5.7之前唯一支持空间函数的的存储引擎

### 2. MySQL常见存储引擎Innodb

MySQL5.5之后默认的存储引擎使用Innodb，Innodb是一种事务型的存储引擎，适合处理更多的小事务。

Innodb具有表空间概念，数据是存储在Innodb的表空间中，具体存储在什么表空间中由**innodb_file_per_table**这个参数决定，MySQL5.5版本之前数据默认存储在系统表空间，即**innodb_file_per_table=OFF**。

Innodb_file_per_table

- ON:独立表空间：tablename.ibd (默认)

- OFF:系统表空间：ibdataX  (x代表的是一个数字)

```mysql
mysql> show variables like 'innodb_file_per_table';
+-----------------------+-------+
| Variable_name         | Value |
+-----------------------+-------+
| innodb_file_per_table | ON    |
+-----------------------+-------+
1 row in set (0.01 sec)

>set global innodb_file_per_table=off;  #关闭后建表使用系统表空间
```



**通过设置innodb_file_per_table这个参数值会影响表空间存储，那么系统表空间和独立表空间要如何选择？**

**比较：**

- 系统表空间无法简单的收缩文件大小，造成大量空间浪费(即使删除了表数据，但是表空间依然存在)
- 独立表空间可以通过optimize table 命令收缩系统文件，不需要重启数据库服务器
- 系统表空间会产生IO瓶颈，因为系统表空间只有一个独立的文件
- 独立表空间可以同时向多个文件刷新数据，利用多个文件增加IO处理的性能

**建议：**

- 强烈建议使用Innodb独立表空间，MySQL5.6后默认使用独立表空间



**怎么把原来存在于系统表空间中的表转移到独立表空间中？**

**步骤：**

1. 使用mysqldump导出所有数据库表数据
2. 停止MySQL服务，修改参数，并删除Innodb相关文件（因为已经备份）
3. 重启MySQL服务，重建Innodb系统表空间
4. 重新导入数据



### 3. Innodb存储引擎特性1

我们将系统表空间中的数据转存到独立表空间后，系统表空间还有什么作用呢？

 系统表空间还有一部分很重要的东西需要存储：

1. 比如Innodb的数据字典，数据字典存放与数据库对象相关的信息如表、列、索引等内容
2. Undo 回滚段



Innodb存储引擎的特性：

- Innodb是一种事务型的存储引擎，完全支持事务的ACID特性
- Innodb为了实现事务特性，Innodb使用了2个特殊的日志类型Redo Log重做日志(主要用于实现事务的持久性)和Undo Log 回滚日志（主要用于未提交事务的回滚）

查看MySQL Innodb配置情况：

```mysql
mysql> show variables like 'innodb_log_buffer_size';
+------------------------+----------+
| Variable_name          | Value    |
+------------------------+----------+
| innodb_log_buffer_size | 16777216 |
+------------------------+----------+
```

- 行级锁是由存储引擎层实现的

  > 什么是锁？

  ​	锁主要是管理资源的并发问题，锁用于实现事务的隔离性

  > 锁的类型？

  1. 共享锁（也称为读锁）

  2. 独占锁（也称为写锁）

  > 锁的粒度？

  1. 表级锁，如果给一个表加上表级锁，那么其他需要使用该表时需要等待解锁后才可以使用

  ```mysql
  mysql> show create table myinnodb;
  +----------+---------------------------------------------------------------------+
  | Table    | Create Table                                                                                                                |
  +----------+---------------------------------------------------------------------+
  | myinnodb | CREATE TABLE `myinnodb` (
    `id` int(11) DEFAULT NULL,
    `c1` varchar(10) DEFAULT NULL
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 |
  +----------+---------------------------------------------------------------------+
  1 row in set (0.01 sec)	
  >lock table myinnodb write;  #添加表级锁后在另外一个连接中将不能使用该表(查询。。)
  >unlock tables; #解锁
  ```

  2. 行级锁，行级别的锁只在存储引擎中有实现比如Innodb，不是在MySQL服务器实现。

### 4. Innodb存储引擎特性2

>阻塞和死锁？

（1）阻塞是由于资源不足引起的排队等待现象。
（2）死锁是由于两个对象在拥有一份资源的情况下申请另一份资源，而另一份资源恰好又是这两对象正持有的，导致两对象无法完成操作，且所持资源无法释放。

> Innodb状态检查

```mysql
mysql>show engine innodb status;
```

> 适用场景

Innodb适合大多数OLTP应用，MySQL5.7后的版本支持全文索引和空间函数。 		

### 5. MySQL常见存储引擎CSV

特点：

- 数据以文件的方式存储csv格式，可以直接查看，存储文件不是二进制文件。
- .csv文件存储表内容
- .csm文件存储表的元数据，如表状态和数据量
- .frm文件存储表结构信息
- **所有的列必须都不能为NULL**
- **不支持索引**
- 可以对数据文件直接编辑

```mysql
create table mycsv(id int not null, c1 varchar(10) not null, c2 char(10) not null) engine=csv;
```



适合场景：

	适合作为数据交换的中间表
	
	电子表格->CSV文件->MySQL数据目录

### 6. MySQL常见存储引擎Archive

文件系统存储特点：

- 使用zlib对表数进行压缩，磁盘I/O更少
- 数据存储在ARZ为后缀的文件中
- .frm文件存储表结构信息
- **只支持insert和select操作**
- **只允许在自增ID列上加索引**

```mysql
#建表
create table mycsv(id int auto_increment not null,
                   c1 varchar(10) not null,
                   c2 char(10) not null) engine=archive;
```



场景：

	日志和数据采集类应用

### 7. MySQL常见存储引擎Memory

特点：

- 也称为HEAP存储引擎，所有的数据保存在内存中
- 支持HASH索引（默认）和BTree索引，HASH索引适合等值查找，BTree索引适合范围查找
- 所有字段都为固定长度，即使指定了长度，长度也会变为一个固定值
- 数据文件只会.frm结尾的文件
- 不支持BLOG和TEXT等大字段
- Memory存储引擎使用表级锁
- 最大大小由max_heap_table_size参数决定

```mysql
#建表，即使指定了长度，长度也会变为一个固定值
create table mycsv(id int auto_increment not null,
                   c1 varchar(10) not null,
                   c2 char(10) not null) engine=memory;
                   
create index idx_c1 on mymemory(c1); #默认hash索引
create index idx_c2 using btree on mymemory(c2); #btree索引 
show index from mymemory \G; #查看索引
show table status like 'mymemory' \G; #查看表状态
```

容易混淆的概念：

![](../images/mysql/mysql_h_20.png)  

使用场景：

- 用于查找或者是映射表，例如邮编和地区的对应表
- 用于保存数据分析中产生的中间表
- 用于缓存周期性聚合数据的结果表

### 8. MySQL常见存储引擎Federated

特点：

- 提供了远程访问MySQL服务器上表的方法
- 本地不存出数据，数据去全部存放到远程服务器上
- 本地需要保存表结构和远程服务器的连接信息

如何使用：

- MySQL默认禁止，启动需要在启动时增加federated参数：

```mysql
>mysql://user_name[:password]@host_name[:port_num]/db_name/tbl_name
```

```mysql
#查看是否支持federated引擎
>show engines;
```

![](../images/mysql/mysql_h_21.png)  

由上图可以看出MySQL默认不支持federate引擎，如果需要开启，需要修改my.cnf文件，在文件末尾添加

```shell
federated=1
```

使用场景：

- 偶尔的统计分析以及手工查询

## 2.2 如何选择存储引擎

前面我们了解了MySQL中常见的存储引擎，以及适用的场景，那么我们如何选择什么存储引擎呢。

可以从如下因素中选择需要的存储引擎，大多数时候我们选择Innodb即可：

- 事务
- 备份
- 崩溃恢复
- 存储引擎的特性

## 2.3 MySQL服务器参数介绍

除了服务器硬件以及存储引擎对MySQL服务器性能的影响外，MySQL存在大量的可以优化的参数。

![](../images/mysql/mysql_h_22.png)  



![](../images/mysql/mysql_h_23.png)  



## 2.4 内存配置相关参数

内存对于MySQL是非常重要的，如何正确的配置MySQL内存参数就显得至关重要了。

- 确定可以使用的内存的上限，不能超过可用的物理内存

- 确定MySQL的每个连接使用的内存

  sort_buffer_size  排序缓冲区的大小

  join_buffer_size  连接缓冲区大小

  read_buffer_size 读缓冲区大小，4k的倍数

  read_rnd_buffer_size 索引缓冲区的大小

  以上4个参数都是为每个线程配置的。

- 确定需要为操作系统保留多少内存，建议一台机器只装MySQL服务

- 如何为缓存池分配内存

  Innodb_buffer_pool_size

  Key_buffer_size 这个参数主要是MyISAM缓冲大小

  ```mysql
  select sum(index_length) from information_schema.tables where engine='myisam'; #查询myisam索引暂用空间大小
  ```



## 2.5 IO相关参数配置

Innodb I/O相关配置：

- Innodb_log_file_size 控制单个事务日志的大小
- Innodb_log_files_in_group  控制事务日志的文件个数
- 事务日志总大小=Innodb_log_files_in_group * Innodb_log_file_size
- Innodb_log_buffer_size Innodb日志事务缓冲区大小，通常不用设置非常大
- Innodb_flush_log_at_trx_commit 刷新日志的频率，值如下
  - 0：每秒进行一次log写入cache，并flush log到磁盘
  - 1 默认：在每次事务提交执行log邪物cache，并flush log到磁盘
  - 2 建议：每次事务提交，执行log数据写入到cache，每秒执行一次flush log到磁盘
- Innodb_flush_method=O_DIRECT  Innodb刷新的方式
- Innodb_file_per_table = 1  设置后Innodb会为每个表建立一个单独的表空间
- Innodb_doublewrite = 1 双写缓冲，避免数据没有写完整导致数据损坏。



MyISAM I/O 相关配置：

delay_key_write:   

- OFF：每次写操作后刷新键缓冲中的脏块到磁盘 

- ON： 只对在键表时指定了delay_key_write选项的表使用延迟刷新
- ALL：对所有MYISAM表都支持延迟键写入

## 2.6 其他常用参数配置

- sync_binlog 控制MySQL如何向磁盘刷新binlog
- tmp_table_size 和max_heap_table_size 控制内存临时表的大小
- max_connections 控制允许的最大连接数

## 2.7 数据库设计对性能的影响

数据库设计对性能的影响

- 过分的反范式化的表建立太多的列
- 过分的范式化造成太多的表关联（MySQL最多关联61个表）
- 在OLTP环境中使用不恰当的分区表
- 使用外键保证数据的完整性，不建议使用外键约束

# 三、MySQL基准测试

了解基准测试，MySQL基准测试工具介绍实例演示。

## 3.1 什么是基准测试

定义：基准测试是一种测量和评估软件性能指标活动，用于建立某个时刻的性能基准，以便当系统发生软硬件变化时重新进行基准测试以评估变化对性能的影响。

我们可以认为：基准测试是针对系统设置的一种压力测试，可以观察系统在不同压力下的行为，不过基准测试与压力测试还是有一定的区别的。

![](../images/mysql/mysql_h_24.png)  

![](../images/mysql/mysql_h_25.png)    

## 3.2 如何进行基准测试

基准测试的目的：

- 建立MySQL服务器的性能基准线
- 模拟比当前系统更高的负载，以找出系统的扩展瓶颈
- 测试不同的硬件、软件和操作系统配置
- 证明新的硬件设备是否配置正确

如何进行基准测试：

- 对整个系统进行基准测试

  优点：

  1. 能够测试整个系统的性能，包括web服务器缓存，数据库等。

  2. 能够反映出系统各个组件接口间的性能问题，体现真实的性能情况

  缺点：

  1. 测试设计复杂，消耗时间长

- 单独对MySQL进行基准测试

  优点：测试设计简单，所耗费时间短

  缺点：无法全面了解整个系统的性能基线

- MySQL基准测试的常见指标

  - 单位时间内所处理的事务数（TPS）
  - 单位时间内所处理的查询数（QPS）
  - 响应时间：平均响应时间、最小响应时间、最大响应时间、各时间所占有百分比
  - 并发量：同时处理的查询请求的数量，注意并发量不等于连接数，我们关注的是正在工作中的并发操作数或同时工作的数量

## 3.3 基准测试演示实例

进行基准测试的步骤：

- 对整个系统还是某一个组件，比如只针对MySQL
- 使用什么样的数据
- 准备基准测试以及数据收集脚本，比如CPU使用率、IO、网络流量、状态与计数器信息等
- 运行基准测试

- 保存以及分析基准测试结果



基准测试时容易忽略的问题：

- 只使用了生产环境数据的部分数据
- 在多用户的场景中，只做了单用户的测试，推荐使用多线程并发测试
- 在单服务器上测试分布式应用，应该使用相同的架构进行测试
- 反复进行同一查询，容易缓存命中，无法反映真实的查询性能

## 3.4 MySQL基准测试工具之mysqlslap

**注意：mysqlslap具体使用案例请自行查找其他文章**

MySQL基准测试工作之 mysqlslap: MySQL服务器自带的基准测试工具，随MySQL一起安装

特点：

1. 模拟服务器负载， 并输出相关统计信息
2. 可以指定也可以自动生成查询语句

mysqlslap常用参数说明：

- --auto-generate-sql 由系统自动生成SQL脚本进行测试
- --auto-generate-sql-add-autoincrement 在生成的表中增加自增ID
- --auto-generate-sql-load-type 指定测试中使用的查询类型
- --auto-generate-sql-write-number 指定初始化数据时生成的数据量 
- --concurrency 指定并发线程的数量
- --engine 指定要测试表的存储引擎，可以用逗号分割多个存储引擎
- --no-drop 指定不清理测试数据
- --iterations 指定测试运行的次数
- --number-of-queries 指定每一个线程执行的查询数量
- --debug-info 指定输出额外的内存以及CPU统计信息
- --number-int-cols 指定测试表中包含的INT类型的数量
- --number-char-cols 指定测试表中包含的varchar类型的数量
- --create-schame 指定了用于执行测试的数据库的名字
- --query 用于指定自定义SQL的脚本，不使用自动生成的脚本进行测试
- --only-print 并不运行测试脚本，而是把生成的脚本打印出来

```shell
#mysqlslap --concurrency=1,50,100,200 --iterations=3 --number-int-cols=5 --number-char-cols=5 --auto-generate-sql -auto-generate-sql-add-autoincrement --engine=myisam,innodb  --number-of-queries=10 --create-schema=test -hlocalhost -uroot -proot -P3306;
```



## 3.5 MySQL基准测试工具之sysbench

安装说明：https://github.com/akopytov/sysbench

sysbench常用参数：

- --test 用于指定所需要执行类型，支持一下参数

  fileio 文件系统I/O性能测试

  cpu cpu性能测试

  memory 内存性能测试

- --oltp 测试要指定具体的lua脚本

  lua脚本位于 sysbench-0.5/sysbench/tests/db

- --mysql-db用于指定执行基准测试的数据库名称

- --mysql-table-engine 用于执行所使用的存储引擎

- --oltp-tables-count 执行测试的表的数量

- --oltp-talbe-size 指定每个表中的数据行数

- --num-threads 指定测试的并发线程数量

- --max-time 指定最大的测试时间

- --report-interval 指定时间间隔多长时间爱你输出一次统计信息

- --mysql-user 指定执行测试的MySQL用户

- --mysql-password 指定执行测试的MySQL用户密码 

- --prepare 用户准备测试数据

- --run用于实际进行测试

- --cleanup 用于清理测试数据

## 3.6 sybench基准测试演示实例

参见视频：https://v.youku.com/v_show/id_XMzIzMDc1MDQzMg==.html?spm=a2h7l.searchresults.soresults.dtitle

**注意：sybench具体使用案例请自行查找其他文章**

# 四、MySQL数据结构优化

## 4.1 数据库结构优化介绍

前面了解了影响MySQL性能的因素有服务器硬件、操作系统、MySQL服务器配置，但总的来说影响MySQL性能最大的因素是MySQL数据结构，良好的数据库逻辑设计和物理设计是数据库获得高性能的基础。

数据库结构优化的目的：

- 减少数据冗余，适当的时候也可冗余

- 尽量避免数据维护中出现更新，插入和删除异常

  插入异常：如表中的某个实体随着另一个实体的存在而存在

  更新异常：如更改表中的某个实体的单独属性时，需要对多行进行更新

  删除异常：如删除表中的某一实体时，会导致其他实体的消失

- 节约数据的存储空间

- 提高查询的效率

## 4.2 数据库结构设计

数据库设计步骤：

- 需求分析：全面了解产品设计的存储需求（存储需求、数据处理需求、数据的安全性和完整性）​	 
- 逻辑设计：
    1. 设计数据的逻辑存储结构
    2. 了解数据实体之间的逻辑关系，解决数据冗余和数据维护的异常
- 物理设计：选择合适的数据库，根据所使用的数据库特点进行表结构设计
   1. 关系型数据库：Oracle、SQLServer、MySQL、PostgresSQL
   2. 非关系型数据库：Mongo、Redis、Hadoop

- 维护优化：根据实际情况对索引、存储结构等进行优化


> 数据库设计范式

- 数据库设计的第一范式
  1. 数据库表中的所有字段都只具有单一属性

  2. 单一属性的列是由基本的数据类型所构成的

  3. 设计出来的表都是简单的二维表
- 数据库设计的第二范式
  1. 数据库第二范式是在第一范式的基础上演化的，即第二范式满足第一范式

  2. 要求一个表中只具有一个业务主键，也就是说符合第二范式的表中不能存在非主键列只对部分主键的依赖关系
- 数据设计的第三范式

  1. 指每一个非主属性既不部分依赖于也不传递依赖于业务主键，也就是在第二范式的基础上消除了非主属性对主键的传递依赖。

## 4.4 需求分析以及逻辑设计-反范式设计

什么叫做反范式化

​	反范式化是针对范式化而言的，在前面介绍了数据库设计的范式，所谓的反范式化就是为了性能和读取效率的考虑而适当的对数据库设计范式的要求进行违反，而允许存在少量数据冗余，换句话来说反范式化就是使用空间来换时间。


## 4.5 范式化设计设计和反范式化设计优缺点

范式化：

优点：

1. 可以尽量减少数据冗余，数据表更新快体积小

2. 范式化的更新操纵比反范式化更快

3. 范式化的表通常比反范式化更小

缺点：

1. 查询经常需要多个表进行关联
2. 更难的索引优化



反范式化：

优点：

1. 可以减少表的关联
2. 可以更好的索引优化

缺点：

1. 存在数据冗余以及数据维护异常

2. 对数据修改需要更多的成本


**建议：实际设计中应该综合范式化和反范式化的优缺点综合考虑 **

## 4.6 物理设计介绍

根据所选择的关系型数据库的特点对逻辑模型进行存储结构设计，这里主要是MySQL

物理设计的内容

- 定义数据库、表字段的命名规范
- 选择合适的存储引擎
- 为表的字段选择合适的数据类型
- 建立数据库结构

定义数据库、表以及字段的命名规范

- 数据库、表字段的命名要遵循可读性原则
- 数据库、表字段的命名要遵循可表意性原则
- 数据库、表字段的命名要遵循长命名原则

选择合适的存储引擎：

![](../images/mysql/mysql_h_26.png)  

## 4.7 物理设计-数据类型的选择

> 为表中的字段选择合适的数据类型

当一个列可以选择多种数据类型是，应该优先考虑数字类型，其次是日期或二进制类型，最后是字符类型。对于相同级别的数据类型，应该优先选择占用空间小的数据类型。

> 如何选择正确的整数类型

![](../images/mysql/mysql_h_27.png)    

> 如何选择正确的实数类型

![](../images/mysql/mysql_h_28.png)  

> 如何选择VARCHAR和CHAR类型

VARCHAR类型的存储特点：

- varchar用于存储变长字符串，只占用必要的存储空间
- varchar和char的存储但是是以字符为单位的，不是以字节
- 列的长度小于255则只占用一个额外的字节用于记录字符串的长度，列的长度大于255则要占用两个额外字节用于记录字符串长度

VARCHAR长度的选择问题：

- 使用最小的符合需求的长度
- varchar(5)和varchar(200)虽然都只会占用实际的存储长度，但是存储'MySQL'字符串性能会有所不同

VARCHAR的适合的场景：

- 字符串列的最大长度比平均长度大很多
- 字符串列很少被更新
- 使用了多字节字符集存储字符串



CHAR类型的存储特点：

- CHAR类型是定长的
- 字符串存储在CHAR类型的列中会删除末尾的空格
- CHAR类型的最大宽度为255

CHAR类型适合的场景：

- CHAR类型适合存储长度近似的值
- CHAR类型适合存储短字符串
- CHAR类型适合存储经常更新的字符串列

## 4.8 物理设计-如何存储日期类型

> DATATIME类型

以YYYY-MM-DD HH:MM:SS[.fraction]格式存储日期时间，DATATIME类型与时区无关，占用8个字节的存储空间

时间范围：1000-01-01 00:00:00 到 9999-12-31 23:59:59

> TIMESTAMP类型

存储了由格林尼治时间1970年1月1日到当前时间的秒数以YYYY-MM-DD HH:MM:SS.[.fraction]的格式显示，占用4个字节。

时间范围：1970-01-01 到 2038-01-19

timestamp 类型显示依赖于所指定的时区，行的数据修改时可以自动修改第一个timestamp列的值，也可以指定修改那个列的timestamp的值。

> date类型和time类型

存储日期常见的方式，以及占用字节数：

​	一是把日期部分存储为字符串（至少要8个字节）

​	二是使用int类型来存储（4个字节）

​	三是使用datetime类型来存储（8个字节）



date类型用于存储日期数据，格式为 YYYY-MM-DD

date类型的优点：

1. 占用的字节数比使用字符串、datetime、int存储要少，使用date类型只需要占用3个字节 。

2. 使用date类型可以利用日期时间函数进行日期之间的计算。

3. date类型用于保存1000-01-01到9999-12-31之间的日期


time类型用于存储时间数据，格式为HH:MM:SS



存储日期时间数据的注意事项

- 不要使用字符串类型来出日期的时间数据

  日期时间类型通常比字符串占用的存储空间小

  日期时间类型在进行查找过滤是可以利用日期来进行比对

- 日期时间类型有着丰富的处理函数，可以方便的对时期类型进行日期计算

- 使用int存储日期时间不如使用Timestamp类型



# 五、MySQL高可用架构设计

介绍二进制日志及其对复制的影响、GTID的复制、MMM、MHA等等

## 5.1 mysql复制功能介绍

复制问题解决了什么问题：

- 实现了不同服务器上的数据分布

- 利用二进制日志增量进行

- 不需要太多的带宽

- 但是使用基于行的复制在进行大批量的更改时会对带宽带来一定的压力，特别是跨IDC环境下进行复制应该分批进行。

- 实现在不同服务器上的数据分布

- 实现在数据读取的负载均衡，需要其他组件配合完成比如利用DNS轮询的方式把程序的读连接到不同的备份数据库，使用LVS，haproxy这样的代理方式。

- 非共享架构，同样的数据分布在多台服务器上，增强了数据的安全性

- 利用备库的备份来减少主库的复杂，复制并不能代替备份

- 方便的进行数据库高可用架构的部署，避免MySQL单点失败，实现数据库高可用和故障切换

- 实现数据库在线升级


## 5.2 mysql 二进制日志

MySQL中有很多类型的日志

1. 错误日志――MySQL服务启动和关闭过程中的信息以及其它错误和警告信息。默认在数据目录下。
2. 一般查询日志――用于记录select查询语句的日志。general_log、general_log_file 默认关闭，建议关闭。
3. 慢查询日志――log-slow-queries记录所有超过long_query_time时间的SQL语句，
4. 二进制日志――记录任何引起数据变化的操作，用于备份和还原。默认存放在数据目录中，在刷新和服务重启时会滚动二进制日志。
5. 中继日志――从主服务器的二进制文件中复制的事件，并保存为二进制文件，格式和二进制日志一样。
6. 事务日志――保证事务的一致性。 

可以参考：http://www.php.cn/mysql-tutorials-362068.html

MySQL服务层日志：二进制日志、慢查询日志、通用日志

MySQL存储引擎层日志：Innodb (重做日志、回滚日志)



其中MySQL中的二进制日志与存储引擎无关，记录了所有对MySQL数据库的修改事件，包括增删改查时间和对表结构的修改事件。可以通过binlog命令工作对日志进行查看。



二进制日志的格式：

> 基于段的格式：binlog_format=STATEMENT

优点： 日志记录量相对较小，节约磁盘以及网络I/O

缺点：必须要记录上下文信息，保证语句自爱从服务器上执行结果和主服务器上相同，但是比如特定的函数UUID()，user()这样的非确定性函数还是无法复制，可能造成MySQL复制的主从服务器数据不一致。



操作示例：

```mysql
>mysql -uroot -proot

mysql> show variables like 'binlog_format'; #查看二进制日志格式，下边显示使用row
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| binlog_format | ROW   |
+---------------+-------+
1 row in set (0.00 sec)

mysql> set session binlog_format=statement;  #设置成statement格式

mysql> show variables like 'log_bin'; #查询二进制日志是否开启，显示关闭
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| log_bin       | OFF   |
+---------------+-------+
```

开启二进制日志

打开mysql 的配置文件my.ini(别说找不到哦)
在mysqld配置项下面加上log_bin=mysql_bin

```ini
[mysqld]
log_bin = mysql_bin
```

刷新bin_logs;

```mysql
mysql>flush logs;
mysql>flush binary logs;
+---------------------+-------------+
| Log_name            | File_size   |
+---------------------+-------------+
| mysql-bin.000001    | 201         |
+---------------------+-------------+
| mysql-bin.000002    | 154         |
+---------------------+-------------+

#各种数据库操作，statement日志会记录自行的SQL
>create xx
>select xxx
>update xx
>delete xx
```

查看二进制日志：

```shell
#cd /home/mysql/sql_log
#ls 
mysql-bin.000001  mysql-bin.000002  mysql-bin.index  mysql-error.log

#binlog mysql-bin.000002  #查看日志内容，可以清楚的看到执行的SQL语句
```



> 基于行的日志格式 binlog_format=row

ROW格式可以避免MySQL复制中出现的主从不一致问题，MySQL5.7默认采用的日志格式

 基于行的日志会分别记录每一行的数据修改

优点：

- 使MySQL主从复制更加安全
-  对每一行数据的修改比基于段的复制高效
-  如果误操作修改了数据库中的数据，同时又么有备份可以恢复时，我们就可以通过分析二进制日志，对日志中的记录的数据修改操作做反向处理的方式达到恢复数据的目的。

缺点：

- 记录日志量较大

  binlog_row_image=[FULL|MINIMAL|NOBLOB]

  FULL 记录所有的列的变化

  MINIMAL 只会记录修改的列的变化

  NOBLOB 不记录BLOB类型的变化

```mysql
mysql> set session binlog_format=row;  #设置成row格式
mysql> flush logs;
mysql> show variables like 'binlog_row_image';
+-----------------+--------+
| Variable_name   | Value  |
+-----------------+--------+
| binlog_row_image| FULL   |
+---------------+----------+
#各种数据操作
mysql> create xxx
mysql> update xxx
mysql> insert 
mysql> delete
```

查看二进制日志：

```mysql
#cd /home/mysql/sql_log
#ls 
mysql-bin.000001  mysql-bin.000002  mysql-bin.000003 mysql-bin.index  mysql-error.log

#binlog -vv mysql-bin.000003  #查看日志内容，可以清楚的看到数据的变化，需要加 -vv 查看看的到
```



> 混合日志格式：binlog_format = MIXED

特点：

- 根据SQL语句由系统决定在基于段和基于行的日志格式中进行选择 
- 数据量的大小由所执行的SQL语句决定



如何选择二进制日志的格式：

建议:

binlog_format=mixed

or

binlog_format=row

binlog_row_image=minimal

## 5.3 mysql二进制日志格式对复制的影响

根据二进制的数据格式，复制可以分为：

- 基于SQL语句的复制（SBR）

  二进制日志格式使用statement格式

  优点：

  1. 生成的日志量少，节约网络传输I/O
  2. 并不强制要求主从数据库的表定义完全相同
  3. 相比于基于行的复制方式更加灵活

  缺点：

  1. 对于非确定性时间，无法保证主从复制数据的一致性
  2. 对于存储过程，触发器，自定义函数进行的修改也可能造成数据不一致
  3. 相比基于行的复制方式在从执行时间上需要更多的执行时间




- 基于行的复制（RBR）

  二进制日志格式使用的是基于行的日志格式

  优点：

  1. 可以应用于任何SQL的复制包括非确定函数，存储过程等
  2.  可以减少数据库锁的使用

  缺点：

  1. 要求主从数据库的表结构相同，否则可能会中断复制

  2. 无法在从上单独的执行触发器



- 混合模式

  根据实际内容在以上两者间切换

## 5.4 mysql复制工作方式

![](../images/mysql/mysql_h_29.png)  

1. 将变更写入二进制日志

2. 从读取主的二进制日志变更并写入到relay_log中

   基于日志点的复制

   基于GTID的复制

3. 在从上重放relay_log中的日志

   基于SQL段的日志使在从库上重新执行记录的SQL完成的

   基于行的日志则使在从库上直接应用对数据库行的修改完成的


## 5.5 基于日志点的复制

如果主从服务器上的MySQL都是最新安装的，为了实现主从复制需要进行如下步骤：

1. 在主DB服务器上建立复制的账号并授权

   ```mysql
   mysql>create user 'repl' @ 'IP段' identified by 'PassWord';
   mysql>grant replication slave on *.* to 'repl' @ 'IP段';
   ```
2. 配置主数据库服务器

   my.ini 或my.cnf文件配置

   ```ini
   [mysqld]
   log_bin = mysql_bin #启用二进制日志
   server-id = 100 #指定主服务器的服务ID,整个服务集群中式唯一的
   ```

3. 配置从数据库服务器

   my.ini 或my.cnf文件配置

   ```ini
   bin_log = mysql_bin
   server-id = 101 # 这个id，在整个服务集群中唯一 
   relay_log = mysql_relay_bin
   log_slave_update = on #可选，如果需要将从服务器作为其他服务器的主服务器时必须设置
   read_only = on #可选，建议从服务器只读
   ```

4. 初始化从服务器数据，导出主库中的数据到从库

   mysqldump --master-data --single-transaction  #会对表进行加锁

   xtrabackup --slave-info 

   基于日志点的复制配置步骤：

   启动复制链路

   ```mysql
   mysql>CHANGE MASTER TO MASTER_HOST = 'master_host_ip'
   					MASTER_USER = 'repl'
   					MASTER_PASSWORD = 'PassWord'
   					MASTER_LOG_FILE = 'mysql_log_file_name'
   					MASTER_LOG_POS = 4;
   ```



案例：

有两台MySQL服务器，一台做主库，一台做从库

主：192.168.3.100

从：192.168.3.101



按照操作步骤：

1. 在主DB服务器上建立复制的账号并授权

```mysql
#mysql -uroot -p

mysql>create user repl@'192.168.3.%' identified by '123456';
mysql>grant replication slave on *.* to 'repl'@'192.168.3.%';
```

2. 配置主库的my.cnf文件

```shell
log_bin = /home/mysql/sql_log/mysql_bin
binlog_format = row
server-id = 1
relay_log = /home/mysql/sql_log/mysqld-relay-bin
```

3. 配置从库的my.cnf

```shell
log_bin = /home/mysql/sql_log/mysql_bin
binlog_format = row
server-id = 2
relay_log = /home/mysql/sql_log/mysqld-relay-bin
read_only = on
```

4. 初始化从服务器的数据

   将主服务器的数据导入到从服务器上，我们事先在主服务器上建议一个crn库

   ```shell
   #导出备份数据,注意如果主从数据库版本是一致的可以备份所有的数据库，包括系统数据库,如果主从数据库版本不一致则备份业务数据即可。
   #mysqldump --single-transaction --master-data=2 --lock-tables --all-database -uroot -p >> all.sql;
   
   #ls 
   all.sql
   ```

5. 将数据拷贝到从服务器，并导入

   ```shell
   #scp all.sql root@192.168.3.101:/root #拷贝备份文件到从库
   # mysql -uroot -p < all.sql #初始化从服务器，导入后主从数据库初始化是一致的。
   ```

6. 从服务器启动复制链路

   ```mysql
   mysql> chage master to master_host='192.168.3.100',
   	-> master_user='repl'
   	-> master_password='123456'
   	-> MASTER_LOG_FILE='mysql-bin.000003', #MASTER_LOG_FILE MASTER_LOG_POS两个参数可以在mysql-bin.000003找到
   	-> MASTER_LOG_POS=1839; 
   	
   > show slave status \G; #查看复制链路是否启动	
   > start slave; #启动复制链路
   > show processlist; # 可以看到从服务器上建立的进程
   ```


基于日志点的复制的

优点：

- 是MySQL最早的复制技术，Bug相对较少
- 对SQL查询没有任何权限
- 故障处理比较容易

缺点：

- 故障转移时重新获取新的住的日志点信息比较困难

## 5.6 基于GTID的复制

MySQL5.6 时支持的模式

基于GTID的复制与基于日志的的复制有很大的区别：

![](../images/mysql/mysql_h_30.png)    



![](../images/mysql/mysql_h_31.png)



什么是GTID即全局事务ID，其保证为每一个在主上提交的事务在复制集群中可以生成一个唯一的ID

GTID=source_id:transaction_id



使用了GTID后不可使用如下操作：

create table ..select

在事务中使用create temporary table 建立临时表使用关联更新事务表和事非事务表



操作步骤：

1. 在主DB服务器上建立服务制账号

   ```mysql
   mysql>create user 'repl' @ 'IP段' identified by 'PassWord';
   mysql>grant replication slave on *.* to 'repl' @ 'IP段';
   ```

2. 配置主数据库服务器

	my.cnf

	```shell
	log_bin = /usr/local/mysql/log/mysql-bin
	server_id = 100
	gtid_mode = on  #启动gtid
	enforce-gtid-consiste #强制gtid一致性，保证事务安全
	log-slave-updates = on # mysql5.7可以不用这个参数
	```

3. 配置从数据库服务器

	```mysql
	server_id = 101
	relay_log = /usr/local/mysql/log/relay_log
	gtid_mode = on
	enforce-gtid-consistency
	log-slave-updates = on
	read_only = on  #建议
	master_info_repository = TABLE #建议
	realy_log_info_repository = TABLE #建议
	```

4. 初始化从服务器数据

	```mysql
	mysqldump --master-data --single-transaction  #会对表进行加锁
	xtrabackup --slave-info 
	```

记录备份时最后的事务的GTID值

5. 启动基于GTID的复制

	```mysql
	mysql>CHANGE MASTER TO MASTER_HOST = 'master_host_ip'
						MASTER_USER = 'repl'
						MASTER_PASSWORD = 'PassWord'
						MASTER_AUTO_POSITION = 1;
	```



**示例，略。**



基于GTID复制的优缺点：

优点：

1. 可以很方便的故障转移
2. 从库上不会丢失主库上的任何修改

缺点：

1. 故障处理比较复杂

2. 对执行SQL有一定的限制


选择复制模式要考虑的问题：

1. 所使用的MySQL版本

2. 复制架构以及主从切换的方式

3. 所使用的高可用管理组件

4. 对应用的支持程度



## 5.7 MySQL复制拓扑

![](../images/mysql/mysql_h_32.png)  

下边来了解一下MySQL常见的拓扑结构

1. **一主多从的复制拓扑**

![](../images/mysql/mysql_h_33.png)  

优点：

- 配置简单
- 可以用多个从库分担读负载 

用途：

- 为不同业务使用不同的从库
- 将一台从库放到远程的IDC，用于灾备恢复
- 分担主库的读负载



2. 主-主复制

![](../images/mysql/mysql_h_34.png)    

主备模式的主主复制模式：

只有一台主服务器对外提供服务，一台服务器处于只读状态，并且只作为热备使用。

在对外提供服务的主库出现故障或者是计划性的维护时才会进行切换，使原来的备库成为主库，而原来的主库会成为新的备库，并处理只读或者是下线状态，维护完成后重新上线。

主备模式下的主-主复制的配置注意事项：

- 确保两台服务器上的初始化数据相同
- 确保两台服务器上已经启动logbin并且有不同的server_id
- 两台服务器上启动log_slave_updates参数
- 在初始化备库上启用read_only





主主模式的主主复制模式：

两个主同时对外提供服务，并不能分担写负载

主主模式下的主主复制缺点：

- 产生数据冲突而造成复制链路中断，耗费大量的时间排查问题
- 容易造成数据丢失

如果一定要使用主主复制模式，应该注意一下事项：

- 两个主中所操作的表最好能够分开
- 使用下边两个参数控制自增ID的生成
  - auto_increment_increment = 2
  - auto_increment_offset = 1 | 2



3. 拥有备库的主-主复制拓扑

![](../images/mysql/mysql_h_35.png)  



3. 级联复制

可以避免分发主库连接从库过多而占用主库过多的带宽，使用分发主库可以解决这个问题。



![](../images/mysql/mysql_h_36.png)  

## 5.8 MySQL复制性能优化

影响主从延迟的原因

- 主库写入二进制的日志时间

- 控制主库的事务大小，分割事务

- 二进制日志传输时间

- 使用MIXED日志格式

  设置set binlog_row_image=minimal

- 默认情况下只有一个SQL线程，主上并发的修改在从上变成了串行

- 使用多线程复制（MySQL5.6）,在MySQL5.7中可以按照逻辑时钟的方式来分配线程



MySQL5.7上使用多线程复制

```mysql
mysql>stop slave
mysql>set global slave_parallel_type = 'logical_clock'; #使用逻辑时钟的方式复制
mysql>set global slave_parallel_workers=4; #设置多线程复制的数量
mysql>start slave;
mysql>show processlist; #查看进程的数量
```



## 5.9 MySQL复制常见问题处理

由于数据损坏或丢失所引起的主从复制错误

- 主库或从库意外宕机引起的错误

  可以使用跳过二进制日志事件或者注入空事务的方式恢复中断的复制链路再使用其他方法来对比主从服务器上的数据。

- 主库上的二进制的日志损坏，只能通过从库中使用change master命令来重新指定

- 备库上的中继日志的损坏

- 在从库上进行数据修改造成的主从复制错误，应该设置从库时read_only

- 不唯一的server_id或server_uuid（记录在数据目录中的auto.cnf文件中）

- max_allow_packet设置引起的主从复制错误



MySQL复制无法解决的问题

- 分担主数据库的写负载
- 自动的故障转移以及主从切换
- 提供肚读写分离

## 5.10 什么是高可用架构

什么是高可用：

“高可用性”（High Availability）通常来描述一个系统经过专门的设计，从而减少停工时间，而保持其服务的高度可用性。

**如果要达到9.99999的高可用：(365\*24\*60)\*(1-0.99999) = 5.256 即，全年最多有5分钟不可用**



如何实现高可用：

1. 应该尽量避免系统不可用的因素，减少系统不可用的时间，如果导致不可用的原因（服务器磁盘空间消尽，性能糟糕的SQL，表结构和索引没有优化，主从数据不一致，人为的操作失误等）
2. 建立完善的监控以及报警系统
3. 对备份数据进行恢复测试
4. 正确配置数据库环境
5. 对不需要的数据进行归档和清理
6. 增加系统冗余，保证系统不可用时可以尽快恢复
7. 避免存在单点故障
8. 主从切换以及故障转移



如何解决单点故障：

单点故障是指在一个系统中提供相同的功能的组件只用一个，如果这个组件失败了，就会影响功能的正常使用。组成应用系统的各个组件都可能成为单点。

如何避免MySQL单点故障？

- 1. 利用SUN共享存储或者DRDB磁盘复制解决MySQL单点故障



共享存储：

![](../images/mysql/mysql_h_37.png)



DRDB:

![](../images/mysql/mysql_h_38.png)    

- 2.利用多写集群或NDB集群来解决MySQL单点故障，比如percona->pxc

![](../images/mysql/mysql_h_39.png)  



- 3. 利用MySQL主从复制来解决MySQL单点故障 ，但是这需要解决如下几个问题

     主服务器切换后如何通知应用新的主服务器的IP地址

     如何检查MySQL主服务器是否可用

     如何处理从服务器和新主服务器之间的那种复制关系

## 5.11 MMM架构介绍

MMM（Multi-Master Replication Manager）是MySQL多主复制管理器的简称，主要作用是监控和管理MySQL的主主复制拓扑，并在当前的主从服务器失效时，进行主和主备服务器之间的主从切换和故障转移等工作。



MMM提供了什么功能？

1. MMM监控MySQL主从复制健康情况

2. 在主库出现宕机时进行故障转移并自动配置其他从对新主进行复制

3. 提供了主，写虚拟IP，在主从服务器出现问题时可以自动迁移虚拟IP


MMM架构：

![](../images/mysql/mysql_h_40.png)  



MMM部署所需要的资源：

  ![](../images/mysql/mysql_h_41.png). 

## 5.12 MMM架构实例演示（上）

MMM部署步骤：

1. 配置主主复制以及主从同步集群
2. 安装主从节点所需要的支持包
3. 安装以及配置MMM工具集
4. 运行MMM监控服务
5. 测试

**MMM拓扑图**

![](../images/mysql/mysql_h_54.png)  



## 5. 13 MMM架构实例演示（下）

https://www.jianshu.com/p/c565f4ccb5d3

## 5.14 MMM架构的优点和缺点

优点：

1. 使用perl脚本语言开发完全开源
2. 提供了VIP（虚拟IP）,使得服务器角色的变更对前端应用透明
3. MMM提供了从服务器的延迟监控
4. MMM提供了主数据库故障转移后从服务器对新主的重新同步功能，很容易对发生故障的主数据库重新上线

缺点：

1. 发布时间比较早不支持MySQL新的复制功能，（a.基于GTID的复制可以保证日志不会重复在slave服务器上备执行 b.对于MySQL5.6后所提供的多线程复制技术也不支持）
2. 没有读的负载均衡的功能
3. 在进行主从切换时，容易造成数据丢失
4. MMM监控服务存在单点故障

## 5.15 MHA架构介绍

 MHA是一位日本MySQL大牛用Perl写一套MySQL故障切换方案，来保证数据库系统的高可用，在宕机的事件内（通常10-30秒），完成故障转意，部署MHA，可避免主从一致性问题，节约购买新服务器的费用，不影响服务器性能，易安装，不改变现有部署
 参考：https://blog.csdn.net/linuxlsq/article/details/52606255

## 5.16 MHA架构实例演示（1）

![](../images/mysql/mysql_h_55.png)  
注意：主从复制使用gtid_mode;

主从链路复制->MHA架构

## 5.17 MHA架构实例演示（2）
MHA只需要在管理节点配置即可。


## 5.18 MHA架构优缺点
优点：
- 同样是由perl语言开发的开源工具
- 可以支持GTID的复制模式
- MHA在进行故障转移时更不容易产生数据丢失
- 同一个监控节点可以监控多个集群

缺点：
- 需要编写脚本或利用第三方工具来实现VIP的配置
- MHA启动后只会对主数据库进行监控
- 需要基于SSH免认证配置，存在一定的安全隐患
- 没有提供从服务器的读写负载均衡的功能

## 5.19 读写分离和负载均衡介绍
通常读写分离和负载均衡是两个不同的概念
读写分离：将MySQL的读操作和写操作分开，写操作尽量在主库上进行，读操作尽量在从上进行，读写分离解决的是如何在复制集群的不同角色上去执行不同的SQL语句。

读的负载均衡:主要解决的是具有相同角色的数据库如何共同分担相同的负载。

> 实现读写分离有2种方式

1. 由程序实现读写分离
2. 由中间件实现读写分离

由程序实现的读写分离
优点：

1. 由开发人员控制什么样的查询在从库中执行，因此比较灵活  
2. 由程序直接连接数据库，所以性能损耗比较少  

缺点：

1. 增加了开发了工作量，使得程序代码复杂
2. 人为控制，容易出现错误

中间件实现读写分离
mysql-proxy/maxScale

mysql-proxy: MySQL公司开发

maxScale:MariaDB公司开发

优点:

1. 由中间件根据查询语法分析，自动完成读写分离
2. 对程序透明，对已有程序不用做任何调整

缺点：

1. 由于增加了中间层，所以对查询性能有损耗
2. 对于延迟敏感的业务无法自动在主库执行


如何实现读的负载均衡：
1. 程序的方式
2. 软件的方式（LVS, Haproxy, MaxScale）
3. 硬件F5

## 5.20 MaxScale实例演示(简要了解)

**具体使用，请自行查阅资料**

MaxSacle存在很多比较有用的插件

![](../images/mysql/mysql_h_56.png)

1. 安装MaxSacle 



最终的高可用架构
![](../images/mysql/mysql_h_57.png)  





# 六、数据库索引优化

介绍Btree索引和Hash索引，详细介绍索引的优化策略等。

索引优化推荐文章：https://www.jianshu.com/p/9c9a0057221f

## 6.1 Btree索引和Hash索引

索引对于数据库性能的影响非常关键，索引的作用就是告诉数据库如何快速的找到需要的数据。当表中的数据比较少时索引的作用可能不太明显，随着表中的数据越来越多，频率越来越高，内存不能完全缓存所有数据的时候，索引的作用就会显得越来越重要，在实际的工作中人们总是忽略或过分强调索引的作用。太多或太少的索引都不可取，只有在正确的列上建立正确的索引才能增强数据库的索引能力。



MySQL支持的索引类型

MySQL的索引是存储引擎层实现的，不是MySQL服务层实现的，不同的存储引擎的索引实现方式也不一样，不是所有的存储的引擎都支持所有的索引类型，即使是同一种索引类型，但是在不同的存储引擎上索引实现方式也可能不同。

- B-tree索引的特点

  B-tree索引是最常见的索引类型，使用B+树的结构存储数据，在B+树中每一个叶子节点都包含一个指向下一个叶子节点的指针，方便叶子节点的遍历。

  1. B-tree索引以B+树的结构存储数据

     ![](../images/mysql/mysql_h_42.png)  

  2. B-tree索引能够加快数据的查询速度

  3. B-tree索引更适合进行范围查找

  4. **B-tree索引是顺序存储的**

- 什么情况下可以用到B树索引

  1. 全值匹配的查询

     order_sn  = '9876432119900'

  2. 匹配最左前缀的查询(如果有多列建立联合索引，只有使用第一列索引作为查询条件才会使用到索引)

  3. 匹配列前缀查询 

     order_sn like '9876%'

  4. 匹配范围值的查询

     order_sn > '9876432119900'

     order_sn < '9876432119999'

  5. 精确匹配左前列并范围匹配另外一列

  6. 只访问索引的查询

- 使用Btree索引的使用限制

  原文：https://www.jianshu.com/p/9c9a0057221f

  1、 最好全值匹配——索引怎么建我怎么用。
  2、 最佳左前缀法则——如果索引了多列，要遵守最左前缀法则。指的是查询要从索引的最左前列开始并且不跳过索引中的列。
  3、不在索引列上做任何操作（计算，函数，（自动或者手动）类型装换），会导致索引失效而导致全表扫描。——MYSQL自带api函数操作，如：left等
  4、存储引擎不能使用索引中范围条件右边的列。——范围之后索引失效。（< ,> between and,）
  5、尽量使用覆盖索引（只访问索引的查询（索引和查询列一致）），减少select*。——按需取数据用多少取多少。
  6、在MYSQL使用不等于（<,>,!=）的时候无法使用索引，会导致索引失效。
  7、is null或者is not null 也会导致无法使用索引。
  8、like以通配符开头（'%abc...'）MYSQL索引失效会变成全表扫描的操作。——覆盖索引。
  9、字符串不加单引号索引失效。
  10、少用or，用它来连接时索引会失效。



- Hash索引的特点

  1. Hash索引时基于Hash表实现的，**只有查询条件精确匹配Hash索引中的列时**，才能够使用Hash索引
  2. 对于Hash索引中的所有列，存储引擎都会为每一行计算一个Hash码，Hash索引中存储的就是Hash码。 

- Hash索引的限制

  1. Hash索引必须进行需要两次查找，先找到Hash值，再找到数据
  2. Hash索引无法用于排序
  3. Hash索引不支持部分索引的查找也不支持范围查找
  4. Hash索引中的Hash码的计算可能存在Hash冲突



为什么要使用索引

- 索引大大减少了存储引擎需要扫描的数据量
- 索引可以帮我们进行排序以避免使用临时表
- 索引可以把随机I/O变为顺序I/O



索引是不是越多越好？NO

- 索引会增加写操作成本
- 太多的索引会增加查询优化器的选择时间

## 6.2 安装演示数据库

sakila-db库中有MySQL官方的数据库与表，用于给开发人员训练数据库使用技巧

步骤：

```mysql
http://downloads.mysql.com/docs/sakila-db.tar.gz
tar -zxvf sakila-db.tar.gz
mysql -uroot -p < sakila-schema.sql
mysql -uroot -l < sakila-data.sql
```

## 6.3 索引优化策略（上）

1. 索引列上不能使用表达式或者函数

   ```mysql
   select .... from prodcut
   where to_days(out_date)-to_days(current_date)<=30; #使用了函数，不会走索引
   
   select .... from prodcut
   where out_date <= to_days(current_date,interval 30 day); #没有使用函数，会走索引
   ```

2. 前缀索引和索引列的选择性

   MySQL中的B-tree索引对键值大小是有限制的

   Innodb索引长度最大不能超过767个字节

   MyISAM索引长度最大不能超过1000个字节

   ```mysql
   create index index_name ON table(col_name(n)); #n表示列的宽度，通过指定列宽度建立索引
   ```

   索引的选择性是不重复的索引值和表的记录数的比值，所以建立前缀索引时尽量考虑到索引重复率不要太高。

3. 联合索引

   如何选择索引列的顺序

   - 经常会被使用的列优先
   - 选择性高的列优先
   - 宽度小的列优先

4. 覆盖索引

   **概念：**如果索引包含所有满足查询需要的数据的索引成为覆盖索引(Covering Index)，也就是平时所说的不需要回表操作

   **判断标准**

   使用explain，可以通过输出的extra列来判断，对于一个索引覆盖查询，显示为**using index**,MySQL查询优化器在执行查询前会决定是否有索引覆盖查询

    

   **注意**

   1. 覆盖索引也并不适用于任意的索引类型，索引必须存储列的值

   2. Hash 和full-text索引不存储值，因此MySQL只能使用B-TREE

   3. 并且不同的存储引擎实现覆盖索引都是不同的

   4. 并不是所有的存储引擎都支持它们

   5. 如果要使用覆盖索引，一定要注意SELECT 列表值取出需要的列，不可以是SELECT *，因为如果将所有字段一起做索引会导致索引文件过大，查询性能下降，不能为了利用覆盖索引而这么做



   优点：

   - 可以优化缓存，减少磁盘IO操作

   - 可以减少随机IO，变随机IO操作变为顺序IO操作

   - 可以避免对Innodb主键索引的二次查询

   - 可以避免MyISAM表进行系统调用



   无法使用覆盖索引的情况：

   - 存储引擎不支持覆盖索引
   - 查询中使用了太多的列
   - 使用了双%号的like查询  



   案例：

   ```mysql
   mysql>desc film; #查看表结构
   mysql> show create table film; #查看建表语句
   mysql> show index from film; #查看表索引
   mysql> explain select language_id from film where language_id = 1 \G;  #覆盖索引
   *************************** 1. row ***************************
              id: 1
     select_type: SIMPLE
           table: film
      partitions: NULL
            type: ref
   possible_keys: idx_fk_language_id
             key: idx_fk_language_id
         key_len: 1
             ref: const
            rows: 1000
        filtered: 100.00
           Extra: Using index
   1 row in set, 1 warning (0.00 sec)
   
   ERROR: 
   No query specified
   
   mysql> explain select * from film where language_id = 1 \G;  #不使用覆盖索引
   *************************** 1. row ***************************
              id: 1
     select_type: SIMPLE
           table: film
      partitions: NULL
            type: ALL
   possible_keys: idx_fk_language_id
             key: NULL
         key_len: NULL
             ref: NULL
            rows: 1000
        filtered: 100.00
           Extra: Using where
   1 row in set, 1 warning (0.00 sec)
   
   ERROR: 
   No query specified
   ```


## 6.4 索引优化策略（中）

使用索引扫描来优化排序

- 通过排序操作
- 按照索引顺序扫描数据 

使用索引扫描来优化的排序条件：

1. 索引的列顺序和order by子句的顺序完全一致。
2. 索引中所有的列的方向（升序，降序）和order by子句完全一致
3. order by中的字段全部在关联表中的第一张表中

案例：

```mysql
mysql>show create table rental;
| Table  | Create Table                                                                                                                                                                                                                                                                                                                                     |
+--------+------------------------------------------------------------------+
| rental | CREATE TABLE `rental` (
  `rental_id` int(11) NOT NULL AUTO_INCREMENT,
  `rental_date` datetime NOT NULL,
  `inventory_id` mediumint(8) unsigned NOT NULL,
  `customer_id` smallint(5) unsigned NOT NULL,
  `return_date` datetime DEFAULT NULL,
  `staff_id` tinyint(3) unsigned NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`rental_id`),
  UNIQUE KEY `rental_date` (`rental_date`,`inventory_id`,`customer_id`),
  KEY `idx_fk_inventory_id` (`inventory_id`),
  KEY `idx_fk_customer_id` (`customer_id`),
  KEY `idx_fk_staff_id` (`staff_id`),
  CONSTRAINT `fk_rental_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_rental_inventory` FOREIGN KEY (`inventory_id`) REFERENCES `inventory` (`inventory_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_rental_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16050 DEFAULT CHARSET=utf8


mysql> explain select * from rental where rental_date > '2005-01-01' order by rental_id \G; #使用主键排序，走索引
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: rental
   partitions: NULL
         type: index
possible_keys: rental_date
          key: PRIMARY
      key_len: 4
          ref: NULL
         rows: 16005
     filtered: 50.00
        Extra: Using where
1 row in set, 1 warning (0.00 sec)

ERROR: 
No query specified


#建立一张myisam存储引擎的表
mysql>  CREATE TABLE `rental_myisam` (
    ->   `rental_id` int(11) NOT NULL AUTO_INCREMENT,
    ->   `rental_date` datetime NOT NULL,
    ->   `inventory_id` mediumint(8) unsigned NOT NULL,
    ->   `customer_id` smallint(5) unsigned NOT NULL,
    ->   `return_date` datetime DEFAULT NULL,
    ->   `staff_id` tinyint(3) unsigned NOT NULL,
    ->   `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ->   PRIMARY KEY (`rental_id`),
    ->   UNIQUE KEY `rental_date` (`rental_date`,`inventory_id`,`customer_id`),
    ->   KEY `idx_fk_inventory_id` (`inventory_id`),
    ->   KEY `idx_fk_customer_id` (`customer_id`),
    ->   KEY `idx_fk_staff_id` (`staff_id`),
    ->   CONSTRAINT `fk_rental_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON UPDATE CASCADE,
    ->   CONSTRAINT `fk_rental_inventory` FOREIGN KEY (`inventory_id`) REFERENCES `inventory` (`inventory_id`) ON UPDATE CASCADE,
    ->   CONSTRAINT `fk_rental_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`) ON UPDATE CASCADE
    -> ) ENGINE=myisam DEFAULT CHARSET=utf8
    -> ;
Query OK, 0 rows affected (0.02 sec)

mysql> explain select * from rental_myisam where rental_date > '2005-01-01' order by rental_id \G; #myisam没有走索引
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: rental_myisam
   partitions: NULL
         type: ALL
possible_keys: rental_date
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 16044
     filtered: 100.00
        Extra: Using where; Using filesort
1 row in set, 1 warning (0.00 sec)

ERROR: 
No query specified

#使用二级索引排序
#透明表索引有两种：分别是主索引和二级索引。
#主索引是在我们创建表激活后由系统自动创建的，这个我们不能修改；二级索引可以我们自己创建。
#主索引是表的主键，二级索引可以根据你自己需要用到表的任何字段的组合来创建。
#在使用二级索引时，WHERE条件字段和字段顺序要与二级索引字段和字段顺序粗略一致，这样才能提高检索效率
mysql> explain select * from rental where rental_date='2005-05-09' order by inventory_id, customer_id \G;
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: rental
   partitions: NULL
         type: ref
possible_keys: rental_date
          key: rental_date
      key_len: 5
          ref: const
         rows: 1
     filtered: 100.00
        Extra: Using index condition
1 row in set, 1 warning (0.00 sec)

ERROR: 
No query specified

 #二级索引方式，myisam与innodb结果与上边的一致
mysql> explain select * from rental_myisam where rental_date='2005-05-09' order by inventory_id, customer_id \G;
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: rental_myisam
   partitions: NULL
         type: ref
possible_keys: rental_date
          key: rental_date
      key_len: 5
          ref: const
         rows: 1
     filtered: 100.00
        Extra: Using index condition
1 row in set, 1 warning (0.01 sec)



 #索引中所有的列的方向（升序，降序）和order by子句完全一致
mysql> explain select * from rental where rental_date='2005-05-09' order by inventory_id desc, customer_id \G;
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: rental
   partitions: NULL
         type: ref
possible_keys: rental_date
          key: rental_date
      key_len: 5
          ref: const
         rows: 1
     filtered: 100.00
        Extra: Using index condition; Using filesort
1 row in set, 1 warning (0.00 sec)

#如果查询中有某个列的范围查询，则其右边的所有列都无法使用索引
mysql> explain select * from rental where rental_date>'2005-05-09' order by inventory_id desc, customer_id \G;
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: rental
   partitions: NULL
         type: ALL
possible_keys: rental_date
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 16005
     filtered: 50.00
        Extra: Using where; Using filesort
1 row in set, 1 warning (0.00 sec)

ERROR: 
No query specified

```



模拟Hash索引优化查询

- 只能处理键值的全值匹配查找
- 所使用的Hash函数决定这索引键的大小

```mysql
mysql> show create table film;
mysql> mysql> show create table film;
+-------+------------------------------------------------------------------------------------------------------------------------------------------------+
| Table | Create Table                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
+-------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| film  | CREATE TABLE `film` (
  `film_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text,
  `release_year` year(4) DEFAULT NULL,
  `language_id` tinyint(3) unsigned NOT NULL,
  `original_language_id` tinyint(3) unsigned DEFAULT NULL,
  `rental_duration` tinyint(3) unsigned NOT NULL DEFAULT '3',
  `rental_rate` decimal(4,2) NOT NULL DEFAULT '4.99',
  `length` smallint(5) unsigned DEFAULT NULL,
  `replacement_cost` decimal(5,2) NOT NULL DEFAULT '19.99',
  `rating` enum('G','PG','PG-13','R','NC-17') DEFAULT 'G',
  `special_features` set('Trailers','Commentaries','Deleted Scenes','Behind the Scenes') DEFAULT NULL,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`film_id`),
  KEY `idx_title` (`title`),
  KEY `idx_fk_language_id` (`language_id`),
  KEY `idx_fk_original_language_id` (`original_language_id`),
  CONSTRAINT `fk_film_language` FOREIGN KEY (`language_id`) REFERENCES `language` (`language_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_film_language_original` FOREIGN KEY (`original_language_id`) REFERENCES `language` (`language_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8 |
+-------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------+
1 row in set (0.00 sec)


mysql> alter table film add title_md5 varchar(32);
mysql> update film set title_md5=md5(title);
mysql> explain select * from film where title_md5=md5('EGG IGBY') and title = 'EGG IGBY' \G;
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: film
   partitions: NULL
         type: ref
possible_keys: idx_title
          key: idx_title
      key_len: 767
          ref: const
         rows: 1
     filtered: 10.00
        Extra: Using where
1 row in set, 1 warning (0.00 sec)

ERROR: 
No query specified
```



## 6.5 索引优化策略（下）

> 利用索引优化锁

- 索引可以减少锁定的行数
- 索引可以加快处理速度，同时也加快了锁的释放

案例：

1. 在没有索引的情况下开启两个会话查询

会话一：

```mysql
mysql> show create table actor \G;
*************************** 1. row ***************************
       Table: actor
Create Table: CREATE TABLE `actor` (
  `actor_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`actor_id`),
  KEY `idx_actor_last_name` (`last_name`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8
1 row in set (0.00 sec)

mysql> drop index idx_actor_last_name on actor; #删除idx_actor_last_name索引
Query OK, 0 rows affected (0.03 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> explain select * from actor where last_name='WOOD' \G; #可以看到未使用任何索引
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: actor
   partitions: NULL
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 200
     filtered: 10.00
        Extra: Using where
1 row in set, 1 warning (0.00 sec)

ERROR: 
No query specified


mysql> begin;  #开启事务
Query OK, 0 rows affected (0.00 sec)

mysql> select * from actor where last_name='WOOD' for update;  #使用排他锁查询
+----------+------------+-----------+---------------------+
| actor_id | first_name | last_name | last_update         |
+----------+------------+-----------+---------------------+
|       13 | UMA        | WOOD      | 2006-02-15 04:34:33 |
|      156 | FAY        | WOOD      | 2006-02-15 04:34:33 |
+----------+------------+-----------+---------------------+
2 rows in set (0.00 sec)

```

会话二：

```mysql
mysql> use sakila;
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
mysql> begin; #开启事务
Query OK, 0 rows affected (0.00 sec)

mysql> select * from actor where last_name='willis' for update; #发现即使查询了别的数据，还是被阻塞了
。。。
```



2. 重建索引，然后执行同样的操作

会话一：

```mysql
mysql> rollback;
mysql> create index idx_actor_last_name on actor(last_name); #重建索引
mysql> explain select * from actor where last_name='WOOD' \G; #可以看到使用了索引查询
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: actor
   partitions: NULL
         type: ref
possible_keys: idx_actor_last_name
          key: idx_actor_last_name
      key_len: 137
          ref: const
         rows: 2
     filtered: 100.00
        Extra: NULL
1 row in set, 1 warning (0.00 sec)

ERROR: 
No query specified
>begin; #开启事务

mysql> select * from actor where last_name='WOOD' for update;  #使用排他锁查询
+----------+------------+-----------+---------------------+
| actor_id | first_name | last_name | last_update         |
+----------+------------+-----------+---------------------+
|       13 | UMA        | WOOD      | 2006-02-15 04:34:33 |
|      156 | FAY        | WOOD      | 2006-02-15 04:34:33 |
+----------+------------+-----------+---------------------+
2 rows in set (0.00 sec)

```

会话二：

```mysql
mysql> rollback;
mysql> begin; #开启事务
Query OK, 0 rows affected (0.01 sec)

mysql> select * from actor where last_name='willis' for update; #使用排他锁，没有阻塞
+----------+------------+-----------+---------------------+
| actor_id | first_name | last_name | last_update         |
+----------+------------+-----------+---------------------+
|       83 | BEN        | WILLIS    | 2006-02-15 04:34:33 |
|       96 | GENE       | WILLIS    | 2006-02-15 04:34:33 |
|      164 | HUMPHREY   | WILLIS    | 2006-02-15 04:34:33 |
+----------+------------+-----------+---------------------+
3 rows in set (0.00 sec)
```



> 索引的维护和优化

1. 删除重复冗余的索引

   primary key(id), unique key(id), index(id)  建立了多个索引，需要删除

   主键索引			唯一索引		单列索引

   index(a), index(a,b)   联合索引，可以删除单独索引

   primary key(id), index(a, id)，联合索引，主键不需要添加到索引中

2. 检查是否有重复冗余的索引，使用命令

   ```shell
   #命令
   pt-duplicate-key-checker h=127.0.0.1
   #建立几个冗余索引
   create index idx_customerid_staffid on payment(customer_id,staff_id);
   ```

3. 查找未被使用过的索引，将使用极少的索引或基本不使用的索引考虑删除

   ```mysql
   mysql> select object_schema, object_name, index_name, b.TABLE_ROWS                                                                                                                                      -> FROM performance_schema.table_io_waits_summary_by_index_usage a
       -> JOIN information_schema.tables b 
       -> ON a.OBJECT_SCHEMA = b.TABLE_SCHEMA
       -> AND a.OBJECT_NAME = b.TABLE_NAME
       -> WHERE index_name IS NOT NULL
       -> AND count_star = 0
       -> ORDER BY object_schema, object_name;
   +---------------+---------------+-----------------------------+------------+
   | object_schema | object_name   | index_name                  | TABLE_ROWS |
   +---------------+---------------+-----------------------------+------------+
   | sakila        | actor         | PRIMARY                     |        200 |
   | sakila        | address       | idx_location                |        603 |
   | sakila        | address       | PRIMARY                     |        603 |
   | sakila        | address       | idx_fk_city_id              |        603 |
   | sakila        | category      | PRIMARY                     |         16 |
   | sakila        | city          | idx_fk_country_id           |        600 |
   | sakila        | city          | PRIMARY                     |        600 |
   | sakila        | country       | PRIMARY                     |        109 |
   | sakila        | customer      | idx_fk_address_id           |        599 |
   | sakila        | customer      | idx_last_name               |        599 |
   | sakila        | customer      | PRIMARY                     |        599 |
   | sakila        | customer      | idx_fk_store_id             |        599 |
   | sakila        | film          | idx_fk_language_id          |       1000 |
   | sakila        | film          | idx_fk_original_language_id |       1000 |
   | sakila        | film          | idx_title                   |       1000 |
   | sakila        | film_actor    | idx_fk_film_id              |       5462 |
   | sakila        | film_actor    | PRIMARY                     |       5462 |
   | sakila        | film_category | PRIMARY                     |       1000 |
   | sakila        | film_category | fk_film_category_category   |       1000 |
   | sakila        | film_text     | PRIMARY                     |       1000 |
   | sakila        | film_text     | idx_title_description       |       1000 |
   | sakila        | inventory     | idx_fk_film_id              |       4581 |
   | sakila        | inventory     | idx_store_id_film_id        |       4581 |
   | sakila        | inventory     | PRIMARY                     |       4581 |
   | sakila        | language      | PRIMARY                     |          6 |
   | sakila        | payment       | idx_fk_staff_id             |      16086 |
   | sakila        | payment       | idx_fk_customer_id          |      16086 |
   | sakila        | payment       | fk_payment_rental           |      16086 |
   | sakila        | payment       | PRIMARY                     |      16086 |
   | sakila        | rental        | idx_fk_customer_id          |      16005 |
   | sakila        | rental        | PRIMARY                     |      16005 |
   | sakila        | rental        | idx_fk_staff_id             |      16005 |
   | sakila        | rental        | rental_date                 |      16005 |
   | sakila        | rental        | idx_fk_inventory_id         |      16005 |
   | sakila        | staff         | idx_fk_store_id             |          2 |
   | sakila        | staff         | idx_fk_address_id           |          2 |
   | sakila        | staff         | PRIMARY                     |          2 |
   | sakila        | store         | idx_unique_manager          |          2 |
   | sakila        | store         | idx_fk_address_id           |          2 |
   | sakila        | store         | PRIMARY                     |          2 |
   | sys           | sys_config    | PRIMARY                     |          6 |
   +---------------+---------------+-----------------------------+------------+
   41 rows in set (0.15 sec)
   
   ```

4. 更新索引统计信息减少索引碎片

```mysql
mysql>analyze table table_name; #innodb不会锁表，myisam会锁表
mysql>optimize table table_name; #会锁表
```

# 七、SQL查询优化

详细介绍慢查询日志以及示例演示，MySQL查询优化器介绍特定SQL的查询优化等

## 7.1 获取有性能问题的SQL的三种方法

前面我们了解了
- 如何设计最优的数据库表结构
- 如何建立最好的索引
- 如何扩展数据库的查询

光有上边的还有不够，我们需要查询优化，索引优化，库表接口优化需要齐头并进。

如何获得性能问题的SQL:
1. 通过用户反馈获取在性能问题的SQL
2. 通过慢查日志获取存在性能问题的SQL
3. 实时获取存在性能问题的SQL

## 7.2 慢查询日志介绍
需要有慢查询日志需要在`my.cnf`配置文件中配置如下参数：
`slow_query_log = ON` 启动停止记录慢查询日志
也可以在运行的MySQL使用 `set global slow_query_log = ON`来启动

`slow_query_log_file `指定慢查询日志的存储路径以及文件，默认存储在MySQL的数据目录中，最好是指定一下目录，将日志文件盒数据文件分开存储。

`long_query_time `指定记录慢查询日志SQL的执行时间阀值，单位是s, 默认值为10s, 通常改为0.001秒也就是1毫秒可能比较合适，慢查询日志会记录所有的符合条件的语句包括查询语句，数据修改语句，已经回滚的语句。

`log_queries_not_using_indexes `是否记录未使用索引的查询记录到慢查询中，只要是没有使用索引都会记录到慢查询日志中，即使执行时间 < long_query_time

案例：
```mysql
mysql> set global long_query_time=0;  #为了生成一些日志数据，改为0
mysql> set global slow_query_log=on;  #开启慢查询日志
Query OK, 0 rows affected (0.03 sec)
```

![](../images/mysql/mysql_h_43.png). 
由于慢查询日志有可能会存在很多，人工的方式去检查几乎是不可能的，太困难了，必须借助相关的工具。

常用慢查询日志工具：
- mysqldumpslow
```shell
mysqldumpslow -s r -t 10 slow-mysql.log
```
参数含义：
-s order (c, t, l, r, at, al, ar) 指定按照那种排序方式输出结果
​    c: 总次数
​    t: 总时间
​    l: 锁的时间
​    r: 总数据行
​    at, al, ar : t,l,r 平均数量，例如：at = 总时间/总次数

-t top 指定取前几条作为结果输出


常用慢日志分析工具: 
pt-query-digest
```shell
pt-query-digest --explain h=127.0.0.1, u=root, p=p@ssWord slow-mysql.log
```

## 7.3 慢查询日志实例

1. 使用mysql自带的慢查询分析工具mysqldumpslow 分析：
可以参考：https://www.cnblogs.com/moss_tan_jun/p/8025504.html
```shell
mysqldumpslow -s -r -t 10 slow-mysql.log  #查询前10条记录
```
![](../images/mysql/mysql_h_44.png)  

2. pt-query-digest分析,推荐方式
```shell
# pt-query-digest --explain h=127.0.0.1, u=root, p=p@ssWord slow-mysql.log > slow.rep

# vi slow.rep 
```
![](../images/mysql/mysql_h_45.png)   

## 7.4 实时获取性能问题的SQL
如何实时的获取性能问题的SQL,可以利用`infomation_schema`下边的PROCESSLIST表获取 
![](../images/mysql/mysql_h_46.png)   

获取慢SQL,我们可以通过接口实时获取这个信息来定位慢SQL
```mysql
select id, user, host, db, command, time, state, info
from infomation_schema.PROCESSLIST
where TIME >=60 \G;  #获取档期那服务器SQL执行时间超过60s的SQL
```

## 7.5 SQL的解析预处理以及生成执行计划
找到了性能慢的sql,接下来我们就来解决为什么会查询慢。

首先了解一下MySQL查询处理的过程
1. 客户端发送SQL请求到服务器
2. 服务器检查是否可以在查询缓存中命中该SQL
4. 根据执行计划，调用存储引擎API来查询数据
5. 将解结果返回给客户端

查询缓存对SQL性能的影响：
优先检查这个查询是否命中查询缓存中的数据，这个是通过对大小写敏感的哈希值实现的，hash查找只能进行全值得匹配，即使只有一个字节不同也不会查找到缓存中的结果。在查询结果之前mysql会检查用户权限，没有权限的查询是不会被解析的，也不会生成执行计划。所以从查询缓存中直接返回结果，并不容易。检查缓存是否命中时，需要对缓存加锁。对于一个读写频繁的系统使用查询缓存很可能会降低查询的效率，所以对于读写频繁的系统建议不要使用查询缓存。

查询缓存影响性能的几个参数：
- query_cache_type 设置查询缓存是否可用  ON OFF DEMAND 如果是DEMAND表示只有在语句中使用SQL_CACHE和SQL_NO_CACHE来控制是否需要缓存  
- query_cache_size 设置查询缓存的内存大小  
- query_cache_limit 设置查询缓存可用存储的最大值，查过这个值就不会被缓存了
- query_cache_wlock_invalidate 设置表示表锁后是否返回缓存中的数据
- query_cache_min_res_unit 设置查询缓存的内存块的最小单位

查询缓存通过后进入下一个阶段MySQL依照这个执行计划和存储引擎交互  
这个阶段包括了多个子过程：  

1. SQL,预处理，优化SQL执行计划  
2. 语法解析阶段是通过关键字对MySQL语句进行解析，并生成一颗对应的'解析树'
3. MySQL解析器将使用MySQL语法规则验证和解析查询，这个阶段包括了多个子过程：

- 检查查询中所涉及的表和数据列是否存在以及名字或别名是否存在歧义等等
- 语法检查全部都通过了，查询优化器就可以生成查询计划了

会影响MySQL生成执行计划的原因：
- 统计信息不准确
- 执行计划中的成本估算不等同与实际的执行计划的成本，因为MySQL服务器层并不知道那些页面存在内存中，哪些页面在磁盘上，哪些需要顺序读取，哪些页面需要随机读取。
- MySQL优化器所认为的最优可能与你所人为的最优不一样，MySQL基于成本模型选择最优的执行计划
- MySQL从不考虑其他的并发查询，这可能回影响当前的查询速度
- MySQL有时候也会基于一些固定的规则来生成执行计划
- MySQL不会考虑不受其控制的成本，比如存储过程，用户自定义函数

MySQL优化器可优化的SQL类型
- 重新定义表的关联顺序
- 将外链接转化为内连接
- 使用等价变化规则，如（5=5 and a > 5) 将备改写为 a > 5
- 优化count()、min()和max(),如果mysql优化了这类值我们可以在执行计划中看到select tables optimized away 表示优化器已经从执行计划中移除了该表，并以一个常数取而代之
- 将一个表达式转换为一个常熟表达式
- 子查询优化
- 提前终止查询
- 对in()条件进行优化

## 7.6 如何确定查询处理各个阶段所消耗的时间
MySQL查询优化的目的就是减少查询所消耗的时间，加快查询响应的速度。

下边我们了解MySQL在各个阶段所消耗的时间。
1. 使用profile方式
    a. mysql>set profiling = 1; 启动profile 这是一个session级别的配置 
    b. 执行查询
    c. show profiles; 查看每一个查询所消耗的总时间信息
    d. show profile for query N; 查询的每个阶段所消耗的时间，N是query_id

    e. 如果想查看cpu的信息可以
    ```mysql
    mysql>show profile cpu for query 1;
    ```

 案例：

```mysql

mysql> set profiling=1;
Query OK, 0 rows affected, 1 warning (0.00 sec)
mysql> select count(*) from film;
+----------+
| count(*) |
+----------+
|     1000 |
+----------+
1 row in set (0.02 sec)

mysql> show profiles; #查看每一个查询所消耗的总时间信息
+----------+------------+---------------------------+
| Query_ID | Duration   | Query                     |
+----------+------------+---------------------------+
|        1 | 0.01702200 | select count(*) from film |
+----------+------------+---------------------------+
1 row in set, 1 warning (0.00 sec)

mysql> show profile for query 1; # 询的每个阶段所消耗的时间，1是query_id
+----------------------+----------+
| Status               | Duration |
+----------------------+----------+
| starting             | 0.005912 |
| checking permissions | 0.000012 |
| Opening tables       | 0.009966 |
| init                 | 0.000100 |
| System lock          | 0.000008 |
| optimizing           | 0.000019 |
| statistics           | 0.000009 |
| preparing            | 0.000069 |
| executing            | 0.000003 |
| Sending data         | 0.000525 |
| end                  | 0.000005 |
| query end            | 0.000006 |
| closing tables       | 0.000360 |
| freeing items        | 0.000019 |
| cleaning up          | 0.000009 |
+----------------------+----------+
15 rows in set, 1 warning (0.01 sec)

mysql> show profile cpu for query 1; #查询cpu信息
+----------------------+----------+----------+------------+
| Status               | Duration | CPU_user | CPU_system |
+----------------------+----------+----------+------------+
| starting             | 0.005912 | 0.000085 |   0.000298 |
| checking permissions | 0.000012 | 0.000007 |   0.000006 |
| Opening tables       | 0.009966 | 0.003997 |   0.004169 |
| init                 | 0.000100 | 0.000023 |   0.000076 |
| System lock          | 0.000008 | 0.000006 |   0.000001 |
| optimizing           | 0.000019 | 0.000004 |   0.000016 |
| statistics           | 0.000009 | 0.000008 |   0.000000 |
| preparing            | 0.000069 | 0.000012 |   0.000057 |
| executing            | 0.000003 | 0.000002 |   0.000001 |
| Sending data         | 0.000525 | 0.000197 |   0.000087 |
| end                  | 0.000005 | 0.000004 |   0.000002 |
| query end            | 0.000006 | 0.000005 |   0.000001 |
| closing tables       | 0.000360 | 0.000176 |   0.000184 |
| freeing items        | 0.000019 | 0.000008 |   0.000011 |
| cleaning up          | 0.000009 | 0.000009 |   0.000001 |
+----------------------+----------+----------+------------+
15 rows in set, 1 warning (0.00 sec)                                                                          
```


2. 使用performance_shema来确定各个阶段所消耗的时间
要使用这个命令，我们需要更新如下两个表的信息
```mysql
mysql>use perfomance_schema;

mysql>update `setup_instruments`
set enabled='YES', TIMED='YES' where name like 'stage%';

mysql>update `setup_consumers`
set enabled='YES' where name like 'events%';
```

消耗数据获取SQL   
![](../images/mysql/mysql_h_47.png)  

格式大致与profile一致  
![](../images/mysql/mysql_h_48.png)  

## 7.7 特定SQL的查询优化
前面我们了解了如如何获取一个存在性能问题的SQL，以及如何度量一个SQL在执行的各个阶段所消耗的时间。

举个例子：

大表更新和删除 
方案：使用分批事务的提交 

如何修改大表的结构  
方案：![](../images/mysql/mysql_h_49.png)  

如何优化 not in 和 <> 查询  
方案：![](../images/mysql/mysql_h_50.png)  

使用汇总表优化  

```mysql
select count(*) from product_comment where product_id = 999;
```

方案：
汇总表就是要提前统计的数据进行汇总并记录在表中，以备后续查询使用。

create table prodct_comment_cnt(product_id int, cnt int)



# 八、数据库的分库分表
详细介绍数据库分库分表的实现原理以及演示案例等等。

## 8.1 数据库分库分表的几种方式
在数据库数据量越来越大，访问频率越来越高的时候，数据库查询变得缓慢，增加硬件效果不是很理想，我们就可以考虑对分库分表。

方式：
1. 把一个实例中的多个数据库拆分到不同的实例  
![](../images/mysql/mysql_h_51.png)  

2. 把一个库中的表分离到不同的数据库中
![](../images/mysql/mysql_h_52.png)   

## 8.2 数据库分片前的准备
对一个库中的相关表进行水平拆分到不同实例的数据库中。
![](../images/mysql/mysql_h_53.png)   

在分库分片前我们应该考虑如下问题
如何选择分区键：
- 分区要尽可能避免分片查询的发生
- 分区键要尽可能使各个分区片中的数据平均

如何存储无需分片的表：
- 每个分片中存储一份相同的数据
- 使用额外的节点存储统一的数据

如何在节点上部署分片：
- 每个分片使用单一数据库，斌切数据库库名也相同
- 将多个分片表存储在一个数据库中，并在表名上加入分片号后缀
- 在一个节点中部署多个数据库，每个数据库包含一个分片

如何分配分片中的数据：
- 按照分区键的hash值取模来分配分片数据
- 按照分区键的范围来分配分片数据
- 利用分区键和分片的映射表来分配分片数据

如何生成全局唯一ID：
- 单节点的时候可以使用MySQL的auto_increment_increment和auto_increment_offset参数
- 多节点的时候-使用全局节点ID（全局节点可能会存在性能瓶颈）
- 多节点的时候使用Redis等缓存服务器中创建全局Id


## 8.3 数据库分片演示（上）
数据库分片软件：oneProxyp安转和配置
```mysql
wget http://www.onexsoft.cn/software/onproxy-rhel6-linux64-v5.8.1-ga.tar.gz
tar -zxvf onproxy-rhel6-linux64-v5.8.1-ga.tar.gz
```

演示略

## 8.4 数据库分片演示（下）

演示略

# 九、数据监控
## 9.1 数据库监控介绍
对于任何系统来说，监控都是非常重要的组成部分，数据库的稳定在一定程度决定了系统的稳定性。那么MySQL数据库我们都要监控什么，怎么对这些数据进行资源的监控。

需要监控的内容：
- 对数据库服务进行监控，可以连接上数据库执行一些简单的SQL达到目的，而不只是通过网络连接到数据库就确定是否可以提供服务。
- 对数据库性能进行监控（QPS和TPS,并发线程数量，慢查询监控。。）
- 如何对Innodb阻塞和死锁进行监控
- 对主从复制进行监控（主从复制链路状态的监控，主从复制延迟的监控，定期的确认主从复制的数据是否一致。。）
- 对服务器的资源进行监控 （磁盘空间，注意服务器磁盘空间并不意味MySQL数据库服务能使用的空间就足够大，CPU的使用情况，内存的使用情况，swap分区的使用情况以及网络IO的情况等）

## 9.2 数据库可用性监控
如何进行数据库可用性监控，如何确认数据库是否可以通过网络连接？
可以通过MySQL本地的SQL文件连接数据库服务器，但是这并不意味着可以通过TCP/IP协议连接到MySQL,因为MySQL服务器一般会设置防火墙的安全维护设备。

可以通过如下命令确认是否可以通过网络连接：
```shell
#1.mysqladmin -umonitor_user -p -h ping
#2.telnet ip db_port
#3.使用程序通过网络连接建立数据库连接
```

如何确认数据库是否可读写？
1. 可以通过检查数据库的read_only参数是否为off，可以定期对主服务器中的主数据库的这个参数进行检查。
2. 建立监控表并对表中的数据进行简单的更新
3. 检查是否刻读执行简单的查询 select version();

如何监控数据库的链接数量？
如果连接数据量不够，可能会出现数据库阻塞问题，所以我们需要时刻的关注数据库连接数量的变化。
```mysql
mysql> show variables like 'max_connections';
mysql> show global status like 'Threads_connected';
```
比如我们可以在监控中定义 threads_connected /max_connections > 0.8 后报警。

## 9.3 数据库性能监控
性能监控不像可用性监控那样，性能监控需要记录性能监控过程中所采集的数据库的状态，用于分析性能趋势。

如何计算QPS和TPS

QPS=(queries2-queries1） / (uptime_since_flush_status2 - uptime_since_flush_status1)  每秒钟所处理的请求数

TPS=（com_insert2+com_update2+com_delete2）-(com_insert1+com_update1+com_delete1）/ (uptime_since_flush_status2 - uptime_since_flush) 每秒钟所处理的事务的数量

如何监控数据库的并发请求数量？
数据库系统的性能随着并发处理请求的数量的增加而下降
```mysql
show global status like 'Threads_running'
```
并发处理的额数量通常会远小于同一时间连接到数据库的线程的数量

如何监控Innodb的阻塞？
下边的SQL只是试用与Innodb
```mysql
select b.trx_mysql_thread_id as '被阻塞线程',
    b.trx_query as '被阻塞SQL',
    c.trx_mysql_thread_id as '阻塞线程',
    c.trx_query as '阻塞SQL',
    (UNIX_TIMESTAMP() - UNIX_TIMESTAMP(c.trx_started)) as '阻塞时间'
FROM information_schema.innodb_lock_waits a
JOIN information_schema.innodb_trx b ON a.requesting_trx_id = b.trx_id
JOIN information_schema.innodb_trx c ON a.blocking_trx_id = c.trx_id
WHERE (UNIX_TIMESTAMP() - UNIX_TIMESTAMP(c.trx_started)) > 15 ;
```

## 9.4 MySQL主从复制监控

如何对主从复制进行监控？

如何进行主从复制链路的状态监控？

如何监控主从复制延迟？
方法1：
```mysql
show slave status; #查看主从延迟的信息
```

方法2：
使用多线程的程序同时对主从服务器的状体来进行检查

主上的二进制文件名和偏移量查看
```mysql
show master status \G;
```
从上查看
```mysql
show slave status; #查看主从延迟的信息
```

已经传输完成的主上的二进制日志的名字和偏移量


注意：当主从复制完成时候，都要检查主从复制的数据一致性，如何检查？
使用工具：pt-table-checksum

```mysql
#pt-table-checksum u=dba, p='PassWord' --database mysql --replicate test.checksums;

主上建立测试的数据库账号
grant select, process, super, replication slave on *.* to 'dba'@'ip' IDENTIFIED BY 'PassWord';
```



# 参考：

[打造扛得住的MYSQL数据库架构](https://blog.csdn.net/wind19/article/details/8600083)

[mysql状态查看 QPS/TPS/缓存命中率查看](https://blog.csdn.net/wwytwen/article/details/42006849)

[系统吞吐量、TPS（QPS）、用户并发量、性能测试概念和公式](http://blog.csdn.net/wind19/article/details/8600083)

[MYSQL调优之索引——索引失效情况](https://www.jianshu.com/p/9c9a0057221f)