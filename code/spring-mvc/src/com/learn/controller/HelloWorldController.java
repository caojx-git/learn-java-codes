package com.learn.controller;

import org.apache.commons.logging.impl.WeakHashtable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caojx on 16-12-28.
 * 基于xml文件配置的Controller需要实现org.springframework.web.servlet.mvc.Controller
 */
public class HelloWorldController implements Controller {

    /*
    * description:
    * handleRequest方法返回的是ModelAndView，这个类有很多对象，可以返回视图的路径，也可以返回视图路径和数据
    * 如下是返回试图welcome.jsp的路径
    * */
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        /*
        * 1.返回试图路径
        * */
        //return new ModelAndView("/jsp/welcome");

        /*
        * 2.返回视图路径和model数据
        * String viewName 试图的路径
        * String modelName
        * Object modelObject
        * */
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("map1","小明1");
        map.put("map2","小明2");
        map.put("map3","小明3");
        map.put("map4","小明4");
        return new ModelAndView("/jsp/welcome","map",map);
    }
}
