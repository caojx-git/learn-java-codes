package edu.xnxy.caojx.filemanager.service;

import edu.xnxy.caojx.filemanager.entity.UserInfo;

import java.util.concurrent.ExecutionException;

/**
 * Description: 用户信息维护业务接口
 *
 * @author caojx
 * Created by caojx on 2017年04月11 下午9:24:24
 */
public interface IUserInfoService {

    /**
     * 用户登录
     * @param userId
     * @param password
     * @return
     * @throws Exception
     */
    public UserInfo login(Long userId, String password) throws Exception;

    /**
     * 查询用户
     * @param userInfo
     * @return
     * @throws Exception
     */
    public UserInfo getUserInfo(UserInfo userInfo) throws Exception;

    /**
     * 新增用户
     * @param userInfo
     * @return
     * @throws Exception
     */
    public void saveUserInfo(UserInfo userInfo) throws Exception;

    /**
     * 更新用户信息
     * @param userInfo
     * @throws Exception
     */
    public void updateUserInfo(UserInfo userInfo) throws Exception;


}

