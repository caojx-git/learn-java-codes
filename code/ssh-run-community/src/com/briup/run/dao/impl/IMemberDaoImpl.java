package com.briup.run.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.briup.run.common.bean.Blackrecord;
import com.briup.run.common.bean.Friendrecord;
import com.briup.run.common.bean.Graderecord;
import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.bean.Memberspace;
import com.briup.run.common.bean.Pointaction;
import com.briup.run.common.bean.Pointrecord;
import com.briup.run.common.exception.DataAccessException;
import com.briup.run.common.util.HibernateSessionFactory;
import com.briup.run.dao.IMemberDao;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class IMemberDaoImpl implements IMemberDao {

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * 按姓名查找用户
	 */
	@Override
	public Memberinfo findMemberinfoByName(String name)
			throws DataAccessException {
		Session session =SessionFactoryUtils.getSession(sessionFactory, true);
		String hql = "from Memberinfo where nickname=?";
		Query query = session.createQuery(hql);
		query.setString(0, name);
		Memberinfo memberinfo = null;
		try {
			memberinfo = (Memberinfo) query.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return memberinfo;
	}

	/*
	 * 保存或更新用户
	 */
	@Override
	public void saveOrUpdateMemberinfo(Memberinfo memberinfo)
			throws DataAccessException {
		Session session =SessionFactoryUtils.getSession(sessionFactory, true);
		try {
			session.saveOrUpdate(memberinfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		
	}

	/*
	 * 查找前几名用户
	 */
	@Override
	public List<Memberinfo> findMemberinfoByNum(int number)
			throws DataAccessException {
		List<Memberinfo> list = null;
		try {
			Session session =SessionFactoryUtils.getSession(sessionFactory, true);
			String hql = "from Memberinfo where rownum<=? order by point desc";
			Query query = session.createQuery(hql);
			query.setInteger(0, number);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return list;
	}

	/*
	 * 查找在线用户数量
	 */
	@Override
	public Integer findMemberinfoOnline() throws DataAccessException {
		List<Memberinfo> list = null;
		try {
			Session session =SessionFactoryUtils.getSession(sessionFactory, true);
			String hql = "from Memberinfo where isonline=?";
			Query query = session.createQuery(hql);
			query.setLong(0, 1L);
			 list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return list.size();
	}

	/*
	 * 按照积分查找等级
	 */
	@Override
	public Graderecord findMemberinfoLevel(Long point)
			throws DataAccessException {
		Graderecord graderecord = null;
		try {
			Session session =SessionFactoryUtils.getSession(sessionFactory, true);
			Criteria criteria = session.createCriteria(Graderecord.class);
			//select * from GraderCorde where minpoint <=point and maxpoint > point;
			criteria.add(Restrictions.le("minpoint", point)).add(Restrictions.gt("maxpoint", point));
			graderecord = (Graderecord) criteria.uniqueResult();
		} catch (Exception e) {
			throw new DataAccessException("message", e.getCause());
		}
		return graderecord;
	}

	/*
	 * 按照积分动作查找Pointaction类
	 */
	@Override
	public Pointaction findPointactionByPointAction(String pointAction)
			throws DataAccessException {
		Pointaction pointaction2 = null;
		try {
			Session session =SessionFactoryUtils.getSession(sessionFactory, true);
			String hql = "from Pointaction where actionname=?";
			Query query = session.createQuery(hql);
			query.setString(0, pointAction);
			pointaction2 = (Pointaction) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return pointaction2;
	}

	/*
	 * 保存积分记录
	 */
	@Override
	public void saveOrUpdatePointrecord(Pointrecord pointrecord)
			throws DataAccessException {
		Session session =SessionFactoryUtils.getSession(sessionFactory, true);
		session.saveOrUpdate(pointrecord);
	}

	/*
	 * 保存好友信息
	 */
	@Override
	public void saveOrUpdateFriend(Friendrecord friend)
			throws DataAccessException {
		Session session =SessionFactoryUtils.getSession(sessionFactory, true);
		session.saveOrUpdate(friend);
	}

	/*
	 * 保存黑名单会员
	 */
	@Override
	public void saveOrUpdateFriend(Blackrecord friend)
			throws DataAccessException {
		try {
			Session session =SessionFactoryUtils.getSession(sessionFactory, true);
			session.saveOrUpdate(friend);
		} catch (Exception e) {
			throw new DataAccessException("message", e.getCause());
		}
	}

	/*
	 * 查找本人的所有好友
	 */
	@Override
	public List<Memberinfo> listFriend(String selfname)
			throws DataAccessException {
		List<Memberinfo> list = null;
		try {
			Session session =SessionFactoryUtils.getSession(sessionFactory, true);
			
			String hql = "from Memberinfo where nickname in (select friendname from Friendrecord where selfname=?)";
			Query query = session.createQuery(hql);
			query.setString(0, selfname);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return list;
	}

	/*
	 * 查找本人的所有黑名单
	 */
	@Override
	public List<Memberinfo> listBlack(String selfname)
			throws DataAccessException {
		List<Memberinfo> list = null;
		try {
			Session session =SessionFactoryUtils.getSession(sessionFactory, true);
			String hql = "from Memberinfo where nickname in (select blackname from Blackrecord where selfname=?)";
			Query query = session.createQuery(hql);
			query.setString(0, selfname);
			list = query.list();
		} catch (Exception e) {
			throw new DataAccessException("message", e.getCause());
		}
		return list;
	}

	/*
	 * 删除黑名单会员
	 */
	@Override
	public void deleleBlack(Blackrecord black) throws DataAccessException {
		Session session =SessionFactoryUtils.getSession(sessionFactory, true);
		session.delete(black);
	}

	/*
	 * 删除好友
	 */
	@Override
	public void deleleFriend(Friendrecord friend) throws DataAccessException {
		Session session =SessionFactoryUtils.getSession(sessionFactory, true);
		session.delete(friend);
	}

	/*
	 * 查找好友
	 */
	@Override
	public Friendrecord findfriend(String selfName, String friendName)
			throws DataAccessException {
		Friendrecord friendrecord = null;
		try {
			Session session =SessionFactoryUtils.getSession(sessionFactory, true);
			String hql = "from Friendrecord where selfname=? and friendname=?";
			Query query = session.createQuery(hql);
			query.setString(0, selfName);
			query.setString(1, friendName);
			friendrecord = (Friendrecord) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return friendrecord;
	}

	/*
	 * 查找黑名单
	 */
	@Override
	public Blackrecord findBlack(String selfName, String blackName)
			throws DataAccessException {
		Blackrecord blackrecord = null;
		try {
			Session session =SessionFactoryUtils.getSession(sessionFactory, true);
			String hql = "from Blackrecord where selfname=? and blackname=?";
			Query query = session.createQuery(hql);
			query.setString(0, selfName);
			query.setString(1, blackName);
			blackrecord =  (Blackrecord) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return blackrecord;
	}

	/*
	 * 查找个人空间
	 */
	@Override
	public Memberspace findSpace(Long id) throws DataAccessException {
		Memberspace memberspace = null;
		try{
			Session sessoin = HibernateSessionFactory.getSession();
			String hql = "from Memberspace where memberid=?";
			Query query = sessoin.createQuery(hql);
			query.setLong(0, id);
			memberspace = (Memberspace) query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
			throw new DataAccessException("mesage", e.getCause());
		}
		return memberspace;
	}

	/*
	 * 删除个人空间
	 */
	@Override
	public void delSpace(Memberspace space) throws DataAccessException {
		Session session =SessionFactoryUtils.getSession(sessionFactory, true);
		session.delete(space);
	}
}
