package caojx.learn.service;

import java.util.List;
import java.util.Map;

import caojx.learn.entity.UserInfo;
import com.alibaba.fastjson.JSONObject;

/**
 * 创建时间：2018-05-01
 *
 * @author caojx
 * @version 1.0
 */
public interface UserService {
    
    UserInfo getUserById(int id);
    
    String getUser(int id);
    
    String getUser2(int id);
    
    List<UserInfo> getUsers();
    
    int insertUserInfo(UserInfo userInfo) throws Exception;
    
    List<UserInfo> selectAll2();
    
    /**
     * @param userId
     * @return
     */
    void rmUserById(int userId);
    
    /**
     *
     */
    void removeAll();
    
    Map getCache();
    
    /**
     * 删除全部或者指定缓存名称
     * @param cacheNames
     * @return
     */
    JSONObject removeAllEhCache(String cacheNames);
    
    /**
     * 获取全部或指定缓存名称
     * @param cacheNames
     * @return
     */
    JSONObject getAllEhCache(String cacheNames);
}
