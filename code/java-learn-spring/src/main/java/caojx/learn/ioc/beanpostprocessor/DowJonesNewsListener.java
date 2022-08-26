package caojx.learn.ioc.beanpostprocessor;

import caojx.learn.ioc.IFXNewsPersister;

/**
 * (1) 标注需要进行解密的实现类
 * <p>
 * 为了能够识别那些需要对服务器连接密码进行解密的IFXNewsListener实现，我们声明了接口 PasswordDecodable，并要求相关IFXNewsListener实现类实现该接口。
 *
 * @author caojx
 * @version $Id: DowJonesNewsListener.java,v 1.0 2019-02-12 15:38 caojx
 * @date 2019-02-12 15:38
 */
public class DowJonesNewsListener implements IFXNewsPersister, PasswordDecodable {

    private String password;

    @Override
    public String getEncodedPassword() {
        return this.password;
    }

    @Override
    public void setDecodedPassword(String password) {
        this.password = password;
    }
}