package personal.caojx.proxy.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 *
 * @ClassName: CglibProxy
 * @Description: Cglib动态代理
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-9 下午7:30
 */
public class CglibProxy implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz){
        //设置创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    /**
     * 拦截所有目标类方法的调用
     * @param obj 目标类的实例
     * @param method 目标方法的反射对象
     * @param args 方法参数
     * @param methodProxy 代理类实例
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("运行方法开始");
        //代理类调用父类的方法
        methodProxy.invokeSuper(obj,args);
        System.out.println("运行方法结束");
        return null;
    }
}
