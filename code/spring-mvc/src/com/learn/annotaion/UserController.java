package com.learn.annotaion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by caojx on 16-12-29.
 * springmvc注解类的实现,可以通过如下简单步骤实现
 * 步骤1：定义一个普通的类 配置注解@Controller //使用注解标示为一个Controller
 * 步骤2：配置请求映射@RequestMapping("/user2")  //配置该类的url的路经
 * 步骤3：方法的返回值为逻辑视图名或者ModeAndView
 */
@Controller
public class UserController {

    @RequestMapping(value="/user/addUser",method = RequestMethod.GET)
    public ModelAndView addUser(){
        return null;
    }

    @RequestMapping(value="/user/deleteUser",method = RequestMethod.GET)
    public ModelAndView deleteUser(){
        return null;
    }

}
