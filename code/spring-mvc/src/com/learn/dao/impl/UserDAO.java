package com.learn.dao.impl;

import com.learn.dao.IUserDAO;
import com.learn.entity.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;


/**
 * Description:springmvc+spring+hibernate
 * Created by caojx on 17-1-17.
 */
public class UserDAO implements IUserDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 添加用户
     */
    @Override
    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }


    /**
     * 查询用户
     */
    @Override
    public List<User> getAllUser() {
        String hql = "from User";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

    /**
     * 删除用户
     */
    @Override
    public boolean delUser(String id) {
        String hql = "delete User u where u.id=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0, id);
        return (query.executeUpdate() > 0);
    }

    /**
     * 查询单个用户信息
     */
    @Override
    public User getUser(String id) {
        String hql = "from User u where u.id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0, id);
        return (User) query.uniqueResult();
    }

    /**
     * 更新用户信息
     */
    @Override
    public boolean updateUser(User user) {
        String hql = "update User u set u.userName = ? , u.age = ? where u.id =?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0, user.getUserName());
        query.setInteger(1, user.getAge());
        query.setString(2, user.getId());
        return (query.executeUpdate() > 0);
    }
}
