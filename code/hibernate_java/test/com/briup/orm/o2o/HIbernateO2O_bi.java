package com.briup.orm.o2o;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.briup.orm.o2o.bi.Husband;
import com.briup.orm.o2o.bi.Wife;

//一对一双向关联测试
public class HIbernateO2O_bi {
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
			
			
			
			/*Husband c1=(Husband)session.load(Husband.class,1L); 
			Husband c2=(Husband)session.load(Husband.class,1L);  
			System.out.println(c1==c2); //true
*/
			/*Husband h = new Husband();
			h.setName("tom");
			
			Wife w = new Wife();
			w.setName("lily");
			
			//内存中建立起来对象之间的关系h-->w
			h.setWife(w);
			
			session.save(h);*/
			
			
			//tran.commit();
			
			//session.close();
			
			
			 Husband s = new Husband();
			  s.setName("hh");
			  session.save(s);
			  s.setName("hi");
			  
			  session.update(s);
			  
			  s.setName("mezi");
			  session.update(s);
			  tran.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test//级联保存 失败的例子
	public void save2(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			
			Husband h = new Husband();
			h.setName("tom");
			
			Wife w = new Wife();
			w.setName("kily");
			/*
			 * 需要从有外键的地方级联保存（即session.save(h)）
			 * 否则保存不上
			 * */
			w.setHusband(h);
			session.save(w);
			
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
			
			//级联查询   设置是否使用延迟加载
			System.out.println("hello");
			Husband h = (Husband)session.get(Husband.class, 1L);
			System.out.println("world");
			
			System.out.println(h.getWife().getName());
			
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test//查询wife对象
	public void query_wife(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			System.out.println("hello");
			Wife w = (Wife)session.get(Wife.class, 1L);
			System.out.println("world");
			
			//如果把wife.hbm.xml文件的映射Husband的配置删除,那么就不能级联查询了
			//所以俩个类直接到底是单向关联还是双向关联主要还是要看的映射文件的配置
			System.out.println(w.getHusband().getName());
			
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
			
			Husband h = (Husband)session.get(Husband.class, 1L);
			
			session.delete(h);
			
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

	
}
