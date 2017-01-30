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

    /**
     * 添加用户
     * */
    @Override
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    /**
     * 获取用户
     * */
    @Override
    public List<User> getAllUser() {
        return userDAO.getAllUser();
    }

    /**
     * 删除用户
     * */
    @Override
    public boolean delUser(String id) {
        return userDAO.delUser(id);
    }

    /**
     *查询单个用户
     */
    @Override
    public User getUser(String id) {
        return userDAO.getUser(id);
    }

    /**
     * 更新用户信息
     * */
    @Override
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

}
