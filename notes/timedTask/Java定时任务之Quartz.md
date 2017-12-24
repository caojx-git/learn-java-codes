# Java定时任务之Quartz

[toc]

## 一、简介
Quartz是OpenSymphony开源组织在Job scheduling领域又一个开源项目，它可以与J2EE与J2SE应用程序相结合也可以单独使用。Quartz可以用来
创建简单或为运行十个，百个，甚至是好几万个Jobs这样复杂的程序。Jobs可以做成标准的Java组件或 EJBs。Quartz的最新版本为Quartz 2.3.0。
--来自百度

官网：http://www.quartz-scheduler.org/

### 1.1 特点
1. 强大的调度功能  
作为Spring默认的调度框架，可以很容易与spring集成，实现灵活可配置的调度功能，并且quartz还提供了调度运行环境的持久化机制，可以保存并恢复调度
现场，即使系统因故障关闭，任务调度现场数据并不会丢失。  
2. 灵活的应用方式  
可以灵活的定义触发器的调度时间表，并且可以对任务和触发器进行关联映射，quartz提供了组件式的监听器、各种插件、线程池等功能，支持任务和调度的
多种组合方式、支持调度数据的多种存储方式。  
3. 分布式和集群能力

### 1.2 主要使用的设计模式

- Builder模式
  如创建Trigger、Job等
- Factory模式
  调度器主要使用工厂模式创建
- 组件模式
- 链式写法

### 1.3 三个核心概念

1. 任务：处理业务逻辑
2. 触发器：调度器触发的时间
3. 调度器：负责定时定频率的执行任务

即：Job、Trigger、Scheduler

### 1.4 Quartz体系结构

![](../images/timedTask/quartz/quartz-1.png)  

重要组成：
- Job：接口，取决于我们的JobDetail，只有一个方法，开发者可以实现该方法，并运行任务，相当于我们TimerTask里边的run方法。
- JobDetail: quartz在每次自行job的时候，会重新创建一个JobDetail实例，所以它不直接接受一个job实例，相反它接受一个job实现类，以编译运行时，
通过newinstance的反射机制实例化Job，因此需要一个类来描述job的实现类以及其他相关的静态信息，如Job的名字、描述、关联监听器等信息，JobDetail
就充当了这个角色。
- JobBuilder：用来定义或者创建JobDetail实例。
- JobStore：接口，用来保存Job数据。
- Trigger：用于描述触发Job的执行时间，触发规则信息。
- TriggerBuilder：用来定义或创建触发器的实例。
- ThreadPool：不同于Timer有且仅有一个后台线程在执行，quartz支持多线程，可以设置quartz执行过程中的线程池大小。
- Scheduler：调度器，代表quartz的一个独立运行的容器，Trigger和Job可以注射到Scheduler中，两者在scheduler中拥有各自的组及名称，组及名称
是scheduler查找容器中某一对象的依据，Trigger组及名称必须唯一，Job和Detail的组及名称也必须唯一，但是Job和Detail的组以名称是可以相同，因为
他们是不同类型的，scheduler中定义了多个接口方法，允许外部通过组及名称访问可以控制scheudler容器中的Trigger、JobDetail。
- Calendar：一个Trigger可以和多个Calendar关联，以排除或包含某些时间点。
- 监听器：
 JobListener、TriggerListener、SchedulerListener


