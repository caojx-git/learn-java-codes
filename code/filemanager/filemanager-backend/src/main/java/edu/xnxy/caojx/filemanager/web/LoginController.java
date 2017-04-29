package edu.xnxy.caojx.filemanager.web;

import edu.xnxy.caojx.filemanager.entity.FileManagerSysBaseType;
import edu.xnxy.caojx.filemanager.entity.UserInfo;
import edu.xnxy.caojx.filemanager.service.IFileManagerSysBaseTypeService;
import edu.xnxy.caojx.filemanager.service.IUserInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 用于登录控制器
 *
 * @author caojx
 *         Created by caojx on 2017年04月10 下午9:04:04
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    private static final Logger log = Logger.getLogger(LoginController.class);

    @Resource
    private IUserInfoService userInfoService;

    @RequestMapping("/loginPage.do")
    public String showLoginPage() {
        return "login";
    }

    /**
     * Description:用户登录
     *
     * @param userId       用户编号
     * @param userPassword 用户密码
     * @param request
     * @return
     */
    @RequestMapping("/login.do")
    @ResponseBody
    public Map<String, Object> login(Long userId, String userPassword, HttpServletRequest request) {
        Map resultMap = new HashMap<String, Object>();
        try {
            if (userId != null && userPassword != null && !"".equals(userPassword)) {
                UserInfo userInfo = userInfoService.login(userId, userPassword);
                if (userInfo != null) {
                    resultMap.put("status", 0);
                    resultMap.put("userInfo", userInfo);
                    request.getSession().setAttribute("userInfo", userInfo);
                } else {
                    resultMap.put("status", 1);
                    resultMap.put("message", "用户不存在");
                }
            }
        } catch (Exception e) {
            log.error("登录失败", e);
            resultMap.put("status", 1);
            resultMap.put("message", "用户非法或密码错误");
        }
        return resultMap;
    }
}
