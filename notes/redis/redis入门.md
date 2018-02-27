# Redis入门
***
[TOC]

主要介绍：

1. NoSQL的概述
2. Redis的概述
3. Redis的安装和使用
4. Jedis的入门
5. Redis的数据类型
6. Keys的特性
7. Redis的特性
8. Redis的持久化


## 一、NoSQL概述
什么是NoSQL？  
>NoSQL(Not Only SQL)，是一种非关系型数据库。

为什么需要使用NoSQL？
>随着互联网的发展，互联网经历了1.0和2.0两个阶段。1.0指的就是类似于新浪，网易早期的时候，
>我们只能打开浏览器去浏览里边的新闻，不能进行相应的互动。进入到Web2.0的时候，我们可以进行
>之间的互动，就像你的朋友圈，你的新浪微博，都可以互动，我们可以进行评论，点赞等。随着Web2.0
>的兴起，非关系型数据库已经成为了一个热门的新的领域。非关系型数据库的发展也是非常迅速的，关系型
>数据库在处理Web2.0这种网站的时候，特别时超大规模的网站的时候，和一些高并发的SNS类型的Web2.0动态
>类型的网站的时候，已经力不从心，暴露出很多问题，比如：
>高并发读写问题、海量数据的高效率存储和访问、高可扩展性和高可用性等问题

NoSQL相关产品    
![](../images/redis/redis-nosql.png)    

NoSQL数据库的四大分类    
键值对（Key-Value）存储  （优点：快速查询。劣势：存储数据缺少结构化。例如：Redis）
列储存  （优点：查询速度快，扩展性强。劣势：功能相对局限。例如：HBase）
文档数据库  (优点：数据结构要求不是特别严格。劣势：查询性能不是特别的高,缺少统一查询语法。例如：mongoDB )
图形数据库  (优点：利用图结构的算法。劣势：需要对整个图作计算才能得出结果，不容易做分布式的集群方案。例如：GridInfo)

四类NoSQL数据库的比较  
![](../images/redis/redis-nosql2.png)    

NoSQL的特点  
总的来说，NoSQL有如下特点  
易扩展：去掉了关系型数据库中的关系，很容易扩展，数据只之间没有关系。  
灵活的数据类型：无需事先定义字段。  
大量数据，高性能：具有非常高的读写性能。  
高可用：NoSQL可以在不影响性能的情况下就可以很方便的事先一些高可用的框架。  

## 二、Redis概述
### 2.1 Redis的由来
2008年意大利的一家创业公司推出的一款基于MySql的网站时时的统计系统，然后没过多久，公司的创始人对MySql的性能感觉非常失望，
于是为网站时时统计系统量身定做了一个数据库，与2009年开发完成。这个数据库就是Redis，不过创始人并不满足只将Redis用于这个
产品，而是希望能有更多的人一起使用他，于是在同一年他将Redis开源，然后开始和另一名Redis的代码主要贡献者一起进行Redis的开发
直到现在，创始人自己也没有想到在短短的几年时间，Redis就拥有了相当大的用户群体。之前在2012年在一个权威的网站上发布过一个
调查的情况，全球约有12%在使用Redis，国内的如新浪、知乎等，国外如GitHub、stackoverflow等。

### 2.2 什么是Redis
Redis是用C语言开发的一个开源的高性能键值对的数据库，他通过提供多种键值的数据类型来适应不同场景下的存储需求，目前为止Redis
支持的数据类型有很多种比如说字符串类型、列表类型、有序集合类型、散列类型、集合类型等。官方还提供了对Redis的测试数据，
由50个并发程序来执行10万次请求，Redis读的速度每秒可以达到11万次，写的速度每秒能达到8万1千次，速度数据是相当惊人的。

### 2.3 Redis的应用场景
缓存  
任务队列（秒杀，抢购）  
网站访问统计  
数据过期的处理（可以精确到毫秒）  
应用排行榜  
分布式的集群架构中的session分离  

## 三、Redis安装与使用
参考：https://redis.io/download
### 3.1 Linux安装
安装
```shell
# yum install gcc-c++  
# wget http://download.redis.io/releases/redis-4.0.1.tar.gz  
# cp redis-4.0.1.tar.gz /usr/loca/src/
# tar -zxvf redis-4.0.1.tar.gz  
# cd redis-4.0.1  
# make  
# make PREFIX=/usr/local/redis install
```
安装完成后  
![](../images/redis/redis-bin.png)  

复制redis.conf配置文件到安装目录  
```shell
# cp /usr/local/src/redis-4.0.1/redis.conf /usr/local/redis/
```
启动redis  
```shell
# cd /usr/local/redis/
# ./redis-server
```
![](../images/redis/redis-server1.png)  

上边的这中方式所有前端启动，关闭命令终端后就redis也就关闭了，下边我们需要修改redis.conf
该为后端启动。  
将daemonize no修改为daemonize yes  
![](../images/redis/redis-conf.png)  

使用后端启动，注意要指定加载的配置文件redis.conf,不然启动方式依然是前端启动。  
```shell
[root@ bin]# ./redis-server ../redis.conf  --启动redis服务
7381:C 30 Jul 18:42:38.012 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
7381:C 30 Jul 18:42:38.012 # Redis version=4.0.1, bits=64, commit=00000000, modified=0, pid=7381, just started
7381:C 30 Jul 18:42:38.012 # Configuration loaded
[root@ bin]# ps -ef | grep -i redis  --查看是否启动
root      7382     1  0 18:42 ?        00:00:00 ./redis-server 127.0.0.1:6379 （默认端口6379）
root      7403  6226  0 18:43 pts/0    00:00:00 grep --color=auto -i redis
```
停止redis
```shell
[root@ bin]# ./redis-cli shutdown
[root@ bin]# ps -ef | grep -i redis
root      7964  6226  0 18:47 pts/0    00:00:00 grep --color=auto -i redis
```
简单使用redis
```shell
[root@ bin]# ./redis-server ../redis.conf 
8024:C 30 Jul 18:49:57.562 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
8024:C 30 Jul 18:49:57.562 # Redis version=4.0.1, bits=64, commit=00000000, modified=0, pid=8024, just started
8024:C 30 Jul 18:49:57.562 # Configuration loaded
[root@ bin]# ./redis-cli 
127.0.0.1:6379> ping
PONG
127.0.0.1:6379> set name redis
OK
127.0.0.1:6379> get name
"redis"
127.0.0.1:6379> keys n*
1) "name"
127.0.0.1:6379> keys *
1) "name"
127.0.0.1:6379> del name
(integer) 1
127.0.0.1:6379> 
```
### 3.2 Windows安装
Redis官方是不支持Windows的，但是Microsoft Open Tech group 在 GitHub上开发了一个Win64的版本  
地址是https://github.com/MicrosoftArchive/redis，在 Release 页面中，有 msi 安装文件以及 .zip 文件。    
![](../images/redis/redis-windows1.png)    

安装redis的msi安装文件  
![](../images/redis/redis-windows2.png)  

![](../images/redis/redis-windows3.png)  
启动redis   
```text 
redis-server.exe redis.windows.conf 
```
但是问题又来了，关闭cmd窗口就会关闭Redis，难道服务器上要一直开着吗？这显然是不科学的，下面看怎么在服务器上部署。    
redis部署  
其实Redis是可以安装成windows服务的，开机自启动，命令如下：  
```text
redis-server --service-install redis.windows.conf
```
报错   
```text
[4600] 31 Jul 13:36:49.181 # HandleServiceCommands: system error caught. error code=1073, message = CreateService failed: unknown error
```
原因：系统服务中已经存在    
解决办法：    
先卸载服务再安装：    
```text
redis-server --service-uninstall
```
然后在：    
```text
redis-server --service-install redis.windows.conf  
```
安装完之后，就可看到Redis已经作为windows服务了   
![](../images/redis/redis-windows4.png)        

但是安装好之后，Redis并没有启动，启动命令如下：  
```text
redis-server --service-start
```
redis停止：  
```text
redis-server --service-stop
```
还可以安装多个实例  
```text
redis-server --service-install –service-name redisService1 –port 10001
redis-server --service-start –service-name redisService1
redis-server --service-install –service-name redisService2 –port 10002
redis-server --service-start –service-name redisService2
redis-server --service-install –service-name redisService3 –port 10003
redis-server --service-start –service-name redisService3
```
卸载命令：  
```text
redis-server --service-uninstall
```

### 3.3 Redis 连接

>由 youj 创建， 最后一次修改 2015-09-27

Redis 连接命令主要是用于连接 redis 服务。

**实例**

以下实例演示了客户端如何通过密码验证连接到 redis 服务，并检测服务是否在运行：

```
redis 127.0.0.1:6379> AUTH "password"
OK
redis 127.0.0.1:6379> PING
PONG
```

------

**Redis 连接命令**

下表列出了 redis 连接的基本命令：

