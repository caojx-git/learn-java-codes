package com.briup.orm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class Test1 {
	public static Connection conn = getConnection();
	public static Connection getConnection() {
		Connection conn = null;
		try {
			// 1.注册驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2.建立连接
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "zheng";
			String password = "zheng";
			conn = DriverManager.getConnection(url, user, password);
		    conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void save(Object t){//t
		//声明成Object为了多态的目的
		/*
		 * 为了获取镜像类；
		 * 获取镜像的方式有以下几种：
		 *   对象.getClass(); Student t = new Student() t.getClass();
		 *   类名.Class  Student.class;
		 *   Class.forName("包名+类名");
		 * */
		Class c = t.getClass();
		//System.out.println(c);
		//获取c镜像类的名字
		String name = c.getSimpleName();//Student
		Properties prop = new Properties();//====Map
		PreparedStatement ps = null;
		try {
			prop.load(new FileInputStream("src/com/briup/day2/" + name + ".properties"));
			String tableName = prop.getProperty("tableName");
			String[] attrs = prop.getProperty("attrList").split(",");
			String[] types = prop.getProperty("attrType").split(",");
			//构建SQL语句
			String sql = "insert into " + tableName + " values(";
			//insert into tableName values(?,?);
			for (int j = 0; j < attrs.length-1; j++) {
				sql += "?,";
			}
			sql += "?)";
			System.out.println(sql+"***********");
			//构建ps
			ps = conn.prepareStatement(sql);
			//给占位符赋值
			for (int i = 0; i < attrs.length; i++) {
				//如何给占位符赋值
				/*
				 * 1.获取你的属性对应的镜像
				 *   Field f = c.getDeclaredField(attrs[i]) attrs[0] -- id
				 *   f.setAccessible(true)//id -->private 
				 *   //private---设置可见性； public----加快访问属性速度
				 *   Object o = f.get(t)---->t.getf();
				 *   //f :属性对应的镜像  id---->Id
				 *     t:表示我们传进来的对线  Student
				 */
				Field f = c.getDeclaredField(attrs[i]);// id
				f.setAccessible(true);// id --private
				Object o = f.get(t);// 获取 t对象的f属性的值
				switch (types[i]) {
				case "int":
					ps.setInt(i+1, (int) o);
					break;
				case "String":
					ps.setString(i+1, (String) o);
					break;
				}
			}
			//执行sql语句
			ps.execute();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Object get(Class c,int id){ //从数据库中读取对象的数据

		//1.从文件中找出对象中的所有属性-----xxx.properties  load(input)
		//2.从数据库中找出属性值---- select * from tableName

		//3.给对象的属性赋值---- Class c  c是我们传入的镜像  --创建对象c.newInstacn()
		//4.通过setXxx 给对象的属性赋值


		//获取c这个镜像对象的名字
		String name = c.getSimpleName();
		//获取这个名字的目的是为了加载.properties文件
		Properties prop = new Properties();
		PreparedStatement ps = null;
		ArrayList<Object> array = new ArrayList<>();
		try {
			prop.load(new FileInputStream("src/com/briup/day2/"+name+".properties"));
		    //获取表名字
			String tableName=prop.getProperty("tableName");
			//获取属性数组
			String [] attrs = prop.getProperty("attrList").split(",");
			//构建sql语句
			String sql = "select * from "+tableName + " where id=?";
			//
			System.out.println("sql:"+sql);
			//构建prepareStatement
		    ps = conn.prepareStatement(sql);
			//给sql语句中的占位符赋值
			ps.setInt(1, id);
			//执行sql语句
			ResultSet rs =ps.executeQuery();
			/*while(rs.next()){
				 try {
					//获取o对象
					Object o = c.newInstance();
					for(int i=0;i<attrs.length;i++){
						//给属性赋值
						
						 * 1.构建出来每个属性对应的set方法
						 * 2.调用set方法给对象的属性赋值
						 * 
						String set = "set"+
								attrs[i].substring(0,1).toUpperCase()+
								attrs[i].substring(1);
								Method[] methods = 
								c.getDeclaredMethods();
								for(Method method:methods){
									{
											e.printStackTrace();
										} 
									}
										
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			/*
			*(1,"zheng")
			*(1,"饿了吗")
			*/
			
			while(rs.next()){
				try {
					//获取o对象 通过镜像如何获取对象：====》newInstance()
					Object o = c.newInstance();
					// o--id=0; o===>name:null;
					for(int i=0;i<attrs.length;i++){
						//给属性赋值
						/*
						 * 1.获取出来每个属性的镜像
						 * 2.调用set方法给对象的属性赋值
						 */ 
						Field f = c.getDeclaredField(attrs[i]);// id
						f.setAccessible(true);// id --private
						//获取属性的类型
						String[] types = prop.getProperty("attrType").split(",");
						if("int".equals(types[i])){
							f.set(o, rs.getInt(i+1));
							//表示给o这个对象的f属性赋值，赋的值为：rs.getInt(i+1)
						}else{
							f.set(o, rs.getString(i+1));
						}
					}
					array.add(o);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			return array;
		}
	
	}

	public static void main(String[] args) {
		// String anme = new Teacher().getClass().getSimpleName();
		// System.out.println(anme);
		// save(new Teacher(10,"teacher10"));
		// save(new Student(10,"student10",20));

	}

	public static void setType(PreparedStatement ps, String type, int index, Object obj) throws SQLException {
		switch (type) {
		case "int":
			ps.setInt(index, (int) obj);
			break;
		case "String":
			ps.setString(index, (String) obj);
			break;
		}
	}

}
