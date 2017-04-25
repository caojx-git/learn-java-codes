package edu.xnxy.caojx.filemanager.service;

import edu.xnxy.caojx.filemanager.entity.FileInfo;
import edu.xnxy.caojx.filemanager.mybatis.mapper.pagination.PageParameter;

import java.util.List;

/**
 * Description:文件管理业务接口
 *
 * @author caojx
 *         Created by caojx on 2017年04月24 下午3:46:46
 */
public interface IFileInfoService {

    /**
     * 获取文件列表
     *
     * @param fileInfo
     * @return
     */
    public List<FileInfo> listFileInfo(FileInfo fileInfo) throws Exception;

    /**
     * 获取文件列表，带分页
     *
     * @param fileInfo
     * @param page
     * @return
     */
    public List<FileInfo> listFileInfo(FileInfo fileInfo, PageParameter page) throws Exception;

    /**
     * 获取文件列表，带分页
     * @param fileName 文件名
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param collegeId 学院编号
     * @param userName 文件所有者名字
     * @param page
     * @return
     * @throws Exception
     */
    public List<FileInfo> listFileInfo(String fileName, String startDate,
                                       String endDate, String collegeId, String userName,
                                       PageParameter page) throws Exception;

    /**
     * 保存文件信息
     *
     * @param fileInfo
     */
    public void saveFileInfo(FileInfo fileInfo) throws Exception;

    /**
     * 清理文件信息
     *
     * @param fileInfo
     */
    public void removeFileInfo(FileInfo fileInfo) throws Exception;
}
