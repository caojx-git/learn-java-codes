package edu.xnxy.caojx.filemanager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Description: 注销
 *
 * Created by caojx on 17-4-28.
 */
@Controller
@RequestMapping("/filter/user")
public class LogOutController {

    /**
     * Description:清理用户信息
     * @param session
     * @return
     */
    @RequestMapping("/logout.do")
    public String logOut(HttpSession session){
        session.removeAttribute("userInfo");
        return "login";
    }
}
