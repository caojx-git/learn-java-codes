package authorization;

import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author caojx
 * Created on 2018/2/20 下午10:06
 * 基于角色的访问控制，验证用户角色
 */
public class RoleTest {

    /**
     * 角色验证，返回boolean类型
     */
    @Test
    public void hasRoleTest() {
        Subject currentUser = ShiroUtil.login("classpath:shiro_role.ini", "jack", "123456");
        //hasRole判断用户是否有某个角色
        if (currentUser.hasRole("role2")) {
            System.out.println("有role2这个角色");
        } else {
            System.out.println("没有role2这个角色");
        }

        //hasRoles判断用户是否有某个角色
        boolean[] results = currentUser.hasRoles(Arrays.asList("role1", "role2"));
        System.out.println(results[0] ? "有role1这个角色" : "没有role1这个角色");
        System.out.println(results[1] ? "有role2这个角色" : "没有role2这个角色");

        //hasAllRoles 判断是否都有某些权限
        System.out.println(currentUser.hasAllRoles(Arrays.asList("role1", "role2")) ? "role1,role2这两个角色都有" : "role1,role2这两个角色不全有");

        currentUser.logout();
    }

    /**
     * 角色验证不通过抛出异常
     */
    @Test
    public void testCheckRole() {
        Subject currentUser=ShiroUtil.login("classpath:shiro_role.ini", "java1234", "123456");
        // Subject currentUser=ShiroUtil.login("classpath:shiro_role.ini", "jack", "123");
        //是否有某个权限
        currentUser.checkRole("role1");

        //是否有某些权限,集合
        currentUser.checkRoles(Arrays.asList("role1","role2"));
        //是否有某些权限，多参数
        currentUser.checkRoles("role1","role2","role3");

        currentUser.logout();
    }
}


