package edu.xnxy.caojx.filemanager.service;

import edu.xnxy.caojx.filemanager.dao.IUserInfoDAO;
import edu.xnxy.caojx.filemanager.entity.UserInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 用户信息维护u业务实现类
 *
 * @author caojx
 *         Created by caojx on 2017年04月11 下午10:04:04
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements IUserInfoService {

    private static final Logger log = Logger.getLogger(UserInfoServiceImpl.class);

    @Resource
    private IUserInfoDAO userInfoDAO;

    /**
     * 用户登录
     *
     * @param userId
     * @param userPassword
     * @return
     * @throws Exception
     */
    public UserInfo login(Long userId, String userPassword) throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        UserInfo userInfo1 = userInfoDAO.get(userInfo);
        if (userInfo1 != null) {
            if (userPassword != null && userPassword.equals(userInfo1.getUserPassword())) {
                return userInfo1;
            } else {
                log.error(userId + "用户密码错误");
                throw new RuntimeException("用户密码错误");
            }
        } else {
            log.error(userId + "用户不存在");
            throw new RuntimeException("用户不存在");
        }
    }

    /**
     * 查询用户
     *
     * @param userInfo
     * @return
     * @throws Exception
     */
    public UserInfo getUserInfo(UserInfo userInfo) throws Exception {
        UserInfo userInfo1 = null;
        try {
            userInfo1 = userInfoDAO.get(userInfo);
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            throw new RuntimeException("获取用户信息失败", e);
        }
        return userInfo1;
    }

    /**
     * 查询用户信息
     *
     * @param userInfo
     * @return
     * @throws Exception
     */
    @Override
    public List<UserInfo> listUserInfo(UserInfo userInfo) throws Exception {
        List<UserInfo> userInfoList = null;
        try {
            userInfoList = userInfoDAO.query(userInfo);
        } catch (Exception e) {
            log.error("查询用户信息失败", e);
            throw new RuntimeException("查询用户信息失败", e);
        }
        return userInfoList;
    }


    /**
     * 新增用户
     *
     * @param userInfo
     * @return
     * @throws Exception
     */
    public void saveUserInfo(UserInfo userInfo) throws Exception {
        UserInfo userInfo1 = null;
        try {
            userInfo1 = userInfoDAO.get(userInfo);
            if (userInfo1 == null) {
                userInfo.setCreateDate(new Date());
                userInfo.setRecStatus(1);
                userInfoDAO.insert(userInfo);
            } else {
                throw new RuntimeException("该用户已注册");
            }
        } catch (Exception e) {
            log.error("新增用户失败", e);
            throw new RuntimeException("新增用户失败", e);
        }
    }

    /**
     * 更新用户信息
     *
     * @param userInfo
     * @throws Exception
     */
    public void updateUserInfo(UserInfo userInfo) throws Exception {
        try {
            userInfoDAO.update(userInfo);
        } catch (Exception e) {
            log.error("更新用户信息失败", e);
            throw new RuntimeException("更新用户信息失败", e);
        }
    }

}
