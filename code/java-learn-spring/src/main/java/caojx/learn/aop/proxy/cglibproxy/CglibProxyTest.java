package caojx.learn.aop.proxy.cglibproxy;

/**
 * @ClassName: CglibProxyTest
 * @Description: CglibProxy代理测试类
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-9 下午7:35
 */
public class CglibProxyTest {

    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        //获取子代理类
        Train train = (Train) proxy.getProxy(Train.class);
        train.move();
    }
}
