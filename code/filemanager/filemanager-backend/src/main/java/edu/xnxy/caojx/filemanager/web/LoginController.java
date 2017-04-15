package edu.xnxy.caojx.filemanager.web;

import edu.xnxy.caojx.filemanager.service.IUserInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Description: 用于登录控制器
 *
 * @author caojx
 * Created by caojx on 2017年04月10 下午9:04:04
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger log = Logger.getLogger(LoginController.class);

    @Resource
    private IUserInfoService userInfoService;

    @RequestMapping("/loginPage")
    public String showLoginPage(){
        return "/login";
    }

}
