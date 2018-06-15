package com.itheima.microservice.sso.service;

import com.itheima.microservice.sso.pojo.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 创建Feign接口，对比比com.itheima.microservice.sso.service.SsoService#checkUser(java.lang.String, java.lang.String)调用服务方式更加方便
 * 不需要手动拼接参数
 */
@FeignClient(name = "itcast-microservice-user")
public interface SsoFeignService {

    @RequestMapping(value = "user/{username}", method = RequestMethod.GET)
    public User findByName(@PathVariable("username") String username);
}
