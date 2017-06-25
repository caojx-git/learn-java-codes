package com.briup.jdbc;

import static com.briup.jdbc.PrepareTest.conn;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
public class BatchTest {
	
	public static void statementBatch() {
		Statement st = null;
		try {
			String sql = "delete from s_emp where id =";
			st = conn.createStatement();
			for(int i = 50;i < 100;i++) {
				//1.将sql语句加入到处理序列中
				st.addBatch(sql+i); 
			}
			//2.执行序列中的sql语句
			int[] i = st.executeBatch();  //计数每条sql语句的更新计数
			System.out.println(i.length);
			conn.commit();
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
	public static void preparedBatch() {
		PreparedStatement ps = null;
		
		try {
			String sql = "delete from s_emp where id =?";
			ps = conn.prepareStatement(sql);
			for(int i = 100;i < 150;i++) {
				ps.setInt(1, i);
				ps.addBatch();
			}
			int[] i = ps.executeBatch();
			System.out.println(i.length);
			conn.commit();
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
	
	public static void main(String[] args) {
		statementBatch();
		preparedBatch();
	}
}
