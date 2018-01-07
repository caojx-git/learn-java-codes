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
  
JobDetail:任务，包含了任务的实现类，以及类的信息  
Trigger:触发器，决定任务什么时候被调用  
scheduler:调度器，将JobDetail绑定在一起，能够定时定频率的执行JobDetail  

重要组成：
- Job：接口，取决于我们的JobDetail，只有一个方法，开发者可以实现该方法，并运行任务，相当于Java中的TimerTask里边的run方法。
- JobDetail: quartz在每次执行job的时候，会重新创建一个JobDetail实例，所以它不直接接受一个job实例，相反它接受一个job实现类，以编译运行时，
通过newinstance的反射机制实例化Job，因此需要一个类来描述job的实现类以及其他相关的静态信息，如Job的名字、描述、关联监听器等信息，JobDetail
就充当了这个角色。
- JobBuilder：用来定义或者创建JobDetail实例。
- JobStore：接口，用来保存Job数据。
- Trigger：用于描述触发Job的执行时间，触发规则信息。
- TriggerBuilder：用来定义或创建触发器的实例。
- ThreadPool：不同于Timer有且仅有一个后台线程在执行，quartz支持多线程，可以设置quartz执行过程中的线程池大小。
- Scheduler：调度器，代表quartz的一个独立运行的容器，Trigger和Job可以注射到Scheduler中，两者在scheduler中拥有各自的组及名称，组及名称
是scheduler查找容器中某一对象的依据，Trigger组及名称必须唯一，Job和Detail的组及名称也必须唯一，但是Job和trigger的组以名称是可以相同，因为
他们是不同类型的，scheduler中定义了多个接口方法，允许外部通过组及名称访问可以控制scheduler容器中的Trigger、JobDetail。
- Calendar：一个Trigger可以和多个Calendar关联，以排除或包含某些时间点。
- 监听器：
 JobListener、TriggerListener、SchedulerListener
 
 ## 二、第一个Quartz程序
 
 准备工作：  
 - 建立maven工程
 - 引入Quartz.jar包  
 
 案例：使用Quartz实现每2s中打印一次hello job   
 
 2.1 HelloJob.java
 ```java
package learn.caojx;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定义一个HelloJob类实现Job接口
 */
public class HelloJob implements Job{

    /**
     * execute里边用于编写具体的业务逻辑，与TimerTask里边的run相似
     */
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //编写具体的业务逻辑

        //打印当前执行的时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Exec Time is:"+simpleDateFormat.format(date));
        System.out.println("hello job！");
    }
}
```

2.2 HelloScheduler.java  
scheduler将job与trigger绑定实现每2s中打印一次hello job  
```java
package learn.caojx;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloScheduler {

    public static void main(String[] args) throws SchedulerException {
        //1.创建一个JobDetail实例，将该类与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob", "group1").build();
        //2.创建一个Trigger实例，定义该Job立即执行，并且每个两秒重复执行一次,注意trigger中的group1与job中的group1不是同一个组，因为她们不是同一种对象
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
        //3.创建Scheduler实例
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        //4.将jobDetail与trigger绑定
        scheduler.scheduleJob(jobDetail,trigger);

    }
}
```

## 三、浅谈Quartz

### 3.1 浅谈Job与JobDetail

1. 浅谈Job

- Job的定义  
实现业务逻辑任务的接口，Job接口非常容易实现，只有一个execute方法，类似于TimerTask的run方法，在里边编写业务逻辑。  
- Job实例在Quartz中的生命周期  
每次调度器执行job时，它在调用execute方法前会创建一个新的job实例。  
当调用完成后，关联的job对象实例会被释放，释放的实例会被垃圾回收。  

2. 浅谈JobDetail  
JobDetail为Job实例提供了许多设置属性，以及JobDataMap成员变量属性，它用来存储特定的job实例的状态信息，调度器需要借助JobDetail对象来添加Job
实例。

jobDetail中的几个重要的属性：  
- name:任务的名称，在JobDetail中是必须的
- group:任务所在的组，在JobDetail中是必须的，默认是DEFAULT  
- jobClass:任务的实现类，在JobDetail中是必须的  
- jobDataMap:用来实现在传参数  

3. 打印Job的相关属性

