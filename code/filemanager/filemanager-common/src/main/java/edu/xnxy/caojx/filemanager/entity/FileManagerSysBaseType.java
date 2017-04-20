package edu.xnxy.caojx.filemanager.entity;

import java.io.Serializable;

/**
 * Description: 文件管理系统常量
 *
 * @author caojx
 * Created by caojx on 2017年04月10 下午10:33:33
 */
public class FileManagerSysBaseType implements Serializable {

    /**
     * 类型编号
     */
    private Long codeType;

    /**
     * 编码
     */
    private Long codeId;

    /**
     * 名称
     */
    private String codeName;

    /**
     * 注释
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

    public Long getCodeType() {
        return codeType;
    }

    public void setCodeType(Long codeType) {
        this.codeType = codeType;
    }

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
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

    @Override
    public String toString() {
        return "FileManagerSysBaseType{" +
                "codeType=" + codeType +
                ", codeId=" + codeId +
                ", codeName='" + codeName + '\'' +
                ", notes='" + notes + '\'' +
                ", ext1=" + ext1 +
                ", ext2='" + ext2 + '\'' +
                ", ext3='" + ext3 + '\'' +
                '}';
    }
}
