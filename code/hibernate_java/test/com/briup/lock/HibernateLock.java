package com.briup.lock;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class HibernateLock {
	private SessionFactory sf;
	private Configuration cfg;
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
			sf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void autoCreateTable(){
		try {
			SchemaExport se = new SchemaExport(cfg);
			se.create(false,true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void save(){
		try {
			
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			Husband h = new Husband();
			h.setName("tom");
			Wife w = new Wife();
			w.setName("lily");
			
			h.setWife(w);
			
			session.save(h);
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//悲观锁 pessimistic  利用数据库中的for update来实现的
	@Test
	public void lock_pessimistic(){
		try {
			
			Session session = sf.openSession();
			Session session2 = sf.openSession();
			
			//第一个session开启事务,查询数据并加锁
			Transaction tran = session.beginTransaction();
			Husband h = 
				(Husband)session.get(Husband.class,1L,LockMode.UPGRADE);
			
			
			//第二个session开启事务,并查询相同的一条数据.
			Transaction tran2 = session2.beginTransaction();
			Husband h2 = 
				(Husband)session2.get(Husband.class, 1L);
			//第二个事务里面修改数据并且更新
			h2.setName("zhangsan11111");
			session2.update(h2);
			
			//第二个事务提交 注意:这个时候第一个事务没有结束
			System.out.println("hello");
			tran2.commit();
			System.out.println("world");
			session2.close();
			
			
			
			//第一个事务结束
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//乐观锁 optimistic
	@Test
	public void lock_optimistic(){
		try {
			
			Session session = sf.openSession();
			Session session2 = sf.openSession();
			
			//第一个事务中 查出数据 假设这时候的version=0  然后做出数据更新 
			Transaction tran = session.beginTransaction();
			Wife w = 
				(Wife)session.get(Wife.class,1L);
			w.setName("tom5");
			session.update(w);
			
			
			
			
			//第二个事务中 查出数据 这时候version也等于0   注意这时候第一个事务没有提交
			Transaction tran2 = session2.beginTransaction();
			Wife w2 = 
				(Wife)session2.get(Wife.class,1L);
			w2.setName("tom6");
			session2.update(w2);
			
			
			
			//第一个事务提交 提交后数据中的 version自动加0变成1
			tran.commit();
			session.close();
			
			
			//第二个事务提交 事务中的拿到的version=0 数据库里面的version=1
			tran2.commit();
			session2.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
