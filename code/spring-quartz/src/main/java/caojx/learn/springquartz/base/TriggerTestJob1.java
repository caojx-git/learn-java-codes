package caojx.learn.springquartz.base;

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
