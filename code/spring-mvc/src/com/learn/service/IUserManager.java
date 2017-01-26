package com.learn.service;

import com.learn.entity.User;

import java.util.List;

/**
 * Description:
 * Created by caojx on 17-1-17.
 */
public interface IUserManager {

    public void addUser(User user);

    public List<User> getAllUser();
}
