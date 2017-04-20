package edu.xnxy.caojx.filemanager.service;

import edu.xnxy.caojx.filemanager.entity.UserInfo;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Description:
 *
 * @author caojx
 *         Created by caojx on 2017年04月12 下午2:53:53
 */
public class UserInfoServiceImplTest {

    @Resource
    private ClassPathXmlApplicationContext applicationContext;

    @Test
    public void testGetUserInfoService(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring-common.xml","spring-datasource.xml"});
       IUserInfoService userInfoService = (IUserInfoService) applicationContext.getBean("userInfoService");
        try {
            userInfoService.login(1L,"123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveUserInfo(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring-common.xml","spring-datasource.xml"});
        IUserInfoService userInfoService = (IUserInfoService) applicationContext.getBean("userInfoService");
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(201314070227L);
            userInfo.setUserName("曹建祥");
            userInfo.setUserPassword("201314070227");
            userInfo.setUserAge(22);
            userInfo.setUserGender(0);
            userInfo.setUserEmail("389715062@qq.com");
            userInfo.setUserAddress("湖南郴州");
            userInfo.setUserPhoneNumber("17373539152");
            userInfo.setCollegeId(1002L);
            userInfo.setManager(1);
            userInfoService.saveUserInfo(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}