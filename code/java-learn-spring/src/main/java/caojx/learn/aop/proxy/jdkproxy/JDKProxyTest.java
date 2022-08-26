package caojx.learn.aop.proxy.jdkproxy;

import caojx.learn.aop.proxy.staticproxy.Car;
import caojx.learn.aop.proxy.staticproxy.Moveable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @ClassName: JDKProxyTest
 * @Description: JDK动态代理测试类
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-9 下午6:54
 */
public class JDKProxyTest {

    public static void main(String[] args) {
        Car car = new Car();
        InvocationHandler handler = new TimeHandler(car);
        Class<?> cls = car.getClass();
        /**
         * 第一参数：classLoader 类加载器
         * 第二个参数：interfaces 实现类接口
         * 第三个参数：InvocationHandler
         */
        //返回一个代理类实例
        Moveable moveable = (Moveable) Proxy.newProxyInstance(car.getClass().getClassLoader(), cls.getInterfaces(), handler);
        moveable.move();
    }
}
