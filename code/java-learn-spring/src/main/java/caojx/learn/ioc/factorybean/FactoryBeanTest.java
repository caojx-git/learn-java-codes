package caojx.learn.ioc.factorybean;

import org.joda.time.DateTime;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 如果一定要取得FactoryBean本身的话，可以通过在bean定义的id之前加前缀&来达到目的。代 码清单4-34展示了获取FactoryBean本身与获取FactoryBean“生产”的对象之间的差别。
 * <p>
 * Spring容器内部许多地方了使用FactoryBean。下面是一些比较常见的FactoryBean实现，你可 以参照FactoryBean的Javadoc以了解更多内容。 
 * JndiObjectFactoryBean 
 * LocalSessionFactoryBean 
 * SqlMapClientFactoryBean 
 * ProxyFactoryBean 
 * TransactionProxyFactoryBean
 */
public class FactoryBeanTest {

    public static void main(String[] args) throws Exception {
        ApplicationContext container = new ClassPathXmlApplicationContext("ioc/factorybean/factorybean-nextdate.xml");

        Object nextDayDate = container.getBean("nextDayDate");
        System.out.println((nextDayDate instanceof DateTime));

        Object factoryBean = container.getBean("&nextDayDate");
        System.out.println((factoryBean instanceof FactoryBean));
        System.out.println((factoryBean instanceof NextDayDateFactoryBean));

        Object factoryValue = ((FactoryBean) factoryBean).getObject();
        System.out.println((factoryValue instanceof DateTime));

        System.out.println(nextDayDate);
        System.out.println(factoryValue);
        System.out.println(((DateTime) factoryValue).getDayOfYear());
        System.out.println(((DateTime) nextDayDate).getDayOfYear());
    }
}
