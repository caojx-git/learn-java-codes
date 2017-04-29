package edu.xnxy.caojx.filemanager.dao;

import edu.xnxy.caojx.filemanager.entity.FileCollectionInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Description: 文件收藏实体类
 *
 * @author caojx
 *         Created by caojx on 2017年04月24 下午12:22:22
 */
@Repository("fileCollectionInfoDAO")
public interface IFileCollectionInfoDAO extends IBaseDAO<FileCollectionInfo> {

    /**
     * Description:根据用户编号和文件编号删除收藏的文件,单条删除
     *
     * @param userId 用户编号
     * @param fileId 文件编号
     */
    public void removeCollectionInfo(@Param("userId") Long userId, @Param("fileId") Long fileId);

    /**
     * Description:根据用户编号和文件编号删除收藏的文件,批量删除
     *
     * @param userId  用户编号
     * @param fileIds 文件编号数组
     */
    public void removeBatch(@Param("userId") Long userId, @Param("fileIds") Long[] fileIds);

}

