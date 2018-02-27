package com.briup.composite;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernateComposite {
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
			
			Person p = new Person();
			Address address = new Address("China","shanghai");
			PersonPK pk = new PersonPK(2,"zs");
			
			p.setAge(20);
			/*
			 * 使用组建address和联合主建pk
			 * */
			p.setAddress(address);
			p.setPk(pk);
			
			session.save(p);
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void query(){
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			PersonPK pk = new PersonPK(1,"tom");
			Person p = 
				(Person)session.get(Person.class,pk);
//			session.delete(p);
			System.out.println(p.getPk().getId());
			System.out.println(p.getPk().getName());
			System.out.println(p.getAge());
			System.out.println(p.getAddress().getCountry());
			System.out.println(p.getAddress().getCity());
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void update(){
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			PersonPK pk = new PersonPK(1,"tom");
			
			Person p = new Person();
			p.setPk(pk);
			p.setAge(40);
			
			session.update(p);
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test(){
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			PersonPK pk = new PersonPK(1,"tom");
			Person p = new Person();
			p.setPk(pk);
			
			PersonPK pk2 = new PersonPK(1,"tom");
			Person p2 = new Person();
			p2.setPk(pk2);
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
