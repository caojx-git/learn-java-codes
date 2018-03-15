#Java消息中间件-ActiveMQ

[TOC]



## 一、简介

### 1.1 案例

老王有两个女儿，每天晚上睡觉前都要给女儿们讲故事，女儿才睡得照，在沒有使中间件之前，老王需要分别给两个女儿讲故事，这样老王又要工作，又要讲故事很晚，老王也很累。

![](../images/activeMQ/jms_1.png)  

老王后来使用了中间件（假设微信是中间件），老王只要将故事推送到微信中，然后让两个女儿订阅微信中的故事，两个女儿就可以在想听故事的时候听到老王的故事，这样老王也省了很多精力。

![](../images/activeMQ/jms_2.png)  

### 1.2 消息中间件带来的好处

- 系统的解耦

- 异步

- 横向扩展

- 安全可靠

  消息中间件会将我们的消息保存，直到我们的消息被消费为止。如果其他系统没有消费，或消费系统出现异常的时候，消费系统可以在下一次恢复正常后继续消费这条消息。

- 顺序保存

### 1.3 消息中间件概述

#### 1. 什么是中间件？

非底层操作系统软件，非业务应用软件，不是直接给最终用户使用的，不能直接给客户带来价值的软件系统统称为中间件。

#### 2. 什么是消息中间件？

关注于**数据的发送和接受**，利用高效可靠的**异步**消息传递机制集成**分布式系统**。

如下是消息中间件示意图：

![](../images/activeMQ/jms_3.png)  

应用程序A通过应用程序接口向消息中间件发送消息，应用程序B通过应用程序接口向消息中间件接收消息。



#### 3.什么是 JMS？

Java消息服务（Java Message Service）即JMS，是一个Java平台中关于面向消息中间件的**API**，用于在两个应用程序之间，或分布式系统中发送消息，进行**异步**通信。

#### 4. 什么是AMQP？

AMQP（advanced message queuing protocol）是一个提供统一消息服务的**应用层**标准协议，基于此协议的客户端与消息中间件可传递消息，**并不受客户端/中间件不同产品，不同开发语言条件限制**。

#### 5. JMS与AMQP对比

|          | JMS规范                                                      | AMQP协议                                                     |
| -------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 定义     | Java API                                                     | Wire-protocol                                                |
| 跨语言   | 否                                                           | 是                                                           |
| 消息类型 | 提供两种消息类型p2p、pub/sub                                 | 提供了5中消息类型<br />direct、fanout、topic、headers、system |
| 消息类型 | TextMessage、MapMessage、ByteMessage、StreamMessage、ObjectMessage、Message | byte[]                                                       |
| 综合评价 | JMS定义了Java API层面的标准；在Java体系中国年，多个client均可以通过JMS进行交互，不需要应用修改代码，但是对于跨平台的支持较差。 | AMQP的主要特征是面向消息、队列、路由（包括点对点和发布/订阅）、可靠性、安全。 |

### 1.4 常见的消息中间件对比

#### 1. ActiveMQ

ActiveMQ是Apache出品，最流行，能力强劲的开源消息总线。ActiveMQ是一个完全支持JMS1.1和J2EE1.4规范的JMS Provider实现，尽管JMS规范出台已经是很久的事情了，但是JMS在当今的J2EE应用中间件任然扮演着特殊的地位。

**ActiveMQ的特性**：

- 多种语言和协议编写客户端
- 支持语言：Java、C、C++、C#、Ruby、Perl、Python、PHP
- 支持应用协议：OpenWire、Stomp REST、WS、Notification、XMPP、AMQP
- 完全支持JMS1.1和J2EE1.4规范（持久化、XA消息、事务）
- 虚拟主题、组合目的、镜像队列



#### 2. RabbitMQ

RabbitMQ是一个开源的AMQP实现，服务端用Erlang语言编写。用于在分布式系统中存储转发消息，在易用性、扩展性、高可用性表现不俗。

**RabbitMQ的特性**：

- 支持多种客户端，如：Java、Python、Ruby、.NET、JMS、C、PHP、ActionScript等
- AMQP的完整实现（vhost、Exchange、Binding、Routing Key等）
- 事务支持/发布确认
- 消息持久化

#### 3. Kafka

