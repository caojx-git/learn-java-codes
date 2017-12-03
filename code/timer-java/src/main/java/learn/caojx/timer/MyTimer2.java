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
public class MyTimer2 {

    public static void main(String[] args) {
        //1.创建一个Timer实例
        Timer timer = new Timer();
        //2.创建一个MyTimerTask2实例
        MyTimerTask2 myTimerTask1 = new MyTimerTask2("No.1",2000L);
        MyTimerTask2 myTimerTask2 = new MyTimerTask2("No.2",2000L);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("current time is "+ simpleDateFormat.format(calendar.getTime()));
        timer.schedule(myTimerTask1,calendar.getTime());
        timer.schedule(myTimerTask2,calendar.getTime());
    }
}
