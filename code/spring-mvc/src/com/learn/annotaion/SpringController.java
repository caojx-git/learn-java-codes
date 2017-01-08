package com.learn.annotaion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Description:对spring的集成,SpringController用户测试SpringMVC与spring的集成
 * Created by caojx on 17-1-8.
 */
@Controller
@RequestMapping("/spring")
public class SpringController {

    //使用注解获取SpringManager的实例,@Resource注解里边使用的值是application.xml配置的对应bean的id或name值
    @Resource(name="springManager")
    private ISpring springManager;

    /**
     * Description:用户返回配置成功的页面
     * @return
     */
    @RequestMapping("/get")
    public String get(){
        System.out.println(springManager.get()+"0000000");
        return "/jsp/success2";
    }
}