Kafka是一个高吞吐量的分布式发布订阅消息系统，是一个分布式的、分区的、可靠的分布式日志存储服务。它通过一种独一无二的设计提供了一个消息系统的功能。

**Kafka的特性**

- 通过O(1)的磁盘数据结构提供消息的持久化，这种结构对于即使数以TB的消息存储也能够保持长时间的稳定性能。
- 高吞吐量：即使是非常比普通的硬件Kafka也可以支持每秒数百万的消息。
- Partion、Consumer Group

#### 4. ActiveMQ、RabbitMQ和Kafka消息中间件对比

|          | ActiveMQ                                                     | RabbitMQ                                                     | KafKa                                                        |
| -------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 跨语言   | 支持(Java优先)                                               | 语言无关                                                     | 支持（Java优先）                                             |
| 支持协议 | OpenWire,Stomp,XMPP,AMQP                                     | AMQP                                                         |                                                              |
| 优点     | 遵循JMS规范安装部署方便                                      | 继承Erlang天生的并发性最初用于金融行业，稳定性，安全性有保障 | 依赖zk，可动态扩展节点，高性能，高吞吐量，无限扩容，消息可指定追溯 |
| 缺点     | 根据其他用户反馈，会莫名丢失消息。目前重心在下代产品apolle上，目前社区不活跃，对5.x维护较少 | Erlang语言难度较大，不支持动态扩展                           | 严格的顺序机制，不支持消息优先级，不支持标准的消息协议，不利于平台的迁移 |
| 综合评价 | 适合中小企业消息应用场景，不适合上千个队列的应用场景         | 适合对性能稳定性要求高的企业级应用                           | 一般应用在大数据日志处理货对实时性（少量延迟），可靠性（少量丢失数据）要求稍低的场景使用 |

### 1.5 JMS规范

#### 1. Java消息服务定义

Java消息服务（Java Message Service）即JMS，是一个Java平台中关于面向消息中间件的**API**，用于在两个应用程序之间，或分布式系统中发送消息，进行**异步**通信。

#### 2. JMS相关概念

**提供者**：实现JMS规范的消息中间件服务器

**客户端**：发送或接受消息的应用程序

**生产者/发布者**：创建并发送消息的客户端

**消费者/订阅者**：接受并处理消息的客户端

**消息**：应用程序之间传递的数据内容

**消息模式**：在客户端之间传递消息的方式，JMS定义了主题和队列两种模式

#### 3. 队列模式

- 客户端包括生产者和消费者
- 队列中的消息只能被一个消费者所消费
- 消费者可以随时消费队列中的消息

下图是队列模式示意图：

![](../images/activeMQ/jms_4.png)

应用1和应用2分别发送了消息M1/M3/M5 和M2/M4/M6共6条消息，然后消息队列中有了这6条消息，然后创建了2个消费者应用3建立了2个连接和应用4建立了一个连接，由结果可见，应用消费者中的每个连接平摊了队列中的每个消息。

#### 4. 主题模式

- 客户端包括发布者可订阅者
- 主题中的消息被所有订阅者消费
- 消费者不能消费订阅之前就发送到主题中的消息

下图是主题模式示意图：

![](../images/activeMQ/jms_5.png) 

创建两个订阅者应用3和应用4与JMS主题先建立连接，然后再创建两个发布者应用1和应用2与JMS主题建立连接分别发送了M1/M3/M5和M2/M4/M6，这时JMS主题中有了6条消息，由于应用3和应用4在之前就订阅了该主题的消息，所以应用3和应用4都能收到所有的消息。主要是订阅者需要提前去订阅主题。

#### 5.JMS编码接口

- ConnectionFactory 用于创建连接到消息中间件的连接工厂，这个工厂一般由消息提供商提供。
- Connection 代表了应用程序和消息服务器之间的通信链路
- Destination 指消息发布和接受的地点，包括队列或主题
- Session 表示一个单线程的上下文会话，用于发送和接收消息
- MessageConsumer 由会话创建，用于接受发送到目标的消息
- MessageProducer 由会话创建，用于发送消息到目标
- Message 是在消费者和生产者之间传送的对象，消息头，一组消息属性，一个消息体



#### 6. JMS编码接口之间的关系

![](../images/activeMQ/jms_6.png)  

