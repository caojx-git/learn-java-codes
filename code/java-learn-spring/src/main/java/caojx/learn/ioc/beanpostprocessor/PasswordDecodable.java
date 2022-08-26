package caojx.learn.ioc.beanpostprocessor;

/**
 * 自定义BeanPostProcessor
 * <p>
 * 假设系统中所有的IFXNewsListener实现类需要从某个位置取得相应的服务器连接密码，而且系 统中保存的密码是加密的，那么在IFXNewsListener发送这个密码给新闻服务器进行连接验证的时候，
 * 首先需要对系统中取得的密码进行解密，然后才能发送。我们将采用BeanPostProcessor技术， 对所有的IFXNewsListener的实现类进行统一的解密操作。
 *
 * @author caojx
 * @version $Id: PasswordDecodable.java,v 1.0 2019-02-12 15:08 caojx
 * @date 2019-02-12 15:08
 */
public interface PasswordDecodable {

    String getEncodedPassword();

    void setDecodedPassword(String password);
}