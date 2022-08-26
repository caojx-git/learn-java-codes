package caojx.learn.aop.advice.introduction;

import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * 类注释，描述
 *
 * @author caojx
 * @version $Id: TestDelegatingintroductioninterceptor.java,v 1.0 2019-02-20 20:26 caojx
 * @date 2019-02-20 20:26
 */
public class TestDelegatingintroductioninterceptor {

    public static void main(String[] args) {

       /* (3) 通过 Delegatingintroductioninterceptor 进行Introduction的拦截 。有了新增加职能的接口定义以及相应实现类使用Delegatingintroductioninterceptor,
        我们就可以把具体的Inroduction拦截委托给具体的实现类来完成，如下代码演示了这一过程：

        "(4) Introduction的最终织入过程在细节上有需要注意的地方，我们将在后而提到。虽然，Delegatingintroductioninterceptor是Introduction型Advice的一个实现，
        但你可能料想不到的是，它其实是个“伪军”，因为它的实现根本就没有兑现Introduction 作为per-instance型Advice的承诺。实际上， Delegatingintroductioninterceptor会使用它所持有的同一个"delegate"接口实例，
        供同一目标类的所有实例共享使用。你想啊，就持有一个接口实现类的实例对象，它往哪里去放对应各个目标对象实例的状态啊？所以，如果要真的想严格达到Introduction型Advice所宜称的那样的效果，我们不能
        使用Delegatingintroductioninterceptor,   而是要 使用它的兄弟，DelegatePerTargetObjectIntroductionInterceptor


        "*/
        ITester delegate = new Tester();
        DelegatingIntroductionInterceptor interceptor = new DelegatingIntroductionInterceptor(delegate);

        //进行织入, 伪代码
        //ITester tester = weaver.weave(developer).with(interceptor).getProxy();
        // tester.testSoftware();
    }

}