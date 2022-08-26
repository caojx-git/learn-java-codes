package caojx.learn.aop.advice.introduction;

/**
 * Delegatingintroductioninterceptor 从名字也可以看的出来，Delegatingintroductioninterceptor不会自已实现将要添加到目标对象上的新的逻辑行为，
 * 而是委派 ( delegate)  给其他实现类。不过这样也好，职责划分可以更加明确嘛！
 * <p>
 * <p>
 * "就以简化的开发人员为例来说明Delegatingintroductioninterceptor的用法
 * 我们声明IDeveloper 接口及其相关类，如代码清单9-12所示．
 *
 * @author caojx
 * @version $Id: IDeveloper.java,v 1.0 2019-02-20 20:15 caojx
 * @date 2019-02-20 20:15
 */
public interface IDeveloper {

    public void developSoftware();
}