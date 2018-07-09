package caojx.learn.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 用户基础信息实体类
 *
 * @author caojx
 *         Created by caojx on 2017年04月10 下午9:07:07
 */
public class UserInfo implements Serializable {

    /**
     * 用户编号
     */
    private Long id;

    /**
     * 用户名称
     */
    private String name;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
