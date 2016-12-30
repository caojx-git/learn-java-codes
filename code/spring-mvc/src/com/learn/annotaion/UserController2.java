package com.learn.annotaion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 * 优化springmvc注解类UserController2.java
 * Created by caojx on 16-12-29.
 */
@Controller
@RequestMapping("/user2")
public class UserController2 {

    /**
     * Description:添加用户方法，直接返回试图和数据，使用@RequestMapping设置访问路径
     * value设置访问路径的值，默认是这个,所以可以不用显示写value=""
     * method设置请求方式，一般是不限至请求方式的，不限至
     * @return ModelAndView
     */
    @RequestMapping("/addUser") //由于每个方法RequestMapping("/user2/addUser"),都会出现user2，我们将user2移动到类体注解中
    public ModelAndView addUser(){
        System.out.println("-------addUser 优化版---------");
        return new ModelAndView("/jsp/annotation","result","addUser");
    }

    /**
     * Description:删除用户，直接返回视图路径和数据
     * @return ModelAndView
     * */
    @RequestMapping("/deleteUser")
    public ModelAndView deleteUser(){
        System.out.println("-------deleteUser 优化版---------");
        return new ModelAndView("/jsp/annotation","result","deleteUser");
    }

    /**
     * Description:直接返回视图路径，返回类型可有为String，返回的数据可以放置到httpServletRequest中
     * @param httpServletRequest
     * @return String
     */
    @RequestMapping("/toUser")
    public String toUer(HttpServletRequest httpServletRequest){
        String result = "this is toUser----优化版";
        httpServletRequest.setAttribute("result", result);
        return "/jsp/annotation";
    }

}