```java
package learn.caojx;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloScheduler {

    public static void main(String[] args) throws SchedulerException {
        //1.创建一个JobDetail实例，将该类与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob", "group1").build();

        System.out.println("jobDetail's name:"+jobDetail.getKey().getName());
        System.out.println("jobDetail's group:"+jobDetail.getKey().getGroup());
        System.out.println("jobDetail's jobClass:"+jobDetail.getJobClass().getName());

        //2.创建一个Trigger实例，定义该Job立即执行，并且每个两秒重复执行一次,注意trigger中的group1与job中的group1不是同一个组，因为她们不是同一种对象
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
        //3.创建Scheduler实例
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        //4.将jobDetail与trigger绑定
        scheduler.scheduleJob(jobDetail,trigger);

    }
}
```

结果：  
```text
jobDetail's name:myJob
jobDetail's group:group1
jobDetail's jobClass:learn.caojx.HelloJob
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
Current Exec Time is:2018-01-07 20:51:03
hello job！
```

### 3.2 浅谈JobExecutionContext

1. JobExecutionContext是什么    
- 当Scheduler调用一个Job，就会将JobExecutionContext传递给Job的execute()方法。  
- job能国通JobExecutionContext对象访问到Quartz运行时的环境以及Job本身的明细数据。    

2. JobDataMap是什么  
- 在进行任务调度时JobDataMap存储在JobExecutionContext中，非常方便获取。
- JobDataMap可以用来装载任何可以序列化的数据对象，当job实例对象被执行时候这些参数对象会传递给它。
- JobDataMap实现了JDK的Map接口，并且添加了一些非常方便的方法来存取基本数据类型。

3. JobDataMap的两种方式    
- 从Map中直接获取  
- Job实现类中添加setter方法对应JobDataMap的键值获取（Quartz框架默认的JobFactory实现类在初始化job实例对象时会自动调用这些setter方法）

4. 从Map中直接获取  

从jobExecutionContext中获取JobDetai和Trigger中传入的参数

> HelloScheduler2.java

在JobDetail和Trigger中传入参数
```java
package learn.caojx;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloScheduler2 {

    public static void main(String[] args) throws SchedulerException {
        //1.创建一个JobDetail实例,并添加一些附加参数
        JobDetail jobDetail = JobBuilder.newJob(HelloJob2.class).withIdentity("myJob", "group1")
                .usingJobData("message","myJob1")
                .usingJobData("FloatJobValue",3.14F)
                .build();

        //2.创建一个Trigger实例,并添加一些附加参数
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "group1")
                .usingJobData("message","hello myTrigger1")
                .usingJobData("DoubleTriggerValue", 2.0D)
                .startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();

        //3.创建Scheduler实例
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        //4.将jobDetail与trigger绑定
        scheduler.scheduleJob(jobDetail,trigger);

    }
}
```

> HelloJob2.java

从jobExecutionContext中获取JobDetail和Trigger中传入的参数  

```java
package learn.caojx;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定义一个HelloJob类实现Job接口,从Map中获取传入的参数
 */
public class HelloJob2 implements Job{

    /**
     * execute里边用于编写具体的业务逻辑，与TimerTask里边的run相似
     */
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //打印当前执行的时间,并且获取jobExecutionContext的参数
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Exec Time is:"+simpleDateFormat.format(date));

        //获取JobDetail的信息
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        System.out.println("My Job name and group are:"+jobKey.getName()+":"+jobKey.getGroup());

        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String jobMessage = jobDataMap.getString("message");
        Float floatJobValue = jobDataMap.getFloat("FloatJobValue");

        System.out.println("JobMessage is :"+jobMessage);
        System.out.println("JobFloatValue is :"+floatJobValue);

        //获取Trigger的信息
        TriggerKey triggerKey = jobExecutionContext.getTrigger().getKey();
        System.out.println("My Trigger name and group are:"+triggerKey.getName()+":"+triggerKey.getGroup());


        JobDataMap triggerDataMap = jobExecutionContext.getTrigger().getJobDataMap();
        String triggerMessage = triggerDataMap.getString("message");
        Double triggerDoubleValue = triggerDataMap.getDouble("DoubleTriggerValue");

        System.out.println("TriggerMessage is :"+triggerMessage);
        System.out.println("TriggerDoubleValue is :"+triggerDoubleValue);
        
        //同时获取JobDetail和Trigger中的信息getMergedJobDataMap方式获取传入参数，如果存在相同的key则前边传入key会覆盖后边的key
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        String msg = dataMap.getString("message");
        Float jobFloatValue = dataMap.getFloat("FloatJobValue");
        Double tDoubleValue = dataMap.getDouble("DoubleTriggerValue");
        System.out.println("jobFloatValue is :"+jobFloatValue);
        System.out.println("msg is :"+jobFloatValue);
        System.out.println("triggerDoubleValue is :"+tDoubleValue);
    }
}
```
结果：  
```text
Current Exec Time is:2018-01-07 21:22:44
My Job name and group are:myJob:group1
JobMessage is :myJob1
JobFloatValue is :3.14
My Trigger name and group are:myTrigger:group1
TriggerMessage is :hello myTrigger1
TriggerDoubleValue is :2.0
jobFloatValue is :3.14
msg is :3.14
triggerDoubleValue is :2.0
```

