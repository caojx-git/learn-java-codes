package com.learn.annotaion;

import com.learn.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * Description:springmvc中参数的传递,接收页面传递到Controller中的参数
 * 方式一：前台页面中的参数名与方法中的参数名一致，springmvc就自动将参数值注入进来
 * 方式二：当前台页面的参数名与后台的方法参数不一致时，使用@RequestParam注解，绑定参数
 * 方式三：通过实体类注入进来，实体类中的属性名与前台页面中保持一致且实体中提供了getter，setter方法，springmvc就可以将参数注入到实体中
 * Created by caojx on 16-12-30.
 */
@Controller
@RequestMapping("/data")
public class DataController {

    /**
     * 方式一
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
     * 方式二
     * Description:添加用户的方法，
     * 前台页面和后台方法参数名不一致，使用@RequestParam注解绑定参数，这种方式相比第一种方式更为常用
     * required的默认值为true，表示必须的参数
     * @param name 用户名
     * @param age 年龄
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/addUser2")
    public String addUser2(@RequestParam(value = "userName",required = true) String name,
                           @RequestParam(value = "age",required = true) int age,
                           HttpServletRequest httpServletRequest){
        System.out.println("-------addUser 接收的参数--name:"+name+"--age:"+name);
        //将接受的参数返回到用户管理页面
        httpServletRequest.setAttribute("userName",name);
        httpServletRequest.setAttribute("age",age);
        return "/jsp/userManager";
    }

    /**
     * 方式三
     * Description:添加用户方法，直接返回试图
     * 通过实体类注入进来，实体类中的属性名与前台页面中保持一致且实体中提供了getter，setter方法，springmvc就可以将参数注入到实体中
     * @return String
     */
    @RequestMapping("/addUser3")
    public String addUser3(User user, HttpServletRequest httpServletRequest){
        System.out.println("-------addUser 接收的参数--userName:"+user.getUserName()+"--age:"+user.getAge());
        //将接受的参数返回到用户管理页面
        httpServletRequest.setAttribute("userName",user.getUserName());
        httpServletRequest.setAttribute("age",user.getAge());
        return "/jsp/userManager";
    }


    /**
     * Description:添加用户方法，使用json方式
     * 通过实体类注入进来，实体类中的属性名与前台页面中保持一致且实体中提供了getter，setter方法，springmvc就可以将参数注入到实体中
     * 前台页面使用json方式传递数据
     * @return String
     */
    @RequestMapping("/addUserJson")
    public String addJson(User user, HttpServletResponse httpServletResponse){
        System.out.println("-------addUserJson 接收的参数--userName:"+user.getUserName()+"--age:"+user.getAge());
        //将接受到参数返回给json页面,其实返回json数据常用的写法使用JSONObject和JSONArray或者是将阿从看json
        String result = "{\"userName\":\" "+ user.getUserName() +" \",\"age\":\" "+ user.getAge()+" \"}";
        httpServletResponse.setContentType("application/json");
        PrintWriter out = null;
        try{
            out = httpServletResponse.getWriter();
            out.print(result);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "/jsp/json";
    }


    /**
     * Description:直接返回视图路径，返回类型可有为String，返回的数据可以放置到httpServletRequest中
     * @return String
     */
    @RequestMapping("/toUser")
    public String toUer(){
        //return "/jsp/addUser";
        return "/jsp/json";
    }

}
