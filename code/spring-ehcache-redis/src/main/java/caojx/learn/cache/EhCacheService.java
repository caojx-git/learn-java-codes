package caojx.learn.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ehCacheService")
public class EhCacheService {
    
    private static final CacheManager cacheManager = new CacheManager();
    private Cache cache;
    
    public EhCacheService() {
        this.cache = cacheManager.getCache("mycache");
    }
    
    public Cache getCache(String cacheName) {
        return cacheManager.getCache(cacheName);
    }
    
    public void removeObjectByCacheName(String cacheName) {
        cacheManager.removeCache(cacheName);
    }
    
    public void removeAll() {
        cacheManager.removalAll();
    }
    
    public Map<Object, Object> getObject(String cacheName) {
        return null;
    }
    
    public List<Map<Object, Object>> getAllObject() {
        return null;
    }
    
    /*
     * 通过名称从缓存中获取数据
     */
    public Object getCacheElement(String cacheKey) throws Exception {
        net.sf.ehcache.Element e = cache.get(cacheKey);
        if (e == null) {
            return null;
        }
        return e.getValue();
    }
    
    /*
     * 将对象添加到缓存中
     */
    public void addToCache(String cacheKey, Object result) throws Exception {
        Element element = new Element(cacheKey, result);
        cache.put(element);
    }
}
