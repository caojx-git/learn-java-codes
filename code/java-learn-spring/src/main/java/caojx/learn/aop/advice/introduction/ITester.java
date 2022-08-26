package caojx.learn.aop.advice.introduction;

/**
 * 使用Delegatingintroductioninterceptor为 Developer 添加新的状态或者行为，我们可以按
 * 照如下步骤进行．
 * (I ) 为新的状态和行为定义接口。我们要为Developer 添加测试人员的职能，首先需要将需要的职能以接口定义的形式声明。这样，就有了ITester声明，如下："
 *
 * @author caojx
 * @version $Id: ITester.java,v 1.0 2019-02-20 20:19 caojx
 * @date 2019-02-20 20:19
 */
public interface ITester {

    boolean isBusyAsTester();

    void testSoftware();
}