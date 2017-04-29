package edu.xnxy.caojx.filemanager.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.xnxy.caojx.filemanager.util.CustomDateSerializer;

import java.util.Date;

/**
 * Description:
 *
 * @author caojx
 *         Created by caojx on 2017年04月24 下午12:22:22
 */
public class FileInfo {

    /**
     * 文件编号
     */
    private Long fileId;

    /**
     * 上传者编号
     */
    private Long userId;

    /**
     * 学院编号
     */
    private Long collegeId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径编码
     */
    private Integer pathCode;

    /**
     * 文件类型
     */
    private Integer fileType;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomDateSerializer.class)
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

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getPathCode() {
        return pathCode;
    }

    public void setPathCode(Integer pathCode) {
        this.pathCode = pathCode;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
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
        return "FileInfo{" +
                "fileId=" + fileId +
                ", userId=" + userId +
                ", fileName='" + fileName + '\'' +
                ", pathCode=" + pathCode +
                ", fileType=" + fileType +
                ", createDate=" + createDate +
                ", notes='" + notes + '\'' +
                ", ext1=" + ext1 +
                ", ext2='" + ext2 + '\'' +
                ", ext3='" + ext3 + '\'' +
                ", recStatus=" + recStatus +
                '}';
    }
}
