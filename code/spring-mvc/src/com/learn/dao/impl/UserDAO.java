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

    @Override
    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<User> getAllUser() {
        String hql = "from User";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }
}
