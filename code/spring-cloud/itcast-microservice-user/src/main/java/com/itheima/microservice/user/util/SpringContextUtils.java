package com.itheima.microservice.user.util;

import org.springframework.context.ApplicationContext;

/**
 * Created by Administrator on 2017/12/8.
 */
public class SpringContextUtils {
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public static Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }
}
