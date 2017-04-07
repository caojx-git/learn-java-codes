package com.briup.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description: 测试c3p0连接池
 *
 * @author caojx
 * Created by caojx on 2017年04月07 上午12:26:26
 */
public class C3P0Util {

    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();

    public static Connection getConnection() {
        try {
            return  dataSource.getConnection();
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        System.out.println(C3P0Util.getConnection());
    }
}
