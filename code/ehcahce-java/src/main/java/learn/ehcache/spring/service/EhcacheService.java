package learn.ehcache.spring.service;

import learn.ehcache.spring.entity.User;

/**
 * @author caojx
 * Created on 2018/3/7 下午下午8:38
 */
public interface EhcacheService {

    // 测试失效情况，有效期为5秒
    public String getTimestamp(String param);

    public String getDataFromDB(String key);

    public void removeDataAtDB(String key);

    public String refreshData(String key);

    public User findById(Long userId);

    public boolean isReserved(String userId);

    public void removeUser(Long userId);

    public void removeAllUser();
}
