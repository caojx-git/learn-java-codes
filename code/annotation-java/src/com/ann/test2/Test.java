package com.ann.test2;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {

	public static void main(String[] args) {
		Filter filter = new Filter();
		filter.setId(10);//查询id为10的用户
		
		Filter filter2 = new Filter();
		filter2.setUserName("lucy"); //查询用户名为lucy的用户
		
		Filter filter3 = new Filter();
		filter3.setEmail("liu@sina.com,zh@163.com,88888@qq.com");
		
		/*Filter2 filterx = new Filter2();
		filterx.setAmount(100);
		filterx.setId(10);
		filterx.setLeader("你好");*/
		
		
		String sql1= query(filter);
		String sql2= query(filter2);
		String sql3= query(filter3);
		//String sqlx= query(filterx);
		
		System.out.println(sql1);
		System.out.println(sql2);
		System.out.println(sql3);
		//System.out.println(sqlx);
	}
	
	private static String query(Filter filter){
		StringBuilder sb = new StringBuilder();
		//1.获取class
		Class c = filter.getClass();
		//2.获取到Table的名字
		boolean exists = c.isAnnotationPresent(Table.class);
		if(!exists){
			return null;
		}
		Table table = (Table) c.getAnnotation(Table.class);
		String tableName = table.value();
		sb.append("select * form ").append(tableName).append(" where 1=1 ");
		//3.遍历所有的字段
		Field[] fArray = c.getDeclaredFields();
		for(Field f:fArray){
			//4.处理每个字段对应的sql
			//4.1拿到字段的名字
			boolean fExists = f.isAnnotationPresent(Column.class);
			if(!fExists){
				continue;
			}
			Column column = f.getAnnotation(Column.class);
			//4.2拿到字段的值
			String columnName = column.value();
			//获取属性的值
			String filedName = f.getName();
			String getMethodName = "get"+filedName.substring(0, 1).toUpperCase()+filedName.substring(1);
			try {
				Method method = c.getMethod(getMethodName);
				Object fileValue =method.invoke(filter);
				//4.3拼接sql
				if(fileValue==null || (fileValue instanceof Integer &&(Integer)fileValue==0)){
					continue;
				}
				sb.append(" and ").append(filedName);
				if(fileValue instanceof String){// in()
					if(((String) fileValue).contains(",")){
						String[] values = ((String)fileValue).split(",");
						sb.append(" in (");
						for(String v:values){
							sb.append("'").append(v).append("'").append(",");
						}
						sb.deleteCharAt(sb.length()-1);
						sb.append(")");
						
					}else{
						sb.append("=").append("'").append(fileValue).append("'");
					}
				}else if(fileValue instanceof Integer){
					sb.append("=").append(fileValue);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
