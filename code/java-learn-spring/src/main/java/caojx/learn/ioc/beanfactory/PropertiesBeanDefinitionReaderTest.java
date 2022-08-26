package caojx.learn.ioc.beanfactory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;

/**
 * BeanFactory的对象注册与依赖绑定方式-SpringIOC-外部配置文件方式-Properties配置格式的加载
 * <p>
 * BeanDefinitionReader->BeanDefinition->BeanDefinitionRegistry
 * <p>
 * Spring的IoC容器支持两种配置文件格式：Properties文件格式和XML文件格式。当然，如果你愿意也可以引入自己的文件格式，前提是真的需要。
 * 采用外部配置文件时，Spring的IoC容器有一个统一的处理方式。通常情况下，需要根据不同的外部配置文件格式，给出相应的BeanDefinitionReader实现类，
 * 由BeanDefinitionReader的相应实现类负责将相应的配置文件内容读取并映射到BeanDefinition，然后将映射后的BeanDefinition注册到一个BeanDefinitionRegistry，
 * 之后，BeanDefinitionRegistry即完成Bean的注册和加载。当然，大部分工作，包括解析文件格式、装配BeanDefinition之类的工作，都是由BeanDefinitionReader的相应实现类来做的，
 * BeanDefinitionRegistry只不过负责保管而已。
 * <p>
 * Spring提供了 org.springframework.beans.factory.support.PropertiesBeanDefinitionReader类用于Properties格式配置文件的加载，所以，我们不用自己去实现BeanDefinitionReader，
 * 只要根据该类的读取规则，提供相应的配置文件即可。
 */
public class PropertiesBeanDefinitionReaderTest {

    public static void main(String[] args) {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        BeanFactory container = bindViaPropertiesFile(defaultListableBeanFactory);
        FXNewsProvider newsProvider = (FXNewsProvider) container.getBean("djNewsProvider");
        System.out.println(newsProvider.getNewsListener());
        System.out.println(newsProvider.getNewPersistener());
    }

    public static BeanFactory bindViaPropertiesFile(BeanDefinitionRegistry registry) {
        PropertiesBeanDefinitionReader propertiesBeanDefinitionReader = new PropertiesBeanDefinitionReader(registry);
        propertiesBeanDefinitionReader.loadBeanDefinitions("ioc/beanfacotry/propertiesBeanDefinitionReader.properties");
        return (BeanFactory) registry;
    }
}
