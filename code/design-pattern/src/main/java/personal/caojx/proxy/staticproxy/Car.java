package personal.caojx.proxy.staticproxy;

import java.util.Random;

/**
 *
 * @ClassName: Car
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-8 下午11:20
 */
public class Car implements Moveable {

    @Override
    public void move() {
        //实现开车
        try {
            Thread.sleep(new Random().nextInt(1000));
            System.out.println("汽车行驶中");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
