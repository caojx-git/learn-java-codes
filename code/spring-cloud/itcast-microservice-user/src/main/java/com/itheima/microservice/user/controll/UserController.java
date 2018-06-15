package com.itheima.microservice.user.controll;

import com.itheima.microservice.user.pojo.User;
import com.itheima.microservice.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/12/6.
 */
@RestController
@RefreshScope
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 对外提供接口服务，根据账号查询用户信息
     *
     * @param username
     * @return
     */
    @GetMapping(value = "user/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return this.userService.getUserByUsername(username);
    }
}
