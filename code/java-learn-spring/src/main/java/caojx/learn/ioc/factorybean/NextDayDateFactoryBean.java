package caojx.learn.ioc.factorybean;

import org.joda.time.DateTime;
import org.springframework.beans.factory.FactoryBean;

/**
 * FactoryBean是Spring容器提供的一种可以扩展容器对象实例化逻辑的接口，请不要将其与容器 名称BeanFactory相混淆。FactoryBean，其主语是Bean，
 * 定语为Factory，也就是说，它本身与其他注 册到容器的对象一样， 只是一个Bean而已， 只不过， 这种类型的Bean本身就是生产对象的工厂 （Factory）。
 * <p>
 * 当某些对象的实例化过程过于烦琐，通过XML配置过于复杂，使我们宁愿使用Java代码来完成这 个实例化过程的时候，或者，某些第三方库不能直接注册到
 * Spring容器的时候，就可以实现org.springframework.beans.factory.FactoryBean接口，给出自己的对象实例化逻辑代码。当然，不使用FactoryBean，
 * 而像通常那样实现自定义的工厂方法类也是可以的。不过，FactoryBean可是Spring提供的对付这种情况的“制式装备” 哦！
 * <p>
 * 如果我们想每次得到的日期都是第二天，可以实现一个如代码清单4-33所示的FactoryBean
 */
public class NextDayDateFactoryBean implements FactoryBean {

    public Object getObject() throws Exception {
        return new DateTime().plusDays(1);
    }

    public Class getObjectType() {
        return DateTime.class;
    }

    public boolean isSingleton() {
        return false;
    }
}
