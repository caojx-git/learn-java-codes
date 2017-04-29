package edu.xnxy.caojx.filemanager.service;

import edu.xnxy.caojx.filemanager.dao.IFileCollectionInfoDAO;
import edu.xnxy.caojx.filemanager.entity.FileCollectionInfo;
import edu.xnxy.caojx.filemanager.mybatis.mapper.pagination.PageParameter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:文件收藏业务类
 * <p>
 * Created by caojx on 17-4-26.
 */
@Service("fileCollectionInfoService")
public class FileCollectionInfoServiceImpl implements IFileCollectionInfoService {

    private static Logger log = Logger.getLogger(FileCollectionInfoServiceImpl.class);

    @Resource
    private IFileCollectionInfoDAO fileCollectionInfoDAO;

    /**
     * Description：获取单条文件收藏信息
     *
     * @param fileCollectionInfo
     * @return
     */
    @Override
    public FileCollectionInfo getFileCollectionInfo(FileCollectionInfo fileCollectionInfo) throws Exception {
        FileCollectionInfo fileCollectionInfo1 = null;
        try {
            fileCollectionInfo1 = fileCollectionInfoDAO.get(fileCollectionInfo);
        } catch (Exception e) {
            log.error("获取单条文件信息出错", e);
            throw new RuntimeException("获取单条文件信息出错", e);
        }
        return fileCollectionInfo1;
    }

    /**
     * Description：获取多条文件收藏信息
     *
     * @param fileCollectionInfo
     * @return
     * @throws Exception
     */
    @Override
    public List<FileCollectionInfo> listFileCollectionInfo(FileCollectionInfo fileCollectionInfo) throws Exception {
        List<FileCollectionInfo> fileCollectionInfoList = null;
        try {
            fileCollectionInfoList = fileCollectionInfoDAO.query(fileCollectionInfo);
        } catch (Exception e) {
            log.error("获取收藏文件信息列表出错", e);
            throw new RuntimeException("获取收藏文件信息列表出错", e);
        }
        return fileCollectionInfoList;
    }

    /**
     * Description：获取多条文件收藏信息,带分页
     *
     * @param fileCollectionInfo
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public List<FileCollectionInfo> listFileCollectionInfo(FileCollectionInfo fileCollectionInfo, PageParameter page) throws Exception {
        List<FileCollectionInfo> fileCollectionInfoList = null;
        try {
            fileCollectionInfoList = fileCollectionInfoDAO.query(fileCollectionInfo, page);
        } catch (Exception e) {
            log.error("获取收藏文件信息列表出错-带分页", e);
            throw new RuntimeException("获取收藏文件信息列表出错-带分页", e);
        }
        return fileCollectionInfoList;
    }

    /**
     * Description:保存收藏文件信息
     *
     * @param fileCollectionInfo
     * @throws Exception
     */
    @Override
    public void saveFileCollectionInfo(FileCollectionInfo fileCollectionInfo) throws Exception {
        try {
            //查询文件是否已经收藏
           FileCollectionInfo fileCollectionInfo1 =  fileCollectionInfoDAO.get(fileCollectionInfo);
           if(fileCollectionInfo1 == null){
               fileCollectionInfoDAO.insert(fileCollectionInfo);
           }else {
               throw new RuntimeException("文件已经收藏");
           }
        } catch (Exception e) {
            log.error("保存收藏文件信息出错", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Description:通过用户编号和文件编号删除收藏信息-单条删除
     *
     * @param userId 用户编号
     * @param fileId 文件编号
     * @throws Exception
     */
    @Override
    public void removeFileCollectionInfo(Long userId, Long fileId) throws Exception {
        try {
            fileCollectionInfoDAO.removeCollectionInfo(userId, fileId);
        } catch (Exception e) {
            log.error("删除收藏文件信息出错-单条删除", e);
            throw new RuntimeException("删除收藏文件信息出错-单条删除", e);
        }

    }

    /**
     * Description:通过用户编号和文件编号删除收藏的文件信息-批量删除
     *
     * @param userId  用户编号
     * @param fileIds 文件编号数组
     * @throws Exception
     */
    @Override
    public void removeBatch(Long userId, Long[] fileIds) throws Exception {
        try {
            fileCollectionInfoDAO.removeBatch(userId, fileIds);
        } catch (Exception e) {
            log.error("删除收藏文件信息出错-批量删除", e);
            throw new RuntimeException("删除收藏文件信息出错-批量删除", e);
        }
    }

}
