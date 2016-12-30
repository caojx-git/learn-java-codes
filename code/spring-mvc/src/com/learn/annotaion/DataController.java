package com.learn.annotaion;

import com.learn.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:springmvc中参数的传递,接收页面传递到Controller中的参数
 * 方式一：前台页面中的参数名与方法中的参数名一致，springmvc就自动将参数值注入进来
 * 方式二：通过实体类注入进来，实体类中的属性名与前台页面中保持一致且实体中提供了getter，setter方法，springmvc就可以将参数注入到实体中
 * Created by caojx on 16-12-30.
 */
@Controller
@RequestMapping("/data")
public class DataController {

    /**
     * Description:添加用户方法，直接返回试图
     * 前台页面中的参数名与方法中的参数名一致，springmvc就自动将参数值注入进来
     * @return String
     */
    @RequestMapping("/addUser")
    public String addUser(String userName,String age,HttpServletRequest httpServletRequest){
        System.out.println("-------addUser 接收的参数--userName:"+userName+"--age:"+age);
        //将接受的参数返回到用户管理页面
        httpServletRequest.setAttribute("userName",userName);
        httpServletRequest.setAttribute("age",age);
        return "/jsp/userManager";
    }

    /**
     * Description:添加用户方法，直接返回试图
     * 通过实体类注入进来，实体类中的属性名与前台页面中保持一致且实体中提供了getter，setter方法，springmvc就可以将参数注入到实体中
     * @return String
     */
    @RequestMapping("/addUser2")
    public String addUser2(User user, HttpServletRequest httpServletRequest){
        System.out.println("-------addUser 接收的参数--userName:"+user.getUserName()+"--age:"+user.getAge());
        //将接受的参数返回到用户管理页面
        httpServletRequest.setAttribute("userName",user.getUserName());
        httpServletRequest.setAttribute("age",user.getAge());
        return "/jsp/userManager";
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
