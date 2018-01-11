package caojx.learn.springquartz.base;

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