首先连接工厂创建一个连接Connection，有了这个连接之后就可以创建会话Session，这里我们的连接也可以创建多个会话Session，而每个会话是在一个线程上下文的。由会话Session我们可以创建生产者和消费者，并且会话也可以用来创建一个新的消息。然后我们可以使用我们的生产者将我们的消息发送到指定的目的地，然后我们的消费者可以到指定的目的地去接收我们的消息。



## 二、安装ActiveMQ

### 2.1 Windows平台安装ActiveMQ

1. 下载安装包 http://www.apache.org/dyn/closer.cgi?filename=/activemq/5.15.3/apache-activemq-5.15.3-bin.zip&action=download

2. 直接启动

   解压安装包后进入bin/win64目录，运行activemq.bat即运行activeMQ

3. 使用服务启动

   解压安装包后进入bin/win64目录，运行installService.bat,运行之后，会将ActiveMQ以服务的方式安装到我们电脑，这样我们就可以以服务的方式启动了。

### 2.2 Linux平台安装ActiveMQ

http://www.apache.org/dyn/closer.cgi?filename=/activemq/5.15.3/apache-activemq-5.15.3-bin.tar.gz&action=download

```shell
#解压安装包
#tar -zxvf apache-activemq-5.15.3.tar.gz
#cd apache-activemq-5.15.3/bin
#ls 
activemq      activemq-diag activemq.jar  env           linux-x86-32  linux-x86-64  macosx        wrapper.jar
#启动activeMQ
#./activemq start #启动后activeMQ默认端口是8161

#需要停止activemq
#./activemq stop
```

访问：http://127.0.0.1:8161 后点击Manage ActiveMQ broker 会要求输入ActiveMQ管理密码，默认是admin/admin

![](../images/activeMQ/activeMQ_1.png)

  



## 三、JMS接口规范连接ActiveMQ

**使用JMS接口规范连接ActiveMQ**

- 创建生产者/消费者
- 创建发布者/订阅者

这里我们将演示p2p和发布订阅两种模式

注意：本节内容可以参考**1.5 JMS规范**

### 3.1 pom.xml

引入activemq依赖

```xml
<!-- 引入activemq依赖-->
<dependency>
  <groupId>org.apache.activemq</groupId>
  <artifactId>activemq-all</artifactId>
  <version>5.15.3</version>
</dependency>
```

### 3.1 队列模式的消息演示 

队列模式的特点：

- 客户端包括生产者和消费者
- 队列中的消息只能被一个消费者所消费
- 消费者可以随时消费队列中的消息

**1.AppProducer.java**

使用队列模式创建一个生产者，发送100个消息到消息队列中，所以**之前要先启动activemq**。

```java
package caojx.learn.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author caojx
 * Created on 2018/3/13 下午下午12:41
 * jms队列模式-消息生产者
 */
public class AppProducer {


    public static final String url="tcp://127.0.0.1:61616"; //连接activemq的地址，61616是连接activemq默认的端口

    public static final String queueName="queue-test"; //队列名称

    public static void main(String[] args) throws JMSException {

        //1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        //2.创建Connection
        Connection connection = connectionFactory.createConnection();

        //3.启动连接
        connection.start();

        //4.创建会话，第一个参数表示是否在事务中处理，由于是演示代码所以不使用事务false，第二个参数是连接应答模式，Session.AUTO_ACKNOWLEDGE表示自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5.创建一个目标 队列
        Destination destination = session.createQueue(queueName);

        //6.创建一个生产者,并指定目标
        MessageProducer producer = session.createProducer(destination);

        for (int i = 1; i <= 100; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("test"+i);
            //8.发送消息
            producer.send(textMessage);
            System.out.println("发送消息"+textMessage.getText());
        }
        //9.关闭连接
        connection.close();
    }
}
```

2. **执行生产者的main方法发送消息**

   控制台：

   ```text
   发送消息test1
   发送消息test2
   ...
   发送消息test97
   发送消息test98
   发送消息test99
   发送消息test100
   ```

   activemq管理界面：

   Name:队列名称 quque-test

   Number Of Pending Messages：挂起消息的数目

   Number Of Consumers：消费者数目

   Messages Enqueued：消息队列中的数目，待消费的数量

   Messages Dequeued：消息出对的数目，已消费的数量

   ![](../images/activeMQ/activemq_queue_1.png)  

3. **AppConsumer.java**

消费者，用于接收生产者发送到消息队列这中的消息

