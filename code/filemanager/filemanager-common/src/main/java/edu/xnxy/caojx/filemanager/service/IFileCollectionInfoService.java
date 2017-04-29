package edu.xnxy.caojx.filemanager.service;

import edu.xnxy.caojx.filemanager.entity.FileCollectionInfo;
import edu.xnxy.caojx.filemanager.mybatis.mapper.pagination.PageParameter;

import java.util.List;

/**
 * Description: 收藏业务类
 * <p>
 * Created by caojx on 17-4-26.
 */
public interface IFileCollectionInfoService {

    /**
     * Description：获取单条文件收藏信息
     *
     * @param fileCollectionInfo
     * @return
     */
    public FileCollectionInfo getFileCollectionInfo(FileCollectionInfo fileCollectionInfo) throws Exception;

    /**
     * Description：获取多条文件收藏信息
     *
     * @param fileCollectionInfo
     * @return
     * @throws Exception
     */
    public List<FileCollectionInfo> listFileCollectionInfo(FileCollectionInfo fileCollectionInfo) throws Exception;

    /**
     * Description：获取多条文件收藏信息,带分页
     *
     * @param fileCollectionInfo
     * @return
     * @throws Exception
     */
    public List<FileCollectionInfo> listFileCollectionInfo(FileCollectionInfo fileCollectionInfo, PageParameter page) throws Exception;

    /**
     * Description:保存收藏文件信息
     *
     * @param fileCollectionInfo
     * @throws Exception
     */
    public void saveFileCollectionInfo(FileCollectionInfo fileCollectionInfo) throws Exception;

    /**
     * Description:通过用户编号和文件编号删除收藏信息-单条删除
     *
     * @param userId 用户编号
     * @param fileId 文件编号
     * @throws Exception
     */
    public void removeFileCollectionInfo(Long userId, Long fileId) throws Exception;

    /**
     * Description:通过用户编号和文件编号删除收藏的文件信息-批量删除
     *
     * @param userId  用户编号
     * @param fileIds 文件编号数组
     * @throws Exception
     */
    public void removeBatch(Long userId, Long[] fileIds) throws Exception;


}
