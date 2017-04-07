package com.briup.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StatementTest {
	public static void select() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn =ConnectTest.getConnection();
			//3.
			st = conn.createStatement();
			//4.
			/*Scanner sc = new Scanner(System.in);
			System.out.println("请输入sql语句：");
			String sel = sc.nextLine();*/
			//select语句
			rs = st.executeQuery("select id,last_name from s_emp");
			//5.
			while(rs.next()) {
				
				int id = rs.getInt("id");
				String name = rs.getString(2);
				//double salary = rs.getDouble(3);
				System.out.println(id+","+name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void update() {
		Connection conn = null;
		Statement st = null;
		try {
			conn =ConnectTest.getConnection();
			conn.setAutoCommit(false); //默认是true-自动提交
			//3.
			st = conn.createStatement();
			//4.
			//插入语句
			//st.executeQuery("insert into s_emp(id,last_name)"+"values(100,'emp100')");
			int count = st.executeUpdate("insert into s_emp(id,last_name)"+"values(1001,'emp1001')");
			//int count = st.executeUpdate("delete from s_emp where id=100");
			//int count  = st.executeUpdate("update s_emp set last_name = 'briup'"+"where id between 27 and 100");
			System.out.println(count);
		} catch (Exception e) {
			e.printStackTrace();
			/*try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}*/
		} finally {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) {
		select();
		update();
	}
}
