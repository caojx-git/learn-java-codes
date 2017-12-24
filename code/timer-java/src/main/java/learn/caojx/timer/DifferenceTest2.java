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
public class DifferenceTest2 {

    public static void main(String[] args) {
        //规定时间格式
        final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取当前的具体时间
        Calendar calendar = Calendar.getInstance();
        System.out.println("Current time is :" + sf.format(calendar.getTime()));
        Timer timer = new Timer();
        /**
         * Task执行时间超过，周期时间，下一次执行时间会接着上一次Task执行完成后执行
         */
 /*       timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //打印当前的计划执行时间
                System.out.println("Schedule exec time is :"+sf.format(scheduledExecutionTime()));
                System.out.println("Task is beging executed!");
            }
        }, calendar.getTime(), 2000L);*/
        /**
         *对于scheduleAtFixedRate来说，为了赶上这6s的时间，会连续执行3次task
         */
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                System.out.println("star Schedule exec time is :"+sf.format(calendar.getTime()));
                try {
                    Thread.sleep(8000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //打印当前的计划执行时间
                System.out.println("end Schedule exec time is :"+sf.format(calendar.getTime()));
                System.out.println("Schedule exec time is :"+sf.format(scheduledExecutionTime()));
                System.out.println("Task is beging executed!");
            }
        }, calendar.getTime(), 2000L);
    }
}