5. Job实现类中添加setter方法对应JobDataMap的键值获取

```text
package learn.caojx;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定义一个HelloJob类实现Job接口,通过在Job中定义JobDataMap中对应的key属性并且提供get和setter方法就可以注入对应的参数值
 */
public class HelloJob3 implements Job{

    private String message;
    private Float floatJobValue;
    private Double doubleTriggerValue;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Float getFloatJobValue() {
        return floatJobValue;
    }

    public void setFloatJobValue(Float floatJobValue) {
        this.floatJobValue = floatJobValue;
    }

    public Double getDoubleTriggerValue() {
        return doubleTriggerValue;
    }

    public void setDoubleTriggerValue(Double doubleTriggerValue) {
        this.doubleTriggerValue = doubleTriggerValue;
    }

    /**
     * execute里边用于编写具体的业务逻辑，与TimerTask里边的run相似
     */
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //打印当前执行的时间,并且获取jobExecutionContext的参数
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Exec Time is:"+simpleDateFormat.format(date));

        //获取JobDetail的信息
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        System.out.println("My Job name and group are:"+jobKey.getName()+":"+jobKey.getGroup());

        //获取Trigger的信息
        TriggerKey triggerKey = jobExecutionContext.getTrigger().getKey();
        System.out.println("My Trigger name and group are:"+triggerKey.getName()+":"+triggerKey.getGroup());

        //提供和JobDataMap中对应key值的getter和setter方法后就可以获取到对应参数的值
        System.out.println("jobFloatValue is :"+floatJobValue);
        System.out.println("msg is :"+message);
        System.out.println("triggerDoubleValue is :"+doubleTriggerValue);
    }
}
```

结果：  
```text
Current Exec Time is:2018-01-07 21:50:15
My Job name and group are:myJob:group1
My Trigger name and group are:myTrigger:group1
jobFloatValue is :3.14
msg is :hello myTrigger1
triggerDoubleValue is :2.0
```

### 3.3 浅谈Trigger

1. 什么是Trigger
Quartz中的触发器用来告诉调度程序中也什么时候触发，即Trigger对象是用来触发执行Job的。  

2. Quartz框架中的Trigger    
![](../images/timedTask/quartz/quartz-trigger.png)

Trigger主要是使用TriggerBuilder来创建的，这里我们主要了解的是CronTriggerImpl和SimpleTriggerImpl

3. 触发器通用属性   
- JobKey  
表示Job实例的标识，触发器被触发时，该指定的job实例会执行。  
- StartTime  
表示触发器的时间表首次被触发的时间，它的值得类型是Java.util.Date。    
- EndTime  
指定触发器的不在被触发的时间，它的值得类型是java.util.Date。

4. 定义Trigger的开始时间和结束时间的,来执行Job

