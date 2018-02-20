package authorization;

import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author caojx
 * Created on 2018/2/20 下午10:30
 * 基于权限的访问控制，验证用户权限
 */
public class PermissionTest {

    /**
     * 判断是否有某些权限，返回boolean类型
     */
    @Test
    public void testIsPermitted() {

        Subject currentUser=ShiroUtil.login("classpath:shiro_permission.ini", "java1234", "123456");
        // Subject currentUser=ShiroUtil.login("classpath:shiro_permission.ini", "jack", "123");
        System.out.println(currentUser.isPermitted("user:select")?"有user:select这个权限":"没有user:select这个权限");
        System.out.println(currentUser.isPermitted("user:update")?"有user:update这个权限":"没有user:update这个权限");
        boolean results[]=currentUser.isPermitted("user:select","user:update","user:delete");
        System.out.println(results[0]?"有user:select这个权限":"没有user:select这个权限");
        System.out.println(results[1]?"有user:update这个权限":"没有user:update这个权限");
        System.out.println(results[2]?"有user:delete这个权限":"没有user:delete这个权限");
        System.out.println(currentUser.isPermittedAll("user:select","user:update")?"有user:select,update这两个权限":"user:select,update这两个权限不全有");

        currentUser.logout();
    }

    /**
     * 检查权限，验证不通过抛出异常
     */
    @Test
    public void testCheckPermitted() {
        Subject currentUser=ShiroUtil.login("classpath:shiro_permission.ini", "java1234", "123456");
        // Subject currentUser=ShiroUtil.login("classpath:shiro_permission.ini", "jack", "123");
        currentUser.checkPermission("user:select");
        currentUser.checkPermissions("user:select","user:update","user:delete");
        currentUser.logout();
    }

}
