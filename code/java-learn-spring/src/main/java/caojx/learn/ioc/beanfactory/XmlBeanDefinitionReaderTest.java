package caojx.learn.ioc.beanfactory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * BeanFactory的对象注册与依赖绑定方式-SpringIOC-XML配置格式的加载
 * <p>
 * XML配置格式是Spring支持最完整，功能最强大的表达方式。当然，一方面这得益于XML良好的 语意表达能力；另一方面，就是Spring框架从开始就自始至终保持XML配置加载的统一性。
 * <p>
 * 与为Properties配置文件格式提供PropertiesBeanDefinitionReader相对应，Spring同样为XML 格式的 配置 文件提 供了 现成的 BeanDefinitionReader 实现 ， 即 XmlBeanDefinitionReader 。
 * XmlBeanDefinitionReader负责读取Spring指定格式的XML配置文件并解析，之后将解析后的文件内 容映射到相应的BeanDefinition，并加载到相应的BeanDefinitionRegistry中（在这里是DefaultListableBeanFactory）。
 * 这时，整个BeanFactory就可以放给客户端使用了。
 * <p>
 * 除了提供 XmlBeanDefinitionReader 用于XML格式配置文件的加载， Spring还在 DefaultListableBeanFactory的基础上构建了简化XML格式配置加载的XmlBeanFactory实现。
 * 从以上代码 最后注释掉的一行，你可以看到使用了XmlBeanFactory之后，完成XML的加载和BeanFactory的初 始化是多么简单
 */
public class XmlBeanDefinitionReaderTest {

    public static void main(String[] args) {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        BeanFactory container = bindViaXMLFile(defaultListableBeanFactory);
        FXNewsProvider newsProvider = (FXNewsProvider) container.getBean("djNewsProvider");
        System.out.println(newsProvider.getNewsListener());
        System.out.println(newsProvider.getNewPersistener());

    }

    public static BeanFactory bindViaXMLFile(BeanDefinitionRegistry registry) {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(registry);
        xmlBeanDefinitionReader.loadBeanDefinitions("ioc/beanfacotry/xmlbeanDefinitionReader.xml");
        return (BeanFactory) registry;

        // 或者直接
        //return new XmlBeanFactory(new ClassPathResource("xmlbeanDefinitionReader.xml"));
    }
}
