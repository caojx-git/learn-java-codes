package caojx.learn.aop.pointcut;

import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * 2. 自定义DynamicMethodMatcherPointcut-代码消单9-5给出了一个自定义的 PKeySpecificQueryMethodPointcut 实现类。
 * <p>
 * <p>
 * DynamicMethodMatcherPointcut 也为其子类提供了部分便利。               ·
 * (l) getClassFilter()方法返回ClassFilter.TRUE,  如果需要对特定的目标对象类型进行限定，子类只要覆写这个方法即可。
 * (2) 对应的 MethodMatcher  的isRuntime总是返回true, 同时StaticMethodMatcherPointcut
 * 提供了两个参数的 matches 方法的实现， 默认直接返回true 。
 * 要实现自定义 DynamicMethodMatcherPointcut,   通常情况下，我们只箭要实现三个参数的
 * matches方法逻辑即可。代码消单9-5给出了一个自定义的 PKeySpecificQueryMethodPointcut 实现类。
 * <p>
 * <p>
 * 如果愿意，我们也可以覆写一下两个参数的matches方法，这样，不用每次都得到三个参数的
 * matches 方法执行的时 候才检查所有的条件。
 *
 * @author caojx
 * @version $Id: PKeySpecificQueryMethodPointcut.java,v 1.0 2019-02-19 11:33 caojx
 * @date 2019-02-19 11:33
 */
public class PKeySpecificQueryMethodPointcut extends DynamicMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> clazz, Object[] args) {
        if (method.getName().startsWith("get") && clazz.getPackage().getName().startsWith("...dao")) {
            if (args != null && args.length > 0) {
                return "12345".equals(args[0].toString());
            }
        }
        return false;
    }
}