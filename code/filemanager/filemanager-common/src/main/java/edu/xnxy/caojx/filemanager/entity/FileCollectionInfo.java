package edu.xnxy.caojx.filemanager.entity;

import java.util.Date;

/**
 * Description: 文件收藏实体类
 *
 * @author caojx
 *         Created by caojx on 2017年04月24 下午12:22:22
 */
public class FileCollectionInfo {

    /**
     * 文件收藏者编号
     */
    private Long userId;

    /**
     * 文件上传者名字
     */
    private String uploader;

    /**
     * 文件编号
     */
    private Long fileId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件所属学院
     */
    private Long collegeId;

    /**
     * 记录创建时间
     */
    private Date createDate;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    @Override
    public String toString() {
        return "FileCollectionInfo{" +
                "userId=" + userId +
                ", uploader='" + uploader + '\'' +
                ", fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", collegeId=" + collegeId +
                ", createDate=" + createDate +
                ", ext1=" + ext1 +
                ", ext2='" + ext2 + '\'' +
                ", ext3='" + ext3 + '\'' +
                '}';
    }
}
