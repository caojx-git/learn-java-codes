package personal.caojx.proxy.staticproxy;

import personal.caojx.proxy.staticproxy.Car;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: Car2
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-8 下午11:25
 */
public class Car2 extends Car {

    @Override
    public void move() {
        long starTime = System.currentTimeMillis();
        System.out.println("汽车开始行驶");
        super.move();
        long endTime = System.currentTimeMillis();
        System.out.println("汽车结束行驶："+(endTime-starTime)+"毫秒");
    }
}
