package edu.xnxy.caojx.filemanager.service;

import edu.xnxy.caojx.filemanager.dao.IFileInfoDAO;
import edu.xnxy.caojx.filemanager.entity.FileInfo;
import edu.xnxy.caojx.filemanager.mybatis.mapper.pagination.PageParameter;
import edu.xnxy.caojx.filemanager.util.Office2PDFUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * Description:文件管理业务实现类
 *
 * @author caojx
 *         Created by caojx on 2017年04月24 下午3:51:51
 */
@Service("fileInfoService")
public class FileInfoServiceImpl implements IFileInfoService {

    private static final Logger log = Logger.getLogger(FileInfoServiceImpl.class);

    @Resource
    private IFileInfoDAO fileInfoDAO;

    /**
     * 获取文件列表
     *
     * @param fileInfo
     * @return
     */
    @Override
    public List<FileInfo> listFileInfo(FileInfo fileInfo) throws Exception {
        List<FileInfo> fileInfoList = null;
        try {
            fileInfoList = fileInfoDAO.query(fileInfo);
        } catch (Exception e) {
            log.error("获取文件列表异常",e);
            throw  new Exception("获取文件列表异常",e);        }
        return fileInfoList;
    }

    /**
     * 获取文件列表，带分页
     *
     * @param fileInfo
     * @param page
     * @return
     */
    @Override
    public List<FileInfo> listFileInfo(FileInfo fileInfo, PageParameter page) throws Exception {
        List<FileInfo> fileInfoList = null;
        try{
            fileInfoList = fileInfoDAO.query(fileInfo, page);
        }catch (Exception e){
            log.error("获取文件列表异常",e);
            throw  new Exception("获取文件列表异常",e);
        }
        return fileInfoList;
    }


    /**
     * 获取文件列表，带分页
     *
     * @param fileName  文件名
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param collegeId 学院编号
     * @param userName  文件所有者名字
     * @param page
     * @return
     * @throws Exception
     */
    @Override
    public List<FileInfo> listFileInfo(String fileName, String startDate, String endDate, Long collegeId, String userName, PageParameter page) throws Exception {
        List<FileInfo> fileInfoList = null;
        try{
            fileInfoList = fileInfoDAO.listFileInfo(fileName, collegeId, startDate, endDate, userName, page);
        }catch (Exception e){
            log.error("获取文件列表异常-带分页",e);
            throw  new Exception("获取文件列表异常-带分页",e);
        }
        return fileInfoList;
    }

    /**
     * 保存文件信息
     *
     * @param fileInfo
     */
    @Override
    public void saveFileInfo(FileInfo fileInfo) throws Exception {
        try {
            Long done_code = fileInfoDAO.getSequence();
            fileInfo.setFileId(done_code);
            fileInfoDAO.insert(fileInfo);
        } catch (SQLException e) {
            log.error("文件信息保存异常", e);
            throw new RuntimeException("文件信息保存异常", e);
        }
    }

    /**
     * 清理文件信息
     *
     * @param fileInfo
     */
    @Override
    public void removeFileInfo(FileInfo fileInfo) throws Exception {
        try {
            fileInfoDAO.update(fileInfo);
        } catch (SQLException e) {
            log.error("删除文件信息异常", e);
            throw new RuntimeException("删除文件信息异常", e);
        }
    }

    /**
     * 文件在线预览，将文件转为pdf
     * @param sourceFile 源文件路径
     * @param destFile 目标文件路径
     * @return true转换成功，false转换失败
     * @throws Exception
     */
    public boolean getPDFPath(String sourceFile,String destFile) throws Exception{
        int result = Office2PDFUtil.office2PDF(sourceFile,destFile);
        if(result == 0){
            return true;
        }
        return false;
    }
}
