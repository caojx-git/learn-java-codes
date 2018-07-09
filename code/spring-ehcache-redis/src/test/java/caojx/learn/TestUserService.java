package caojx.learn;

import caojx.learn.entity.UserInfo;
import caojx.learn.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring*.xml","classpath:ehcache.xml"})
public class TestUserService {
    
    private static final Logger logger = LoggerFactory.getLogger(TestUserService.class);
    
    @Resource
    private UserService userService;
    
    @Test
    public void testGetUser() throws InterruptedException {
        long date1 = System.currentTimeMillis();
        UserInfo userInfo = new UserInfo();
        System.out.println("=================================");
        for(int i=0;i<1;i++){
//          Thread.sleep(2000);
            System.out.println(i+"===="+userService.getUser(1));
            System.out.println(i+"===="+userService.getUser(1));
            System.out.println(i+"===="+userService.getUser2(1));
            System.out.println(i+"===="+userService.getUser2(1));
        }

//      Thread.sleep(5000);
//      userService.rmUserById(1);
//      for(int i=0;i<3;i++){
//          System.out.println(i+"删除后查询===="+userService.getUser(1));
//      }
//      userService.removeAll();
//      for(int i=0;i<3;i++){
//          System.out.println(i+"清理后查询===="+userService.getUser(1));
//      }
//      System.out.println("=================================");
//      System.out.println("耗时===="+(System.currentTimeMillis()-date1));
    }
}
