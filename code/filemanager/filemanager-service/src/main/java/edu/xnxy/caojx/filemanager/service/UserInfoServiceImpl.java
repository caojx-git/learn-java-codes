package edu.xnxy.caojx.filemanager.service;

import edu.xnxy.caojx.filemanager.dao.IUserInfoDAO;
import edu.xnxy.caojx.filemanager.entity.FileManagerSysBaseType;
import edu.xnxy.caojx.filemanager.entity.UserInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 用户信息维护u业务实现类
 *
 * @author caojx
 * Created by caojx on 2017年04月11 下午10:04:04
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {

    private static final Logger log = Logger.getLogger(UserInfoServiceImpl.class);

    @Resource
    private IUserInfoDAO userInfoDAO;

    /**
     * 用户登录
     * @param userId
     * @param password
     * @return
     * @throws Exception
     */
    public UserInfo login(Long userId, String password) throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        UserInfo userInfo1 = userInfoDAO.get(userInfo);
        if(userInfo1 != null){
            if(password.equals(userInfo1.getUserPassword())){
                return userInfo1;
            }else {
                log.error(userId+"用户密码错误");
                throw new RuntimeException(userId+"用户密码错误");
            }
        }else {
            log.error(userId+"用户不存在");
            throw new RuntimeException(userId+"用户不存在");
        }
    }

    /**
     * 查询用户
     * @param userInfo
     * @return
     * @throws Exception
     */
    public UserInfo getUserInfo(UserInfo userInfo) throws Exception {
        UserInfo userInfo1 = null;
        try{
            userInfo1 = userInfoDAO.get(userInfo);
        }catch (Exception e){
            log.error("获取用户信息失败",e);
            throw new RuntimeException("获取用户信息失败",e);
        }
        return userInfo1;
    }

    /**
     * 新增用户
     * @param userInfo
     * @return
     * @throws Exception
     */
    public Map<String,Object> saveUserInfo(UserInfo userInfo) throws Exception {
        UserInfo userInfo1 = null;
        Map<String,Object> resultMap = null;
        try {
            resultMap = new HashMap<String, Object>();
            userInfo1 = userInfoDAO.get(userInfo);
            if(userInfo1 == null){
                userInfoDAO.insert(userInfo);
                resultMap.put("status","0");
                resultMap.put("msg","注册成功");
            }else {
                resultMap.put("status","1");
                resultMap.put("msg","该用户已经存在");
            }
        } catch (Exception e) {
            log.error("新增用户失败",e);
            throw new RuntimeException("新增用户失败",e);
        }
        return resultMap;
    }

    /**
     * 更新用户信息
     * @param userInfo
     * @throws Exception
     */
    public void updateUserInfo(UserInfo userInfo) throws Exception {
        try{
            userInfoDAO.update(userInfo);
        }catch (Exception e){
            log.error("更新用户信息失败",e);
            throw new RuntimeException("更新用户信息失败",e);
        }
    }

}
