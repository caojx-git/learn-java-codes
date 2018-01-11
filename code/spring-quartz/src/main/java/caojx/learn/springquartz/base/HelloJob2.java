package caojx.learn.springquartz.base;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定义一个HelloJob类实现Job接口
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

        //getMergedJobDataMap方式获取传入参数，如果存在相同的key则前边传入key会覆盖后边的key
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        String msg = dataMap.getString("message");
        Float jobFloatValue = dataMap.getFloat("FloatJobValue");
        Double tDoubleValue = dataMap.getDouble("DoubleTriggerValue");
        System.out.println("jobFloatValue is :"+jobFloatValue);
        System.out.println("msg is :"+jobFloatValue);
        System.out.println("triggerDoubleValue is :"+tDoubleValue);
    }
}
