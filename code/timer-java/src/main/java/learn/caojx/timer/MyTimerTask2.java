package learn.caojx.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

/**
 * @description:
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/12/3 下午9:02
 */
public class MyTimerTask2 extends TimerTask {

    private String name; //当前task的名称
    private Long costTime; //当前task需要花费的时间

    public MyTimerTask2(String inputName, Long inputCostTime) {
        this.name = inputName;
        this.costTime = inputCostTime;
    }

    /**
     * The action to be performed by this timer task.
     */
    public void run() {
        //以yyyy-MM-dd HH:mm:ss的格式打印当前执行时间
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(this.name + "'s current exec time is: " + simpleDateFormat.format(calendar.getTime()));
        try {
            Thread.sleep(costTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //获取costTime之后的时间
        calendar = Calendar.getInstance();
        System.out.println(this.name + "'s finish time is: " + simpleDateFormat.format(calendar.getTime()));
        throw new RuntimeException();
    }
}
