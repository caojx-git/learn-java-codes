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
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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

    @Resource
    private IFileManagerSysBaseTypeService fileManagerSysBaseTypeService;

    @RequestMapping("/loginPage")
    public String showLoginPage(){
        return "/login";
    }

    @RequestMapping("/registerPage")
    public String  showRegisterPage(){
        return "/register";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(@RequestParam("userName") String userId, @RequestParam("password") String password ){
        Map<String,Object> resultMap = null;
        UserInfo userInfo = null;
        try {
           userInfo =  userInfoService.login(Long.valueOf(userId),password);
           resultMap.put("result",userInfo);
           resultMap.put("status","0");
           resultMap.put("message","登陆成功");
        }catch (Exception e){
            resultMap.put("status", "1");
            resultMap.put("message","获取下拉框数据出错");
            log.error("获取下拉框数据出错",e);
        }
        return resultMap;
    }

    @RequestMapping("/getSysBaseType")
    @ResponseBody
    public Map<String, Object> getSysBaseType(@RequestParam("codeType") String codeType, @RequestParam("codeId") String codeId, HttpServletRequest httpServletRequest){
        Map<String,Object> resultMap = null;
        try {
            resultMap = new HashMap<String,Object>();
            resultMap.put("result",fileManagerSysBaseTypeService.listFileManagerSysBaseType(codeType,codeId));
            resultMap.put("status", "0");
        } catch (Exception e) {
            resultMap.put("status", "1");
            resultMap.put("message","获取下拉框数据出错");
            log.error("获取下拉框数据出错",e);
        }
        httpServletRequest.getSession().setAttribute("map",resultMap);
        return resultMap;
    }

    @RequestMapping("/register")
    @ResponseBody
    public Map<String, Object> register(UserInfo userInfo){
        Map<String,Object> resultMap = null;
        try{
            Map<String,Object> map = userInfoService.saveUserInfo(userInfo);
            if("1".equals(map.get("status"))){
               resultMap.put("message",map.get("msg"));
            }
            resultMap.put("status", "0");
            resultMap.put("message", "注册成功");
        }catch (Exception e){
            resultMap.put("status", "1");
            resultMap.put("message","注册失败");
            log.error("注册失败",e);
        }
        return resultMap;
    }
}
