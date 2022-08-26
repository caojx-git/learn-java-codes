package caojx.learn.ioc.methodinjectionreplacement;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * 方法替换测试
 */
public class MethodReplacerTest {

    public static void main(String[] args) {
        BeanFactory container = new XmlBeanFactory(new ClassPathResource("ioc/methodinjectionreplacement/factorybean-method-replacer.xml"));
        MockNewsPersister persister = (MockNewsPersister) container.getBean("mockPersister");

        /**
         * 输出：
         * before executing method[getNewsBean] on Object[caojx.learn.ioc.methodinjectionreplacement.MockNewsPersister$$EnhancerBySpringCGLIB$$6b5f9be].
         * sorry,We will do nothing this time.
         * end of executing method[getNewsBean] on Object[caojx.learn.ioc.methodinjectionreplacement.MockNewsPersister$$EnhancerBySpringCGLIB$$6b5f9be].
         *
         * 我们把MockNewsPersister的getNewsBean方法逻辑给完全替换掉了。现在该方法基本上什 么也没做，哇……
         *
         * 最后需要强调的是，这种方式刚引入的时候执行效率不是很高。而且，当你充分了解并应用Spring AOP之后，我想你也不会再回头求助这个特色功能。不过，怎么说这也是一个选择，场景合适的话， 为何不用呢？
         *
         * 如果要替换的方法存在参数，或者对象存在多个重载的方法，可以在<replaced-method>内 部通过<arg-type>明确指定将要替换的方法参数类型。祝“替换”愉快！
         */
        persister.getNewsBean();
    }
}
