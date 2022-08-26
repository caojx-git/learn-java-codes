package caojx.learn.ioc.beanfactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * BeanFactory的对象注册与依赖绑定方式-SpringIOC-注解方式
 * <p>
 * 在Spring 2.5发布之前，Spring框架并没有正式支持基于注解方式的依赖注入；  Spring 2.5发布的基于注解的依赖注入方式，
 * 如果不使用classpath-scanning功能的话，仍然部分 依赖于“基于XML配置文件”的依赖注入方式。另外，注解是Java 5之后才引入的，
 * 所以，以下内容只适用于应用程序使用了Spring 2.5以及Java 5 或者更高版本的情况之下。
 */
public class AnnotationTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc/beanfacotry/xmlbeanDefinitionReader-annotation.xml");
        FXNewsProviderAnnotaion newsProvider = (FXNewsProviderAnnotaion) applicationContext.getBean("FXNewsProviderAnnotaion");
        System.out.println(newsProvider.getNewsListener());
        System.out.println(newsProvider.getNewPersistener());
    }

}
