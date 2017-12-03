package learn.caojx.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * @description:
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/12/2 下午10:08
 */
public class MyTimerTask extends TimerTask {

    private String name;
    private Integer count = 0;

    public MyTimerTask(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The action to be performed by this timer task.
     */
    public void run() {
        if (count < 3) {
            //打印当前name的内容
            System.out.println("current exec name is: " + name);

            //以yyyy-MM-dd HH:mm:ss的格式打印当前执行时间
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("Current exec time is: " + simpleDateFormat.format(calendar.getTime()));
            count++;
        } else {
            this.cancel();
            System.out.println("Task cancel()!");
        }
    }
}
