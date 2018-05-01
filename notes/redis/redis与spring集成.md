# Redis与Spring整合

[TOC]

练习源码：https://github.com/caojx-git/learn/tree/master/code/spring-redis

本文主要参考：

[架构之路之spring+redis的集成](https://blog.csdn.net/tomcat_2014/article/details/55260306)

[](https://blog.csdn.net/u010690828/article/details/77141083)

[spring整合redis(集群、主从)](https://blog.csdn.net/sunqingzhong44/article/details/70976038?locationNum=6&fps=1)

## 一、Redis简介

Redis是用C语言开发的一个开源的高性能键值对的数据库，他通过提供多种键值的数据类型来适应不同场景下的存储需求，目前为止Redis
支持的数据类型有很多种比如说字符串类型、列表类型、有序集合类型、散列类型、集合类型等。官方还提供了对Redis的测试数据，
由50个并发程序来执行10万次请求，Redis读的速度每秒可以达到11万次，写的速度每秒能达到8万1千次，速度数据是相当惊人的。


## 二、Redis与Spring整合

### 2.1 前言

在学习Redis的时候，大家应该知道，Java操作redis通常使用的是Jedis，通过java代码来操作redis的数据存储读取等操作，用过的人应该知道，Jedis客户端已经足够简单和轻量级了，但是呢，在此同时，Spring也为Redis提供了支持，就是在Spring-data模块中的Spring-Data-Redis（SDR），它一部分是基于Jedis客户端的API封装，另一部分是对Spring容器的整合。

大家应该都知道，Spring容器可以作为一个大工厂，为各种对象创建实例，关于SSM与Redis的整合，准确点，其实也就是Spring与Redis的整合。

又说回来了，Spring要和Redis整合需要用到的，就是Spring-Data模块中的Spring-data-Redis。

### 2.2 引入依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>caojx.learn</groupId>
    <artifactId>spring-redis</artifactId>
    <version>1.0-SNAPSHOT</version>
    
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>4.3.12.RELEASE</version>
            <scope>test</scope>
        </dependency>
    
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-redis</artifactId>
            <version>1.7.2.RELEASE</version>
        </dependency>
        
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
            <version>2.8.1</version>
        </dependency>
    </dependencies>
    
</project>
```

### 2.3 配置文件

#### 1. 修改redis.conf

由于我的redis部署在虚拟机中，我们需要修改redis.config配置文件让redis可以让其他主机访问

```config
#bind 127.0.0.1 
protected-mode no
```



#### 2. redis.properties

```properties
#访问地址,如果redis不是安装在本机，则远程的redis需要允许远程访问
redis.host=127.0.0.1  
#访问端口  
redis.port=6379  
#注意，如果没有password，此处不设置值，但这一项要保留  
redis.password=  
  
#最大空闲数，数据库连接的最大空闲时间。超过空闲时间，数据库连接将被标记为不可用，然后被释放。设为0表示无限制。  
redis.maxIdle=300  
#连接池的最大数据库连接数。设为0表示无限制  
redis.maxActive=600  
#最大建立连接等待时间。如果超过此时间将接到异常。设为-1表示无限制。  
redis.maxWait=1000  
#在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；  
redis.testOnBorrow=true 
```

#### 3.  redis-context.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    
    <!-- scanner redis properties  -->
    <context:property-placeholder location="classpath:redis.properties" ignore-unresolvable="true"/>
    
    <!--（1）如果你有多个数据源需要通过<context:property-placeholder管理，且不愿意放在一个配置文件里，那么一定要加上ignore-unresolvable=“true"-->
    <!--（2）注意新版的（具体从哪个版本开始不清楚，有兴趣可以查一下）JedisPoolConfig的property name，不是maxActive而是maxTotal，而且没有maxWait属性，建议看一下Jedis源码。-->
    
    <!-- redis连接池 -->
    <bean id="jedisConfig" class="redis.clients.jedis.JedisPoolConfig">
        <property name="maxTotal" value="${redis.maxActive}"></property>
        <property name="maxIdle" value="${redis.maxIdle}"></property>
        <property name="maxWaitMillis" value="${redis.maxWait}"></property>
        <property name="testOnBorrow" value="${redis.testOnBorrow}"></property>
    </bean>
    
    <!-- redis连接工厂 -->
    <bean id="connectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
        <property name="hostName" value="${redis.host}"></property>
        <property name="port" value="${redis.port}"></property>
        <property name="password" value="${redis.password}"></property>
        <property name="poolConfig" ref="jedisConfig"></property>
    </bean>
    
    <!-- redis操作模板，这里采用尽量面向对象的模板 -->
    <bean id="redisTemplate" class="org.springframework.data.redis.core.StringRedisTemplate">
        
        <property name="connectionFactory" ref="connectionFactory"/>
        <!--     如果不配置Serializer，那么存储的时候只能使用String，如果用对象类型存储，那么会提示错误 can't cast to String！！！-->
        <property name="keySerializer">
            <bean class="org.springframework.data.redis.serializer.StringRedisSerializer"/>
        </property>
        <property name="valueSerializer">
            <bean class="org.springframework.data.redis.serializer.JdkSerializationRedisSerializer"/>
        </property>
        <!--开启事务-->
        <property name="enableTransactionSupport" value="true"/>
    </bean>
    
</beans>
```

注意：

​	由于我们之前引用了mongo配置文件的properties读取，所以这里的<context:property-placeholder location="classpath:redis.properties"/>在项目加载的时候无法识别里面的占位符错误"Could not resolve placeholder"，主要原因是：

​    	Spring容器采用反射扫描的发现机制，在探测到Spring容器中有一个org.springframework.beans.factory.config.PropertyPlaceholderConfigurer的Bean就会停止对剩余PropertyPlaceholderConfigurer的扫描（Spring 3.1已经使用PropertySourcesPlaceholderConfigurer替代PropertyPlaceholderConfigurer了）。

而<context:property-placeholder/>这个基于命名空间的配置，其实内部就是创建一个PropertyPlaceholderConfigurer Bean而已。换句话说，即Spring容器仅允许最多定义一个PropertyPlaceholderConfigurer(或<context:property-placeholder/>)，其余的会被Spring忽略掉。

​	解决方法就是改成<context:property-placeholder location="classpath:redis.properties" 
**ignore-unresolvable="true"**/>即可！



### 2.4 测试

```java
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:redis-context.xml"})
public class SpringRedisTest {
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    /**
     * stringRedisTemplate的操作
     */
    @Test
    public void testSpringRedis() {
      
        
        // String读写
        redisTemplate.delete("myStr");
        redisTemplate.opsForValue().set("myStr", "skyLine");
        System.out.println(redisTemplate.opsForValue().get("myStr"));
        System.out.println("---------------");
    
        // List读写
        redisTemplate.delete("myList");
        redisTemplate.opsForList().rightPush("myList", "T");
        redisTemplate.opsForList().rightPush("myList", "L");
        redisTemplate.opsForList().leftPush("myList", "A");
        List<String> listCache = redisTemplate.opsForList().range("myList", 0, -1);
        for (String s : listCache) {
            System.out.println(s);
        }
        System.out.println("---------------");
    
        // Set读写
        redisTemplate.delete("mySet");
        redisTemplate.opsForSet().add("mySet", "A");
        redisTemplate.opsForSet().add("mySet", "B");
        redisTemplate.opsForSet().add("mySet", "C");
        Set<String> setCache = redisTemplate.opsForSet().members("mySet");
        for (String s : setCache) {
            System.out.println(s);
        }
        System.out.println("---------------");
    
        // Hash读写
        redisTemplate.delete("myHash");
        redisTemplate.opsForHash().put("myHash", "BJ", "北京");
        redisTemplate.opsForHash().put("myHash", "SH", "上海");
        redisTemplate.opsForHash().put("myHash", "HN", "河南");
        Map<Object, Object> hashCache = redisTemplate.opsForHash().entries("myHash");
        for (Map.Entry entry : hashCache.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println("---------------");
    }
}
```



结果：

```text
skyLine  
---------------  
A  
T  
L  
---------------  
C  
B  
A  
---------------  
HN - 河南  
BJ - 北京  
SH - 上海  
---------------  
```



### 2.5 封装公共操作方法

上面的代码基本使用的是StringRedisTemplate接口，redisTemplate还提供了list，set，hash类型，同时也可以保存javaBean对象，前提是改对象实现Serializable接口，下面是提出的公共方法

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis工具类
 */
public class RedisTemplateUtil {
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    public void set(String key, Object value) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }
    
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
    public void setList(String key, List<?> value) {
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush(key, value);
    }
    
    public Object getList(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }
    
    public void setSet(String key, Set<?> value) {
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add(key, value);
    }
    
    public Object getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }
    
    
    public void setHash(String key, Map<String, ?> value) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, value);
    }
    
    public Object getHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }
    
    
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void clearAll(){
        redisTemplate.multi();
    }
}
```

## 三、Redis与Spring整合-主从

redis主从配置下整合到spring 

redis主从配置参考：[redis主从配置.md](https://github.com/caojx-git/learn/blob/master/notes/redis/redis%E4%B8%BB%E4%BB%8E%E9%85%8D%E7%BD%AE.md)

### 3.1 配置文件

#### 1 reids配置

```shell
[caojx@localhost bin]$ ps -ef | grep redis
caojx      7248      1  0 07:42 ?        00:00:00 ./redis-server *:6376
caojx      7258      1  0 07:42 ?        00:00:00 ./redis-server *:6377
caojx      7268      1  0 07:42 ?        00:00:00 ./redis-server *:6378
caojx      7279      1  0 07:42 ?        00:00:00 ./redis-server *:6379
caojx      7294      1  0 07:42 ?        00:00:03 /home/caojx/redis/redis_master/bin/redis-server *:26376 [sentinel]
caojx      7299      1  1 07:42 ?        00:00:03 /home/caojx/redis/redis_slave1/bin/redis-server *:26377 [sentinel]
caojx      7304      1  1 07:42 ?        00:00:03 /home/caojx/redis/redis_slave2/bin/redis-server *:26378 [sentinel]
caojx      7309      1  1 07:42 ?        00:00:03 /home/caojx/redis/redis_slave3/bin/redis-server *:26379 [sentinel]
```

注意：我的redis在虚拟机中启动，所以需要修改redis.conf和sentinel.conf配置，允许其他主机访问。

**redis.conf**

```shell
#bind 127.0.0.1 
protected-mode no
.....
```

**sentinel.conf**

```shell
#bind 127.0.0.1
sentinel monitor mymaster 192.168.46.137 6379 2 #192.168.46.137为虚拟机的ip地址，不能是127.0.0.1
protected-mode no
....
```

#### 2. redis-ms.properties 

```properties
#redis主从

