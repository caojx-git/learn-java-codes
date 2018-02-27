package com.briup.hql;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class HibernateHQL {
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
	
	
	
	
	@Test//hql语句不能执行insert语句
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
			
			g.getUsers().add(u1);
			g.getUsers().add(u2);
			g.getUsers().add(u3);
			
			u1.setGroup(g);
			u2.setGroup(g);
			u3.setGroup(g);
			
			
			session.save(g);
			
			tran.commit();
			session.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void hql_first(){
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			/*
			 * hql语句不同于sql语句
			 * 在hql语句中会省略select子句会省略
			 * 
			 * 在hql中不能出现表名和列名，必须使用类名和属性名
			 * 
			 * hql语句经过hibernate翻译成sql语句
			 * 
			 * from Group  --》 select * from t_group
			 * */
			String hql = "from Group";
			Query query = session.createQuery(hql);
			List<Group> list = query.list();
			
			for(Group g:list){
				System.out.println(g.getName());
				System.out.println("------------------");
				for(User u :g.getUsers()){
					System.out.println(u.getName());
				}
			}
			
			tran.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void hql_select(){
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			/*
			 * 不能使用*--无法识别
			 * 这种查询返回的结果是list的集合
			 * 集合中存放的是Object类型的数组
			 * 每一个数组对象代表一行数据
			 * */
			String hql = "select g.id,g.name from Group g";
			Query query = session.createQuery(hql);
			List<Object[]> list = query.list();
			
			for(Object[] obj:list){
				System.out.println(Arrays.toString(obj));
			}
			
			tran.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Test//hql语句中使用类的构造器
	public void hql_select_new(){
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			String hql = "select new User(u.id,u.name) from User u";
			Query query = session.createQuery(hql);
			List<User> list = query.list();

			for(User u:list){
				System.out.println("u.id = "+u.getId());
				System.out.println("u.name = "+u.getName());
				System.out.println("u.group = "+u.getGroup());
				System.out.println("-------------------");
			}
			
			tran.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test//hql中的延迟加载
	public void test(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			String hql = "from User";
			Query query = session.createQuery(hql);
			List<User> list = query.list();
			
			
			for(User u:list){
				System.out.println("u.id = "+u.getId());
				System.out.println("u.name = "+u.getName());
				System.out.println("u.group = "+u.getGroup());
				//System.out.println("group.set = "+u.getGroup().getUsers().size());
				System.out.println("-------------------");
			}
			//session.get(User.class, 1l);
			
			tran.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test//hql中不使用延迟加载
	public void hql_select_join_fetch(){
		try {
			
			/*
			 * 映射文件还是延迟加载 lazy="proxy"
			 * 但是我们在hql语句中不想使用延迟加载可以使用 join fetch 使这次查询为立即查询
			 * */
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			String hql = "from User u join fetch u.group";
			/*
			 * 建立连接查询
			 * 封装Group对象，再封装User对象，并将Group保存在User对象中
			 * */
			Query query = session.createQuery(hql);
			List<User> list = query.list();
			
			
			for(User u:list){
				System.out.println("u.id = "+u.getId());
				System.out.println("u.name = "+u.getName());
				System.out.println("u.group = "+u.getGroup());
				System.out.println("group.set = "+u.getGroup().getUsers().size());
				System.out.println("-------------------");
			}
			
			tran.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void hql_select_join(){
		
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			/*
			 *只使用join 也可以连接查询，但是不会将Group对象放置到User对象中 
			 */
			String hql = "from User u join u.group";
			Query query = session.createQuery(hql);
			List<Object[]> list = query.list();
			
			
			for(Object[] obj:list){
				System.out.println(Arrays.toString(obj));
			}
			
			
			tran.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void hql_select_where(){
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			
			String hql = "from User u where u.name=?";
			//String hql1 = "from User u where name=:n1";
			Query query = session.createQuery(hql);
			//Query query1 = session.createQuery(hql1);
			//?占位符 从左往右 坐标从0开始
			query.setString(0, "zs");
			//可以使用属性名--hql1
			//query.setString("name", "zs");
			
			/*
			 * 当确定查询返回的结果只有一条数据的时候，可以使用uniqueResult()
			 */
			User u = (User)query.uniqueResult();
			
			System.out.println(u.getId());
			System.out.println(u.getName());
			
			tran.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void hql_delete(){
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			//注意:不能级联删除
			String hql = "delete from User u where u.name='zs'";
			Query query = session.createQuery(hql);
			int count = query.executeUpdate();
			System.out.println(count);
			tran.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void hql_update(){
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			String hql = "update User u set u.name='tom' where u.name='lisi'";
			Query query = session.createQuery(hql);
			query.executeUpdate();
			
			
			tran.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test//分页查询
	public void hql_fenye(){
		try {
			Session session = sf.openSession();
			Transaction tran = session.beginTransaction();
			
			String hql = "from User order by id";
			Query query = session.createQuery(hql);
			/*
			 * 如果数据量比较大，则使用分批次查询
			 * 
			 * */
			//第一条数据下标0开始编号
			int a = 5;
			//一共查询几条
			int b = 5;
			query.setFirstResult(a);
			query.setMaxResults(b);
			
			List<User> list = query.list();
			
			for(User u:list){
				System.out.println(u.getId()+" "+u.getName());
			}
			
			tran.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
