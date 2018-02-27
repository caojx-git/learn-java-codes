package com.briup.criteria;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class HibernateCriteria {
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
			
			Husband h = new Husband("wangwu4",29,1700);
			
			Wife w = new Wife();
			w.setName("lily");
			
			//建立俩个实体类对象之间的关系
			h.setWife(w);
			
			session.save(h);
			
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test//查询出所有数据
	public void criteria1(){
		try {
			
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			//session的作用:
			//1.执行增删改查操作
			//2.提供缓存功能
			//3.创建高级查询接口的实现类对象
			
			//Criteria是接口
			Criteria criteria = 
					session.createCriteria(Husband.class);
			//这时候会把Husband类所对应的表中所有数据查询出来
			//因为这里面还没有添加查询的筛选条件
			List<Husband> list = criteria.list();
			
			
			
			for(Husband h:list){
				System.out.println(h.getId());
				System.out.println(h.getName());
				System.out.println(h.getAge());
				System.out.println(h.getSalary());
				//System.out.println(h.getWife().getName());
				System.out.println("------------------");
			}
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test//添加查询条件
	public void criteria2(){
		try {
			
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			Criteria criteria = 
					session.createCriteria(Husband.class);
			
			
			
			//Criteria接口  Criterion接口  Restrictions类
			//Criterion 是 Criteria 的查询条件
			//Criterion也是一个接口,我们要使用这个接口的实现类对象来表示查询的条件
			//Criteria中的方法add(查询条件)
			//Criteria 提供了 add(Criterion criterion) 方法来添加查询条件
			//Restrictions 工具类可以创建Criterion接口类型的对象,也就是创建查询条件对象
			//Restrictions类中有很多静态方法,这些方法的返回值就是我们要的查询条件对象(Criterion接口的实现类对象)
			
			//Restrictions.gt("id", 2L)方法的返回值就是Criterion接口的实现类对象(查询条件对象)
//			select * from husband where id>2;
//			criteria.add(Restrictions.gt("id", 2L));
			
//			select * from husband where salary<=4000
//			criteria.add(Restrictions.le("salary", 4000d));
			
//			select * from husband where name='wangwu3'
//			criteria.add(Restrictions.eq("name", "wangwu3"));
			
//			select * from husband where name like 'wang%'
//			criteria.add(Restrictions.like("name", "wang%"));
			
//			select * from husband where id in(1,2,3,4);
//			criteria.add(Restrictions.in("id", new Long[]{1L,2L,3L,4L}));
			
//			select * from husband where salary between 4000 and 9000;
//			注意:这里也可以是一个日期 比如生日在俩个日期之间
//			criteria.add(Restrictions.between("salary", 4000d, 9000d));
			
//			select * from husband where salary is null;
//			criteria.add(Restrictions.isNull("salary"));
			
//			select * from husband where name is not null;
			criteria.add(Restrictions.isNotNull("name"));
			
			
			List<Husband> list = criteria.list();
			
			for(Husband h:list){
				System.out.println(h.getId());
				System.out.println(h.getName());
				System.out.println(h.getAge());
				System.out.println(h.getSalary());
//				System.out.println(h.getWife().getName());
				System.out.println("------------------");
			}
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	


	@Test//连着添加查询条件
	public void criteria3(){
		try {
			
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			Criteria criteria = 
					session.createCriteria(Husband.class);
			
			//连着添加条件 默认是以and的方式连接条件
			//select * from husband where id>2 and id<7;
//			criteria.add(Restrictions.gt("id",2L));
//			criteria.add(Restrictions.lt("id", 7L));
			
			
			//上面的写法等价于下面的写法
			//同时我们也可以这来写  俩个条件连着添加
			criteria.add(Restrictions.gt("id", 2L)).add(Restrictions.le("id",4L));
			
			
			List<Husband> list = criteria.list();
			
			//也可以这样把添加添加条件和执行查询写在一起
//			List<Husband> list = criteria.add(Restrictions.gt("id", 2L)).
//					add(Restrictions.le("id",4L)).list();
			
			
			for(Husband h:list){
				System.out.println(h.getId());
				System.out.println(h.getName());
				System.out.println(h.getAge());
				System.out.println(h.getSalary());
//				System.out.println(h.getWife().getName());
				System.out.println("------------------");
			}
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	

	@Test//添加or的条件
	public void criteria4(){
		try {
			
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			Criteria criteria = 
					session.createCriteria(Husband.class);
			
			//Restrictions.or(条件1,条件2);  方法的返回值还Criterion类型对象
			//select * from husband where (id>2 or name='zhangsan') and salary=3000;
//			criteria.add(
//							Restrictions.or(Restrictions.gt("id",2L), Restrictions.eq("name","zhangsan"))
//					    ).
//					 add(Restrictions.eq("salary",3000d));
			
			
			
			//Restrictions.disjunction()后可以跟很多个or关联的条件
			//select * from husband where id>3 and id<7 and (name='zs' or name like 'zs%' or salary<1000) and id=9;
			criteria.add(Restrictions.gt("id",3L)).
					 add(Restrictions.lt("id",7L)).
					 add(
							 //or有三个条件 使用disjunction
							Restrictions.disjunction().
							add(Restrictions.eq("name","zs")).
							add(Restrictions.like("name","zs%")).
							add(Restrictions.lt("salary", 1000d))
						).
					 add(Restrictions.eq("id", 9L));
			
			
			List<Husband> list = criteria.list();
			
			for(Husband h:list){
				System.out.println(h.getId());
				System.out.println(h.getName());
				System.out.println(h.getAge());
				System.out.println(h.getSalary());
//				System.out.println(h.getWife().getName());
				System.out.println("------------------");
			}
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Test//连接查询
	public void criteria5(){
		try {
			
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			Criteria criteria = 
					session.createCriteria(Husband.class);
			
			//在查询Husband的基础上再连接查询把wife也查出来
			//这里的wife指的的是Hubband中名字叫做wife的属性
			criteria.createCriteria("wife");
			
			
			List<Husband> list = criteria.list();
			
			for(Husband h:list){
				System.out.println(h.getId());
				System.out.println(h.getName());
				System.out.println(h.getAge());
				System.out.println(h.getSalary());
				System.out.println(h.getWife().getName());
				System.out.println("------------------");
			}
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	@Test//排序
	public void criteria6(){
		try {
			
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			Criteria criteria = 
					session.createCriteria(Husband.class);
			
//			criteria.addOrder(Order.asc("name"));
			criteria.addOrder(Order.desc("salary"));
			
			List<Husband> list = criteria.list();
			
			for(Husband h:list){
				System.out.println(h.getId());
				System.out.println(h.getName());
				System.out.println(h.getAge());
				System.out.println(h.getSalary());
//				System.out.println(h.getWife().getName());
				System.out.println("------------------");
			}
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Test
	public void QBE() {
		Husband hh = new Husband();
		//hh.setId(1);//设置id是无法生效的
		hh.setName("wangwu1");
		Example e = Example.create(hh);
		e.excludeZeroes();//忽略0，不然没有赋值的属性就默认为0
		e.ignoreCase();//忽略大小写
		e.excludeNone();
		Session s = sf.openSession();
		Criteria c = s.createCriteria(Husband.class);
		c.add(e);
		//select * from husband where name=wangwu1 age=0 salary=0;
		List<Husband> list = c.list();
		System.out.println(list.size());
		for(Husband h:list) {
			System.out.println(h);
			System.out.println(h.getWife());
		}
	}
	
}
