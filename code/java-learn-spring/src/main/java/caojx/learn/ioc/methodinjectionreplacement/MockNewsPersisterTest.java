package caojx.learn.ioc.methodinjectionreplacement;

import caojx.learn.ioc.IFXNewsPersister;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * 偷梁换柱之术
 * <p>
 * 我们知道，拥有prototype类型scope的bean，在请求方每次向容器请求该类型对象的时候，容器都 会返回一个全新的该对象实例。
 * 为了简化问题的叙述，我们直接将FX News系统中的FXNewsBean定义 注册到容器中，并将其scope设置为prototype。
 * 因为它是有状态的类型，每条新闻都应该是新的独 立个体；同时， 我们给出 MockNewsPersister类， 使其实现 IFXNewsPersister接口，
 * 以模拟注入 FXNewsBean实例后的情况。
 */
public class MockNewsPersisterTest implements IFXNewsPersister {

    public static void main(String[] args) {
        BeanFactory container = new XmlBeanFactory(new ClassPathResource("ioc/methodinjectionreplacement/factorybean-newsbean.xml"));
        MockNewsPersister persister = (MockNewsPersister) container.getBean("mockPersister");
        persister.persistNews();
        persister.persistNews();

        //输出
//        persist bean:caojx.learn.ioc.methodinjectionreplacement.FXNewsBean@2a18f23c
//        persist bean:caojx.learn.ioc.methodinjectionreplacement.FXNewsBean@2a18f23c


       /* 从输出看，对象实例是相同的，而这与我们的初衷是相悖的。因为每次调用persistNews都会调 用getNewsBean()方法并返回一个FXNewsBean实例，
        而FXNewsBean实例是prototype类型的，因此每 次不是应该输出不同的对象实例嘛？

        好了，问题实际上不是出在FXNewsBean的scope类型是否是prototype的，而是出在实例的取得方 式上面。
        虽然 FXNewsBean 拥有prototype类型的scope， 但当容器将一个 FXNewsBean 的实例注入 MockNewsPersister之后，
        MockNewsPersister就会一直持有这个FXNewsBean实例的引用。虽然每 次输出都调用了 getNewsBean() 方法并返回了 FXNewsBean 的实例，
        但实际上每次返回的都是 MockNewsPersister持有的容器第一次注入的实例。这就是问题之所在。换句话说，第一个实例注入 后，
        MockNewsPersister再也没有重新向容器申请新的实例。所以，容器也不会重新为其注入新的 FXNewsBean类型的实例。

        知道原因之后，我们就可以解决这个问题了。解决问题的关键在于保证getNewsBean()方法每次 从容器中取得新的FXNewsBean实例，而不是每次都返回其持有的单一实例。*/


//        偷梁换柱之术-1. 方法注入
//        通过<lookup-method>的name属性指定需要注入的方法名，bean属性指定需要注入的对象，当 getNewsBean方法被调用的时候，容器可以每次返回一个新的FXNewsBean类型的实例。
        BeanFactory container2 = new XmlBeanFactory(new ClassPathResource("ioc/methodinjectionreplacement/factorybean-method-injection.xml"));
        MockNewsPersister persister2 = (MockNewsPersister) container2.getBean("mockPersister");
        persister2.persistNews();
        persister2.persistNews();

//        persist bean:caojx.learn.ioc.methodinjectionreplacement.FXNewsBean@523884b2
//        persist bean:caojx.learn.ioc.methodinjectionreplacement.FXNewsBean@5b275dab


        //偷梁换柱之术-2. 使用BeanFactoryAware接口

  /*    我们知道，即使没有方法注入，只要在实现getNewsBean()方法的时候，能够保证每次调用BeanFactory的getBean("newsBean")，就同样可以每次都取得新的FXNewsBean对象实例。
        现在，我们唯 一需要的，就是让MockNewsPersister拥有一个BeanFactory的引用。 Spring框架提供了一个BeanFactoryAware接口，容器在实例化实现了该接口的bean定义的过程
        中，会自动将容器本身注入该bean。这样，该bean就持有了它所处的BeanFactory的引用。*/

        BeanFactory container3 = new XmlBeanFactory(new ClassPathResource("ioc/methodinjectionreplacement/factorybean-method-beanFactoryAware.xml"));
        MockNewsPersisterBeanFactoryAware persister3 = (MockNewsPersisterBeanFactoryAware) container3.getBean("mockPersister");
        persister3.persistNews();
        persister3.persistNews();


        //偷梁换柱之术-3.使用ObjectFactoryCreatingFactoryBean
        /*
         * ObjectFactoryCreatingFactoryBean 是Spring提供的一个 FactoryBean 实现， 它返回一个 ObjectFactory实例。从ObjectFactoryCreatingFactoryBean返回的这个ObjectFactory实例
         * 可以 为 我 们 返 回 容 器 管 理 的 相 关 对 象 。 实 际 上 ， ObjectFactoryCreatingFactoryBean 实 现 了 BeanFactoryAware接口，它返回的ObjectFactory实例只是特定于与
         * Spring容器进行交互的一个实现 而已。使用它的好处就是，隔离了客户端对象对BeanFactory的直接引用。
         */
        BeanFactory container4 = new XmlBeanFactory(new ClassPathResource("ioc/methodinjectionreplacement/factorybean-method-objectFactorycreatingfactoryBean.xml"));
        MockNewsPersisterObjectFactoryCreatingFactoryBean persister4 = (MockNewsPersisterObjectFactoryCreatingFactoryBean) container4.getBean("mockPersister");
        persister4.persistNews();
        persister4.persistNews();


    }
}
