package com.learn.controller;

import com.learn.entity.User;
import com.learn.service.IUserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description:springmvc+spring+hibernate
 * Created by caojx on 17-1-17.
 */
@Controller
@RequestMapping("/user3")
public class UserController3 {

    @Resource(name = "userManager")
    private IUserManager userManager;

    /**
     * Description:调转到添加用户界面
     * @return
     */
    @RequestMapping("/toAddUser3")
    public String toAddUser(){
        return "jsp/addUser3";
    }

    @RequestMapping("/addUser3")
    public String addUser(User user){
        userManager.addUser(user);
        return "/jsp/success3";
    }

    @RequestMapping("/getAllUser")
    public String getAllUser(HttpServletRequest httpServletRequest){
        List<User> users = userManager.getAllUser();
        httpServletRequest.setAttribute("result","aaa");
        httpServletRequest.setAttribute("aaa","aaa");
        httpServletRequest.setAttribute("users",users);
        return "/jsp/userManager3";
    }
}
