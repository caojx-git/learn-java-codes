package com.learn.controller;

import com.learn.entity.User;
import com.learn.service.IUserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * Description:springmvc+spring+hibernate
 * Created by caojx on 17-1-17.
 */
@Controller
@RequestMapping("/user3")
public class UserController3 {

    @Resource(name = "userManager")
    private IUserManager userManager;

    /**
     * Description:调转到添加用户界面
     *
     * @return
     */
    @RequestMapping("/toAddUser3")
    public String toAddUser() {
        return "/jsp/addUser3";
    }

    /**
     * 添加用户
     */
    @RequestMapping("/addUser3")
    public String addUser(User user) {
        userManager.addUser(user);
        //return "/jsp/success3";
        return "redirect:/user3/getAllUser";//springmvc url重定向，中方式不太提倡，只是说明有这么一种用法
    }

    /**
     * 查询用户
     */
    @RequestMapping("/getAllUser")
    public String getAllUser(HttpServletRequest httpServletRequest) {
        List<User> users = userManager.getAllUser();
        httpServletRequest.setAttribute("users", users);
        return "/jsp/userManager3";
    }

    /**
     * 删除用户
     */
    @RequestMapping("/delUser3")
    public void delUser(String id, HttpServletResponse httpServletResponse) {
        String result = "{\"result\":\"error\"}";
        if (userManager.delUser(id)) {
            result = "{\"result\":\"success\"}";
        }
        PrintWriter out = null;
        httpServletResponse.setContentType("application/json"); //这里说明了返回数据的类型是json类型，返回其他类型前台会接受不到
        try {
            out = httpServletResponse.getWriter();
            out.write(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    /**
     * 查询单个实体,并将数据返回到编辑页面
     */
    @RequestMapping("/getUser3")
    public String getUser(String id, HttpServletRequest httpServletRequest) {
        User user = userManager.getUser(id);
        httpServletRequest.setAttribute("user", user);
        return "/jsp/editUser";
    }

    /**
     * 更新用户信息
     */
    @RequestMapping("/updateUser3")
    public String updateUser(User user, HttpServletRequest httpServletRequest) {
        if (userManager.updateUser(user)) {
            user = userManager.getUser(user.getId());
            httpServletRequest.setAttribute("user", user);
            return "/jsp/editUser";
        } else {
            return "/jsp/error";
        }
    }

}
