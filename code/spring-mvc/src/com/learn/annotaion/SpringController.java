package com.learn.annotaion;

import org.springframework.stereotype.Controller;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    public String get(HttpServletRequest request){
        //获取spring的上线文对象
        WebApplicationContext webApplicationContext1 = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        //获取springmvc的上下文对象
        WebApplicationContext webApplicationContext2 = RequestContextUtils.getWebApplicationContext(request);

        //获取到上下文对象后，就可以获取上下问文中的bean
        ISpring springManager2 = (ISpring) webApplicationContext2.getBean("springManager");

        System.out.println(springManager.get());
        System.out.println(springManager2.get());
        return "/jsp/success2";
    }
}
