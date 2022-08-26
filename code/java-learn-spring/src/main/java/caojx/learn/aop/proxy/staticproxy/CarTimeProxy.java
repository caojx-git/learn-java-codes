package caojx.learn.aop.proxy.staticproxy;

/**
 * @ClassName: CarTimeProxy
 * @Description: 汽车时间记录的代理
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-8 下午11:30
 */
public class CarTimeProxy implements Moveable {

    private Moveable moveable;

    public CarTimeProxy(Moveable moveable) {
        super();
        this.moveable = moveable;
    }

    @Override
    public void move() {
        long starTime = System.currentTimeMillis();
        System.out.println("汽车开始行驶");
        moveable.move();
        long endTime = System.currentTimeMillis();
        System.out.println("汽车结束行驶：" + (endTime - starTime) + "毫秒");
    }
}
