package caojx.learn.ioc.methodinjectionreplacement;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * 与方法注入只是通过相应方法为主体对象注入依赖对象不同，方法替换更多体现在方法的实现层 面上，它可以灵活替换或者
 * 说以新的方法实现覆盖掉原来某个方法的实现逻辑。基本上可以认为，方 法替换可以帮助我们实现简单的方法拦截功能。
 * 要知道，我们现在可是在不知不觉中迈上了AOP的大 道哦！
 * <p>
 * 假设某天我看FXNewsProvider不爽，想替换掉它的getAndPersistNews方法默认逻辑，这时， 我就可以用方法替换将它的原有逻辑给替换掉。
 * <p>
 * <p>
 * 首先，我们需要给出org.springframework.beans.factory.support.MethodReplacer的实现 类，在这个类中实现将要替换的方法逻辑。
 * 假设我们只是简单记录日志，打印简单信息，那么就可以 给出一个类似代码清单4-39所示的MethodReplacer实现类。
 */
public class FXNewsProviderMethodReplacer implements MethodReplacer {

    /**
     * Reimplement the given method.
     *
     * @param target the instance we're reimplementing the method for
     * @param method the method to reimplement
     * @param args   arguments to the method
     * @return return value for the method
     */
    @Override
    public Object reimplement(Object target, Method method, Object[] args) throws Throwable {
        System.out.println("before executing method[" + method.getName() + "] on Object[" + target.getClass().getName() + "].");

        System.out.println("sorry,We will do nothing this time.");

        System.out.println("end of executing method[" + method.getName() + "] on Object[" + target.getClass().getName() + "].");
        return null;
    }
}
