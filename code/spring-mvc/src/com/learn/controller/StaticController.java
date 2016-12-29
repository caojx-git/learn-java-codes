package com.learn.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by caojx on 16-12-29.
 */
public class StaticController extends MultiActionController{

    /*
    * 返回static.jsp的视图路径
    * */
    public ModelAndView img(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return new ModelAndView("/jsp/static");
    }
}
