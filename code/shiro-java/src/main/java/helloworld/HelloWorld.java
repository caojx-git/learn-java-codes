package helloworld;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * @author caojx
 * Created on 2018/2/18 下午10:17
 */
public class HelloWorld {

    public static void main(String[] args) {
        //1.读取配置文件，初始化SecurityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2.获取SecurityManager实例
        SecurityManager securityManager = factory.getInstance();
        //把SecurityManager实例绑定到SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);

        //得到当前执行的用户
        Subject currentUser = SecurityUtils.getSubject();
        //创建token令牌， 用户名/密码
        UsernamePasswordToken token = new UsernamePasswordToken("tom","12345");

        try{
            //身份认证
            currentUser.login(token);
            System.out.println("身份认证成功");
        }catch (AuthenticationException e){
            System.out.println("身份认证失败");
            e.printStackTrace();
        }

        currentUser.logout();
    }
}
