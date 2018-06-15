package com.itheima.microservice.user.service;

import com.itheima.microservice.user.mapper.UserMapper;
import com.itheima.microservice.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Administrator on 2017/12/6.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    /**
     * 根据账号查询用户
     *
     * @param username
     * @return
     */
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

}
