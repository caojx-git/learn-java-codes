package com.briup.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.briup.bean.Car;

//Hibernate基础知识的测试
public class HibernateFeature {
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
			System.out.println("close factory");
			sf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void autoCreateTable(){
		
		try {
			SchemaExport se = new SchemaExport(cfg);
			se.create(false, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void firstHibernate(){
		
		try {
			//第一步  创建Configuration对象
			Configuration cfg = new Configuration();
			
			//第二步  读取配置文件
			//会自动的读取src下面名字叫做hibernate.cfg.xml的配置文件
			//因为配置文件中的第三部分已经写上了映射文件的路径,所以映射文件也会被自动读取的
			cfg.configure();
			
			//第三步  创建Sessionfactory接口类型的对象
			SessionFactory sf = cfg.buildSessionFactory();
			
			//第四步  创建Session接口类型的对象
			Session session = sf.openSession();
			
			//第五步  开启事务 并且获得事务对象 以便之后的提交或者回滚
			Transaction tran = session.beginTransaction();
			
			//第六步  执行需要完成的操作(插入 删除 更新 查找)
			Car car = new Car(10,"BMW",400000);
			//session.save(car);
			
			//第七步  提交事务
			tran.commit();
			
			//第八步  关闭资源
			session.close();
			sf.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test//get方法查询数据
	public void get(){
		
		Session session = sf.openSession();
		
		//查询Car这个类所对应的表中主键值为1的数据
		System.out.println("hello");
		Car car = (Car)session.get(Car.class,1);
		System.out.println("world");
		
		
		System.out.println(car);
		
		session.close();
		
	}
	
	@Test//load方法查询数据
	public void load(){
		
		try {
			Session session = sf.openSession();
			
			//查询Car这个类所对应的表中主键值为1的数据
			/* 
			 * load方法
			 * 延迟加载，不会立即执行操作，会返回一个代理类对象
			 * 只有真正使用对象时才会执行查找操作
			 */
			Car car = (Car)session.load(Car.class,1);
			//该car是一个代理对象class com.briup.bean.Car_$$_javassist_0
			System.out.println(car.getClass());
			System.out.println("hello");
		
			System.out.println(car.getId());
			System.out.println(car);
			System.out.println("world");
			
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test//session的缓存测试
	public void session_cache1(){
		
		try {
			Session session = sf.openSession();
			
			Transaction tran = session.beginTransaction();
			
			Car car = new Car(1,"BMW",400000);
			
			session.save(car);
			car.setBrand("test");
			car.setBrand("test2");
			
			System.out.println("hello");
			tran.commit();
			System.out.println("world");
			
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test//session的缓存测试
	public void session_cache2(){
		
		try {
			Session session = sf.openSession();
			
			//不仅把数据查询出来放到对象里面,而且还把对象放到了缓存中
			Car car1 = (Car)session.get(Car.class, 1);
			
			System.out.println(car1);
			
			System.out.println("-----------------");
			
			Car car2 = (Car)session.get(Car.class, 1);
			System.out.println(car2);
			
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	

	@Test//session的缓存测试
	public void session_cache3(){
		
		try {
			Session session = sf.openSession();
			
			Transaction tran = session.beginTransaction();
			
			Car car = new Car(1,"BMW",400000);
			session.save(car);
			
			Car car1 = (Car)session.get(Car.class, 1);
			System.out.println(car1);
			
			tran.commit();
			
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	@Test//session的flush方法
	public void session_flush(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			Car car = new Car(1,"BMW",400000);
			session.save(car);
			//session.update(car);
			System.out.println("hello");
			/*
			 *  刷新缓存，强制同步缓存中的对象
			 *  强制刷新，但不提交事物，并且不会清空缓存
			 */
			session.flush();  //强制刷新但不提交事物
			System.out.println("world");
			tran.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Test//session的flush方法
	public void session_flush2(){
		
		try {
			Session session = sf.openSession();
			
			Transaction tran = session.beginTransaction();
			
			Car car = new Car(1,"BMW",400000);
			session.save(car);
			System.out.println("hello");
			session.flush();
			System.out.println("world");
			car.setBrand("test");
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	@Test//session的clear方法
	public void session_clear(){
		
		try {
			Session session = sf.openSession();
			
			Transaction tran = session.beginTransaction();
			
			Car car = new Car(1,"BMW",400000);
			session.save(car);
			//clear清空缓存，在清空之前不会刷新，直接清空缓存中的对象清除
			session.clear(); 
			//提交事务
			tran.commit();
			// 关闭session并提交事物
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test//saveOrUpdate方法
	public void saveOrUpdate(){
		try {
			Session session = sf.openSession();
			
			Transaction tran = session.beginTransaction();
			
			Car car = new Car(1,"BMW",400000);
			//只有数据库中存时才会做更新并保存
			session.saveOrUpdate(car);
			System.out.println("hello");
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test//merge方法 --- 合并，按照指定的merge指定的对象存放到缓存中
	public void merge(){
		try {
			Session session = sf.openSession();
			
			Transaction tran = session.beginTransaction();
			
			Car car = new Car(1,"BMW",400000);
			
			session.merge(car);
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	@Test//merge方法和saveOrUpdate方法的不同
	public void merge_saveOrUpdate(){
		try {
			Session session = sf.openSession();
			
			Transaction tran = session.beginTransaction();
			
			Car car1 = new Car(1,"BMW",400000);
			
			session.save(car1);
			session.flush();
			/*
			 * session缓存中已经有了一个Car  id = 1
			 * 又new一个Car id = 1--->会出错提示缓存中存在相同的对象
			 * 所以以我们使用merge保存两个id形同的临时态的对象
			 * */
			Car car2 = new Car(1,"BMW123",400000);
			
//			session.update(car2);
			session.saveOrUpdate(car2);
			session.merge(car2);
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test//获得session对象的方法 openSession()
	public void session_open(){
		try {
			Session s1 = sf.openSession();
			Session s2 = sf.openSession();
			Session s3 = sf.openSession();
			
			System.out.println(s1==s2);
			System.out.println(s1==s3);
			System.out.println(s2==s3);
			
			
			s1.close();
			s2.close();
			s3.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	

	@Test//获得session对象的方法 getCurrentSession()
	public void session_current(){
		try {
			/*
			*在当前线程中，第一次调用这个方法会返回一个新的session对象
			*并将这个session对象与当前线程绑定，之后在这个线程中再调用这个方法，
			*直接返回之前绑定的线程对象
			**/
			Session s1 = sf.getCurrentSession();
			Session s2 = sf.getCurrentSession();
			Session s3 = sf.getCurrentSession();
			
			System.out.println(s1==s2);//true
			System.out.println(s1==s3);//true
			System.out.println(s2==s3);//true
			
			s1.close();
			s2.close();
			s3.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	@Test//获得session对象的方法 getCurrentSession()
	public void session_current2(){
		try {
			Session s1 = sf.getCurrentSession();
			
			Transaction tran = s1.beginTransaction();
			
			Car car1 = new Car(1,"BMW",400000);
			s1.save(car1);
			
			tran.commit();   //使用getCurrentSession()，事物提交之后，session会自动关闭---》下边关闭会报错
			s1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test//结合俩种获得session对象的方法测试
	public void session_open_current(){
		try {
			
			Session s1 = sf.openSession();
			Session s2 = sf.getCurrentSession();
			System.out.println(s1.getClass());
			System.out.println(s2.getClass());
			
			System.out.println(s1==s2);//false
			
			s1.close();
			s2.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test//hibernate中进行jdbc的操作
	public void session_jdbc(){
		try {
			
			Session session = sf.openSession();
			
			//session.connection();
			
			session.doWork(new Work(){

				@Override
				public void execute(Connection conn) throws SQLException {
					Statement stmt = conn.createStatement();
					
					String sql = "insert into t_car(id,car_name,car_price) values(1,'BWM',300000)";
					stmt.execute(sql);
					
				}
			});
			
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test//hibernate中id值的自动增长策略
	public void idTest(){
		try {
			
			Session session = sf.openSession();
			
			Transaction tran = session.beginTransaction();
			
			Car car = new Car(1,"BMW",300000);
			Car car2 = new Car(1,"BMW",300000);
			Car car3 = new Car(1,"BMW",300000);
			session.save(car);
			session.save(car2);
			session.save(car3);
			
			tran.commit();
			
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Test//实体类对象的 瞬时态/自由态 Transient
	public void e_transient(){
		
		try {
			
			Session session = sf.openSession();
			
			Transaction tran = session.beginTransaction();
			
			/*
			 * 1.session的缓存中有没有这个对象          没有
			 * 2.数据库中有没有这个对象所对应的记录  没有
			 * 
			 */
			Car car = new Car(1,"BMW1",100000);
			//hibernate不保证瞬时态对象的属性变化和数据库同步
			//所以也不会有update更新语句执行
			car.setBrand("test");
			
			tran.commit();
			
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test//实体类对象的 持久态 Persistent
	public void e_persistent(){
		
		try {
			
			Session session = sf.openSession();
			
			Transaction tran = session.beginTransaction();
			
			/*
			 * 1.session的缓存中有没有这个对象          有
			 * 2.数据库中有没有这个对象所对应的记录  有
			 * 
			 */
			Car car = (Car)session.get(Car.class, 1);
			
//			Car car2 = new Car(2,"BMW1",100000);
//			session.save(car2);
			
			//hibernate会保持久态对象的属性变化和数据库同步
			//所以会有update更新语句执行
			car.setBrand("test");
			
			
			tran.commit();
			
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test//实体类对象的 脱管态/游离态 Detached
	public void e_detached(){
		
		try {
			
			Session session = sf.openSession();
			
			Transaction tran = session.beginTransaction();
			
			/*
			 * 特点:
			 * 1.session的缓存中有没有这个对象          没有
			 * 2.数据库中有没有这个对象所对应的记录  有
			 * 
			 */
			
			//这时候还是持久态
			Car car = (Car)session.get(Car.class, 1L);
			//clear后就变成了脱管态 
			//因为session的缓存中没了这个对象
			//但是数据库中有这个对象对应的记录
			session.clear();
			
			//hibernate不保证脱管态对象的属性变化和数据库同步
			//所以也不会有update更新语句执行
			car.setBrand("test123");
			
			tran.commit();
			
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
