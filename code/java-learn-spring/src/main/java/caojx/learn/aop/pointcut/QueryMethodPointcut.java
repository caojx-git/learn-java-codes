package caojx.learn.aop.pointcut;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * 自定义 StaticMethodMatcherPointcut-如提供一个Pointcut实现，捕捉系统里数据访问层的数据访问对象中的查询方法所在的 Joinpoint 那么可以实现一个StaticMethodMatcherPointcut,如代码所示
 * <p>
 * "虽然我认为以上SpringAOP提供的Pointcut已经 足够使用  ，但却无法保证一定就没有更加特殊的需求，以致千以上Pointcut类型都无法满足 要求。这种悄况下，我们可以扩展SpringAOP的Pointcut定义，给出自定义的Pointcut实现。
 * 要自定义Pointcut,  不用白手起家，Spring AOP已经提供了相应的扩展抽象类支持，我们只需要继"
 * "承相应的抽象父类  ，然后实现或者覆写相应方法逻辑即可。前面已经讲过，SpringAOP的Pointcut 类型"
 * "可以划分为 StaticMethodMatcherPointcut 和 DynamicMethodMatcherPointcut 两个类 。要实 现自定
 * 义Pointcut,  通常在这两个抽象类的基础上实现相应子类即可。"
 * <p>
 * <p>
 * "StaticMethodMatcherPointcut 根据自身语意，为其子类提供了如下儿个方面的默认实现。
 * 默认所有 StaticMethodMatcherPointcut 的子类的 ClassFilter 均为ClassFilter.TRUE,
 * 即忽略类的类型匹配。如果具体子类需要对目标对象的类型做进一步限制，可以通过public void  setClassFilter(ClassFilter  classFilter)方法 设置相应的ClassFilter实现．"
 * <p>
 * <p>
 * "因为是StaticMethodMatcherPointcut,   所以，其MethodMatcher 的 isRuntime 方法  返回
 * false,  同时三个参数的 matches 方法抛出 UnsupportedOperationException异常，以表示该方法不应该被调用到。
 * 最终，我们需要做的就是实现两个参数的matches方法了 。"
 *
 * @author caojx
 * @version $Id: QueryMethodPointcut.java,v 1.0 2019-02-19 11:14 caojx
 * @date 2019-02-19 11:14
 */
public class QueryMethodPointcut extends StaticMethodMatcherPointcut {

    /**
     * 捕捉系统里数据访问层的数据访问对象中的查询方法所在的 Joinpoint
     *
     * @param method
     * @param clazz
     * @return
     */
    @Override
    public boolean matches(Method method, Class<?> clazz) {
        return method.getName().startsWith("get") && clazz.getPackage().getName().startsWith("...dao");
    }
}