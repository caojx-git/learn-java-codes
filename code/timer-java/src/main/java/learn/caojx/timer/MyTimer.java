package learn.caojx.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

/**
 * @description:
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/12/2 下午10:16
 */
public class MyTimer {

    public static void main(String[] args) {
        //1.创建一个Timer实例
        Timer timer = new Timer();
        //2.创建一个MyTimerTask实例
        MyTimerTask myTimerTask = new MyTimerTask("No.1");
        //3.通过Timer定时定频率的调用MyTimerTask业务逻辑
        //实现第一次执行是在当前时间2秒钟之后，之后每个1s执行一次
        //timer.schedule(myTimerTask, 2000L, 1000L);

        //------schedule用法1---------

        /**
         * 获取当前时间，并设置成距离当前时间三秒之后的时间
         * 如当前是2017-12-03 16:10:16
         * 则设置后的时间则为2017-12-03 16:10:19
         */
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Current exec time is: " + simpleDateFormat.format(calendar.getTime()));
        calendar.add(Calendar.SECOND, 3);
        /**
         * 1.在时间等于或超过time的时候执行且仅执行一次task
         * 如在2017-12-03 16:10:19执行一次task：打印任务名字
         */
        //myTimerTask.setName("schedule1");
        //timer.schedule(myTimerTask, calendar.getTime());

        //-------schedule用法2------
        /**
         * 2.时间等于或超过time首次执行task之后，每隔period毫秒重复执行一次task
         */
        //myTimerTask.setName("schedule2");
        //timer.schedule(myTimerTask, calendar.getTime(), 2000L);

        //-----schedule用法3--------
        /**
         * 3.等待delay毫秒后执行且仅执行一次task
         */
        //myTimerTask.setName("schedule3");
        //timer.schedule(myTimerTask,2000L);

        //-----schedule用法3-------
        /**
         * 等待delay毫秒后首次执行task，之后每隔period毫秒重复执行task
         */
        //myTimerTask.setName("schedule4");
        //timer.schedule(myTimerTask,3000L, 2000L);


        //-----scheduleAtFixedRate用法1-----
        /**
         * 时间等于或超过time时首次执行task，之后每隔period毫秒重复执行一次task
         */
        //myTimerTask.setName("scheduleAtFixedRate1");
        //timer.scheduleAtFixedRate(myTimerTask,calendar.getTime(),2000L);

        //-----scheduleAtFixedRate用法2----
        /**
         * 等待delay毫秒后首次执行task，之后每隔period毫秒重复执行一次task
         */
        //myTimerTask.setName("scheduleAtFixedRate2");
        //timer.scheduleAtFixedRate(myTimerTask,2000L,2000L);

        //----TimerTask的cancel()-----
        /**
         * 作用：取消当前的TimerTask里边的任务。
         */
        //myTimerTask.setName("schedule");
        //timer.schedule(myTimerTask, 3000L, 2000L);

        //---TimerTask的scheduledExecutionTime-----
        /**
         * 作用：返回此任务最近实际执行的已安排执行的时间，返回值为long型。
         */
        myTimerTask.setName("schedule");
        timer.schedule(myTimerTask,3000L);
        System.out.println("schedule time is " + simpleDateFormat.format(myTimerTask.scheduledExecutionTime()));
    }
}
