package personal.caojx.proxy.staticproxy;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: Client
 * @Description: 代理测试类
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-8 下午11:24
 */
public class StaticProxyTest {

    public static void main(String[] args){
        //普通的方式实现汽车行驶
        //Moveable moveable = new Car();
        //moveable.move();

        //静态代理使用继承方式
        //Moveable moveable1 = new Car2();
        //moveable1.move();

        //使静态用聚合方法 --推荐
        //Car car = new Car();
        //Moveable moveable3 = new CarTimeProxy(car);
        //moveable3.move();

        Car car = new Car();
        CarTimeProxy ctp = new CarTimeProxy(car);
        CarLogProxy clp = new CarLogProxy(ctp);
        clp.move();
    }
}