```java
package caojx.learn.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author caojx
 * Created on 2018/3/13 下午下午12:56
 * jms队列模式-消息消费者
 */
public class AppConsumer {

    public static final String url="tcp://127.0.0.1:61616"; //连接activemq的地址，61616是连接activemq默认的端口

    public static final String queueName="queue-test"; //队列名称

    public static void main(String[] args) throws JMSException {

        //1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        //2.创建Connection
        Connection connection = connectionFactory.createConnection();

        //3.启动连接
        connection.start();

        //4.创建会话，第一个参数表示是否在事务中处理，由于是演示代码所以不使用事务false，第二个参数是连接应答模式，Session.AUTO_ACKNOWLEDGE表示自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5.创建一个目标 队列
        Destination destination = session.createQueue(queueName);

        //6.创建一个消费者,并指定目标
        MessageConsumer consumer = session.createConsumer(destination);

       //7.创建一个监听器,接收消息
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("接收消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //注意，由于消息接收是异步的，所以不能关闭connection
        //connection.close();
    }
}
```

4. **执行消费者的main方法接收消息**

控制台：

```text
接收消息：test1
....
接收消息：test99
接收消息：test100
```

activemq管理界面：

Name:队列名称 quque-test

Number Of Pending Messages：挂起消息的数目

Number Of Consumers：消费者数目

Messages Enqueued：消息队列中的数目，待消费的数量

Messages Dequeued：消息出对的数目，已消费的数量

![](../images/activeMQ/activemq_queue_2.png)  

**注意：如果先启动多个消费者，再启动生产者发送消息，队列模式的消费者会平均分摊消息队列中的消息**

### 3.2 主题模式的消息演示

主题模式的特点：

- 客户端包括发布者可订阅者
- 主题中的消息被所有订阅者消费
- 消费者不能消费订阅之前就发送到主题中的消息

**1.AppProducer.java**

使用主题模式创建一个发布者，发送100个消息到主题中，所以**之前要先启动activemq**。

```java
package caojx.learn.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author caojx
 * Created on 2018/3/13 下午下午12:41
 * jms主题模式-消息发布者
 */
public class AppProducer {


    public static final String url="tcp://127.0.0.1:61616"; //连接activemq的地址，61616是连接activemq默认的端口

    public static final String topicName="topic-test"; //主题名称

    public static void main(String[] args) throws JMSException {

        //1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        //2.创建Connection
        Connection connection = connectionFactory.createConnection();

        //3.启动连接
        connection.start();

        //4.创建会话，第一个参数表示是否在事务中处理，由于是演示代码所以不使用事务false，第二个参数是连接应答模式，Session.AUTO_ACKNOWLEDGE表示自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5.创建一个目标 主题
        Destination destination = session.createTopic(topicName);

        //6.创建一个发布者,并指定目标
        MessageProducer producer = session.createProducer(destination);

        for (int i = 1; i <= 100; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("test"+i);
            //8.发送消息
            producer.send(textMessage);
            System.out.println("发送消息"+textMessage.getText());
        }
        //9.关闭连接
        connection.close();
    }
}
```

2. **AppConsumer.java**

订阅者，用于接收发布者发送的对应主题消息

```java
package caojx.learn.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author caojx
 * Created on 2018/3/13 下午下午12:56
 * jms主题模式-消息订阅者
 */
public class AppConsumer {

    public static final String url="tcp://127.0.0.1:61616"; //连接activemq的地址，61616是连接activemq默认的端口

    public static final String topicName="topic-test"; //主题名称

    public static void main(String[] args) throws JMSException {

        //1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        //2.创建Connection
        Connection connection = connectionFactory.createConnection();

        //3.启动连接
        connection.start();

        //4.创建会话，第一个参数表示是否在事务中处理，由于是演示代码所以不使用事务false，第二个参数是连接应答模式，Session.AUTO_ACKNOWLEDGE表示自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5.创建一个目标
        Destination destination = session.createTopic(topicName);

        //6.创建一个订阅者,并指定目标 主题
        MessageConsumer consumer = session.createConsumer(destination);

       //7.创建一个监听器,接收消息
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("接收消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //注意，由于消息接收是异步的，所以不能关闭connection
        //connection.close();
    }
}
```

