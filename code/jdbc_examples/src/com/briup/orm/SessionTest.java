package com.briup.orm;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SessionTest {
	String tableName = "Student";
	Map<String,String> cfs = new HashMap<String,String>();
	String[] methodNames;//用于存入实体类中的get方法数据
	
	public SessionTest() {
		cfs.put("_id", "id");
		cfs.put("_name", "name");
		cfs.put("_age", "age");
		methodNames = new String[cfs.size()];
	}
	
	public void save(Object o) throws Exception {
		String sql = createSQL(); //创建SQL串
		System.out.println(sql);
		Properties prop = new Properties();
		prop.load(new FileInputStream("src/com/briup/orm/db.properties"));
		String driver = prop.getProperty("driver");
		System.out.println(driver);
		String url = prop.getProperty("url");
		String user = prop.getProperty("user");
		String password = prop.getProperty("password");
		Class.forName(driver); //注册驱动
		System.out.println(url);
		Connection conn = DriverManager.getConnection(url, user, password);
		
		PreparedStatement ps = conn.prepareStatement(sql);
		for(int i = 0; i < methodNames.length; i++) {
			///返回一个 Method 对象，它反映此 Class 对象所表示的类或接口的指定公共成员方法
			Method method = o.getClass().getMethod(methodNames[i]);
			//返回一个 Class 对象，该对象描述了此 Method 对象所表示的方法的正式返回类型
			Class r = method.getReturnType();
			if(r.getName().equals("java.lang.String")) {
				//对带有指定参数的指定对象调用由此 Method 对象表示的底层方法。
				//个别参数被自动解包，以便与基本形参相匹配，基本参数和引用参数都随需服从方法调用转换
				String returnValue = (String)method.invoke(o);
				System.out.println(returnValue);
				ps.setString(i+1, returnValue);
			} else if(r.getName().equals("int")) {
				Integer returnValue = (Integer)method.invoke(o);
				System.out.println(returnValue);
				ps.setInt(i+1, returnValue);
			} 
			System.out.println(method.getName()+"|"+r.getName());
		}
		ps.executeUpdate();
		conn.commit();
		ps.close();
		conn.close();
	}
	
	public String createSQL() {
		String str1 = "";
		int index = 0;
		for(String s : cfs.keySet()) {
			String v = cfs.get(s);//取出实体类成员属性
			//将成员属性第一个字符大写,拼实体类成员属性的getter方法
			methodNames[index] = "get" + Character.toUpperCase(v.charAt(0)) + v.substring(1);
			str1 += v + ","; ////根据表中字段名拼成字段串
			index++;
		}
		str1 = str1.substring(0,str1.length() -1);
		String str2 = "";
		//根据表中字段数，拼成?串
		for (int i = 0; i < cfs.size(); i++){	
			str2 += "?,";
		}
		str2 = str2.substring(0,str2.length() -1);
		String sql = "insert into " + tableName + "(" + str1 + ")" + " values (" + str2 + ")";
		System.out.println(sql);
		return sql;
	}
	
}
