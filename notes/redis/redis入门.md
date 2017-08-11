# Redis入门
***
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
我们只能打开浏览器去浏览里边的新闻，不能进行相应的互动。进入到Web2.0的时候，我们可以进行
之间的互动，就像你的朋友圈，你的新浪微博，都可以互动，我们可以进行评论，点赞等。随着Web2.0
的兴起，非关系型数据库已经成为了一个热门的新的领域。非关系型数据库的发展也是非常迅速的，关系型
数据库在处理Web2.0这种网站的时候，特别时超大规模的网站的时候，和一些高并发的SNS类型的Web2.0动态
类型的网站的时候，已经力不从心，暴露出很多问题，比如：
高并发读写问题、海量数据的高效率存储和访问、高可扩展性和高可用性等问题

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
### 2.1Redis的由来
2008年意大利的一家创业公司推出的一款基于MySql的网站时时的统计系统，然后没过多久，公司的创始人对MySql的性能感觉非常失望，
于是为网站时时统计系统量身定做了一个数据库，与2009年开发完成。这个数据库就是Redis，不过创始人并不满足只将Redis用于这个
产品，而是希望能有更多的人一起使用他，于是在同一年他将Redis开源，然后开始和另一名Redis的代码主要贡献者一起进行Redis的开发
直到现在，创始人自己也没有想到在短短的几年时间，Redis就拥有了相当大的用户群体。之前在2012年在一个权威的网站上发布过一个
调查的情况，全球约有12%在使用Redis，国内的如新浪、知乎等，国外如GitHub、stackoverflow等。

### 2.2什么时Redis
Redis是用C语言开发的一个开源的高性能键值对的数据库，他通过提供多种键值的数据类型来适应不同场景下的存储需求，目前为止Redis
支持的数据类型有很多种比如说字符串类型、列表类型、有序集合类型、散列类型、集合类型等。官方还提供了对Redis的测试数据，
由50个并发程序来执行10万次请求，Redis读的速度每秒可以达到11万次，写的速度每秒能达到8万1千次，速度数据是相当惊人的。

### 2.3Redis的应用场景
缓存  
任务队列（秒杀，抢购）  
网站访问统计  
数据过期的处理（可以精确到毫秒）  
应用排行榜  
分布式的集群架构中的session分离  

## 三、Redis的安装
参考：https://redis.io/download
### 3.1Linux安装
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
### 3.2Windows安装
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
安装完之后，就可看到Redis已经作为windows服务了：
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


## 参考文章
- http://www.jianshu.com/p/7913f9984765