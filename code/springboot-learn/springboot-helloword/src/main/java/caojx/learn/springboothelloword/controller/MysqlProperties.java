package caojx.learn.springboothelloword.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Mysql属性配置文件
 * 将上面application.properties文件中的mysql配置注入到该实体类，注意这里的prefix指定的是mysql，对应
 * 配置文件中的结构
 * @author caojx
 * Created on 2018/3/22 下午下午5:00
 */
@Component
@ConfigurationProperties(prefix="mysql")
 class MysqlProperties {

    private String jdbcName;

    private String dbUrl;

    private String userName;

    private String password;

    public String getJdbcName() {
        return jdbcName;
    }

    public void setJdbcName(String jdbcName) {
        this.jdbcName = jdbcName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
