package learn.caojx.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @description:
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/12/3 下午6:01
 */
public class DifferenceTest {

    public static void main(String[] args) {
        //规定时间格式
        final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取当前的具体时间
        Calendar calendar = Calendar.getInstance();
        System.out.println("Current time is :" + sf.format(calendar.getTime()));

        //设置成6s前的时间
        calendar.add(Calendar.SECOND, -6);
        Timer timer = new Timer();
        //第一次执行的时间为6s前，之后每隔2s执行一次
        /**
         * 尽管我们第一次执行的时间提前了6s，但是第一次执行时间还是以当前时间为准
         */
      /*  timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //打印当前的计划执行时间
                System.out.println("Schedule exec time is :"+sf.format(scheduledExecutionTime()));
                System.out.println("Task is beging executed!");
            }
        }, calendar.getTime(), 2000L);
*/
        /**
         *对于scheduleAtFixedRate来说，为了赶上这6s的时间，会连续执行3次task
         */
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //打印当前的计划执行时间
                System.out.println("Schedule exec time is :"+sf.format(scheduledExecutionTime()));
                System.out.println("Task is beging executed!");
            }
        }, calendar.getTime(), 2000L);
    }
}
