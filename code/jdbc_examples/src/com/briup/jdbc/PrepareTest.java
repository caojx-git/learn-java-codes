package com.briup.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PrepareTest {
	
	public static void main(String[] args) {
		select();
		//dml1();
	}
	
	public static Connection conn = getConnection();
	
	public static Connection getConnection() {	
		Connection conn = null;
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "briup";
			String password = "briup";
			//1.注册驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2.建立连接
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void select() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select id, last_name from s_emp"+" where id between ? and ?";
		
		try {
			//3.创建statement对象
			ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);  //ps.setInt(parameterIndex, x);  第一个参数指定问好的位置，第二个参数指问好的值
			ps.setInt(2, 150);
			//执行sql语句
			rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				System.out.println(id+","+name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void dml1() {
		PreparedStatement ps = null;
		String sql = "insert into s_emp(id,last_name,salary) values(?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			for(int i = 101;i < 150;i++) {
				ps.setInt(1, i);
				ps.setString(2, "emp"+i);
				ps.setDouble(3, i*100);
				ps.executeUpdate();
			}
			conn.commit();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void dml() {
		Statement st = null;
		try {
			st = conn.createStatement();
			for(int i = 50; i < 100; i++) {
				String sql = "insert into s_emp(id,last_name,salary)"+" values("+i+",'emp"+i+"',"+i*100+")";
				System.out.println(sql);
				st.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
