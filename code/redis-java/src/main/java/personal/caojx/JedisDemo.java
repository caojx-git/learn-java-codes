package personal.caojx;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Test;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: JedisDemo
 * @Description: Jedis的测试
 * @version: v1.0.0
 * @author: caojx
 * @date: 17-7-30 下午7:18
 */
public class JedisDemo {

    /**
     * 单实例的测试
     */
    @Test
    public void jedisTest(){
        //1.设置IP地址和端口
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //2.保存数据
        jedis.set("name", "tom");
        //3.获取数据
        String name = jedis.get("name");
        System.out.println(name);
        //4.释放资源
        jedis.close();
    }

    /**
     * 连接池方式连接
     * 注意Jedis对象并不是线程安全的，在多线程下使用同一个Jedis对象会出现并发问题。
     * 为了避免每次使用Jedis对象时都需要重新构建，Jedis提供了JedisPool。
     * JedisPool是基于Commons Pool 2实现的一个线程安全的连接池。
     */
    @Test
    public void jedisPoolTest(){
        //1.获取连接池对象
        JedisPoolConfig config = new JedisPoolConfig();
        //2.设置对大连接数
        config.setMaxTotal(30);
        //3.设置最大空闲连接数
        config.setMaxIdle(10);
        //4.获得连接池
        JedisPool jedisPool = new JedisPool(config,"127.0.0.1",6379);

        //5获取核心对象
        Jedis jedis = null;
        try{
            //通过连接池获得连接
            jedis = jedisPool.getResource();
            //设置数据
            jedis.set("name","张三");
            //获取数据
            String name = jedis.get("name");
            System.out.println(name);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //6.释放资源
            if(jedis!= null){
                jedis.close();
            }
            if(jedisPool != null){
                jedisPool.close();
            }
        }
    }

    public static void main(String[] args){
        System.out.println(new JedisDemo().test());
    }

    public String test(){
        String name = null;
        try{
            name = "tom";
            throw new Exception("test");
        }catch (Exception e){
            e.printStackTrace();
            name = "jack";
            return name;
        }finally {
            name = "rose";
            return name;
        }
    }
    
    /**
     * redis 主从测试
     */
    @Test
    public void sentinelTest() {
        Set<String> sentinels = new HashSet<String>();
        sentinels.add(new HostAndPort("192.168.46.137", 26376).toString());
        sentinels.add(new HostAndPort("192.168.46.137", 26377).toString());
        sentinels.add(new HostAndPort("192.168.46.137", 26378).toString());
        sentinels.add(new HostAndPort("192.168.46.137", 26379).toString());
        JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sentinels, "123456");
        
        System.out.println("Current master: " + sentinelPool.getCurrentHostMaster().toString());
        
        Jedis master = sentinelPool.getResource();
        master.set("username","jager");
        
        System.out.println(master.get("username"));
        
        sentinelPool.close();
        sentinelPool.destroy();
    }

}
