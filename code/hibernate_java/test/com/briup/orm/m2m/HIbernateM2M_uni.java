package com.briup.orm.m2m;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.briup.orm.m2m.uni.Student;
import com.briup.orm.m2m.uni.Teacher;

//一对多双向关联测试
public class HIbernateM2M_uni {
	private Configuration cfg;
	private SessionFactory sf;
	
	@Before
	public void before(){
		
		try {
			cfg = new Configuration();
			cfg.configure();
			sf = cfg.buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@After
	public void after(){
		
		try {
			if(sf!=null)sf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test//自动建表
	public void autoCreateTable(){
		
		try {
			SchemaExport se = new SchemaExport(cfg);
			se.create(false, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test//级联保存
	public void save(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			
			Teacher t = new Teacher();
			t.setName("zs");
			
			Student s1 = new Student();
			s1.setName("tom1");
			Student s2 = new Student();
			s2.setName("tom2");
			Student s3 = new Student();
			s3.setName("tom3");
			
			//内存中建立对象之间的关联关系
			t.getStudents().add(s1);
			t.getStudents().add(s2);
			t.getStudents().add(s3);
			
			
			
			session.save(t);
			
			
			
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	@Test//级联查询
	public void query(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			Teacher t = 
					(Teacher)session.get(Teacher.class,1L);
				
			for(Student s:t.getStudents()){
				System.out.println(s);
			}
			
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test//级联删除
	public void delete(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			Teacher t = 
					(Teacher)session.get(Teacher.class,1L);
				
			session.delete(t);
			
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test//级联删除
	public void delete2(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			Teacher t = 
					(Teacher)session.get(Teacher.class,1L);
			
			//解除对象之间的关联关系
			/*
			 * 如果过配置文件使用delete-orphan在缓存中解除对象之间的关系是，会级联删除所关联的对象
			 * */
			t.getStudents().clear();
			
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
