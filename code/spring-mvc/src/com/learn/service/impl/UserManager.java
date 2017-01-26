package com.learn.service.impl;

import com.learn.dao.IUserDAO;
import com.learn.entity.User;
import com.learn.service.IUserManager;

import java.util.List;


/**
 * Description:
 * Created by caojx on 17-1-17.
 */
public class UserManager implements IUserManager {


    private IUserDAO userDAO;

    public IUserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    @Override
    public List<User> getAllUser() {
        return userDAO.getAllUser();
    }

}
