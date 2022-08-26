package caojx.learn.ioc.beanpostprocessor;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * (3) 将自定义的BeanPostProcessor注册到容器 只有将自定义的BeanPostProcessor实现类告知容器，容器才会在合适的时机应用它。
 * 所以，我 们需要将PasswordDecodePostProcessor注册到容器。 对于BeanFactory类型的容器来说，我们需要通过手工编码的方式将相应的BeanPostProcessor 注册到容器，也就是调用ConfigurableBeanFactory的addBeanPostProcessor()方法，见如下代码
 * <p>
 * 对于ApplicationContext容器来说，事情则方便得多，直接将相应的BeanPostProcessor实现 类通过通常的XML配置文件配置一下即可。ApplicationContext容器会自动识别并加载注册到容器 的BeanPostProcessor，如下配置内容将我们的PasswordDecodePostProcessor注册到容器：
 * 见xml配置
 * <p>
 * 合理利用BeanPostProcessor这种Spring的容器扩展机制，将可以构造强大而灵活的应用系统。
 *
 * @author caojx
 * @version $Id: Test.java,v 1.0 2019-02-12 15:44 caojx
 * @date 2019-02-12 15:44
 */
public class Test {

    public static void main(String[] args) {
        ConfigurableBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource(""));
        beanFactory.addBeanPostProcessor(new PasswordDecodePostProcessor());
        //...
        // getBean();

    }

}