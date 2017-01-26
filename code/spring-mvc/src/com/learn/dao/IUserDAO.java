package com.learn.dao;

import com.learn.entity.User;

import java.util.List;

/**
 * Description:springmvc+spring+hibernate
 * Created by caojx on 17-1-17.
 */
public interface IUserDAO {

    /**
     *
     * @param user
     */
    public void addUser(User user);

    /**
     *
     */
    public List<User> getAllUser();
}
