package caojx.learn.ioc.beanfactorypostprocessor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//todo
public class DatePropertyEditorBeanApplicationContext2Test {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc/beanfactorypostprocessor/date-property-editor-applicationcontext2.0.xml");


        DateFoo dateFoo = (DateFoo) applicationContext.getBean("dateFoo");
        System.out.println(dateFoo.getDate());
    }
}
