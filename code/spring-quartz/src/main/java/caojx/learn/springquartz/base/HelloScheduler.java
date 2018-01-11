package caojx.learn.springquartz.base;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloScheduler {

    public static void main(String[] args) throws SchedulerException {
        //1.创建一个JobDetail实例，将该类与HelloJob Class绑定
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob", "group1").build();

        System.out.println("myJob");
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