#sentinel1的IP和端口
sentinel1.host=192.168.46.137
sentinel1.port=26376

#sentinel2的IP和端口
sentinel2.host=192.168.46.137
sentinel2.port=26377

#sentinel3的IP和端口
sentinel3.host=192.168.46.137
sentinel3.port=26378

#sentinel4的IP和端口
sentinel4.host=192.168.46.137
sentinel4.port=26379

#sentinel的鉴权密码
im.hs.server.redis.sentinel.masterName=mymaster
im.hs.server.redis.sentinel.password=123456

#最大闲置连接数
im.hs.server.redis.maxIdle=500
#最大连接数，超过此连接时操作redis会报错
im.hs.server.redis.maxTotal=5000
im.hs.server.redis.maxWaitTime=1000
#最小闲置连接数，spring启动的时候自动建立该数目的连接供应用程序使用，不够的时候会申请。
im.hs.server.redis.minIdle=300
#在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；
im.hs.server.redis.testOnBorrow=true
```

#### 3. redis-ms-context.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    
    <!-- scanner redis properties  -->
    <context:property-placeholder location="classpath:redis-ms.properties" ignore-unresolvable="true"/>
    
    <!-- redis连接池 -->
    <bean id="jedisConfig" class="redis.clients.jedis.JedisPoolConfig">
        <property name="maxTotal" value="${im.hs.server.redis.maxTotal}" />
        <property name="minIdle" value="${im.hs.server.redis.minIdle}" />
        <property name="maxWaitMillis" value="${im.hs.server.redis.maxWaitTime}" />
        <property name="maxIdle" value="${im.hs.server.redis.maxIdle}" />
        <property name="testOnBorrow" value="${im.hs.server.redis.testOnBorrow}" />
    </bean>
    
    <!--哨兵配置-->
    <bean id="sentinelConfiguration" class="org.springframework.data.redis.connection.RedisSentinelConfiguration">
        <property name="master">
            <bean class="org.springframework.data.redis.connection.RedisNode">
                <property name="name" value="${im.hs.server.redis.sentinel.masterName}"></property>
            </bean>
        </property>
        <property name="sentinels">
            <set>
                <bean class="org.springframework.data.redis.connection.RedisNode">
                    <constructor-arg name="host" value="${sentinel1.host}"></constructor-arg>
                    <constructor-arg name="port" value="${sentinel1.port}"></constructor-arg>
                </bean>
                <bean class="org.springframework.data.redis.connection.RedisNode">
                    <constructor-arg name="host" value="${sentinel2.host}"></constructor-arg>
                    <constructor-arg name="port" value="${sentinel2.port}"></constructor-arg>
                </bean>
                <bean class="org.springframework.data.redis.connection.RedisNode">
                    <constructor-arg name="host" value="${sentinel3.host}"></constructor-arg>
                    <constructor-arg name="port" value="${sentinel3.port}"></constructor-arg>
                </bean>
                <bean class="org.springframework.data.redis.connection.RedisNode">
                    <constructor-arg name="host" value="${sentinel4.host}"></constructor-arg>
                    <constructor-arg name="port" value="${sentinel4.port}"></constructor-arg>
                </bean>
            </set>
        </property>
    </bean>
    
    <!--连接工厂-->
    <bean id="connectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
        <constructor-arg name="sentinelConfig" ref="sentinelConfiguration"></constructor-arg>
        <constructor-arg name="poolConfig" ref="jedisConfig"></constructor-arg>
        <property name="password" value="${im.hs.server.redis.sentinel.password}"></property>
    </bean>
    
    <!-- redis操作模板，这里采用尽量面向对象的模板 -->
    <bean id="redisTemplate" class="org.springframework.data.redis.core.StringRedisTemplate">
        
        <property name="connectionFactory" ref="connectionFactory"/>
        <!--     如果不配置Serializer，那么存储的时候只能使用String，如果用对象类型存储，那么会提示错误 can't cast to String！！！-->
        <property name="keySerializer">
            <bean class="org.springframework.data.redis.serializer.StringRedisSerializer"/>
        </property>
        <property name="valueSerializer">
            <bean class="org.springframework.data.redis.serializer.JdkSerializationRedisSerializer"/>
        </property>
        <!--开启事务-->
        <property name="enableTransactionSupport" value="true"/>
    </bean>
</beans>
```

