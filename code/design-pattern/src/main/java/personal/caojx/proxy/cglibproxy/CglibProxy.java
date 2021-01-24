package personal.caojx.proxy.cglibproxy;

import net.sf.cglib.core.DebuggingClassWriter;
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

    public Object getProxy(Class clazz){
        // //在指定目录下生成动态代理类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, System.getProperty("user.dir"));
        // 创建Enhancer对象，类似于JDK动态代理的Proxy类，下一步就是设置几个参数
        Enhancer enhancer = new Enhancer();
        // 设置目标类的字节码文件
        enhancer.setSuperclass(clazz);
        // 设置回调函数
        enhancer.setCallback(this);
        // 正式创建代理类
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