TriggerTest1.java  
```java
package learn.caojx;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory; 
import sun.nio.cs.ext.SimpleEUCEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定义Trigger执行的开始时间和结束时间
 */
public class TriggerTest1 {

    public static void main(String[] args) throws SchedulerException {
        //打印当前执行的时间,并且获取jobExecutionContext的参数
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Exec Time is:"+simpleDateFormat.format(date));

        //1.创建一个JobDetail实例
        JobDetail jobDetail = JobBuilder.newJob(TriggerTestJob1.class).withIdentity("myJob").build();

        //获取距离但钱时间3秒后的时间
        date.setTime(date.getTime()+3000);
        //获取距离但钱时间6秒后的时间
        Date endDate = new Date();
        endDate.setTime(endDate.getTime()+6000);

        //2.创建一个Trigger实例
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger","group1")
                .startAt(date)
                .endAt(endDate)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();

        //3.创建Scheduler实例
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        //4.将jobDetail与trigger绑定
        scheduler.scheduleJob(jobDetail,trigger);

    }
}
```  

TriggerTestJob1.java
```java
package learn.caojx;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定义一个HelloJob类实现Job接口,通过在Job中定义JobDataMap中对应的key属性并且提供get和setter方法就可以注入对应的参数值
 */
public class TriggerTestJob1 implements Job{

    private String message;
    private Float floatJobValue;
    private Double doubleTriggerValue;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Float getFloatJobValue() {
        return floatJobValue;
    }

    public void setFloatJobValue(Float floatJobValue) {
        this.floatJobValue = floatJobValue;
    }

    public Double getDoubleTriggerValue() {
        return doubleTriggerValue;
    }

    public void setDoubleTriggerValue(Double doubleTriggerValue) {
        this.doubleTriggerValue = doubleTriggerValue;
    }

    /**
     * execute里边用于编写具体的业务逻辑，与TimerTask里边的run相似
     */
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //打印当前执行的时间,并且获取jobExecutionContext的参数
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Exec Time is:"+simpleDateFormat.format(date));

       Trigger currentTrigger = jobExecutionContext.getTrigger();
        System.out.println("start Time is:"+simpleDateFormat.format(currentTrigger.getStartTime()));
        System.out.println("end Time is:"+simpleDateFormat.format(currentTrigger.getEndTime()));

        JobKey jobKey = currentTrigger.getJobKey();
        System.out.println("Jobkey Info---jobName"+jobKey.getName()+"--jobGroup:"+jobKey.getGroup());
    }
}
```

结果：
```text
Current Exec Time is:2018-01-07 22:17:34
start Time is:2018-01-07 22:17:34
end Time is:2018-01-07 22:17:38
Jobkey Info---jobNamemyJob--jobGroup:DEFAULT
Current Exec Time is:2018-01-07 22:17:36
start Time is:2018-01-07 22:17:34
end Time is:2018-01-07 22:17:38
Jobkey Info---jobNamemyJob--jobGroup:DEFAULT
```

### 3.4 SimpleTrigger

1. SimpleTrigger的作用
在一个指定的时间段内执行一次作业任务，或是在指定的时间间隔内多次执行作业任务  

2. 案例  
```java
package learn.caojx;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SimpleTrigger的作用
 * 在一个指定的时间段内执行一次作业任务，或是在指定的时间间隔内多次执行作业任务 
 */
public class SimpleTriggerTest1 {

    public static void main(String[] args) throws SchedulerException {
        //打印当前执行的时间,并且获取jobExecutionContext的参数
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Exec Time is:"+simpleDateFormat.format(date));

        //1.创建一个JobDetail实例
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob").build();

        //获取距离当前时间的4秒中后的时间
        date.setTime(date.getTime()+4000);

        //2.创建一个Trigger实例
        // 距离当前4s钟后执行且仅执行一次任务
      /*  SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("myTrigger","group1")
                .startAt(date)
                .build();*/

        // 距离当前4s钟后首次执行且仅执行任务，之后每隔2s中重复执行一次任务,从复执行3次
        /*注意：
        1.重复次数可以为0，正整数或SimpleTrigger.REPEAT_INDEFINITELY常量值
        2. 重复的执行间隔必须为0或长整数
        3. 一旦被指定了endTime参数，那么他会覆盖重复次数的参数（withRepeatCount）效果
        */
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("myTrigger","group1")
                .startAt(date)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2)
                .withRepeatCount(3))
                .build();
        
        //3.创建Scheduler实例
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        //4.将jobDetail与trigger绑定
        scheduler.scheduleJob(jobDetail,trigger);

    }
}
```