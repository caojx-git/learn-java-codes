package com.briup.spring.chap3;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;



public class HibernateDaoImpl implements CustomerDao {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveCustomer(Customer customer) {
		try {
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);//true代表真正的实例
			session.save(customer);
			System.out.println(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteCustomer(Customer customer) {
		
	}

}
