package caojx.learn.springquartz.base;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CronTrigger的作用
 * CronTrigger是基于日历的作业调度器，而不是像SimpleTrigger那样精确的指定时间间隔，使用CronTrigger可以实现如每月的5号去执行一下任务，或者
 * 在星期一、星期三、星期五的10点去执行一下任务，所以ConTrigger比SimpleTrigger更常用。
 */
public class CronTriggerTest1 {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        //打印当前执行的时间,并且获取jobExecutionContext的参数
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current Exec Time is:"+simpleDateFormat.format(date));
        //1.创建一个JobDetail实例
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob").build();

        //每秒钟触发一次任务
        //cron表达式中*表示每，?表示没用忽略
        CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger()
                .withIdentity("myTrigger","group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ? *"))
                .build();


        //其他
        //1. 2018年内每天10点15分触发一次
        //0 15 10 ? * * 2018
        //2. 每天的14点整至14点59分55秒，以及18点整至18点59分55秒，每5秒触发一次啊
        //0/5 * 14,18 * * ?

        //3.创建Scheduler实例
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        //4.将jobDetail与trigger绑定
        Date date1 =  scheduler.scheduleJob(jobDetail,trigger);
        //打印scheduler最近执行的时间
        System.out.println("scheduler start:"+simpleDateFormat.format(date1));
        //scheduler执行2s后挂起
        Thread.sleep(2000L);
        scheduler.standby();

        //scheduler再挂起3s后重新启动
        Thread.sleep(3000L);
        scheduler.start();

        //shutdown(true)表示等待所有正在执行的job执行完毕后，再关闭schduler
        //shutdown(false)即shutdown()表示直接关闭scheduler,默认
        scheduler.shutdown();

        System.out.println("scheduler is shutdown?"+scheduler.isShutdown());

    }
}
