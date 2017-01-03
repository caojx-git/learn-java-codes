package com.learn.annotaion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:springmvc将Controller中返回的数据，交给页面
 * springmvc支持的返回方式有，ModelAndView,Model,ModelMap,Map,List,View,String,void
 * 其中ModelAndView，Model,ModelMap，View就不再介绍了，这里主要介绍一下返回Map，list，String
 * Created by caojx on 16-12-30.
 */
@Controller
@RequestMapping("/data2")
public class DataController2 {

    /**
     * 方式一，返回Map
     * Description:将接受到的参数通过Map方式返回,返回这种数据类型需要添加@ResponseBody，说明这是一个响应体。
     * @return Map
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public Map<String,Object> addUser(@RequestParam(value = "userName",required = true) String userName,
                                      @RequestParam(value = "age",required = true)int age){
        System.out.println("-------addUser 接收的参数--userName:"+userName+"--age:"+age);
        //将接受的参数返回到用户管理页面
       Map<String,Object> map = new HashMap<String,Object>();
       map.put("userName",userName);
       map.put("age",age);
       return map;
    }

    /**
     * 方式二，返回List
     * Description:将接受到的参数通过list方式返回,返回这种数据类型需要添加@ResponseBody，说明这是一个响应体。
     * @return List
     */
    @RequestMapping("/addUser2")
    @ResponseBody
    public List<User> addUser2(@RequestParam(value = "userName",required = true) String userName,
                         @RequestParam(value = "age",required = true)int age){
        System.out.println("-------addUser 接收的参数--userName:"+userName+"--age:"+age);
        //将接受的参数返回到用户管理页面
        List<User> users = new ArrayList<User>();
        User user = new User();
        user.setUserName(userName);
        user.setAge(age);
        users.add(user);
        return users;
    }

    /**
     * 方式三，使用jackson，返回jsonString
     * Description:将接受到的参数通过jsonString方式返回,返回这种数据类型需要添加@ResponseBody，说明这是一个响应体。
     * 需要新增3个jar包支持
     * jackson-annotationsxxx.jar
     * jackson-corexxx.jar
     * jackson-databindxxx.jar
     *
     * json字符串中有中文的时候，容易出现乱码问题，可以在@RequestMapping中添加produces = "application/json;charset=UTF-8"对编码进行处理，
     * 也可时在springmvc配置文件中使用拦截器的方式实现，我个人比较喜欢拦截器的方式，使用拦截器的方式，就不用每次都在@RequestMapping中写produces。
     * @return List
     */
    @RequestMapping(value = "/addUser3",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String addUser3(@RequestParam(value = "userName",required = true) String userName,
                               @RequestParam(value = "age",required = true)int age) throws JsonProcessingException {
        System.out.println("-------addUser 接收的参数--userName:"+userName+"--age:"+age);
        //将接受的参数返回到用户管理页面
        User user = new User();
        user.setUserName(userName);
        user.setAge(age);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        return jsonString;
    }

    /**
     * Description:直接返回视图路径，返回类型可有为String，返回的数据可以放置到httpServletRequest中
     * @return String
     */
    @RequestMapping("/toUser")
    public String toUer(){
        return "/jsp/addUser";
    }

}
