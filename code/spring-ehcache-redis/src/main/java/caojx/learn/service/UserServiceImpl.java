package caojx.learn.service;

import caojx.learn.cache.EhCacheService;
import caojx.learn.dao.IUserInfoDAO;
import caojx.learn.entity.UserInfo;
import com.alibaba.fastjson.JSONObject;
import net.sf.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService{
    
    @Resource
    private IUserInfoDAO userInfoDAO;
    
    @Autowired
    private EhCacheService ehCacheService;
    
    // 将查询到的数据缓存到myCache中,并使用方法名称加上参数中的userNo作为缓存的key
    // 通常更新操作只需刷新缓存中的某个值,所以为了准确的清除特定的缓存,故定义了这个唯一的key,从而不会影响其它缓存值
    @Cacheable(value = "myCache", key = "'get'+#id")
    public UserInfo getUserById(int id) {
        Cache cache = ehCacheService.getCache("myCache");
        System.out.println("进来了查询方法......cache.getKeys()="+cache.getName());
        UserInfo u = new UserInfo();
        u.setName("土豆大人");
        return u;
    }
    
    // 将查询到的数据缓存到myCache中,并使用方法名称加上参数中的userNo作为缓存的key
    // 通常更新操作只需刷新缓存中的某个值,所以为了准确的清除特定的缓存,故定义了这个唯一的key,从而不会影响其它缓存值
    @Cacheable(value = "myCache", key = "'getUser'")
    public String getUser(int id) {
        System.out.println("进来了查询方法......");
        return "我是缓存嗷嗷嗷嗷嗷";
    }
    
    @Cacheable(value = "myCache2", key = "'getUser'")
    public String getUser2(int id) {
        System.out.println("进来了查询方法......");
        return "我是缓存嗷嗷嗷嗷嗷22222222222";
    }
    
    @CacheEvict(value = "myCache", key = "'getUser'+#id")
    public void rmUserById(int id) {
        System.out.println("移除缓存中此用户号[" + id + "]的缓存");
    }
    
    @Cacheable(value = "myCache", key = "")
    public List<UserInfo> getUsers() {
        Cache cache = ehCacheService.getCache("myCache");
        System.out.println("cache.getKeys()="+cache.getKeys());
        return userInfoDAO.query(new UserInfo());
    }
    
    // allEntries为true表示清除value中的全部缓存,默认为false
    @CacheEvict(value = "myCache", allEntries = true)
    public void removeAll() {
        System.out.println("移除全部myCache的缓存");
    }
    
    //@CachePut更新缓存
    @CachePut(value="myCache",key="'id:'+#p0['id']")
    public List<UserInfo> selectAll2() {
        return userInfoDAO.query2(new UserInfo());
    }
    
    @Transactional(rollbackFor = Exception.class)
    public int insertUserInfo(UserInfo userInfo) throws Exception {
        
        int result = userInfoDAO.insert(userInfo);
        if (true) {
            // throw new Exception("主动异常");
        }
        System.out.println(result);
        return result;
    }
    
    @Cacheable(value="myCache",key="'getCache'")
    public Map getCache(){
        Map map = new HashMap();
        map.put("1", 1);
        return map;
    }
    
    @Override
    public JSONObject removeAllEhCache(String cacheNames) {
        if(StringUtils.isNotEmpty(cacheNames)){
            String[] cacheNameStr = cacheNames.split(",");
            for(String cacheName : cacheNameStr){
                ehCacheService.removeObjectByCacheName(cacheName);
            }
        }else{
            ehCacheService.removeAll();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("000","删除缓存成功");
        return jsonObject;
    }
    
    @Override
    public JSONObject getAllEhCache(String cacheNames) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("000","获取缓存成功");
        if(StringUtils.isNotEmpty(cacheNames)){
            String[] cacheNameStr = cacheNames.split(",");
            for(String cacheName : cacheNameStr){
                Map<Object, Object> map = ehCacheService.getObject(cacheName);
                jsonObject.put(cacheName+"("+map.size()+")",map);
            }
        }else{
            List<Map<Object, Object>> list = ehCacheService.getAllObject();
            for(Map<Object, Object> map : list){
                for(Map.Entry<Object, Object> entry:map.entrySet()){
                    Map value=(Map) entry.getValue();
                    jsonObject.put(entry.getKey().toString()+"("+value.size()+")",entry.getValue());
                }
            }
        }
        return jsonObject;
    }
}
