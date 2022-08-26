package caojx.learn.ioc.beanfactorypostprocessor;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认情况下，Spring容器找不到合适的PropertyEditor将字符串“2007/10/16”转换成对
 * 象所声明的java.util.Date类型。所以，我们通过CustomEditorConfigurer将刚实现的DatePro- pertyEditor注册到容器，
 * 以告知容器按照DatePropertyEditor的形式进行String到java.util. Date类型的转换工作。
 * 如果使用的容器是BeanFactory的实现，比如XmlBeanFactory，就需要通过编码手动应用 CustomEditorConfigurer到容器，类似如下形式:
 * <p>
 */
public class DatePropertyEditorBeanFactoryTest {

    public static void main(String[] args) {
        ConfigurableListableBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("ioc/beanfactorypostprocessor/date-property-editor-beanfactory.xml"));

        CustomEditorConfigurer ceConfigurer = new CustomEditorConfigurer();
        Map customerEditors = new HashMap();
        customerEditors.put(java.util.Date.class, caojx.learn.ioc.beanfactorypostprocessor.DatePropertyEditorA.class);
        ceConfigurer.setCustomEditors(customerEditors);


        ceConfigurer.postProcessBeanFactory(beanFactory);


        DateFoo dateFoo = (DateFoo) beanFactory.getBean("dateFoo");
        System.out.println(dateFoo.getDate());
    }
}
