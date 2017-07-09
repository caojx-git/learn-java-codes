package personal.caojx.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: TimeHandler
 * @Description: JDK动态代理类
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-9 下午6:48
 */
public class TimeHandler implements InvocationHandler {

    //被代理目标对象
    private Object target;

    public TimeHandler(Object target) {
        this.target = target;
    }

    /**
     *
     * @param proxy 被代理对象
     * @param method 被代理对象的方法
     * @param args 被代理对象的方法参数
     * @return Object 方法的返回值
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long starTime = System.currentTimeMillis();
        System.out.println("汽车开始行驶");
        method.invoke(target,args);
        long endTime = System.currentTimeMillis();
        System.out.println("汽车结束行驶："+(endTime-starTime)+"毫秒");
        return null;
    }
}
