package caojx.learn.aop.proxy.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 使用动态字节码生成技术扩展对象行为的原理是，我们可以对目标对象进行继承扩展，为其生成
 * 相应的子类，而子类可以通过覆写来扩展父类的行为，只要将横切逻辑的实现放到子类中，
 * 然后让系统使用扩展后的目标对象的子类，就可以达到与代理模式相同的效果了。SubClass instanceof SuperClass == true 不是吗？
 * <p>
 * 使用CGLIB对类进行扩展的唯一限制就是无法对final方法进行覆写。
 *
 * @ClassName: CglibProxy
 * @Description: Cglib动态代理
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-9 下午7:30
 */
public class CglibProxy implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz) {
        //设置创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    /**
     * 拦截所有目标类方法的调用
     *
     * @param obj         目标类的实例
     * @param method      目标方法的反射对象
     * @param args        方法参数
     * @param methodProxy 代理类实例
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("运行方法开始");
        //代理类调用父类的方法
        methodProxy.invokeSuper(obj, args);
        System.out.println("运行方法结束");
        return null;
    }
}
