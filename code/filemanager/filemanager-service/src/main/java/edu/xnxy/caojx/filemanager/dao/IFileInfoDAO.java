package edu.xnxy.caojx.filemanager.dao;

import edu.xnxy.caojx.filemanager.entity.FileInfo;
import edu.xnxy.caojx.filemanager.mybatis.mapper.pagination.PageParameter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Description:文件信息维护接口
 *
 * @author caojx
 *         Created by caojx on 2017年04月24 下午3:42:42
 */
@Repository("fileInfoDAO")
public interface IFileInfoDAO extends IBaseDAO<FileInfo> {

    /**
     * 获取序列号
     *
     * @return
     */
    public Long getSequence();

    /**
     * 获取文件信息
     */
    public List<FileInfo> listFileInfo(@Param("fileName") String fileName, @Param("collegeId") Long collegeId,
                                @Param("startDate") String startDate, @Param("endDate") String endDate,
                                @Param("userName") String userName, @Param("page") PageParameter page);

}
