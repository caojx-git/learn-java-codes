package edu.xnxy.caojx.filemanager.web;

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
import java.io.IOException;
import java.util.HashMap;
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

    @Resource
    private IFileManagerSysBaseTypeService fileManagerSysBaseTypeService;

    @RequestMapping("/loginPage.do")
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping("/indexPage.do")
    public String showIndexPage() {
        return "index";
    }

    @RequestMapping("/login.do")
    public ModelAndView login(@RequestParam("userId") String userId, @RequestParam("userPassword") String userPassword,HttpServletRequest request) {
        String path = "index";
        Map resultMap = new HashMap<String, Object>();
        try {
            UserInfo userInfo = userInfoService.login(Long.valueOf(userId.trim()), userPassword);
            request.getSession().setAttribute("userInfo",userInfo);
            resultMap.put("status", "0");
            resultMap.put("userInfo", userInfo);
        } catch (Exception e) {
            path = "login";
            resultMap.put("status", "1");
            resultMap.put("message", e.getMessage());
            log.error("获取下拉框数据出错", e);
        }
        return new ModelAndView(path, resultMap);
    }

    @RequestMapping("/getSysBaseType")
    @ResponseBody
    public Map<String, Object> getSysBaseType(@RequestParam("codeType") String codeType, @RequestParam("codeId") String codeId, HttpServletRequest httpServletRequest) {
        Map<String, Object> resultMap = null;
        try {
            resultMap = new HashMap<String, Object>();
            resultMap.put("result", fileManagerSysBaseTypeService.listFileManagerSysBaseType(Long.valueOf(codeType.trim()), Long.valueOf(codeId)));
            resultMap.put("status", "0");
        } catch (Exception e) {
            resultMap.put("status", "1");
            resultMap.put("message", "获取下拉框数据出错");
            log.error("获取下拉框数据出错", e);
        }
        httpServletRequest.getSession().setAttribute("map", resultMap);
        return resultMap;
    }

}
