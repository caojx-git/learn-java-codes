package com.briup.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.briup.bean.Car;

public class HibernateBase {
		@Test
		public void test() {
			//1.建立Configuration对象，读取配置文件和映射文件
			/*	new Configuration()默认是读取hibernate.properties
			 *	所以使用configure()读取hibernate.cfg.xml
			 *
			 */
			Configuration cfg = new Configuration();
			//在src下查找hibernate.cfg.xml文件
			cfg.configure();
			//2.建立SessionFactory对象
			/*
			 * 创建SessionFactory
			 * 一个数据库对应一个SessionFactory 
			 * SessionFactory是线线程安全的。
			 */
			SessionFactory factory = cfg.buildSessionFactory();
			
			//3.产生Session对象-->通过openSession()开启一个新的Session
			//创建session
			//此处的session并不是web中的session
			//session只有在用时，才建立concation,session还管理缓存。
			//session用完后，必须关闭。
			//session是非线程安全，一般是一个请求一个session.
			Session session = factory.openSession();
			//4.开启事物
			//手动开启事务(可以在hibernate.cfg.xml配置文件中配置自动开启事务)
			Transaction tran = session.beginTransaction();
			//5.操作数据库
			Car car = new Car(100,"BYD",50000.0);
			/*
			 * 保存数据，此处的数据是保存对象，这就是hibernate操作对象的好处，
			 * 我们不用写那么多的JDBC代码，只要利用session操作对象，至于hibernat如何存在对象，这不需要我们去关心它，
			 * 这些都有hibernate来完成。我们只要将对象创建完后，交给hibernate就可以了。
			 */
			session.save(car);
			//Class c = car.getClass();
			//Object o = session.get(c, session);
			//System.out.println(o);
			//6.事物提交
			tran.commit();
			//7.资源回收
			session.close();
			factory.close();
		}
}	
