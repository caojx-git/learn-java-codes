package caojx.learn.ioc.methodinjectionreplacement;

import caojx.learn.ioc.IFXNewsPersister;

/**
 * 我们知道，拥有prototype类型scope的bean，在请求方每次向容器请求该类型对象的时候，容器都 会返回一个全新的该对象实例。
 * 为了简化问题的叙述，我们直接将FX News系统中的FXNewsBean定义 注册到容器中，并将其scope设置为prototype。
 * 因为它是有状态的类型，每条新闻都应该是新的独 立个体；同时， 我们给出 MockNewsPersister类， 使其实现 IFXNewsPersister接口，
 * 以模拟注入 FXNewsBean实例后的情况。
 */
public class MockNewsPersister implements IFXNewsPersister {

    private FXNewsBean newsBean;

    public void persistNews() {
        System.out.println("persist bean:" + getNewsBean());
    }

    public FXNewsBean getNewsBean() {
        return newsBean;
    }

    public void setNewsBean(FXNewsBean newsBean) {
        this.newsBean = newsBean;
    }
}
