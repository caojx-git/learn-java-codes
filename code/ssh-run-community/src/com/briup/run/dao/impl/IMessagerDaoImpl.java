package com.briup.run.dao.impl;

import java.util.List;





import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.hql.ast.tree.FromClause;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import sun.nio.cs.ext.GBK;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.bean.Messagerecord;
import com.briup.run.common.exception.DataAccessException;
import com.briup.run.common.util.HibernateSessionFactory;
import com.briup.run.dao.IMessengerDao;

public class IMessagerDaoImpl implements IMessengerDao {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	//查找新邮件数量
	@Override
	public Integer findNewMessageNum(String nickname)
			throws DataAccessException {
		int num = 0;
		try {
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);
			String hql = "from Messagerecord where status=? and receiver=?";
			Query query = session.createQuery(hql);
			query.setLong(0, 0L);
			query.setString(1, nickname);
			num = query.list().size();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return num;
	}
	//查找会员个数
	@Override
	public Integer findMemberinfoNum() throws DataAccessException {
		List<Memberinfo> list = null;
		try {
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);
			String hql = "from Memberinfo";
			Query query = session.createQuery(hql);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return list.size();
	}

	/**
	 * @author mcao
	 * @param sum   需要查找好友的数量
	 * @return Memberinfo
	 */
	@Override
	public List<Memberinfo> findOneMemberinfo(int sum) throws DataAccessException {
		List<Memberinfo> list = null;
		try {
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);
			String  hql = "from Memberinfo";
			Query query = session.createQuery(hql);
			list = query.list();
			System.out.println(sum+"------------");
			Memberinfo memberinfo = list.get(sum);
			list.clear();
			list.add(memberinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return list;
	}

	//通过年龄，性别，城市查找朋友
	@Override
	public List<Memberinfo> findFriends(String age, String gender, String city)
			throws DataAccessException {
		List<Memberinfo> list = null;
		try {
			System.out.println(age+"---"+gender+"---"+city);
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);
			Criteria criteria = session.createCriteria(Memberinfo.class);
			if (!age.equals("unlimited")) {
				String[] a = age.split("-");
				criteria.add(Restrictions.ge("age", Long.parseLong(a[0]))).add(Restrictions.lt("age", Long.parseLong(a[1])));
			}
			if(!gender.equals("unlimited")){
				criteria.add(Restrictions.eq("gender", gender));
				
			}
			if (!city.equals("unlimited")) {
				System.out.println(city);
				criteria.add(Restrictions.eq("provincecity", city));
			}
			list = criteria.list();	
			System.out.println(list.size()+"------");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return list;
	}
	//保存短信
	@Override
	public void saveMessage(Messagerecord message) throws DataAccessException {
		try {
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);
			session.saveOrUpdate(message);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}

	}

	//获取已发邮件
	@Override
	public List<Messagerecord> listSendMessage(String senderName)
			throws DataAccessException {
		List<Messagerecord> list = null;
		try {
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);
			String hql = "from Messagerecord where sender=?";
			Query query = session.createQuery(hql);
			query.setString(0, senderName);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return list;
	}

	//获取已收的邮件
	@Override
	public List<Messagerecord> listRecieveMessage(String recieveName)
			throws DataAccessException {
		List<Messagerecord> list = null;
		try {
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);
			String hql = "from Messagerecord where receiver=?";
			Query query = session.createQuery(hql);
			query.setString(0, recieveName);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return list;
	}

	//查看邮件
	@Override
	public Messagerecord findMessage(Long id) throws DataAccessException {
		Messagerecord messagerecord = null;
		try {
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);
			String hql = "from Messagerecord where id=?";
			Query query = session.createQuery(hql);
			query.setLong(0, id);
			messagerecord = (Messagerecord) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return messagerecord;
	}
	//删除收到的邮件
	@Override
	public void deleteRecieveMessage(Long id) throws DataAccessException {
		try {
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);
			Messagerecord message = findMessage(id);
			session.delete(message);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}

	}
	//删除已发的邮件
	@Override
	public void deleteSendMessage(Long id) throws DataAccessException {
		try {
			Session session = SessionFactoryUtils.getSession(sessionFactory, true);
			Messagerecord message = findMessage(id);
			session.delete(message);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}

	}
}
