package caojx.learn.ioc.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * (2) 实现相应的BeanPostProcessor对符合条件的Bean实例进行处理 我们通过PasswordDecodable接口声明来区分将要处理的对象实例，
 * 当检查到当前对象实例实 现了该接口之后，就会从当前对象实例取得加密后的密码，并对其解密。然后将解密后的密码设置回 当前对象实例。
 * 之后，返回的对象实例所持有的就是解密后的密码，逻辑如代码清单4-53所示。
 *
 * @author caojx
 * @version $Id: PasswordDecodePostProcessor.java,v 1.0 2019-02-12 15:40 caojx
 * @date 2019-02-12 15:40
 */
public class PasswordDecodePostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof PasswordDecodable) {
            String encodedPassword = ((PasswordDecodable) bean).getEncodedPassword();
            String decodedPassword = decodePassword(encodedPassword);
            ((PasswordDecodable) bean).setDecodedPassword(decodedPassword);
        }
        return bean;
    }

    private String decodePassword(String encodedPassword) {
        // 实现解码逻辑
        return encodedPassword;
    }
}