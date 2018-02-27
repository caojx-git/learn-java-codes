package com.briup.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	private static Properties properties;
	private static Connection connection;
	static{
		properties = new Properties();
		//路径path
		File file = new File("D:/workspace/xnxy/src/com/briup/common/jdbc.properties");
		System.out.println(file.exists());
		System.out.println(file.getAbsolutePath());
		try {
			InputStream is = new FileInputStream(file);
			properties.load(is);
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			/*username = properties.getProperty("username");
			password = properties.getProperty("password");*/
			System.out.println(driver);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		try {
			Class.forName(driver);
			//connection = DriverManager.getConnection(url, username, password);
			connection = DriverManager.getConnection(url, properties); //这种方法连接数据库，properties文件中用户名key值只能是user
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static void main(String[] args) {
		System.out.println(ConnectionFactory.getConnection());
	}
	
}
