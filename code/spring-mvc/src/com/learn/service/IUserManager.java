package com.learn.service;

import com.learn.entity.User;

import java.util.List;

/**
 * Description:
 * Created by caojx on 17-1-17.
 */
public interface IUserManager {

    /**
     * 添加用户
     * */
    public void addUser(User user);

    /**
     * 查询所有用户
     * */
    public List<User> getAllUser();

    /**
     * 删除用户
     * */
    public boolean delUser(String id);

    /**
     * 查询单个用户
     * */
    public User getUser(String id);

    /**
     * 更新用户信息
     * */
    public boolean updateUser(User user);
}
