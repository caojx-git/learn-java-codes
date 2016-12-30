package com.learn.annotaion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description:
 * springmvc注解类的实现,可以通过如下简单步骤实现
 * 步骤1：定义一个普通的类 配置注解@Controller //使用注解标示为一个Controller
 * 步骤2：配置请求映射@RequestMapping("/user")  //配置该类的url的路经
 * 步骤3：方法的返回值为逻辑视图名或者ModeAndView
 * 提示：基于注解的Controller是可以些多个方法的
 * Created by caojx on 16-12-29.
 */
@Controller
public class UserController {

    /**
     * Description:添加用户方法，直接返回试图和数据，使用@RequestMapping设置访问路径
     * value设置访问路径的值，默认是这个
     * method设置请求方式，一般是不限至请求方式的，设置了请求方式，则只会接受设置的方式的请求，不设置的话没有限制
     * 提示：这里方法addUser中并没有参数HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse，
     * 这两个参数在使用注解的时候是非必须的。
     * @return ModelAndView
     */
    @RequestMapping(value="/user/addUser",method = RequestMethod.GET)
    public ModelAndView addUser(){
        System.out.println("-------addUser---------");
        return new ModelAndView("/jsp/annotation","result","addUser");
    }

    /**
     * Description:删除用户，直接返回视图路径和数据
     * @return ModelAndView
     * */
    @RequestMapping(value="/user/deleteUser",method = RequestMethod.GET)
    public ModelAndView deleteUser(){
        System.out.println("-------deleteUser---------");
        return new ModelAndView("/jsp/annotation","result","deleteUser");
    }

}
