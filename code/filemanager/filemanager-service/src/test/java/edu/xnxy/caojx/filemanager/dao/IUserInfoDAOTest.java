package edu.xnxy.caojx.filemanager.dao;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Description:
 *
 * @author caojx
 * Created by caojx on 2017年04月12 下午2:50:50
 */
public class IUserInfoDAOTest {

    @Resource
    private ClassPathXmlApplicationContext applicationContext;

    @Test
    public void testGetUserInfoDao(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"spring-common.xml","spring-datasource.xml"});
        System.out.println(applicationContext.getBean("userInfoDAO"));
    }

}