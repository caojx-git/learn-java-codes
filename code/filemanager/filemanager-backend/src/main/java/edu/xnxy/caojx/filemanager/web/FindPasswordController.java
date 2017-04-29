package edu.xnxy.caojx.filemanager.web;

import edu.xnxy.caojx.filemanager.entity.UserInfo;
import edu.xnxy.caojx.filemanager.service.IUserInfoService;
import edu.xnxy.caojx.filemanager.web.util.MD5;
import edu.xnxy.caojx.filemanager.web.util.MailUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Description: 找回密码控制器
 * <p>
 * Created by caojx on 17-4-29.
 */
@Controller
@RequestMapping("/findPassword")
public class FindPasswordController {

    private static Logger log = Logger.getLogger(FindPasswordController.class);

    @Resource
    private IUserInfoService userInfoService;

    /**
     * Description: 跳转到邮箱显示页面，并返回用户信息
     *
     * @param userInfo
     * @return
     */
    @RequestMapping("/viewMailPage.do")
    public ModelAndView showViewMailPage(UserInfo userInfo) {
        Map<String, Object> resultMap = null;
        try {
            resultMap = new HashMap<String, Object>();
            UserInfo userInfo1 = userInfoService.getUserInfo(userInfo);
            if(userInfo1 != null){
                resultMap.put("status", 0);
                resultMap.put("userInfo", userInfo1);
            }else {
                resultMap.put("status", 1);
                resultMap.put("message", "用户不存在");
            }
        } catch (Exception e) {
            resultMap.put("status", 1);
            resultMap.put("message", "获取用户信息失败");
            log.error("获取用户信息失败", e);
        }
        return new ModelAndView("viewEmail", resultMap);
    }

    /**
     * Description: 发送邮件
     *
     * @param userInfo
     * @param request
     * @return
     */
    @RequestMapping("/sendMail.do")
    @ResponseBody
    public Map<String, Object> sendMail(UserInfo userInfo, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            //生成密钥
            String secretKey = UUID.randomUUID().toString();
            //设置过期时间
            Date outDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);// 30分钟后过期
            long date = outDate.getTime() / 1000 * 1000;// 忽略毫秒数  mySql 取出时间是忽略毫秒数的

            //更新用户的表中的过期时间、密钥信息
            try {
                userInfo.setOutDate(date);
                userInfo.setValidataCode(secretKey);
                userInfoService.updateUserInfo(userInfo);
            } catch (Exception e) {
                log.error("更新用户信息失败", e);
                throw new Exception("更新用户信息失败", e);
            }

            //将用编号、过期时间、密钥生成链接密钥
            String key = userInfo.getUserId() + "$" + date + "$" + secretKey;

            String digitalSignature = MD5.getMD5Str(key);// 数字签名

            String path = request.getContextPath();

            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;

            String resetPassHref = basePath + "/findPassword/findPassword.do?sid=" + digitalSignature + "&userId=" + userInfo.getUserId();

            String emailContent = "请勿回复本邮件.点击下面的链接,重设密码,本邮件超过30分钟,链接将会失效，需要重新申请找回密码." + resetPassHref;

            //发送邮件
            MailUtil.sendMail(emailContent, userInfo.getUserEmail());
            resultMap.put("status", 0);
            resultMap.put("message", "邮件已发送致邮箱:" + userInfo.getUserEmail() + "请登录邮箱找回密码");
        } catch (Exception e) {
            resultMap.put("status", 1);
            resultMap.put("message", "服务出错了");
            log.error("邮件发送失败", e);
        }
        return resultMap;
    }

    /**
     * Description: 验证连接是否有效
     * @param userId
     * @param sid
     */
    @RequestMapping("/findPassword.do")
    public ModelAndView findPassword(Long userId,String sid){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            //判断连接是否正确
            if(userId == null  || sid == null){
                log.warn("修改密码链接无效");
                resultMap.put("status",0);
                resultMap.put("message","修改密码链接无效");
            }
            //通过userId查询用户
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            UserInfo userInfo1 = userInfoService.getUserInfo(userInfo);
            //获取当前用户申请找回密码的过期时间
            //找回密码链接已经过期
            if(userInfo1 != null){
                if(userInfo1.getOutDate() < System.currentTimeMillis()){
                    log.warn("连接已经失效");
                    resultMap.put("status",0);
                    resultMap.put("message","连接已经失效");
                }
                //获取用户的数字签名
                String key = userInfo1.getUserId()+"$"+userInfo1.getOutDate()+"$"+userInfo1.getValidataCode();
                String digitalSignature = MD5.getMD5Str(key);//生成数字签名
                if(!digitalSignature.equals(sid)){
                    log.warn("数字签名不正确");
                    resultMap.put("status",0);
                    resultMap.put("message","数字签名不正确");
                }else {//跳转到修改密码页面
                    resultMap.put("userInfo",userInfo1);
                    return new ModelAndView("findPassword",resultMap);
                }
            }
        }catch (Exception e){
            log.error("连接不能识别",e);
            resultMap.put("status",0);
            resultMap.put("message","连接不能识别");
        }
        return new ModelAndView("login",resultMap);
    }

    /**
     * Description: 找回密码
     * @param userId 用户编号
     * @param newPassword 新密码
     * @return
     */
    @RequestMapping("/updatePassword.do")
    @ResponseBody
    public Map<String,Object> updatePassword(Long userId, String newPassword){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            //生成密钥
            String secretKey = UUID.randomUUID().toString();
            userInfo.setValidataCode(secretKey);
            userInfo.setUserPassword(MD5.getMD5Str(newPassword.toString()));
            userInfoService.updateUserInfo(userInfo);
            resultMap.put("status",0);
        }catch (Exception e){
            resultMap.put("status",1);
            resultMap.put("message","密码修改失败");
        }
        return resultMap;
    }
}
