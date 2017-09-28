package com.briup.spring.aop.bean.annotation;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)  //Order排序注解只对list,或数组集合有效
@Component
public class BeanImplTwo implements BeanInterface {

}
