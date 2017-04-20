package edu.xnxy.caojx.filemanager.web;

import edu.xnxy.caojx.filemanager.entity.FileManagerSysBaseType;
import edu.xnxy.caojx.filemanager.entity.UserInfo;
import edu.xnxy.caojx.filemanager.service.IFileManagerSysBaseTypeService;
import edu.xnxy.caojx.filemanager.service.IUserInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 用户管理Controller
 *
 * @author caojx
 * Created by caojx on 2017年04月20 上午9:59:59
 */
@Controller
@RequestMapping("/userManager")
public class UserManagerController {

    private static final Logger log = Logger.getLogger(UserManagerController.class);

    @Resource
    private IFileManagerSysBaseTypeService fileManagerSysBaseTypeService;

    @Resource
    private IUserInfoService userInfoService;

    @RequestMapping("/userManagerPage.do")
    public String showUserManagerPage(HttpServletRequest request){
        List<FileManagerSysBaseType> collegeList = fileManagerSysBaseTypeService.listFileManagerSysBaseType(1002L,null);
        request.setAttribute("collegeList",collegeList);
        return "userManager";
    }

    @RequestMapping("/addUserPage.do")
    public String showAddUserPage(HttpServletRequest request){
        try {
            List<FileManagerSysBaseType> collegeList = fileManagerSysBaseTypeService.listFileManagerSysBaseType(1002L,null);
            List<UserInfo> userInfoList = userInfoService.listUserInfo(new UserInfo());
            request.setAttribute("userInfoList",userInfoList);
            request.setAttribute("collegeList",collegeList);
        } catch (Exception e) {
           log.error("获取用户信息出错",e);
        }
        return "addUser";
    }

    @RequestMapping("/addUser.do")
    @ResponseBody
    public Map<String,Object> addUser(UserInfo userInfo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            userInfoService.saveUserInfo(userInfo);
            resultMap.put("status",0);
            resultMap.put("message","注册成功");
        } catch (Exception e) {
            log.error("注册失败", e);
            resultMap.put("status",1);
            resultMap.put("message",e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/userInfoList.do")
    public String listUserInfo(UserInfo userInfo,HttpServletRequest request){
        try {
            List<FileManagerSysBaseType> collegeList = fileManagerSysBaseTypeService.listFileManagerSysBaseType(1002L,null);
            List<UserInfo> userInfoList = userInfoService.listUserInfo(userInfo);
            request.setAttribute("userInfoList",userInfoList);
            request.setAttribute("collegeList",collegeList);
        } catch (Exception e) {
            log.error("查询失败", e);
        }
        return "userManager";
    }

    @RequestMapping("/editUserInfo.do")
    public String editUserInfo(UserInfo userInfo,HttpServletRequest request){
        try {
            List<FileManagerSysBaseType> collegeList = fileManagerSysBaseTypeService.listFileManagerSysBaseType(1002L,null);
            UserInfo userInfo1= userInfoService.getUserInfo(userInfo);
            request.setAttribute("collegeList",collegeList);
            request.setAttribute("userInfo1",userInfo1);
        } catch (Exception e) {
            log.error("查询用户信息失败", e);
        }
        return "editUser";
    }

    @RequestMapping("/saveUserInfo.do")
    public String saveUserInfo(UserInfo userInfo,HttpServletRequest request){
        try {
            userInfoService.updateUserInfo(userInfo);
            request.setAttribute("userInfo1",userInfo);
        } catch (Exception e) {
            log.error("保存用户信息失败", e);
        }
        return "editUser";
    }

}
