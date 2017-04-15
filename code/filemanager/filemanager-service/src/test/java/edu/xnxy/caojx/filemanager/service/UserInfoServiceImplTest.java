package edu.xnxy.caojx.filemanager.service;

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
}