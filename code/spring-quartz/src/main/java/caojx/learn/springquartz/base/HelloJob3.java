package caojx.learn.springquartz.base;

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