### 3.2 测试

```java
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * spring整合单台多台redis主从
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:redis-ms-context.xml"})
public class SpringRedisTest2 {
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    /**
     * stringRedisTemplate的操作
     */
    @Test
    public void testSpringRedis() {
      
        
        // String读写
        redisTemplate.delete("myStr");
        redisTemplate.opsForValue().set("myStr", "skyLine");
        System.out.println(redisTemplate.opsForValue().get("myStr"));
        System.out.println("---------------");
    
        // List读写
        redisTemplate.delete("myList");
        redisTemplate.opsForList().rightPush("myList", "T");
        redisTemplate.opsForList().rightPush("myList", "L");
        redisTemplate.opsForList().leftPush("myList", "A");
        List<String> listCache = redisTemplate.opsForList().range("myList", 0, -1);
        for (String s : listCache) {
            System.out.println(s);
        }
        System.out.println("---------------");
    
        // Set读写
        redisTemplate.delete("mySet");
        redisTemplate.opsForSet().add("mySet", "A");
        redisTemplate.opsForSet().add("mySet", "B");
        redisTemplate.opsForSet().add("mySet", "C");
        Set<String> setCache = redisTemplate.opsForSet().members("mySet");
        for (String s : setCache) {
            System.out.println(s);
        }
        System.out.println("---------------");
    
        // Hash读写
        redisTemplate.delete("myHash");
        redisTemplate.opsForHash().put("myHash", "BJ", "北京");
        redisTemplate.opsForHash().put("myHash", "SH", "上海");
        redisTemplate.opsForHash().put("myHash", "HN", "河南");
        Map<Object, Object> hashCache = redisTemplate.opsForHash().entries("myHash");
        for (Map.Entry entry : hashCache.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println("---------------");
    }
}
```

