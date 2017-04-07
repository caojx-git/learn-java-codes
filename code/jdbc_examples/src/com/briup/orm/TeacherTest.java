package com.briup.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TeacherTest {
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
	
	public static void createTable() {
		Statement st = null;
		try {
			st = conn.createStatement();
			String sql = "create table teacher("
					+ "id number(5) primary key,"
					+ "name varchar2(10))";
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void save(Teacher t) {
		PreparedStatement ps = null;
		String sql = "insert into teacher values(?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,t.getId());
			ps.setString(2, t.getName());
			int i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void dropTable() {
		String sql = "drop table teacher";
		Statement st = null;
		try {
			st = conn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static Teacher get(int id) {
		String sql = "select * from teacher where id=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Teacher t = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				int tid = rs.getInt("id");
				String tname = rs.getString("name");
				t = new Teacher(tid, tname);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return t;
	}
	
	public static void main(String[] args) {
		dropTable();
		createTable();
		for (int i = 1;i < 10;i++) {
			save(new Teacher(i,"teacher"+i));
		}
		Teacher t = get(9);
		System.out.println(t);
	}
}
