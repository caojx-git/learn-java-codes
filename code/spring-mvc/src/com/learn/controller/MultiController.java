package com.learn.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * Created by caojx on 16-12-29.
 * 实现在一个Controller中写多个方法,需要extends MultiActionController
 */
public class MultiController extends MultiActionController{

    public ModelAndView add(){
        System.out.println("---------add---------");
        return new ModelAndView("/jsp/multi");
    }

    public ModelAndView update(){
        return null;
    }

}
