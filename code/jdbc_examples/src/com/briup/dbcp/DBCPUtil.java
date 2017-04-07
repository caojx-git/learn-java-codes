package com.briup.dbcp;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.omg.CORBA.Object;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Description:使用dpcp连接池连接数据库
 *
 * @author caojx
 * Created by caojx on 2017年04月06 下午11:06:06
 */
public class DBCPUtil {

    /**
     * 数据源
     */
    private static DataSource DS;

    private static String configFile = "/dbcp.properties";

    /**
     * @return
     * @Description: 从数据源获取一个连接
     */
    public Connection getConnection() {
        Connection connection = null;
        if (DS != null) {
            try {
                connection = DS.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }
        return connection;
    }

    /**
     * 默认的构造函数
     */
    public DBCPUtil() {
        this.initDbcp();
    }

    /**
     * @Description: 方式一，DBCP通过读取配置文件获取参数后，通过BasicDataSourceFactory获取DataSource
     */
    private static void initDbcp() {
        Properties properties = new Properties();
        try {
            properties.load(Object.class.getResourceAsStream(configFile));
            DS = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造函数，初始化了DS，指定数据库
     */
    public DBCPUtil(String connectURI) {
        initDS(connectURI);
    }

    /**
     * 构造函数，初始化DS，制定所有参数
     */
    public DBCPUtil(String connectURI, String username, String password, String driverClass,
                    int initialSize, int maxActive, int maxIdle,
                    int maxWait, int minIdle) {
        initDS(connectURI, username, password, driverClass, initialSize, maxActive, maxIdle, maxWait, minIdle);

    }

    /**
     * @param connectURI 数据库
     * @return
     * @Description 创建数据源，除了数据库外，都使用默认的参数
     */
    public static void initDS(String connectURI) {
        initDS(connectURI, "myoracle", "myoracle", "oracle.jdbc.driver.OracleDriver", 5, 30, 10, 1, 1000);
    }

    /**
     * @param connectURI
     * @param username
     * @param password
     * @param driverClass
     * @param initialSize
     * @param maxActive
     * @param maxIdle
     * @param maxWait
     * @param minIdle
     * @Description: 方式二，手动给创建DataSource，并给其设置参数
     */
    public static void initDS(String connectURI, String username, String password, String driverClass,
                              int initialSize, int maxActive, int maxIdle,
                              int minIdle, int maxWait) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(connectURI);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxTotal(maxActive);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMaxWaitMillis(maxWait);
        dataSource.setMinIdle(minIdle);
        DS = dataSource;
    }


    public static void main(String[] args) {

        System.out.println(new DBCPUtil().getConnection());
    }

}
