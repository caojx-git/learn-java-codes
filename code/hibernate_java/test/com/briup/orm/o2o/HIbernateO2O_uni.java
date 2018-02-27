package com.briup.orm.o2o;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.briup.orm.o2o.uni.Husband;
import com.briup.orm.o2o.uni.Wife;

//一对一单向关联测试
public class HIbernateO2O_uni {
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
			
			
			Husband h = new Husband();
			h.setName("tom");
			
			Wife w = new Wife();
			w.setName("lily");
			
			//内存中建立起来对象之间的关系h--->w
			h.setWife(w);
			session.save(h);
			//可以在映射文件中配置cascade的值，做级联操作
			//session.save(w);
			
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test//级联保存  一对一关系 保持外键的唯一性
	public void save2(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			
			Husband h = new Husband();
			h.setName("zs");
			
			Wife w = (Wife)session.get(Wife.class, 1L);
			
			h.setWife(w);
			
			session.save(h);
			
			
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
			Husband h = (Husband)session.get(Husband.class, 2L);
			//Husband h = (Husband)session.load(Husband.class, 2L);
			/*
			 * load 是延迟加载要查询的对象
			 * lazy = "proxy"是延迟加载当前对象所关联的对象
			 * */
			System.out.println("world");
			//System.out.println(h.getWife().getName());
			
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test//设置级联查询的时候使用select还是join连接查询
	public void fetch_select_join(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
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
	
	
	
	@Test//级联删除
	public void delete(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			
			
			Husband h = (Husband)session.get(Husband.class, 2L);
			
			//级联查询出来之后对象h和对象w在内存中已经存在关系(1:1),因为在文件中做了映射
			//所以可以进行级联删除(同时也是设置了允许级联删除的)
			session.delete(h);
			
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test//删除 对象h没有关联到w
	public void delete2(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			//游离态-- Transient 不存在数据库中也不存在缓存中
			Husband h = new Husband();
			h.setId(1L);
			
			
			//对象h和对象w在内存没有关联 所以没有级联删除--缓存中没有他们的关联关系
			session.delete(h);
			
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test//删除 对象h关联到w 都是拖管状态的对象
	public void delete3(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			
			Husband h = new Husband();
			h.setId(1L);
			
			Wife w = new Wife();
			w.setId(1L);
			
			h.setWife(w);
			
			//hibernate中也是可以这样进行级联删除的
			session.delete(h);
			
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test//删除 
	public void delete4(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			Husband h = (Husband) session.get(Husband.class, 2L);
			/*
			 * 在session的缓存中解除h和w直接的关联关系
			 * 解除关联关系后，执行session.delete(h)
			 * 不会级联删除
			 * */

			h.setWife(null);
			
			session.delete(h);
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test//级联更新
	public void update(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			Husband h = (Husband)session.get(Husband.class, 1L);
			
			//wife对象这时候也是持久化状态
			//因为查询Husband的对象的时候做了级联查询把wife对象也查询出来
			//wife的属性改变了会保持数据库的同步
			h.getWife().setName("lily");
			
			session.update(h);
			
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
			
			//查询wife对象  不会级联的查询到对应的丈夫 这知识单向关联的映射
			//只能通过一个方级联查询到另外一方,反之则不可以,除非改成双向关联的配置
			Wife w = (Wife)session.get(Wife.class, 3L);
			
			System.out.println(w.getName());
			
			tran.commit();
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
}
