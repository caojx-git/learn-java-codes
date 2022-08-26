package caojx.learn.ioc.beanfactory;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * BeanFactory的对象注册与依赖绑定方式-SpringIOC-直接编码方式
 * <p>
 * BeanFactory:
 * BeanFactory作为一个IoC Service Provider，为了能够明确管理各个业务对象以及业务对象之间的 依赖绑定关系，同样需要某种途径来记录和管理这些信息。
 * BeanFactory只是一个接口，我们最终需要一个该接口的实现来进行实际的Bean的管理，DefaultListableBeanFactory 就是这么一个比较通用的 BeanFactory 实现类。
 * DefaultListableBeanFactory除了间接地实现了BeanFactory接口，还实现了BeanDefinitionRegistry接口，该接口才 是在BeanFactory的实现中担当Bean注册管理的角色。
 * <p>
 * <p>
 * 基本上，BeanFactory接口只定义如何访问容 器内管理的Bean的方法， 各个 BeanFactory 的具体实现类负责具体Bean的注册以及管理工作。BeanDefinitionRegistry接口定义抽象了Bean的注册逻辑。
 * 打个比方说，BeanDefinitionRegistry就像图书馆的书架，所有的书是放在书架上的。虽然你还书或者借书都是跟图书馆（也就是BeanFactory，或许BookFactory可能更好些）打交道，但书架才是图书馆存放各类图书的地方。
 * 所以，书架相对于图书馆来说，就是它的“BookDefinitionRegistry”。
 * <p>
 * <p>
 * BeanDefinition:
 * 每一个受管的对象， 在容器中都会有一个 BeanDefinition的实例（instance）与之相对应， 该 BeanDefinition的实例负责保存对象的所有必要信息，包括其对应的对象的class类型、是否是抽象类、构造方法参数以及其他属性等。
 * 当客户端向BeanFactory请求相应对象的时候，BeanFactory会通过这些信息为客户端返回一个完备可用的对象实例 。 RootBeanDefinition 和 ChildBeanDefinition是BeanDefinition的两个主要实现类。
 */
public class DefaultListableBeanFactoryTest {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();
        BeanFactory container = bindViaCode(beanRegistry);
        FXNewsProvider newsProvider = (FXNewsProvider) container.getBean("djNewsProvider");
        System.out.println(newsProvider.getNewsListener());
        System.out.println(newsProvider.getNewPersistener());
    }

    /**
     * BeanFactory的对象注册与依赖绑定方式-SpringIOC-直接编码方式
     *
     * @param registry
     * @return
     */
    public static BeanFactory bindViaCode(BeanDefinitionRegistry registry) {
        AbstractBeanDefinition newsProvider = new RootBeanDefinition(FXNewsProvider.class);
        AbstractBeanDefinition newsListener = new RootBeanDefinition(DowJonesNewsListener.class);
        AbstractBeanDefinition newsPersister = new RootBeanDefinition(DowJonesNewsPersister.class);
        // 将bean定义注册到容器中
        registry.registerBeanDefinition("djNewsProvider", newsProvider);
        registry.registerBeanDefinition("djListener", newsListener);
        registry.registerBeanDefinition("djPersister", newsPersister);

        // 指定依赖关系
        // 1. 可以通过构造方法注入方式
        ConstructorArgumentValues argValues = new ConstructorArgumentValues();
        argValues.addIndexedArgumentValue(0, newsListener);
        argValues.addIndexedArgumentValue(1, newsPersister);
        newsProvider.setConstructorArgumentValues(argValues);

        // 2. 或者通过setter方法注入方式
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("newsListener", newsListener));
        propertyValues.addPropertyValue(new PropertyValue("newPersistener", newsPersister));
        newsProvider.setPropertyValues(propertyValues);
        // 绑定完成
        return (BeanFactory) registry;
    }
}
