package personal.caojx.proxy.staticproxy;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: CarTimeProxy
 * @Description: 汽车日志记录的代理
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-8 下午11:30
 */
public class CarLogProxy implements Moveable {

    private Moveable moveable;

    public CarLogProxy(Moveable moveable){
        super();
        this.moveable = moveable;
    }

    @Override
    public void move() {
        System.out.println("日志开始");
        moveable.move();
        System.out.println("日志结束");
    }
}
