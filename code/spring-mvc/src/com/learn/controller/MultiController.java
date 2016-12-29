package com.learn.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by caojx on 16-12-29.
 * 实现在一个Controller中写多个方法,需要extends MultiActionController
 */
public class MultiController extends MultiActionController{

    /**
    * 基于配置文件xml实现的多方法Controller中方法的参数必须有两个参数,基于注解的时候不用
    * @param httpServletRequest
    * @param httpServletResponse
    * */
    public ModelAndView add(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println("---------add---------");
        return new ModelAndView("/jsp/multi","method","add");
    }

    public ModelAndView update(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println("---------update---------");
        return new ModelAndView("/jsp/multi","method","update");
    }

}
