package edu.xnxy.caojx.filemanager.dao;

import edu.xnxy.caojx.filemanager.entity.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * Description: 用户信息维护DAO接口
 *
 * @author caojx
 * Created by caojx on 2017年04月10 下午10:49:49
 */
@Repository("userInfoDAO")
public interface IUserInfoDAO extends IBaseDAO<UserInfo> {

}