3. **测试**

   主题模式需要先让订阅者订阅主题，然后发布者往主题中发送消息，订阅者才可以订阅到到消息。所以这里先启动订阅者（启动3个）,然后启动1个发布者发布消息。

   ​

   启动3个发布者后，topic-test显示有3个订阅者

   ![](../images/activeMQ/activemq_topic_1.png)  

   启动发布者发布消息

   发布者控制台

   ```text
   发送消息test1
   发送消息test2
   ...
   发送消息test100
   ```

   3个消费者控制台都是，即都接收到一样的消息

   ```text
   接收消息：test1
   接收消息：test2
   接收消息：test3
   ....
   接收消息：test100
   ```

   ![](../images/activeMQ/activemq_topic_2.png)    

   ​

## 四、Spring集成JMS

本节了解spring继承jms连接activemq。

### 4.1 spring继承jms连接activemq

spring继承jms连接activema，提供了

- ConnectionFactory 用于管理连接的连接工厂

  ConnectionFactory是spring为我们提供的连接池，之所有需要连接池是由于JmsTemplate每次发送消息都会重新创建连接，会话和productor 。在spring中分别提供了SingleConnectionFactory和CachingConnectionFactory。

- JmsTemplate 用于接收和发送消息的模板类

  JmsTemplate是spring提供的，只需向spring容器内注册这个类就可以使用JmsTemplate方便的操作jms。

  JmsTemplate类是线程安全的，可以在整个应用范围使用。

- MessageListerner 消息监听器

  实现一个onMessage方法，该方法只接收一个Message参数。




### 4.2 编码实现

编码实现可以边参考，可以更好理解jms

[理解JMS规范中消息的传输模式和消息持久化](http://blog.csdn.net/wilsonke/article/details/42804245)

[理解JMS规范中的持久订阅和非持久订阅](http://blog.csdn.net/aitangyong/article/details/26013387)

1.**项目目录**

![](../images/activeMQ/activemq_spring_1.png)  

2. **pom.xml**

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>caojx.learn</groupId>
    <artifactId>activemq-java</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>activemq-java</name>
    <url>http://maven.apache.org</url>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <spring.version>4.3.14.RELEASE</spring.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>3.8.1</version>
            <scope>test</scope>
        </dependency>

        <!--集成spring-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jms</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
            <scope>test</scope>
        </dependency>

        <!--集成spring的时候，可以不需要activemq-all依赖-->
        <dependency>
            <groupId>org.apache.activemq</groupId>
            <artifactId>activemq-core</artifactId>
            <version>5.7.0</version>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework</groupId>
                    <artifactId>spring-context</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

    </dependencies>
</project>
```

3. **ProducerService.java**

```java
package caojx.learn.jms.spring.queue;

/**
 * @author caojx
 * Created on 2018/3/13 下午下午9:25
 * 接收消息业务接口
 */
public interface ProducerService {

    void sendMessage(String message);
}
```

4. **ProducerServiceImpl.java**

```java
package caojx.learn.jms.spring.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * @author caojx
 * Created on 2018/3/13 下午下午9:33
 * 接收消息业务实现类
 */
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage(final String message) {
        //使用消息模板发送消息
        jmsTemplate.send(new MessageCreator() {
            //创建一个消息
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(message);
                return textMessage;
            }
        });
        System.out.println("发送消息："+message);
    }
}
```

5. **spring-jms-common.xml**

jms与spring集成公共配置，如果要使用持久模式必须指定clientId（接收者Id）,这样即使是在主题模式下订阅者也能接收到之前发布者该给对应订阅者的消息，即相当于消费者有个vip卡，消息提供者会给消费者预留消息。极力推荐两篇文章会对jms理解更深刻

[理解JMS规范中消息的传输模式和消息持久化](http://blog.csdn.net/wilsonke/article/details/42804245)

[理解JMS规范中的持久订阅和非持久订阅](http://blog.csdn.net/aitangyong/article/details/26013387)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>

    <!--ActiveMQ为我们提供的ConnectionFactory-->
    <bean id="targetConnectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
        <!-- 配置activemq的连接配置 -->
        <property name="brokerURL" value="tcp://127.0.0.1:61616"></property>
        <property name="userName" value="admin"></property>
        <property name="password" value="admin"></property>
    </bean>

    <!--spring jms为我们提供连接池，代理activemq的连接工厂-->

    <!--
    持久订阅时，客户端向JMS 服务器注册一个自己身份的ID，当这个客户端处于离线时，JMS Provider 会为这个ID 保存所有发送到主题的消息，
    当客户再次连接到JMS Provider时，会根据自己的ID得到所有当自己处于离线时发送到主题的消息。
    -->
    <bean id="connectionFactory" class="org.springframework.jms.connection.CachingConnectionFactory">
        <!--代理的连接工厂-->
        <property name="targetConnectionFactory" ref="targetConnectionFactory"></property>
        <!-- 持久订阅模式时接收者ID必须指定,如果使用非持久订阅模式时不用配置 -->
        <!--<property name="clientId" value="client_119"/>-->
        <!-- Session缓存数量 -->
        <property name="sessionCacheSize" value="10" />
    </bean>

    <!--一个队列目的地，点对点队列模式-->
    <bean id="queueDestination" class="org.apache.activemq.command.ActiveMQQueue">
        <!-- 设置消息队列的名字 -->
        <constructor-arg index="0" value="queue"/>
    </bean>

    <!--一个主题目的地，发布订阅模式-->
    <bean id="topicDestination" class="org.apache.activemq.command.ActiveMQTopic">
        <!-- 设置消息主题的名字 -->
        <constructor-arg index="0" value="topic"/>
    </bean>
</beans
```

