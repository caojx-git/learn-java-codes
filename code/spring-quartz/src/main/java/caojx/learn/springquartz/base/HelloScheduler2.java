package caojx.learn.springquartz.base;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloScheduler2 {

    public static void main(String[] args) throws SchedulerException {
        //1.创建一个JobDetail实例,并添加一些附加参数
        JobDetail jobDetail = JobBuilder.newJob(HelloJob3.class).withIdentity("myJob", "group1")
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
