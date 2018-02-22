package dao;

import entity.User;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author caojx
 * Created on 2018/2/22 下午1:42
 */
@Repository
public interface UserDao {

    /**
     * 通过用户名查询用户
     *
     * @param userName
     * @return
     */
    public User getByUserName(String userName);

    /**
     * 通过用户名查询角色信息
     *
     * @param userName
     * @return
     */
    public Set<String> getRoles(String userName);

    /**
     * 通过用户名查询权限信息
     *
     * @param userName
     * @return
     */
    public Set<String> getPermissions(String userName);
}