6. **spring-jms-producer.xml**

提供者配置文件，会映入公共配置，jmsTemplate用于配置消息模板，编写的时候可以自行选择主题模式或队列模式

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!--导入公共配置-->
    <import resource="spring-jms-common.xml"></import>

    <!--消息模板,用于发送消息-->
    <bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
        <property name="connectionFactory" ref="connectionFactory"/>
        <!--发送消息的目的地,一个队列-->
        <!--<property name="defaultDestination" ref="queueDestination"/>-->
        <!--发送消息的目的地,一个主题-->
        <property name="defaultDestination" ref="topicDestination"/>
    </bean>

    <!--bean配置-->
    <bean class="caojx.learn.jms.spring.queue.ProducerServiceImpl"></bean>
</beans>
```

7. **spring-jms-consumer.xml**

消费者配置文件，使用消息监听器监听消息

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--导入公共配置-->
    <import resource="spring-jms-common.xml"></import>

    <!--配置消息监听器-->
    <bean id="consumerMessageListener" class="caojx.learn.jms.spring.queue.ConsumerMessageListener"></bean>

    <!--配置消息监听容器-->
    <bean id="jmsContainer" class="org.springframework.jms.listener.DefaultMessageListenerContainer">
        <!--连接工厂-->
        <property name="connectionFactory" ref="connectionFactory"/>
        <!--队列模式-->
        <!--<property name="destination" ref="queueDestination"/>-->
        <!--主题模式-->
        <property name="destination" ref="topicDestination"/>
        <!--消息监听器-->
        <property name="messageListener" ref="consumerMessageListener"/>

        <!-- 持久订阅模式时接收者ID必须指定,如果使用非持久订阅模式时不用配置 -->
 <!--       <property name="clientId" value="client_119"/>
        <property name="durableSubscriptionName" value="client_119"/>-->
    </bean>
</beans>
```

8. **ConsumerMessageListener.java**

消费者消息监听器，接收消息

```java
package caojx.learn.jms.spring.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author caojx
 * Created on 2018/3/13 下午下午9:49
 */
public class ConsumerMessageListener implements MessageListener {

    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("接收消息："+textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
```

9.**AppProducer.java**

消息发送者

```java
package caojx.learn.jms.spring.queue;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author caojx
 * Created on 2018/3/13 下午下午9:43
 * 消息提供者
 */
public class AppProducer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jms-producer.xml");
        ProducerService service = context.getBean(ProducerService.class);
        for (int i = 0; i < 100; i++) {
            service.sendMessage("test"+i);
        }
        //关闭资源
        context.close();
    }
}
```

10.**AppConsumer.java**

消息消费者

```java
package caojx.learn.jms.spring.queue;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author caojx
 * Created on 2018/3/13 下午下午9:57
 * 消息消费者
 */
public class AppConsumer {
    public static void main(String[] args) throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jms-consumer.xml");
    }
}
```