## 四、redis与Spring整合-集群

注意本节内容来自：[spring整合redis(集群、主从)](https://blog.csdn.net/sunqingzhong44/article/details/70976038?locationNum=6&fps=1) 对应的参考ip端口改成自己的，通过对前面的单台redis与spring整合和redis主从与spring整合，相信很快可以实现redis集群与spring整合

redis集群参考：[Redis 集群](https://github.com/caojx-git/learn/blob/master/notes/redis/redis%E9%9B%86%E7%BE%A4.md)

### 4.1 redis-cluster.properties

```properties
redis.host1=192.168.1.235  
redis.port1=7001  
redis.host2=192.168.1.235  
redis.port2=7002  
redis.host3=192.168.1.235  
redis.port3=7003  
redis.host4=192.168.1.235  
redis.port4=7004  
redis.host5=192.168.1.235  
redis.port5=7005  
redis.host6=192.168.1.235  
redis.port6=7006  
  
redis.maxRedirects=3  
redis.maxIdle=30  
redis.maxTotal=100  
redis.minIdle=5  
redis.maxWaitMillis=30000   
redis.testOnBorrow=true  
redis.testOnReturn=true   
redis.testWhileIdle=true  
redis.timeout=3000  
```

### 4.2 redis-cluster.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>  
<beans xmlns="http://www.springframework.org/schema/beans"  
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"  
    xmlns:context="http://www.springframework.org/schema/context"  
    xmlns:mvc="http://www.springframework.org/schema/mvc"  
    xmlns:tx="http://www.springframework.org/schema/tx"  
    xsi:schemaLocation="http://www.springframework.org/schema/beans    
                            http://www.springframework.org/schema/beans/spring-beans-4.0.xsd    
                            http://www.springframework.org/schema/tx   
                            http://www.springframework.org/schema/tx/spring-tx-4.0.xsd    
                            http://www.springframework.org/schema/context    
                            http://www.springframework.org/schema/context/spring-context-4.0.xsd    
                            http://www.springframework.org/schema/mvc    
                            http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd">  
    
         <bean id="redisClusterConfiguration" class="org.springframework.data.redis.connection.RedisClusterConfiguration">    
                <property name="maxRedirects" value="${redis.maxRedirects}"></property>    
                <property name="clusterNodes">    
                    <set>    
                        <bean class="org.springframework.data.redis.connection.RedisNode">    
                            <constructor-arg name="host" value="${redis.host1}"></constructor-arg>    
                            <constructor-arg name="port" value="${redis.port1}"></constructor-arg>    
                        </bean>    
                        <bean class="org.springframework.data.redis.connection.RedisNode">    
                            <constructor-arg name="host" value="${redis.host2}"></constructor-arg>    
                            <constructor-arg name="port" value="${redis.port2}"></constructor-arg>    
                        </bean>    
                        <bean class="org.springframework.data.redis.connection.RedisNode">    
                            <constructor-arg name="host" value="${redis.host3}"></constructor-arg>    
                            <constructor-arg name="port" value="${redis.port3}"></constructor-arg>    
                        </bean>    
                        <bean class="org.springframework.data.redis.connection.RedisNode">    
                            <constructor-arg name="host" value="${redis.host4}"></constructor-arg>    
                            <constructor-arg name="port" value="${redis.port4}"></constructor-arg>    
                        </bean>    
                        <bean class="org.springframework.data.redis.connection.RedisNode">    
                            <constructor-arg name="host" value="${redis.host5}"></constructor-arg>    
                            <constructor-arg name="port" value="${redis.port5}"></constructor-arg>    
                        </bean>    
                        <bean class="org.springframework.data.redis.connection.RedisNode">    
                            <constructor-arg name="host" value="${redis.host6}"></constructor-arg>    
                            <constructor-arg name="port" value="${redis.port6}"></constructor-arg>    
                        </bean>    
                </set>    
        </property>    
    </bean>    
      
      
    <bean id="jedisPoolConfig"   class="redis.clients.jedis.JedisPoolConfig">    
        <property name="maxTotal" value="${redis.maxTotal}" />  
        <property name="maxIdle" value="${redis.maxIdle}" />  
        <property name="maxWaitMillis" value="${redis.maxWaitMillis}" />  
        <property name="testOnBorrow" value="${redis.testOnBorrow}" />  
    </bean>    
         
    <bean id="jeidsConnectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory"  >    
          
         <property name="poolConfig" ref="jedisPoolConfig" />  
         <constructor-arg name="clusterConfig" ref="redisClusterConfiguration"/>    
        <constructor-arg name="poolConfig" ref="jedisPoolConfig"/>     
        <!-- <property name="timeout" value="${redis.timeout}" /> -->  
    </bean>    
        
    <bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">    
        <property name="connectionFactory" ref="jeidsConnectionFactory" />  
         <property name="keySerializer" >    
            <bean class="org.springframework.data.redis.serializer.StringRedisSerializer" />    
        </property>    
        <property name="valueSerializer" >    
            <bean class="org.springframework.data.redis.serializer.JdkSerializationRedisSerializer" />    
        </property>    
        <property name="hashKeySerializer">    
            <bean class="org.springframework.data.redis.serializer.StringRedisSerializer"/>    
        </property>    
        <property name="hashValueSerializer">    
            <bean class="org.springframework.data.redis.serializer.JdkSerializationRedisSerializer"/>    
        </property>    
    </bean>     
          
  </beans>  
```



## 五、参考博文

[架构之路之spring+redis的集成](https://blog.csdn.net/tomcat_2014/article/details/55260306)

[SSM整合redis](https://blog.csdn.net/u010690828/article/details/77141083)

[spring整合redis(集群、主从)](https://blog.csdn.net/sunqingzhong44/article/details/70976038?locationNum=6&fps=1)

[Spring整合redis，通过sentinel进行主从切换](https://blog.csdn.net/albertfly/article/details/61419502)