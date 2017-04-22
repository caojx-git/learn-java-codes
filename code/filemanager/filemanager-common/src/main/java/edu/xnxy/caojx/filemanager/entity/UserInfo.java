package edu.xnxy.caojx.filemanager.entity;

import java.io.Serializable;
import java.sql.Types;
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
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户性别
     */
    private Integer userGender;

    /**
     * 用户年龄
     */
    private Integer userAge;

    /**
     * 学院
     */
    private Long collegeId;

    /**
     * 是否是管理员 1是，0否
     */
    private Integer manager;

    /**
     * 管理员权限 1超级管理员，2普通管理员
     */
    private Integer managerType;

    /**
     * 住址
     */
    private String userAddress;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 联系方式，手机号
     */
    private String userPhoneNumber;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 备注
     */
    private String notes;

    /**
     * 扩展字段1
     */
    private Integer ext1;

    /**
     * 扩展字段2
     */
    private String ext2;

    /**
     * 扩展字段3
     */
    private String ext3;

    /**
     * 是否有效
     */
    private Integer recStatus;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getUserGender() {
        return userGender;
    }

    public void setUserGender(Integer userGender) {
        this.userGender = userGender;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    public Integer getManagerType() {
        return managerType;
    }

    public void setManagerType(Integer managerType) {
        this.managerType = managerType;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getExt1() {
        return ext1;
    }

    public void setExt1(Integer ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public Integer getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(Integer recStatus) {
        this.recStatus = recStatus;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userGender=" + userGender +
                ", userAge=" + userAge +
                ", collegeId=" + collegeId +
                ", manager=" + manager +
                ", managerType=" + managerType +
                ", userAddress='" + userAddress + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", createDate=" + createDate +
                ", ext1=" + ext1 +
                ", ext2='" + ext2 + '\'' +
                ", ext3='" + ext3 + '\'' +
                ", recStatus=" + recStatus +
                '}';
    }
}
