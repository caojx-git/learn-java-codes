package personal.caojx.proxy.staticproxy;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: CarTimeProxy
 * @Description: 汽车时间记录的代理
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-8 下午11:30
 */
public class Car3 implements Moveable {

    private Car car;

    public Car3(Car car){
        super();
        this.car = car;
    }

    @Override
    public void move() {
        long starTime = System.currentTimeMillis();
        System.out.println("汽车开始行驶");
        car.move();
        long endTime = System.currentTimeMillis();
        System.out.println("汽车结束行驶："+(endTime-starTime)+"毫秒");
    }
}
