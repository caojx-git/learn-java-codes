package edu.xnxy.caojx.filemanager.service;

import edu.xnxy.caojx.filemanager.dao.IFileInfoDAO;
import edu.xnxy.caojx.filemanager.entity.FileInfo;
import edu.xnxy.caojx.filemanager.mybatis.mapper.pagination.PageParameter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        fileInfoList = fileInfoDAO.query(fileInfo);
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
        fileInfoList = fileInfoDAO.query(fileInfo, page);
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
    public List<FileInfo> listFileInfo(String fileName, String startDate, String endDate, String collegeId, String userName, PageParameter page) throws Exception {
        List<FileInfo> fileInfoList = null;
        Long collegeVale = null;
        if(!"".equals(collegeId)){
            collegeVale = Long.valueOf(collegeId.trim()).longValue();
        }
        fileInfoList = fileInfoDAO.listFileInfo(fileName, collegeVale, startDate, endDate, userName, page);
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
            fileInfoDAO.delete(fileInfo);
        } catch (SQLException e) {
            log.error("删除文件信息异常", e);
            throw new RuntimeException("删除文件信息异常", e);

        }
    }
}
