package com.imooc.db;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


/**
 * 获取数据库连接
 * */
public class DBAccess {
	
	public SqlSession getSqlSession(){
		SqlSession session = null;
		try{
			//读取配置文件获取数据库连接信息
			Reader reader = Resources.getResourceAsReader("com/imooc/config/Configuration.xml");
			//获取sqlSessionFactory
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			//获取session
			session = sessionFactory.openSession();
		}catch(Exception e){
			e.printStackTrace();
		}
		return session;
	}
	
	public static void main(String[] args) {
		System.out.println(new DBAccess().getSqlSession());
	}

}
