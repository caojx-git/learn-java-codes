package com.learn.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Description: 用户实体类
 * Created by caojx on 16-12-30.
 */
@Entity//使用javax中的注解，扩展性更强
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid") //注解生成策略
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;
    @Column(length = 25)
    private String userName;
    @Column(length = 24)
    private int age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
