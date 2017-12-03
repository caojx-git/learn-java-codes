package learn.caojx.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/**
 * @description:
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/12/3 下午5:28
 */
public class CancelTest {

    public static void main(String[] args) throws InterruptedException {
        //创建Timer实例
        Timer timer = new Timer();
        //创建TimerTask实例
        MyTimerTask task1 = new MyTimerTask("task1");
        MyTimerTask task2 = new MyTimerTask("task2");
        //获取当前的执行时间并打印
        Date startTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("start time is: "+simpleDateFormat.format(startTime.getTime()));
        //task1首次执行是距离现在时间的3s之后执行，之后每隔2s执行一次
        //task2首次执行是距离现在时间的1s之后执行，之后每隔2s执行一次
        timer.schedule(task1, 3000L, 2000L);
        timer.schedule(task2, 1000L, 2000L);

        //休眠5s
        Thread.sleep(5000L);

        //获取当前的执行时间并打印
        Date cancelTime = new Date();
        System.out.println("cancel time is: "+ simpleDateFormat.format(cancelTime.getTime()));
        //取消所有任务
        timer.cancel();
        System.out.println("Tasks all canceled!");
    }
}
