package com.briup.jdbc;

import java.beans.Statement;
import java.sql.*;

import oracle.jdbc.OracleDriver;
public class ConnectTest {
	public static Connection getConnection() throws Exception {
		//1.注册驱动
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//Class.forName("com.MySQL.jdbc.Driver");
		//Class.forName("com.microsoft.jdbc.sqlserve.SQLServerDriver");
		//2.建立连接
		//协议：数据 库类型：连接方式：ip地址：监听端口：数据库名字
		//jdbc:oracle:thin:@localhost:1521:XE    --- XE 简易版数据库名字
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "myoracle";
		String password = "myoracle";
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	
/*	public static Connection getConnection1() throws Exception {
		//1.注册驱动
		Driver driver = new OracleDriver();
		DriverManager.registerDriver(driver);
		//2.建立连接
		//协议：数据 库类型：连接方式：ip地址：监听端口：数据库名字
		//jdbc:oracle:thin:@localhost:1521:XE    --- XE 简易版数据库名字
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "briup";
		String password = "briup";
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}*/
	
	/*public static Connection getConnection2() throws Exception {
		//1.注册驱动
		System.setProperty("jdbc.drivers", "oracle.jdbc.driver.OracleDriver");
		//2.建立连接
		//协议：数据 库类型：连接方式：ip地址：监听端口：数据库名字
		//jdbc:oracle:thin:@localhost:1521:XE    --- XE 简易版数据库名字
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "briup";
		String password = "briup";
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	*/
	
	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = getConnection();
			System.out.println(conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
