package caojx.learn.dao;

import caojx.learn.entity.UserInfo;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: 用户信息维护DAO接口
 *
 * @author caojx
 * Created by caojx on 2017年04月10 下午10:49:49
 */
@Repository("userInfoDAO")
public interface IUserInfoDAO extends IBaseDAO<UserInfo> {
    
    /**
     * 查询多条记录
     *
     * @param userInfo
     * @return
     */
    public List<UserInfo> query2(@Param("entity") UserInfo userInfo);
    
}