注意:如果使用持久订阅，由于设置了clientId,如果先启动**AppConsumer.java**消费者，由于消费者会等待接受消息，对应的clientId连接没有关闭，再启动**AppProducer.java**发布消息的时候会报错。如果不使用持久订阅，即不设置clientid，则不会出现如下错误。

下边的报错也不算是错误，如果**AppProducer.java**先发布消息context.close();关闭了clientId,然后**AppConsumer.java**接收消息就不会有这个问题了。就好比发了个消息就下线了，然后接收者上线就可以接收到消息。

```java
Exception in thread "main" org.springframework.jms.InvalidClientIDException: Broker: localhost - Client: client_119 already connected from tcp://127.0.0.1:57479; nested exception is javax.jms.InvalidClientIDException: Broker: localhost - Client: client_119 already connected from tcp://127.0.0.1:57479
	at org.springframework.jms.support.JmsUtils.convertJmsAccessException(JmsUtils.java:282)
	at org.springframework.jms.support.JmsAccessor.convertJmsAccessException(JmsAccessor.java:169)
	at org.springframework.jms.core.JmsTemplate.execute(JmsTemplate.java:487)
	at org.springframework.jms.core.JmsTemplate.send(JmsTemplate.java:559)
	at org.springframework.jms.core.JmsTemplate.send(JmsTemplate.java:550)
	at caojx.learn.jms.spring.queue.ProducerServiceImpl.sendMessage(ProducerServiceImpl.java:22)
	at caojx.learn.jms.spring.queue.AppProducer.main(AppProducer.java:15)
Caused by: javax.jms.InvalidClientIDException: Broker: localhost - Client: client_119 already connected from tcp://127.0.0.1:57479
	at org.apache.activemq.broker.region.RegionBroker.addConnection(RegionBroker.java:247)
	at org.apache.activemq.broker.jmx.ManagedRegionBroker.addConnection(ManagedRegionBroker.java:227)
	at org.apache.activemq.broker.BrokerFilter.addConnection(BrokerFilter.java:99)
	at org.apache.activemq.advisory.AdvisoryBroker.addConnection(AdvisoryBroker.java:119)
	at org.apache.activemq.broker.BrokerFilter.addConnection(BrokerFilter.java:99)
	at org.apache.activemq.broker.BrokerFilter.addConnection(BrokerFilter.java:99)
	at org.apache.activemq.broker.BrokerFilter.addConnection(BrokerFilter.java:99)
	at org.apache.activemq.broker.TransportConnection.processAddConnection(TransportConnection.java:843)
	at org.apache.activemq.broker.jmx.ManagedTransportConnection.processAddConnection(ManagedTransportConnection.java:77)
	at org.apache.activemq.command.ConnectionInfo.visit(ConnectionInfo.java:139)
	at org.apache.activemq.broker.TransportConnection.service(TransportConnection.java:330)
	at org.apache.activemq.broker.TransportConnection$1.onCommand(TransportConnection.java:194)
	at org.apache.activemq.transport.MutexTransport.onCommand(MutexTransport.java:50)
	at org.apache.activemq.transport.WireFormatNegotiator.onCommand(WireFormatNegotiator.java:125)
	at org.apache.activemq.transport.AbstractInactivityMonitor.onCommand(AbstractInactivityMonitor.java:301)
	at org.apache.activemq.transport.TransportSupport.doConsume(TransportSupport.java:83)
	at org.apache.activemq.transport.tcp.TcpTransport.doRun(TcpTransport.java:233)
	at org.apache.activemq.transport.tcp.TcpTransport.run(TcpTransport.java:215)
	at java.lang.Thread.run(Thread.java:748)
```



## 五、activeMQ集群

### 5.1 activeMQ集群理论



### 5.2 activeMQ集群实践



## 六、使用其他消息中间件





## 七、总结





## 参考与推荐文章

[理解JMS规范中消息的传输模式和消息持久化](http://blog.csdn.net/wilsonke/article/details/42804245)

[理解JMS规范中的持久订阅和非持久订阅](http://blog.csdn.net/aitangyong/article/details/26013387)

[Spring整合JMS(消息中间件)](http://blog.csdn.net/suifeng3051/article/details/51718675)

[ActiveMQ订阅模式持久化实现](http://blog.csdn.net/fulai0_0/article/details/52127320)

https://www.cnblogs.com/wangjian1990/p/6689703.html