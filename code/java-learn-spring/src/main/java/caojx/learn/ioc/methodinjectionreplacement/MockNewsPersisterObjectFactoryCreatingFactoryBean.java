package caojx.learn.ioc.methodinjectionreplacement;

import caojx.learn.ioc.IFXNewsPersister;
import org.springframework.beans.factory.ObjectFactory;

/**
 * ObjectFactoryCreatingFactoryBean 是Spring提供的一个 FactoryBean 实现，
 * 它返回一个 ObjectFactory实例。从ObjectFactoryCreatingFactoryBean
 * 返回的这个ObjectFactory实例可以 为 我 们 返 回 容 器 管 理 的 相 关 对 象 。
 * 实 际 上 ， ObjectFactoryCreatingFactoryBean 实 现 了 BeanFactoryAware接口，
 * 它返回的ObjectFactory实例只是特定于与Spring容器进行交互的一个实现 而已。使用它的好处就是，隔离了客户端对象对BeanFactory的直接引用。
 */
public class MockNewsPersisterObjectFactoryCreatingFactoryBean implements IFXNewsPersister {

    private ObjectFactory newsBeanFactory;

    public void persistNews(FXNewsBean bean) {
        persistNews();
    }

    public void persistNews() {
        System.out.println("persist bean:" + getNewsBean());
    }

    public FXNewsBean getNewsBean() {
        return (FXNewsBean) newsBeanFactory.getObject();
    }

    public void setNewsBeanFactory(ObjectFactory newsBeanFactory) {
        this.newsBeanFactory = newsBeanFactory;
    }
}
