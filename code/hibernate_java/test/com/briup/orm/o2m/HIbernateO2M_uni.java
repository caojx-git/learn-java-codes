package com.briup.orm.o2m;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.briup.orm.o2m.uni.Group;
import com.briup.orm.o2m.uni.User;

//一对多单向关联测试
public class HIbernateO2M_uni {
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
			
			
			Group g = new Group();
			g.setName("g1");
			
			User u1 = new User();
			u1.setName("zs");
			
			User u2 = new User();
			u2.setName("lisi");
			
			User u3 = new User();
			u3.setName("wangwu");
			
			//内存中建立起对象之间的关联
			g.getUsers().add(u1);
			g.getUsers().add(u2);
			g.getUsers().add(u3);
			
			
			session.save(g);
			
			
			
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
			
			System.out.println("hello");
			Group g = 
					(Group)session.get(Group.class,1L);
			System.out.println("world");
				
			
			System.out.println(g.getName());
			/*for(User u:g.getUsers()){
				System.out.println(u.getId()+"  "+u.getName());
			}*/
				
			
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
			
			Group g = 
					(Group)session.get(Group.class,1L);
				
			session.delete(g);
			
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test//级联删除
	public void delete_cascade_all(){
		//cascade="all"
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			Group g = 
					(Group)session.get(Group.class,3L);
			
			g.getUsers().clear();
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test//级联删除
	public void delete_cascade_alldeleteorphan(){
		//cascade="all-delete-orphan"
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			Group g = 
					(Group)session.get(Group.class,1L);
			g.getUsers().clear();
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
