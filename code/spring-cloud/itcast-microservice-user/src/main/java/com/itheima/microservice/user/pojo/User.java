package com.itheima.microservice.user.pojo;

import java.util.Date;

/**
 * Created by Administrator on 2017/12/10.
 */
public class User {
    private String id;

    private String username;

    private String password;

    private String email;

    private Date birthday;

    private String sex;

    private int state;

    private String code;

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getSex() {
        return sex;
    }

    public int getState() {
        return state;
    }

    public String getCode() {
        return code;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public User(String id, String username, String password, String email, Date birthday, String sex, Integer state, String code) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.sex = sex;
        this.state = state;
        this.code = code;
    }

    public User() {
    }
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", state=" + state +
                ", code='" + code + '\'' +
                '}';
    }
}
