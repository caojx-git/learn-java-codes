package edu.xnxy.caojx.filemanager.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

/**
 * Description:
 *
 * @author caojx
 * Created by caojx on 2017年04月09 下午7:42:42
 */
public class DBTest {

    @Test
    public void testConnection(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-datasource.xml");
        ComboPooledDataSource dataSource = (ComboPooledDataSource) applicationContext.getBean("dataSource");
        try {
            System.out.println(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
