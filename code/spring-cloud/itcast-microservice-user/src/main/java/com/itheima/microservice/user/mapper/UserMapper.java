package com.itheima.microservice.user.mapper;

import com.itheima.microservice.user.pojo.User;

/**
 * Created by Administrator on 2017/12/8.
 */

public interface UserMapper {

    User getUserByUsername(String username);
}
