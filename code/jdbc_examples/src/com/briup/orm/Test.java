package com.briup.orm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Test {	
	public static Connection conn = getConnection();
	
	public static void save(Object obj){
		Class clz = obj.getClass();
		// 获取类名
		String className = clz.getSimpleName();
		Properties prop = new Properties();
		PreparedStatement ps = null;
		try {
			prop.load(new FileInputStream(
				"src/com/briup/orm/"+className+".properties"));
		//sql语句
			String tableName = prop.getProperty("tableName");
			String[] attrs = prop.getProperty("attrList").split(",");
			String[] types = prop.getProperty("attrType").split(",");
			String sql = "insert into "+tableName+" values(";
			for(int i=0;i<attrs.length-1;i++){
				sql+="?,";
			}
			sql+="?)";
			ps = conn.prepareStatement(sql);
			for(int i=0;i<attrs.length;i++){
				switch (types[i]) {
				case "int":
					//返回一个 Field 对象，该对象反映此 Class 对象所表示的类或接口的指定已声明字段。
					Field f = clz.getDeclaredField(attrs[i]);
					//private---设置可见性：public--加快访问速度
					//f--表示我们属性的镜像
					//obj--表示传进来的对象
					f.setAccessible(true); 
					int id = f.getInt(obj);
					ps.setInt(i+1, id);
					break;
				case "String":
					Field f1 = clz.getDeclaredField(attrs[i]);
					f1.setAccessible(true);
					// 返回指定对象上此 Field 表示的字段的值。
					String name = (String) f1.get(obj);
					ps.setString(i+1, name);
					break;
				}
			}
			int i = ps.executeUpdate();
			System.out.println("更新"+i+"行");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static Object get(Class c,int id){
		//1.获取到所有的属性
		//2.重数据库里边通过id取出指定的所有数据
		//3.判断属性的类型，赋值  newInstance  ---> f.set(obj,rs.getXxx())
		Object o = null;
		Statement st = null;
		ResultSet rs = null;
		String className = c.getSimpleName();
		Field[] fs = c.getDeclaredFields();
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("src/com/briup/orm/" + className
					+ ".properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// sql语句
		String tableName = prop.getProperty("tableName");
		String[] attrs = prop.getProperty("attrList").split(",");
		String[] types = prop.getProperty("attrType").split(",");
		// 通过id取出数据库中的数据
		String sql = "select ";
		for (int j = 0; j < attrs.length - 1; j++) {
			sql += attrs[j] + ",";
		}
		sql += attrs[attrs.length - 1];
		sql += " from " + tableName + " where id =" + id;
		System.out.println(sql);
		// 执行sql语句
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			o = c.newInstance();
			while (rs.next()) {
				//通过o对象 通过镜像获取对象---newInstance()
				for(int i = 0;i < attrs.length;i++) {
					Field f = c.getDeclaredField(attrs[i]);
					f.setAccessible(true);
					// 判断数据类型
					switch (types[i]) {
						case "int":
							//设置属性的值
							f.set(o, rs.getInt(i+1));
							break;
						case "String":
							f.set(o, rs.getString(i+1));
							break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			//创建对象
		return o;
	}
	
	public static void main(String[] args) {
		Teacher t1 = new Teacher();
		//save(t);
		//Dept d = new Dept(1, "briup", 1);
		//save(d);
		Class c = t1.getClass();
		System.out.println(t1);
		Teacher t2 = (Teacher) get(c,1);
		System.out.println(t2);
	}
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			//1.注册驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "briup";
			String password = "briup";
			//2.建立连接
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
}	