| 序号 | 命令及描述                                                   |
| ---- | ------------------------------------------------------------ |
| 1    | [AUTH password](https://www.w3cschool.cn/redis/connection-auth.html) 验证密码是否正确 |
| 2    | [ECHO message](https://www.w3cschool.cn/redis/connection-echo.html) 打印字符串 |
| 3    | [PING](https://www.w3cschool.cn/redis/connection-ping.html) 查看服务是否运行 |
| 4    | [QUIT](https://www.w3cschool.cn/redis/connection-quit.html) 关闭当前连接 |
| 5    | [SELECT index](https://www.w3cschool.cn/redis/connection-select.html) 切换到指定的数据库 |

### 3.4 Redis 安全

我们可以通过 redis 的配置文件设置密码参数，这样客户端连接到 redis 服务就需要密码验证，这样可以让你的 redis 服务更安全。

**实例**

我们可以通过以下命令查看是否设置了密码验证：

```
127.0.0.1:6379> CONFIG get requirepass
1) "requirepass"
2) ""

```

默认情况下 requirepass 参数是空的，这就意味着你无需通过密码验证就可以连接到 redis 服务。

你可以通过以下命令来修改该参数：

```
127.0.0.1:6379> CONFIG set requirepass "w3cschool.cn"
OK
127.0.0.1:6379> CONFIG get requirepass
1) "requirepass"
2) "w3cschool.cn"

```

设置密码后，客户端连接 redis 服务就需要密码验证，否则无法执行命令。

**语法**

**AUTH** 命令基本语法格式如下：

```
127.0.0.1:6379> AUTH password

```

**实例**

```
127.0.0.1:6379> AUTH "w3cschool.cn"
OK
127.0.0.1:6379> SET mykey "Test value"
OK
127.0.0.1:6379> GET mykey
"Test value"
```

## 四、Jedis入门

- Jedis是Redis官方首选的Java客户端开发包,Redis的各种语言客户端列表，请参见[Redis Client](https://redis.io/clients)。  
- [Jedis地址](https://github.com/xetorthio/jedis)

使用Jedis依赖的Jar包,引入Maven依赖
```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
    <version>2.4.2</version>
</dependency>
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>2.9.0</version>
    <type>jar</type>
    <scope>compile</scope>
</dependency>
```

JedisDemo.java Jedis简单使用测试
```java
package personal.caojx;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName: JedisDemo
 * @Description: Jedis的测试
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-30 下午7:18
 */
public class JedisDemo {

    /**
     * 单实例的测试
     */
    @Test
    public void jedisTest(){
        //1.设置IP地址和端口
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //2.保存数据
        jedis.set("name", "tom");
        //3.获取数据
        String name = jedis.get("name");
        System.out.println(name);
        //4.释放资源
        jedis.close();
    }

    /**
     * 连接池方式连接
     * 注意Jedis对象并不是线程安全的，在多线程下使用同一个Jedis对象会出现并发问题。
     * 为了避免每次使用Jedis对象时都需要重新构建，Jedis提供了JedisPool。
     * JedisPool是基于Commons Pool 2实现的一个线程安全的连接池。
     */
    @Test
    public void jedisPoolTest(){
        //1.获取连接池对象
        JedisPoolConfig config = new JedisPoolConfig();
        //2.设置对大连接数
        config.setMaxTotal(30);
        //3.设置最大空闲连接数
        config.setMaxIdle(10);
        //4.获得连接池
        JedisPool jedisPool = new JedisPool(config,"127.0.0.1",6379);

        //5获取核心对象
        Jedis jedis = null;
        try{
            //通过连接池获得连接
            jedis = jedisPool.getResource();
            //设置数据
            jedis.set("name","张三");
            //获取数据
            String name = jedis.get("name");
            System.out.println(name);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //6.释放资源
            if(jedis!= null){
                jedis.close();
            }
            if(jedisPool != null){
                jedisPool.close();
            }
        }
    }
  }
```

## 五、Redis的数据结构
- 字符串(String)
- 字符串列表(list)
- 有序字符串集合(sorted set)
- 哈希(hash)
- 字符串集合(set)

### 5.1 存储String
二进制安全的，存入和获取的数据相同  
Value最多可以容纳的数据长度是512M  

存储String常用命令
- 赋值
- 删除
- 扩展命令
- 取值

```text
set key value
[root@ bin]# ./redis-cli 
127.0.0.1:6379> set name tom  --设置值set key value
OK
127.0.0.1:6379> get name --取值get key
"tom"
127.0.0.1:6379> getset company baidu --先取值在设置值，nil表示没有取到值
(nil)
127.0.0.1:6379> get company
"baidu"
127.0.0.1:6379> del company --删除值 del key
(integer) 1
127.0.0.1:6379> get company
(nil)
127.0.0.1:6379> incr num --数值的自增，如果这个值原来不存在，先初始化为0再加1，注意自增的值必须可以转成整形，不能转成整形的值是不能使用incr的
(integer) 1
127.0.0.1:6379> get num
"1"
127.0.0.1:6379> incr num
"2"
127.0.0.1:6379> set company baidu
OK
127.0.0.1:6379> incr company --company不能转成整形，所以不能自增
(error) ERR value is not an integer or out of range
127.0.0.1:6379> decr num --数值的自减，如果值原来不存在，先初始化为0再减1，注意自减的值必须可以转成整形，否则会报错
(integer) 1
127.0.0.1:6379> get num
"1"
127.0.0.1:6379> decr num2
(integer) -1
127.0.0.1:6379> get num2
"-1"
127.0.0.1:6379> incrby num 5 --指定自增值，指定自增5
(integer) 6
127.0.0.1:6379> incrby num3 5 --指定自增值，如果这个值原来不存在，初始化为0再增加指定值，注意这个值必须可以转化成整形
(integer) 5
127.0.0.1:6379> decrby num3 3 --指定自减值，如果这个值原来不存在，初始化为0再减少指定值，注意这个值必须可以转化成整形
(integer) 2
127.0.0.1:6379> decrby num4 3
(integer) -3
127.0.0.1:6379> append num4 5 --字符串追加
(integer) 3
127.0.0.1:6379> get num4
"-35"
127.0.0.1:6379> append num5 123
(integer) 3
127.0.0.1:6379> get num5
"123"
```
### 5.2 存储Hash
Redis中的hash可以看成具有String的key，String的value的map容器。这个容器非常是和存储值对象的信息，例如用户名、密码、年龄等。

- 存储Hash常用命令
  - 赋值
  - 删除
  - 取值
  - 增加数字

```text
127.0.0.1:6379> hset myhash username jack --设置hash值，容器名为myhash，你可以将myhash当成一个map容器或集合
(integer) 1
127.0.0.1:6379> hset myhash age 18
(integer) 1
127.0.0.1:6379> hmset myhash2 username rose age 21--批量设置hash值
OK
127.0.0.1:6379> hget myhash username --获取hash值
"jack"
127.0.0.1:6379> hget myhash age
"18"
127.0.0.1:6379> hmget myhash2 username age --批量获取hash值
1) "rose"
2) "21"
127.0.0.1:6379> hgetall myhash --或取所有的hash值
1) "username"
2) "jack"
3) "age"
4) "18"
127.0.0.1:6379> hdel mysh2 username age --删除hash值，不存在返回长度0
(integer) 0
127.0.0.1:6379> hgetall myhash2
1) "username"
2) "rose"
3) "age"
4) "21"
127.0.0.1:6379> hdel myhash2 username age
(integer) 2
127.0.0.1:6379> hmset myhash2 username rose age 21
OK
127.0.0.1:6379> hgetall myhash2
1) "username"
2) "rose"
3) "age"
4) "21"
127.0.0.1:6379> del myhash2 --删除整个hash，即删除增个容器
(integer) 1
127.0.0.1:6379> hget myhash2 username
(nil)
127.0.0.1:6379> hget myhash age --hash值自增
"18"
127.0.0.1:6379> hincrby myhash age 5
(integer) 23
127.0.0.1:6379> hget myhash age
"23"
127.0.0.1:6379> hexists myhash username --判断hash中的某个属性是否存在，存在返回>1的值，不存在返回0
(integer) 1
127.0.0.1:6379> hexists myhash password
(integer) 0
127.0.0.1:6379> hgetall myhash
1) "username"
2) "jack"
3) "age"
4) "23"
127.0.0.1:6379> hlen myhash --判断hash的大小
(integer) 2
127.0.0.1:6379> hkeys myhash --获取hash中的所有属性的key值
1) "username"
2) "age"
127.0.0.1:6379> hvals myhash --获取hash中的所有属性的值
1) "jack"
2) "23"
```

### 5.3 存储list
- ArrayList使用数组方式
- LinkedList使用双向连接方式
- 双向链表中增加数据
- 双向俩链表中删除数据

- 存储list的常用命令
  - 两端添加
  - 查看列表
  - 两端弹出
  - 获取列表元素个数
```text
127.0.0.1:6379> lpush mylist a b c --依次向左边插入元素 a b c
(integer) 3
127.0.0.1:6379> lpush mylist 1 2 3 --依次向左边插入元素 1 2 3
(integer) 6
127.0.0.1:6379> rpush mylist2 a b c --依次向右边插入元素 a b c
(integer) 3
127.0.0.1:6379> rpush mylist2 1 2 3 --依次向右边插入元素 1 2 3
(integer) 6
127.0.0.1:6379> lrange mylist 0 5 --查看list的中的元素lrange listKey start(开始索引0开始) stop(结束索引，如果是负数表示从最后开始取，-1表示最后一个元素，-2表示到数第二个，类推)
1) "3"
2) "2"
3) "1"
4) "c"
5) "b"
6) "a"
127.0.0.1:6379> lrange mylist2 0 -1
1) "a"
2) "b"
3) "c"
4) "1"
5) "2"
6) "3"
127.0.0.1:6379> lrange mylist2 0 -2
1) "a"
2) "b"
3) "c"
4) "1"
5) "2"
127.0.0.1:6379> lpop mylist  --弹出mylist左边的元素
"3"
127.0.0.1:6379> lrange mylist 0 -1
1) "2"
2) "1"
3) "c"
4) "b"
5) "a"
127.0.0.1:6379> rpop mylist --弹出mylist右边的元素
"a"
127.0.0.1:6379> lrange mylist 0 -1
1) "2"
2) "1"
3) "c"
4) "b"
127.0.0.1:6379> llen mylist --查看mylist的长度
(integer) 4
127.0.0.1:6379> lpushx mylist u --仅当mylist存在的时候，在左边插入元素u
(integer) 5
127.0.0.1:6379> lpushx mylist3 u --mylist不存在不会插入
(integer) 0
127.0.0.1:6379> rpushx mylist y --仅当mylist存在的时候，在右边插入元素y
(integer) 6
127.0.0.1:6379> lrange mylist 0 -1
1) "u"
2) "2"
3) "1"
4) "c"
5) "b"
6) "y"
127.0.0.1:6379> lpush mylist3 1 2 3
(integer) 3
127.0.0.1:6379> lpush mylist3 1 2 3
(integer) 6
127.0.0.1:6379> lpush mylist3 1 2 3
(integer) 9
127.0.0.1:6379> lrange mylist3 0 -1
1) "3"
2) "2"
3) "1"
4) "3"
5) "2"
6) "1"
7) "3"
8) "2"
9) "1"
127.0.0.1:6379> lrem mylist3 2 3  --lrem key count value 从左向右便利key依次删除2个值为3的元素
                                    如果：count > 0 从前往后删除count个值为value的元素
                                    如果：count < 0 从后往前删除count个值为value的元素
                                    如果: count = 0 删除所有值为value的元素
127.0.0.1:6379> lrange mylist3 0 -1
1) "2"
2) "1"
3) "2"
4) "1"
5) "3"
6) "2"
7) "1"
127.0.0.1:6379> lrem mylist3 0 2  --删除所有等于2的元素
(integer) 3
127.0.0.1:6379> lrange mylist3 0 -1
1) "1"
2) "1"
3) "3"
4) "1"
127.0.0.1:6379> lset mylist 3 mmm --在指定的索引为值插入元素
OK
127.0.0.1:6379> lrange mylist 0 -1
1) "u"
2) "2"
3) "1"
4) "mmm"
5) "b"
6) "y"
127.0.0.1:6379> linsert mylist4 before b 11 --在指定的元素之前插入元素,即在第一个b之前插入11
(integer) 7
127.0.0.1:6379> linsert mylist4 after b 11 --在指定的元素之后插入元素，即在第一个b之后插入11
(integer) 8
127.0.0.1:6379> lrange mylist4 0 -1
1) "c"
2) "11"
3) "b"
4) "11"
5) "a"
6) "c"
7) "b"
8) "a"
127.0.0.1:6379> lrange mylist5 0 -1
1) "3"
2) "2"
3) "1"
127.0.0.1:6379> lrange mylist6 0 -1
1) "c"
2) "b"
3) "a"
127.0.0.1:6379> rpoplpush mylist5 mylist6--rpoplpush source destination 将source中的元素弹出，push到destination中
"1"
127.0.0.1:6379> lrange mylist6 0 -1
1) "1"
2) "c"
3) "b"
4) "a"                 
```

### 5.4 存储Set
Redis中可以将Set看成没有排序的字符集合，和List类型不同的是，Set集合中不允许出现重复的元素，
Set中可以包含的最大元素数量是4294967295个

- 存储Set的使用场景
 - 跟踪一些唯一性数据
 - 用于维护数据对象之间的关联关系

- 存储Set的常用命令
  - 添加/删除元素
  - 获得集合中的元素
  - 集合中的差集运算
  - 集合中的交集运算
  - 集合中的并集运算
  - 扩展命令

```text
127.0.0.1:6379> sadd myset a b c  --set中添加元素
(integer) 3
127.0.0.1:6379> sadd myset a  --set中不能添加重复的元素
(integer) 0
127.0.0.1:6379> sadd myset 1 2 3
(integer) 3
127.0.0.1:6379> srem myset 1 2 --set中删除元素
(integer) 2
127.0.0.1:6379> smembers myset --查看set中的所有元素
1) "c"
2) "b"
3) "3"
4) "a"
127.0.0.1:6379> scard myset --查看set的长度
(integer) 4
127.0.0.1:6379> sismember myset a --判断set中是否存在元素a，存在返回1，不存在返回0
(integer) 1
127.0.0.1:6379> sismember myset q
(integer) 0
127.0.0.1:6379> sadd mya1 a b c  
(integer) 3
127.0.0.1:6379> sadd myb1 a c 1 2
(integer) 4
127.0.0.1:6379> sdiff mya1 myb1 --set的差集运算，运算结果与key的顺序有关
1) "b"
127.0.0.1:6379> sdiff myb1 mya1
1) "1"
2) "2"
127.0.0.1:6379> sadd mya2 a b c
(integer) 3
127.0.0.1:6379> sadd myb2 a c 1 2
(integer) 4
127.0.0.1:6379> sinter mya2 myb2 --set的交集运算
1) "c"
2) "a"
127.0.0.1:6379> sadd mya3 a b c
(integer) 3
127.0.0.1:6379> sadd myb3 a c 1 2
(integer) 4
127.0.0.1:6379> sunion mya3 myb3 --set的并集运算，会去掉重复的元素
1) "a"
2) "c"
3) "b"
4) "2"
5) "1"
127.0.0.1:6379> srandmember myset --从set中随即取出一个元素
"c"
127.0.0.1:6379> sdiffstore my1 mya1 myb1 --将多个set集合的差值存放到my1集合上
(integer) 1
127.0.0.1:6379> smembers my1
1) "b"
127.0.0.1:6379> sinterstore my2 mya2 myb2 --将多个set集合的交集存放到my2集合上
(integer) 2
127.0.0.1:6379> smembers my2
1) "a"
2) "c"
127.0.0.1:6379> sunion mya3 myb3
1) "a"
2) "c"
3) "b"
4) "2"
5) "1"
127.0.0.1:6379> sunionstore my3 mya3 myb3 --将多个set集合的并集存放到my3上
(integer) 5
127.0.0.1:6379> smembers my3
1) "a"
2) "c"
3) "b"
4) "2"
5) "1" 
```

### 5.5 存储Sorted-Set
- Sorted-Set和Set的区别
  1. Sorted-Set与Set很相似，都是存储字符串类型的集合，都不允许出现重复的元素在集合当中，
  2. Sorted-Set与Set的区别就是Sorted-Set中的每个元素都会有一个分数与之关联，那么Redis
    就会利用这个分数对Sorted-Set中的元素进行从小到大的排序，尽管Sorted-Set中的元素必须是
    唯一的，但是分数是可以重复的。
  3. Sorted-Set中的成员在集合中都是有序的

- 使用场景
  - 游戏的排名
  - 微博的热门话题

- 存储Sorted-Set常用命令
  - 添加元素
  - 获得元素
  - 删除元素
  - 范围查询
  - 扩展命令
```text
127.0.0.1:6379> zadd mysort 70 zs 80 ls 90 ww --添加元素，每个元素需要与一个分数对应
(integer) 3
127.0.0.1:6379> zadd mysort 100 zs --zs的分数会覆盖成为100，但是元素智能存在一个，所以返回0
(integer) 0
127.0.0.1:6379> zadd mysort 60 tom
(integer) 1
127.0.0.1:6379> zscore mysort zs --查看zs的分数
"100"
127.0.0.1:6379> zcard mysort  --查看Sorted-Set的大小
(integer) 4
127.0.0.1:6379> zrem mysort tom ww --删除tom和ww
(integer) 2
127.0.0.1:6379> zcard mysort
(integer) 2
127.0.0.1:6379> zadd mysort 85 jack 95 rose
(integer) 2
127.0.0.1:6379> zrange mysort 0 -1 --通过索引查看某个范围的值，0表示开始，-1表示结尾
1) "ls"
2) "jack"
3) "rose"
4) "zs"
127.0.0.1:6379> zrange mysort 0 -1 withscores --查看范围值同时显示分数，默认从小到大排序
1) "ls"
2) "80"
3) "jack"
4) "85"
5) "rose"
6) "95"
7) "zs"
8) "100"
127.0.0.1:6379> zrevrange mysort 0 -1 withscores --从大到小排序
1) "zs"
2) "100"
3) "rose"
4) "95"
5) "jack"
6) "85"
7) "ls"
8) "80"
127.0.0.1:6379> zremrangebyrank mysort 0 4 --按照范围进行删除
(integer) 4
127.0.0.1:6379> zadd mysort 80 zs 90 ls 100 ws
(integer) 3
127.0.0.1:6379> zremrangebyscore mysort 80 100 --按照分数范围排序
(integer) 3
127.0.0.1:6379> zrange mysort 0 -1
(empty list or set)
127.0.0.1:6379> zadd mysort 70 zs 80 li 90 ww
(integer) 3
127.0.0.1:6379> zrangebyscore mysort 0 100 withscores
1) "zs"
2) "70"
3) "li"
4) "80"
5) "ww"
6) "90"
127.0.0.1:6379> zrangebyscore mysort 0 100 withscores limit 0 2 --查看某个分数在某个范围的元素，并指定查看个数
1) "zs"
2) "70"
3) "li"
4) "80"
127.0.0.1:6379> zincrby mysort 3 li --某个元素的分数自增3
"83"
127.0.0.1:6379> zscore mysort li
"83"
127.0.0.1:6379> zcount mysort 80 90 --查看分数在某个范围内的个数
(integer) 2
```
### 5.6 Redis HyperLogLog

> 由 youj 创建， 最后一次修改 2015-09-29
>
> https://www.w3cschool.cn/redis/redis-hyperloglog.html

Redis 在 2.8.9 版本添加了 HyperLogLog 结构。

Redis HyperLogLog 是用来做基数统计的算法，HyperLogLog 的优点是，在输入元素的数量或者体积非常非常大时，计算基数所需的空间总是固定 的、并且是很小的。

在 Redis 里面，每个 HyperLogLog 键只需要花费 12 KB 内存，就可以计算接近 2^64 个不同元素的基 数。这和计算基数时，元素越多耗费内存就越多的集合形成鲜明对比。

但是，因为 HyperLogLog 只会根据输入元素来计算基数，而不会储存输入元素本身，所以 HyperLogLog 不能像集合那样，返回输入的各个元素。

**什么是基数?**

比如数据集 {1, 3, 5, 7, 5, 7, 8}， 那么这个数据集的基数集为 {1, 3, 5 ,7, 8}, 基数(不重复元素)为5。 基数估计就是在误差可接受的范围内，快速计算基数。

**实例**

以下实例演示了 HyperLogLog 的工作过程：

```
redis 127.0.0.1:6379> PFADD w3ckey "redis"

1) (integer) 1

redis 127.0.0.1:6379> PFADD w3ckey "mongodb"

1) (integer) 1

redis 127.0.0.1:6379> PFADD w3ckey "mysql"

1) (integer) 1

redis 127.0.0.1:6379> PFCOUNT w3ckey

(integer) 3

```

**Redis HyperLogLog 命令**

下表列出了 redis HyperLogLog 的基本命令：

| 序号 | 命令及描述                                                   |
| ---- | ------------------------------------------------------------ |
| 1    | [PFADD key element [element ...\]](https://www.w3cschool.cn/redis/hyperloglog-pfadd.html) 添加指定元素到 HyperLogLog 中。 |
| 2    | [PFCOUNT key [key ...\]](https://www.w3cschool.cn/redis/hyperloglog-pfcount.html) 返回给定 HyperLogLog 的基数估算值。 |
| 3    | [PFMERGE destkey sourcekey [sourcekey ...\]](https://www.w3cschool.cn/redis/hyperloglog-pfmerge.html) 将多个 HyperLogLog 合并为一个 HyperLogLog |

### 5.7 Redis 管道技术 

> 由 youj 创建， 最后一次修改 2015-09-28
>
> https://www.w3cschool.cn/redis/redis-pipelining.html

Redis是一种基于客户端-服务端模型以及请求/响应协议的TCP服务。这意味着通常情况下一个请求会遵循以下步骤：

- 客户端向服务端发送一个查询请求，并监听Socket返回，通常是以阻塞模式，等待服务端响应。
- 服务端处理命令，并将结果返回给客户端。

Redis 管道技术可以在服务端未响应时，客户端可以继续向服务端发送请求，并最终一次性读取所有服务端的响应。

**实例**

查看 redis 管道，只需要启动 redis 实例并输入以下命令：

```
$(echo -en "PING\r\n SET w3ckey redis\r\nGET w3ckey\r\nINCR visitor\r\nINCR visitor\r\nINCR visitor\r\n"; sleep 10) | nc localhost 6379

+PONG
+OK
redis
:1
:2
:3
```

以上实例中我们通过使用 **PING** 命令查看redis服务是否可用， 之后我们们设置了 w3ckey 的值为 redis，然后我们获取 w3ckey 的值并使得 visitor 自增 3 次。

在返回的结果中我们可以看到这些命令一次性向 redis 服务提交，并最终一次性读取所有服务端的响应

**管道技术的优势**

管道技术最显著的优势是提高了 redis 服务的性能。

**一些测试数据**

在下面的测试中，我们将使用Redis的Ruby客户端，支持管道技术特性，测试管道技术对速度的提升效果。

```
require 'rubygems' 
require 'redis'
def bench(descr) 
start = Time.now 
yield 
puts "#{descr} #{Time.now-start} seconds" 
end
def without_pipelining 
r = Redis.new 
10000.times { 
  r.ping 
} 
end
def with_pipelining 
r = Redis.new 
r.pipelined { 
    10000.times { 
        r.ping 
   } 
} 
end
bench("without pipelining") { 
 without_pipelining 
} 
bench("with pipelining") { 
    with_pipelining 
}

```

从处于局域网中的Mac OS X系统上执行上面这个简单脚本的数据表明，开启了管道操作后，往返时延已经被改善得相当低了。

```
without pipelining 1.185238 seconds 
with pipelining 0.250783 seconds

```

如你所见，开启管道后，我们的速度效率提升了5倍。

**Java中使用管道技术实例**

通过 pipeline 方式当有大批量的操作时候，我们可以节省很多原来浪费在网络延
迟的时间，需要注意到是用 pipeline 方式打包命令发送，redis 必须在处理完所有命令前先缓
存起所有命令的处理结果。打包的命令越多，缓存消耗内存也越多。所以并不是打包的命令
越多越好。具体多少合适需要根据具体情况测试。

```java
import org.jredis.JRedis;
import org.jredis.connector.ConnectionSpec;
import org.jredis.ri.alphazero.JRedisClient;
import org.jredis.ri.alphazero.JRedisPipelineService;
import org.jredis.ri.alphazero.connection.DefaultConnectionSpec;

public class TestPipeline {
    public static void main(String[] args) {
        long start = System.currentTimeMillis(); //采用 pipeline 方式发送指令 usePipeline();
        long end = System.currentTimeMillis();
        System.out.println("用 pipeline 方式耗时:" + (end - start) + "毫秒");
        start = System.currentTimeMillis();
        //普通方式发送指令
        withoutPipeline();
        end = System.currentTimeMillis();
        System.out.println("普通方式耗时:" + (end - start) + "毫秒");
    }

    //采用 pipeline 方式发送指令
    private static void usePipeline() {
        try {
            ConnectionSpec spec = DefaultConnectionSpec.newSpec(
                    "192.168.115.170", 6379, 0, null);
            JRedis jredis = new JRedisPipelineService(spec);
            for (int i = 0; i < 100000; i++) {
                jredis.incr("test2");
            }
            jredis.quit();
        } catch (Exception e) {
        }
    }

    //普通方式发送指令
    private static void withoutPipeline() {
        try {
            JRedis jredis = new JRedisClient("192.168.115.170", 6379);
            for (int i = 0; i < 100000; i++) {
                jredis.incr("test2");
            }
            jredis.quit();
        } catch (Exception e) {
        }
    }
}
```

执行结果如下:

```text
-- JREDIS -- INFO: Pipeline thread <response-handler> started.
-- JREDIS -- INFO: Pipeline <org.jredis.ri.alphazero.connection.SynchPipelineConnection@1bf73fa>
connected
用 pipeline 方式耗时:11531 毫秒
-- JREDIS -- INFO: Pipeline <org.jredis.ri.alphazero.connection.SynchPipelineConnection@1bf73fa> disconnected
-- JREDIS -- INFO: Pipeline thread <response-handler> stopped.
普通方式耗时:15985 毫秒
```


所以用两种方式发送指令，耗时是不一样的，具体是否使用 pipeline 必须要基于大家手中的

网络情况来决定，不能一切都按最新最好的技术来实施，因为它有可能不是最适合你的。

## 六、Keys的通用操作

```text
127.0.0.1:6379> keys * --keys pattern 查看Redis中的key，可以使用通配符
 1) "myb2"
 2) "mylist4"
 3) "mya3"
 4) "mylist5"
 5) "mya2"
 6) "my2"
.....
 127.0.0.1:6379> del myb2 mylist4 mya3 --删除key
 (integer) 3
 127.0.0.1:6379> exists myb2 --判断key是否存在，0表示不存在，1表示存在
 (integer) 0
 127.0.0.1:6379> exists my2
 (integer) 1
127.0.0.1:6379> get company
"baidu"
127.0.0.1:6379> rename company newcompany --对key进行重命名
OK
127.0.0.1:6379> get company
(nil)
127.0.0.1:6379> get newcompany
"baidu"
127.0.0.1:6379> expire newcompany 1000 --设置key值过期的时间，单位是s(秒)
(integer) 1
127.0.0.1:6379> ttl newcompany --查看key值剩余的过期时间
(integer) 992
127.0.0.1:6379> type newcompany --查看key的类型
string
127.0.0.1:6379> type mylist
list
127.0.0.1:6379> type myset
set
127.0.0.1:6379> type myhash
hash
127.0.0.1:6379> type mysort
zset
127.0.0.1:6379> flushall --清空所有的key
```

## 七、Redis的特性

- 相关特性
  - 多数据库

  - 事物特性

### 7.1 多数据库  

  一个Redis实例，可以包含多个数据库，一个客户端可以指定连接Redis实例的那个数据库。  
  一个Redis实例，最多可提供16个数据库，下标是0～15，客户端默认连接的是第0号数据库，可以通过select选择具体连接那个数据库。  

```text
127.0.0.1:6379> select 1 --1号数据库没有任何key
OK
127.0.0.1:6379[1]> keys * 
(empty list or set)
127.0.0.1:6379[1]> select 0 --Redis默认连接0号数据库
OK
127.0.0.1:6379> keys *
 1) "name"
 2) "newcompany"
 3) "myb1"
 4) "num"
....
127.0.0.1:6379> move myset 1 --将key=myset移动到1号数据库
(integer) 1
127.0.0.1:6379> select 1
OK
127.0.0.1:6379[1]> keys *
1) "myset"
127.0.0.1:6379[1]> select 0
OK
127.0.0.1:6379> keys myset
(empty list or set)
```

### 7.2 事物特性

Redis和其他众多的数据库一样，Redis也提供了事物的操作，在Redis中可以使用multi、exec、discard三个命令
来实现事物。Redis在事物中所有的命令都将顺序化，串行化执行，在事物进行期间，Redis不会为其他的客户端提供
任何的服务，从而保证事物中的所有命令都被原子化执行。和关系型数据库相比，如果Redis总某个命令执行失败，后边
的命令还会执行。其中multi相当与开启事物，在开启事物之后，没有提交之前，所有的操作都在这个事物中。exec相当
于提交事物，discard相当与事物会滚。如果在事物开启之前，如果客户端和服务器之间出现通讯故障，被导致网络断开，
那么他所执行的这些语句都将不会被提交。然而如果网络中断事件发生在执行exec之后的那么这个事物的所有命令都会被
服务器执行。

multi命令：相当于开启事物，执行该命令后会将multi之后的所有命令存放到一个队列中，待执行exec后才会将队列中的执行到服务器中
exec：提交事物，将队列中的命令执行到服务器中
discard：事物会滚

下边对三个命令进行演示，我们会开启两个命令窗口，都连接到0号数据库
窗口1：
```text
127.0.0.1:6379> select 0
OK
127.0.0.1:6379> get num
"1"
127.0.0.1:6379> incr num
(integer) 2
127.0.0.1:6379> get num
"2"
```
窗口2：
```text
127.0.0.1:6379> select 0
OK
127.0.0.1:6379> get num
"2"
```
上边可以看到，两个窗口的数据是一致的，下边我们开启事物

窗口1：
```text
127.0.0.1:6379> select 0
OK
127.0.0.1:6379> get num
"1"
127.0.0.1:6379> incr num
(integer) 2
127.0.0.1:6379> get num
"2"
127.0.0.1:6379> multi  --开启事物
OK
127.0.0.1:6379> incr num
QUEUED
```

窗口2：
```text
127.0.0.1:6379> select 0
OK
127.0.0.1:6379> get num
"2"
127.0.0.1:6379> get num --由于事物没有提交，依然是2
"2"
```
下边进行事物的提交
窗口1：
```text
127.0.0.1:6379> select 0
OK
127.0.0.1:6379> get num
"1"
127.0.0.1:6379> incr num
(integer) 2
127.0.0.1:6379> get num
"2"
127.0.0.1:6379> multi  --开启事物
OK
127.0.0.1:6379> incr num
QUEUED
127.0.0.1:6379> exec --提交事物
1) (integer) 3
2) "3"

```

窗口2：
```text
127.0.0.1:6379> select 0
OK
127.0.0.1:6379> get num
"2"
127.0.0.1:6379> get num --由于事物没有提交，依然是2
"2"
127.0.0.1:6379> get num --事物提交后，结果变成了3
"3"
```

事物的回滚演示
```text
127.0.0.1:6379> set user tom
OK
127.0.0.1:6379> get user
"tom"
127.0.0.1:6379> multi --开启事物
OK
127.0.0.1:6379> set user jerry
QUEUED
127.0.0.1:6379> discard --会滚事物
OK
127.0.0.1:6379> get user
"tom"
```

## 八、Redis持久化
Redis的高性能是由于他所有的数据都存放在内存中，那怎么能保证Redis在重启之后数据不丢失，那么就需要将数据
从内存中同步到我们的硬盘上，那么这个过程我们将之称为持久化操作，Redis的持久化有两种方式。
- RDB方式
- AOF方式

- 持久化使用方式
  - RDB持久化，Redis默认使用的方式，即在指定的时间内，将内存中的数据或快照写入到硬盘的文件中。
  - AOF持久化，这种机制将以日志的方式记录服务器处理的每一个操作，在Redis启动之初，他会读取该配置文件
    然后重新构建我们的数据库，这个的话来保证我们数据库中的数据是完整的。
  - 无持久话，我们可以通过配置来禁用Redis的持久化功能，这样我们就可以任务Redis就是一个缓存的功能
  - 同时使用RDB和AOF来进行持久化

### 8.1 RDB方式持久化
优势：
如果使用这种方式，那么整个Redis数据库将会只包含一个文件，那么这对于我们文件备份而言是非常完美的。比如说你可能每个小时
归档一次最近24小时的数据，同时还要每天归档依次最近30天的数据，那么可以通过这样的策略，一旦系统出现灾难性的故障我们可以
非常容易进行数据恢复，即优势如下。
- 对于灾难的恢复对于我们而言是非常不错的选择，我们可以非常容易的将一个单独文件压缩后再将它转移到
  其他的存储介质上。
- 性能最大化，对于Redis的服务进程而言，在开始进行持久化的时候，唯一需要做的就是分岔出一些进程，之后再由子进程完成这些持久化的操作，
  这样就可以极大的避免服务器进行IO的操作。
- 相比AOF那种操作，如果数据集很大，RDB这种启动的效率会更高。
  劣势：
- 如果想要保证数据的高可用性，最大限度的避免数据丢失，那么RDB将不是一个很好的选择，因为系统一般会在数据库持久化时间之前出现一些宕机的情况，
  这就会出现Redis还没有来的及往硬盘上写，数据就丢失了。
- 由于RDB是通过Fork子进程的方式进行持久化，在数据量非常大的时候，可能会导致整个服务器停止几秒甚至几百毫秒情况

redis.conf配置：
```text
save 900 1  #   after 900 sec (15 min) if at least 1 key changed（每900秒即15分钟，至少由一个key发生改变往硬盘上写一次)
save 300 10 #   after 300 sec (5 min) if at least 10 keys changed(每300秒即5分钟，至少有10个key发生改变往硬盘上写一次）
save 60 10000 #   after 60 sec if at least 10000 keys changed（每60秒，至少由10000个key发生改变往硬盘上写一次）
.....
# The filename where to dump the DB
dbfilename dump.rdb --数据库RDB备份文件名

# The working directory.
#
# The DB will be written inside this directory, with the filename specified
# above using the 'dbfilename' configuration directive.
# The Append Only File will also be created inside this directory.
# Note that you must specify a directory here, not a file name.
dir ./  --数据库RDB文件的存放路径，默认是当前目录
```
![](../images/redis/redis-dump.png)  

### 8.2 AOF持久化机制
优势：
- 这种方式可以带来更安全的持久化机制，Redis中提供了三种同步策略(每秒同步、每修改同步、不同步)
  每秒同步：事实上每秒同步也是异步完成的，但是他的效率也是非常高的，所差的是一旦系统出现宕机的现象，那么这一秒中之内修改的数据就会出现丢失。
  每修改同步：我们可以将其视为同步持久化，每一次数据发生变化都会被立即记录到磁盘当中，当然这种效率比较底，但是他是最安全的。
  不同步：不持久化

- 由于这种机制对日志文件的写入操作采用的是append追加方式，所以即使出现宕机的情况也不会破坏日志文件里边原有的内容，然而如果我们只写入了
  一半的数据就出现了系统崩溃问题，这种方式也不用当心，在Redis下一次启动之前我们可以通过redis -check -aof 这个工具来帮助我们解决数据一致性的问题

- 如果日志过大，Redis可以启动自动重写机制，那么Redis以append这个模式，不断将修改的数据写入到老的磁盘文件中，同时Redis还会创建一个新的文件用于记录
  此期间来产生的那些修改命令被执行了，那么因此在进行重写切换的时候可以更好的取保证数据的安全性。

- AOF包含一个格式清晰，易于理解的文件，用于记录所有的修改操作，我们可以通过这个文件来完成数据的重建工作。

劣势：
- 对于我们一个相同数量的数据集而言，AOF文件要比RDB的文件要大一些
- 根据同步策略的不同，AOF在运行效率上往往会底与RDB

redis.conf配置：
```text
appendonly no #aof这个方式默认是关闭的，如果需要打开则需要将no修改为yes

# The name of the append only file (default: "appendonly.aof")

appendfilename "appendonly.aof" #aof日志文件存放路径

appendfsync always #同步策略，总是同步
# appendfsync everysec
# appendfsync no
```

重启Redis之后我们执行如下命令
```text
127.0.0.1:6379> set name jack
OK
127.0.0.1:6379> set num 10
OK
127.0.0.1:6379> set n1 10
OK
127.0.0.1:6379> set n2 10
OK
127.0.0.1:6379> keys *
1) "name"
2) "n1"
3) "num"
4) "n2"
127.0.0.1:6379> flushall --清空所有的key
OK
127.0.0.1:6379> keys *
(empty list or set)
```

关闭redis之后我们可以看到生成了appendonly.aof文件  
![](../images/redis/redis-aof.png)  
去掉appendonly.aof中最后一行,保存
```text
vim appendonly.aof

$2
10
*1
$8
flushall --去掉该行
```
重启redis之后，可以看到redis中的所有数据都恢复了
```text
[caojx@ bin]$ sudo ./redis-server ../redis.conf 
[caojx@ bin]$ ./redis-cli
127.0.0.1:6379> keys *
1) "name"
2) "n2"
3) "n1"
4) "num"
```

### 8.3 Redis 数据备份与恢复

> 由 youj 创建， 最后一次修改 2015-09-19
>
> https://www.w3cschool.cn/redis/redis-backup.html

Redis **SAVE** 命令用于创建当前数据库的备份。

**语法**

redis Save 命令基本语法如下：

```
redis 127.0.0.1:6379> SAVE 

```

**实例**

```
redis 127.0.0.1:6379> SAVE 
OK

```

该命令将在 redis 安装目录中创建dump.rdb文件。

**恢复数据**
CONFIG
```
redis 127.0.0.1:6379> CONFIG GET dir
1) "dir"
2) "/usr/local/redis/bin"


```

以上命令 **CONFIG GET dir** 输出的 redis 安装目录为 /usr/local/redis/bin。

------

**Bgsave**

创建 redis 备份文件也可以使用命令 **BGSAVE**，该命令在后台执行。

**实例**

```
127.0.0.1:6379> BGSAVE

Background saving started
```

## 九、Redis发布订阅

> 由 youj 创建，小路依依 最后一次修改 2015-09-16
>
> https://www.w3cschool.cn/redis/redis-pub-sub.html

Redis 发布订阅(pub/sub)是一种消息通信模式：发送者(pub)发送消息，订阅者(sub)接收消息。

Redis 客户端可以订阅任意数量的频道。

下图展示了频道 channel1 ， 以及订阅这个频道的三个客户端 —— client2 、 client5 和 client1 之间的关系：

![](../images/redis/redis_subscribe_1.png)  

当有新消息通过 PUBLISH 命令发送给频道 channel1 时， 这个消息就会被发送给订阅它的三个客户端：

![](../images/redis/redis_subscribe_2.png)  

### 9.1 实例

以下实例演示了发布订阅是如何工作的。在我们实例中我们创建了订阅频道名为 **redisChat**:

```Sh
redis 127.0.0.1:6379> SUBSCRIBE redisChat

Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "redisChat"
3) (integer) 1
```

现在，我们先重新开启个 redis 客户端，然后在同一个频道 redisChat 发布两次消息，订阅者就能接收到消息。

```
redis 127.0.0.1:6379> PUBLISH redisChat "Redis is a great caching technique"

(integer) 1

redis 127.0.0.1:6379> PUBLISH redisChat "Learn redis by w3cschool.cn"

(integer) 1

# 订阅者的客户端会显示如下消息
1) "message"
2) "redisChat"
3) "Redis is a great caching technique"
1) "message"
2) "redisChat"
3) "Learn redis by w3cschool.cn"
```

### 9.2 Redis 发布订阅命令

下表列出了 redis 发布订阅常用命令：

下表列出了 redis 发布订阅常用命令：

| 序号 | 命令及描述                                                   |
| ---- | ------------------------------------------------------------ |
| 1    | [PSUBSCRIBE pattern [pattern ...\]](https://www.w3cschool.cn/redis/pub-sub-psubscribe.html) 订阅一个或多个符合给定模式的频道。 |
| 2    | [PUBSUB subcommand [argument [argument ...\]]](https://www.w3cschool.cn/redis/pub-sub-pubsub.html) 查看订阅与发布系统状态。 |
| 3    | [PUBLISH channel message](https://www.w3cschool.cn/redis/pub-sub-publish.html) 将信息发送到指定的频道。 |
| 4    | [PUNSUBSCRIBE [pattern [pattern ...\]]](https://www.w3cschool.cn/redis/pub-sub-punsubscribe.html) 退订所有给定模式的频道。 |
| 5    | [SUBSCRIBE channel [channel ...\]](https://www.w3cschool.cn/redis/pub-sub-subscribe.html) 订阅给定的一个或多个频道的信息。 |
| 6    | [UNSUBSCRIBE [channel [channel ...\]]](https://www.w3cschool.cn/redis/pub-sub-unsubscribe.html) 指退订给定的频道。 |

## 十、Redis 服务器

>由 youj 创建， 最后一次修改 2015-09-15
>
>https://www.w3cschool.cn/redis/redis-server.html

Redis 服务器命令主要是用于管理 redis 服务。

### 10.1 实例

以下实例演示了如何获取 redis 服务器的统计信息：

```
redis 127.0.0.1:6379> INFO

# Server
redis_version:2.8.13
redis_git_sha1:00000000
redis_git_dirty:0
redis_build_id:c2238b38b1edb0e2
redis_mode:standalone
os:Linux 3.5.0-48-generic x86_64
arch_bits:64
multiplexing_api:epoll
gcc_version:4.7.2
process_id:3856
run_id:0e61abd297771de3fe812a3c21027732ac9f41fe
tcp_port:6379
uptime_in_seconds:11554
uptime_in_days:0
hz:10
lru_clock:16651447
config_file:

# Clients
connected_clients:1
client-longest_output_list:0
client-biggest_input_buf:0
blocked_clients:0

# Memory
used_memory:589016
used_memory_human:575.21K
used_memory_rss:2461696
used_memory_peak:667312
used_memory_peak_human:651.67K
used_memory_lua:33792
mem_fragmentation_ratio:4.18
mem_allocator:jemalloc-3.6.0

# Persistence
loading:0
rdb_changes_since_last_save:3
rdb_bgsave_in_progress:0
rdb_last_save_time:1409158561
rdb_last_bgsave_status:ok
rdb_last_bgsave_time_sec:0
rdb_current_bgsave_time_sec:-1
aof_enabled:0
aof_rewrite_in_progress:0
aof_rewrite_scheduled:0
aof_last_rewrite_time_sec:-1
aof_current_rewrite_time_sec:-1
aof_last_bgrewrite_status:ok
aof_last_write_status:ok

# Stats
total_connections_received:24
total_commands_processed:294
instantaneous_ops_per_sec:0
rejected_connections:0
sync_full:0
sync_partial_ok:0
sync_partial_err:0
expired_keys:0
evicted_keys:0
keyspace_hits:41
keyspace_misses:82
pubsub_channels:0
pubsub_patterns:0
latest_fork_usec:264

# Replication
role:master
connected_slaves:0
master_repl_offset:0
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0

# CPU
used_cpu_sys:10.49
used_cpu_user:4.96
used_cpu_sys_children:0.00
used_cpu_user_children:0.01

# Keyspace
db0:keys=94,expires=1,avg_ttl=41638810
db1:keys=1,expires=0,avg_ttl=0
db3:keys=1,expires=0,avg_ttl=0

```

------

### 10.2 Redis 服务器命令

下表列出了 redis 服务器的相关命令:

| 序号 | 命令及描述                                                   |
| ---- | ------------------------------------------------------------ |
| 1    | [BGREWRITEAOF](https://www.w3cschool.cn/redis/server-bgrewriteaof.html) 异步执行一个 AOF（AppendOnly File） 文件重写操作 |
| 2    | [BGSAVE](https://www.w3cschool.cn/redis/server-bgsave.html) 在后台异步保存当前数据库的数据到磁盘 |
| 3    | [CLIENT KILL [ip:port\] [ID client-id]](https://www.w3cschool.cn/redis/server-client-kill.html) 关闭客户端连接 |
| 4    | [CLIENT LIST](https://www.w3cschool.cn/redis/server-client-list.html) 获取连接到服务器的客户端连接列表 |
| 5    | [CLIENT GETNAME](https://www.w3cschool.cn/redis/server-client-getname.html) 获取连接的名称 |
| 6    | [CLIENT PAUSE timeout](https://www.w3cschool.cn/redis/server-client-pause.html) 在指定时间内终止运行来自客户端的命令 |
| 7    | [CLIENT SETNAME connection-name](https://www.w3cschool.cn/redis/server-client-setname.html) 设置当前连接的名称 |
| 8    | [CLUSTER SLOTS](https://www.w3cschool.cn/redis/server-cluster-slots.html) 获取集群节点的映射数组 |
| 9    | [COMMAND](https://www.w3cschool.cn/redis/server-command.html) 获取 Redis 命令详情数组 |
| 10   | [COMMAND COUNT](https://www.w3cschool.cn/redis/server-command-count.html) 获取 Redis 命令总数 |
| 11   | [COMMAND GETKEYS](https://www.w3cschool.cn/redis/server-command-getkeys.html) 获取给定命令的所有键 |
| 12   | [TIME](https://www.w3cschool.cn/redis/server-time.html) 返回当前服务器时间 |
| 13   | [COMMAND INFO command-name [command-name ...\]](https://www.w3cschool.cn/redis/server-command-info.html) 获取指定 Redis 命令描述的数组 |
| 14   | [CONFIG GET parameter](https://www.w3cschool.cn/redis/server-config-get.html) 获取指定配置参数的值 |
| 15   | [CONFIG REWRITE](https://www.w3cschool.cn/redis/server-config-rewrite.html) 对启动 Redis 服务器时所指定的 redis.conf 配置文件进行改写 |
| 16   | [CONFIG SET parameter value](https://www.w3cschool.cn/redis/server-config-set.html) 修改 redis 配置参数，无需重启 |
| 17   | [CONFIG RESETSTAT](https://www.w3cschool.cn/redis/server-config-resetstat.html) 重置 INFO 命令中的某些统计数据 |
| 18   | [DBSIZE](https://www.w3cschool.cn/redis/server-dbsize.html) 返回当前数据库的 key 的数量 |
| 19   | [DEBUG OBJECT key](https://www.w3cschool.cn/redis/server-debug-object.html) 获取 key 的调试信息 |
| 20   | [DEBUG SEGFAULT](https://www.w3cschool.cn/redis/server-debug-segfault.html) 让 Redis 服务崩溃 |
| 21   | [FLUSHALL](https://www.w3cschool.cn/redis/server-flushall.html) 删除所有数据库的所有key |
| 22   | [FLUSHDB](https://www.w3cschool.cn/redis/server-flushdb.html) 删除当前数据库的所有key |
| 23   | [INFO [section\]](https://www.w3cschool.cn/redis/server-info.html) 获取 Redis 服务器的各种信息和统计数值 |
| 24   | [LASTSAVE](https://www.w3cschool.cn/redis/server-lastsave.html) 返回最近一次 Redis 成功将数据保存到磁盘上的时间，以 UNIX 时间戳格式表示 |
| 25   | [MONITOR](https://www.w3cschool.cn/redis/server-monitor.html) 实时打印出 Redis 服务器接收到的命令，调试用 |
| 26   | [ROLE](https://www.w3cschool.cn/redis/server-role.html) 返回主从实例所属的角色 |
| 27   | [SAVE](https://www.w3cschool.cn/redis/server-save.html) 异步保存数据到硬盘 |
| 28   | [SHUTDOWN [NOSAVE\] [SAVE]](https://www.w3cschool.cn/redis/server-shutdown.html) 异步保存数据到硬盘，并关闭服务器 |
| 29   | [SLAVEOF host port](https://www.w3cschool.cn/redis/server-slaveof.html) 将当前服务器转变为指定服务器的从属服务器(slave server) |
| 30   | [SLOWLOG subcommand [argument\]](https://www.w3cschool.cn/redis/server-showlog.html) 管理 redis 的慢日志 |
| 31   | [SYNC](https://www.w3cschool.cn/redis/server-sync.html) 用于复制功能(replication)的内部命令 |

## 十一、Redis高可用

参考：http://blog.csdn.net/hechurui/article/details/49508813

​	    https://www.cnblogs.com/jager/p/6349860.html

### 11.1 Redis 主从同步

1. **原理**

如果设置了一个从服务器，在连接时它发送了一个SYNC命令，不管它是第一次连接还是再次连接都没有关系。

然后主服务器开始后台存储，并且开始缓存新连接进来的修改数据的命令。当后台存储完成后，主服务器把数据文件发送到从服务器，
从服务器将其保存在磁盘上，然后加载到内存中。然后主服务器把刚才缓存的命令发送到从服务器。这是作为命令流来完成的，并且
和Redis协议本身格式相同。

你可以通过telnet自己尝试一下。在Redis服务器工作时连接到Redis端口，发送SYNC命令，会看到一个批量的传输，并且主服务器接收
的每一个命令都会通过telnet会话重新发送一遍。

当主从服务器之间的连接由于某些原因断开时，从服务器可以自动进行重连接。当有多个从服务器同时请求同步时，主服务器只进行一个后台存储。

当连接断开又重新连上之后，一般都会进行一个完整的重新同步，但是从Redis2.8开始，只重新同步一部分也可以。

2. **案例**

> 电子商务网站上的商品，一般都是一次上传，无数次浏览的，说专业点也就是”多读少写”

对于这种场景，我们可以使如下这种架构：

![](../images/redis/redis_ha_1.png)  

如图中所示，我们将一台Redis服务器作主库(Matser)，其他三台作为从库(Slave)，主库只负责写数据，每次有数据更新都将更新的数据同步到它所有的从库，而从库只负责读数据。这样一来，就有了两个好处：

- 读写分离，不仅可以提高服务器的负载能力，并且可以根据读请求的规模自由增加或者减少从库的数量，棒极了；
- 数据被复制成了了好几份，就算有一台机器出现故障，也可以使用其他机器的数据快速恢复。

需要注意的是：在Redis主从模式中，一台主库可以拥有多个从库，但是一个从库只能隶属于一个主库。

2.**安装**

````shell
#mkdir /home/caojx/redis
#mkdir /home/caojx/redis/redis_master 	   #创建reids安装目录
#mkdir /home/caojx/redis/redis_master/conf #用来存放redis配置文件
#mkdir /home/caojx/redis/redis_slave1
#mkdir /home/caojx/redis/redis_slave1/conf
#mkdir /home/caojx/redis/redis_slave2
#mkdir /home/caojx/redis/redis_slave2/conf
#mkdir /home/caojx/redis/redis_slave3
#mkdir /home/caojx/redis/redis_slave3/conf
#cd /home/caojx/redis
#yum install yum install gcc-c++
#wget http://download.redis.io/releases/redis-4.0.8.tar.gz
#cd redis-4.0.8
#make
#make PREFIX=/home/caojx/redis/redis_master install #redis安装
#make PREFIX=/home/caojx/redis/redis_slave1 install
#make PREFIX=/home/caojx/redis/redis_slave2 install
#make PREFIX=/home/caojx/redis/redis_slave3 install
#cp /home/caojx/redis/redis-4.0.8/redis.conf /home/caojx/redis/redis_master/conf #将redis配置文件复制到对应的安装目录
#cp /home/caojx/redis/redis-4.0.8/redis.conf /home/caojx/redis/redis_slave1/conf
#cp /home/caojx/redis/redis-4.0.8/redis.conf /home/caojx/redis/redis_slave2/conf
#cp /home/caojx/redis/redis-4.0.8/redis.conf /home/caojx/redis/redis_slave3/conf
#cp /home/caojx/redis/redis-4.0.8/sentinel.conf /home/caojx/redis/redis_slave3/conf
#cp /home/caojx/redis/redis-4.0.8/sentinel.conf /home/caojx/redis/redis_slave2/conf
#cp /home/caojx/redis/redis-4.0.8/sentinel.conf /home/caojx/redis/redis_slave1/conf
#cp /home/caojx/redis/redis-4.0.8/sentinel.conf /home/caojx/redis/redis_master/conf
````

3.**配置**

主节点（master）:127.0.0.1:6376
从节点（slave1）:127.0.0.1:6377
从节点（slave2）:127.0.0.1:6376
从节点（slave3）:127.0.0.1:6378

在Redis中，要实现主从复制架构非常简单，只需要在从数据库的(redis.conf)配置文件中加上如下命令即可,主数据库不用配置：

> 方式1：在从数据的redis.conf配置主数据库的ip/port

```Shell
slaveof 主数据库地址  主数据库端口
```

> 方式2：在启动从数据库的时候指定主数据库的ip/port

```shell
# ./redis-server --slaveof 主数据库地址 主数据库端口
```

**master的redis.conf配置**

主数据库master的不需要要配置什么，这里主要配置一下端口和密码

```shell
#vim /home/caojx/redis/redis_master/conf/redis.conf
port 6376 #设置主数据库的启动端口
requirepass 123456 #设置主数据库的密码
daemonize yes #支持后端启动
```

**slave1的redis.conf配置**

```shell
port 6377 #slave1的启动端口
slaveof 127.0.0.1 6376 #主数据库的ip 主数据库的port
masterauth 123456 #主数据库的认证密码
requirepass 123456 #从数据库密码
daemonize yes #支持后端启动
```

**slave2的redis.conf配置**
```shell
port 6378
slaveof 127.0.0.1 6376
masterauth 123456
requirepass 123456
daemonize yes
```

**slave3的redis.conf配置**
```shell
port 6379
slaveof 127.0.0.1 6376
masterauth 123456
requirepass 123456
daemonize yes
```

注意： requirepass：是认证密码，应该之后要作主从切换，所以建议所有的密码都一致。masterauth是从机对主机验证时，所需的密码(即主机的requirepass)。

4. **启动**

```shell
#启动master
#cd /home/caojx/redis/redis_master/bin
#./redis-server ../conf/redis.conf 
#启动slave1
#cd /home/caojx/redis/redis_slave1/bin
#./redis-server ../conf/redis.conf 
#启动slave2
#cd /home/caojx/redis/redis_slave2/bin
#./redis-server ../conf/redis.conf 
#启动slave3
#cd /home/caojx/redis/redis_slave3/bin
#./redis-server ../conf/redis.conf 

#ps -ef | grep -i redis #查看是否启动
caojx      5024      1  0 04:00 ?        00:00:00 ./redis-server 127.0.0.1:6376
caojx      5029      1  0 04:00 ?        00:00:00 ./redis-server 127.0.0.1:6377
caojx      5035      1  0 04:00 ?        00:00:00 ./redis-server 127.0.0.1:6378
caojx      5041      1  0 04:00 ?        00:00:00 ./redis-server 127.0.0.1:6379

#连接master
#/home/caojx/redis/redis_master/bin/redis-cli -p 6376
127.0.0.1:6376> auth 123456
OK
127.0.0.1:6376> info replication
# Replication #检查启动结果
role:master #显示未主数据库
connected_slaves:3
slave0:ip=127.0.0.1,port=6377,state=online,offset=630,lag=0
slave1:ip=127.0.0.1,port=6378,state=online,offset=630,lag=1
slave2:ip=127.0.0.1,port=6379,state=online,offset=630,lag=0
master_replid:164ccc098cc0a663b443d0ca4c81f44800380e5a
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:630
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:630
127.0.0.1:6376> exit

#连接slave1
[caojx@localhost bin]$ /home/caojx/redis/redis_slave1/bin/redis-cli -p 6377
127.0.0.1:6377> auth 123456
OK
127.0.0.1:6377> info replication
# Replication
role:slave #现实为从数据库
master_host:127.0.0.1
master_port:6376
master_link_status:up
master_last_io_seconds_ago:2
master_sync_in_progress:0
slave_repl_offset:798
slave_priority:100
slave_read_only:1
connected_slaves:0
master_replid:164ccc098cc0a663b443d0ca4c81f44800380e5a
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:798
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:798
```

5. **主从同步验证**

我们在主数据库127.0.0.1:6376中设置值，查看是否会同步到从数据库中，如果同步则配置成功。

```shell
#连接主数据库，并设置test-sync的值为helloredis
#/home/caojx/redis/redis_master/bin/redis-cli -p 6376
127.0.0.1:6376> auth 123456
OK
127.0.0.1:6376> set test-sync helloredis
OK
127.0.0.1:6376> get test-sync
"helloredis"
127.0.0.1:6376> exit

#测试slave1是否可以取得test-sync的值
#/home/caojx/redis/redis_slave1/bin/redis-cli -p 6377
127.0.0.1:6377> auth 123456
OK
127.0.0.1:6377> get test-sync
"helloredis"
127.0.0.1:6377> exit

#测试slave2是否可以取得test-sync的值
#/home/caojx/redis/redis_slave2/bin/redis-cli -p 6378
127.0.0.1:6378> auth 123456
OK
127.0.0.1:6378> get test-sync
"helloredis"
127.0.0.1:6378> exit

#测试slave3是否可以取得test-sync的值
#/home/caojx/redis/redis_slave3/bin/redis-cli -p 6379
127.0.0.1:6379> auth 123456
OK
127.0.0.1:6379> get test-sync
"helloredis"

#测试从数据库是否可写
127.0.0.1:6379> set x y
(error) READONLY You can't write against a read only slave.
127.0.0.1:6379> 
```

由上边的结果可以看到，数据确实从主库同步到了从库，但是从数据库不可写，主从复制，读写分离就实现了。

可以在从库的配置文件中加上如下的配置项允许从库写数据：

```shell
slave-read-only no
```

但是，因为从库中修改的数据不会被同步到任何其他数据库，并且一旦主库修改了数据，从库的数据就会因为自动同步被覆盖，所以一般情况下，不建议将从库设置为可写。

有至少同步给指定数量的数据库时，主数据库才是可写的：

```shell
min-slaves-to-write 3
min-slave2-max-lag 10
```

第一个参数表示只有当3个或3个以上的从数据库连接到主库时，主数据库才是可写的，否则返回错误。
第二个参数表示允许从数据库失去连接的最长时间，该选项默认是关闭的，在分布式系统中，打开并合理配置该选项可以降低主从架构因为网络分区导致的数据不一致问题。

### 11.2 Redis 主从切换

上边实现类主从同步，但是万一主机挂了怎么办，这是个麻烦事情，所以redis提供了一个sentinel（哨兵），以此来实现主从切换的功能，类似与zookeeper。

1. **手动切换(不建议使用)**

Redis的主从架构，如果没有设置哨兵，那么如果master出现故障，需要手动将slave切换成master继续服务。下面先说明如何进行手动切换：

```shell
#在新的master上执行：
SLAVEOF NO ONE #从数据库还可以通过运行命令,来停止接受来自其他数据库的同步而升级成为主库。
#在其他的slave上执行：
SLAVEOF <新的masterip> <新的masterport>
```

原来的主redis恢复正常了，要重新切换回去。重新切回的步骤如下：

```shell
1 将现在的主redis的数据进行保存（save指令）
2 将现在的主redis根目录下dump.rdb文件拷贝覆盖到原来主redis的根目录
3 启动原来的主redis
4 在现在的主redis中切换 SLAVEOF <旧的masterip> <旧的masterport>
5 在其他的slave节点切换 SLAVEOF <旧的masterip> <旧的masterport>
6 完毕
```

2. **自动切换（高可用方案）**

手动的方式容易造成失误，容易导致数据丢失，而且如果主从节点很多，切换起来也很麻烦。自动切换一般通过设置哨兵实现。哨兵可以对master和slave进行监控，并在master出现故障的时候，能自动将slave切换成master。

**Redis哨兵（Redis Sentinel）**的启动和redis实例的启动没有关系。所以可以在任何机器上启动redis哨兵。Redis Sentinel 是一个分布式系统，可以在整个redis主从架构中运行多个 Sentinel 进程（progress）。建议至少要保证有两个哨兵在运行，要不然物理机宕机后哨兵进程也不存在了，就无法进行主从切换。

3. **配置**

我们这里有4台redis服务器（1主3从），所以启动5个哨兵。每个哨兵的配置如下

配置sentinel.conf文件

```shell
#vi /home/caojx/redis/redis_master/conf/sentinel.conf

# 指名哨兵启动端口
port 26376
# 指明日志文件名
#logfile "/home/caojx/redis/log/sentinel_master_log.log"
# 是否支持后端启动
#daemonize yes
# 哨兵监控的master，主从配置一样，这里只用输入redis主节点的ip/port和法定人数。
sentinel monitor mymaster 127.0.0.1 6376 2#这个2代表，当集群中有2个sentinel认为master挂了时，才能真正认为该master已经不可用了 
# master或slave多长时间（默认30秒）不能使用后标记为s_down状态。
sentinel down-after-milliseconds mymaster 5000
# 若sentinel在该配置值内未能完成failover操作（即故障时master/slave自动切换），则认为本次failover失败。
sentinel failover-timeout mymaster 18000
# 设置master和slaves验证密码
sentinel auth-pass mymaster 123456 
# 指定了在执行故障转移时， 最多可以有多少个从服务器同时对新的主服务器进行同步
sentinel parallel-syncs mymaster 1

#vi /home/caojx/redis/redis_slave1/conf/sentinel.conf

# 指名哨兵启动端口
port 26377
# 指明日志文件名
#logfile "/home/caojx/redis/log/sentinel_slave1_log.log"
# 是否支持后端启动
#daemonize yes
# 哨兵监控的master，主从配置一样，这里只用输入redis主节点的ip/port和法定人数。
sentinel monitor mymaster 127.0.0.1 6376 2#这个2代表，当集群中有2个sentinel认为master挂了时，才能真正认为该master已经不可用了 
# master或slave多长时间（默认30秒）不能使用后标记为s_down状态。
sentinel down-after-milliseconds mymaster 5000
# 若sentinel在该配置值内未能完成failover操作（即故障时master/slave自动切换），则认为本次failover失败。
sentinel failover-timeout mymaster 18000
# 设置master和slaves验证密码
sentinel auth-pass mymaster 123456 
# 指定了在执行故障转移时， 最多可以有多少个从服务器同时对新的主服务器进行同步
sentinel parallel-syncs mymaster 1


#vi /home/caojx/redis/redis_slave2/conf/sentinel.conf

# 指名哨兵启动端口
port 26378
# 指明日志文件名
logfile "/home/caojx/redis/log/sentinel_slave2_log.log"
# 哨兵监控的master，主从配置一样，这里只用输入redis主节点的ip/port和法定人数。
sentinel monitor mymaster 127.0.0.1 6376 2#这个2代表，当集群中有2个sentinel认为master挂了时，才能真正认为该master已经不可用了 
# master或slave多长时间（默认30秒）不能使用后标记为s_down状态。
sentinel down-after-milliseconds mymaster 5000
# 若sentinel在该配置值内未能完成failover操作（即故障时master/slave自动切换），则认为本次failover失败。
sentinel failover-timeout mymaster 18000
# 设置master和slaves验证密码
sentinel auth-pass mymaster 123456 
# 指定了在执行故障转移时， 最多可以有多少个从服务器同时对新的主服务器进行同步
sentinel parallel-syncs mymaster 1

#vi /home/caojx/redis/redis_slave3/conf/sentinel.conf

# 指名哨兵启动端口
port 26379
# 指明日志文件名
#logfile "/home/caojx/redis/log/sentinel_slave3_log.log"
# 是否支持后端启动
#daemonize yes
# 哨兵监控的master，主从配置一样，这里只用输入redis主节点的ip/port和法定人数。
sentinel monitor mymaster 127.0.0.1 6376 2#这个2代表，当集群中有2个sentinel认为master挂了时，才能真正认为该master已经不可用了 
# master或slave多长时间（默认30秒）不能使用后标记为s_down状态。
sentinel down-after-milliseconds mymaster 5000
# 若sentinel在该配置值内未能完成failover操作（即故障时master/slave自动切换），则认为本次failover失败。
sentinel failover-timeout mymaster 18000
# 设置master和slaves验证密码
sentinel auth-pass mymaster 123456 
# 指定了在执行故障转移时， 最多可以有多少个从服务器同时对新的主服务器进行同步
sentinel parallel-syncs mymaster 1
```

4. **启动sentinel**

```shell
#启动master
#/home/caojx/redis/redis_master/bin/redis-server /home/caojx/redis/redis_master/conf/sentinel.conf --sentinel

#/home/caojx/redis/redis_slave1/bin/redis-server /home/caojx/redis/redis_slave1/conf/sentinel.conf --sentinel

#/home/caojx/redis/redis_slave2/bin/redis-server /home/caojx/redis/redis_slave2/conf/sentinel.conf --sentinel

#/home/caojx/redis/redis_slave3/bin/redis-server /home/caojx/redis/redis_slave3/conf/sentinel.conf --sentinel

#ps -ef | grep redis
caojx  1116  1  0 04:01 ?  00:00:02 /home/caojx/redis/redis_slave1/bin/redis-server 127.0.0.1:6377
caojx  1122  1  0 04:02 ?  00:00:02 /home/caojx/redis/redis_slave2/bin/redis-server 127.0.0.1:6378
caojx  1127  1  0 04:02 ?  00:00:02 /home/caojx/redis/redis_slave3/bin/redis-server 127.0.0.1:6379
caojx  1958  1  0 04:46 ?  00:00:00 ./redis-server 127.0.0.1:6376

caojx  1936  1068  0 04:44 pts/0 00:00:00 /home/caojx/redis/redis_master/bin/redis-server *:26376 [sentinel]
caojx  1940  1168  0 04:45 pts/1    00:00:00 ./redis-server *:26377 [sentinel]
caojx  1944  1200  0 04:45 pts/2    00:00:00 ./redis-server *:26378 [sentinel]
caojx  1949  1255  0 04:45 pts/4    00:00:00 ./redis-server *:26379 [sentinel]
```

5. **测试**

杀死master（6376）看是否自动切换到其中的一台slave

```shell
#kill -9 1958
#在setinel日志信息中可以看到如下信息，即master从6376切换到了6379
1940:X 27 Feb 04:47:09.779 # +switch-master mymaster 127.0.0.1 6376 127.0.0.1 6379
1940:X 27 Feb 04:47:09.779 * +slave slave 127.0.0.1:6378 127.0.0.1 6378 @ mymaster 127.0.0.1 6379
#登录6379验证
# ../../redis_slave3/bin/redis-cli -p 6379
127.0.0.1:6379> auth 123456
OK
127.0.0.1:6379> info replication
# Replication
role:master #现实6379变成了master,实现了自动切换
connected_slaves:2
slave0:ip=127.0.0.1,port=6378,state=online,offset=31212,lag=0
slave1:ip=127.0.0.1,port=6377,state=online,offset=31079,lag=1
master_replid:edd3fa87e36e165ba811052fab54bf5c1797ecd9
master_replid2:ce6109ff57ca0183044ee0e22d2a02a997320672
master_repl_offset:31478
second_repl_offset:9665
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:290
repl_backlog_histlen:31189
```

6. **其他**

通过哨兵查看集群的信息：

```shell
#redis-cli -p 26379
sentinel master mymaster//查看master的状态 
SENTINEL slaves mymaster //查看salves的状态
SENTINEL sentinels mymaster //查看哨兵的状态
SENTINEL get-master-addr-by-name mymaster//获取当前master的地址
info sentinel//查看哨兵信息
```

### 11.3 Jedis 代码测试 

```java
public static void main(String[] args) {
        Set<String> sentinels = new HashSet<String>();
        sentinels.add(new HostAndPort("127.0.0.1", 26376).toString());
        sentinels.add(new HostAndPort("127.0.0.1", 26377).toString());
        sentinels.add(new HostAndPort("127.0.0.1", 26378).toString());
        sentinels.add(new HostAndPort("127.0.0.1", 26379).toString());
        JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels);

        System.out.println("Current master: " + sentinelPool.getCurrentHostMaster().toString());

        Jedis master = sentinelPool.getResource();
        //master.set("username","jager");

        System.out.println(master.get("username"));

        sentinelPool.close();
        sentinelPool.destroy();
    }
```

## 参考文章

- http://www.jianshu.com/p/7913f9984765
- https://www.w3cschool.cn/redis/
- http://blog.csdn.net/hechurui/article/details/49508813
- https://www.cnblogs.com/jager/p/6349860.html
