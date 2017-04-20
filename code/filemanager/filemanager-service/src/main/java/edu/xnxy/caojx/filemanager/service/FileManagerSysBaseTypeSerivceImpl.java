package edu.xnxy.caojx.filemanager.service;

import edu.xnxy.caojx.filemanager.dao.IFilemanagerSysBaseTypeDAO;
import edu.xnxy.caojx.filemanager.entity.FileManagerSysBaseType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @author caojx
 *         Created by caojx on 2017年04月17 下午8:20:20
 */
@Service("fileManagerSysBaseTypeService")
public class FileManagerSysBaseTypeSerivceImpl implements IFileManagerSysBaseTypeService{

    private static final Logger log = Logger.getLogger(UserInfoServiceImpl.class);

    @Resource
    private IFilemanagerSysBaseTypeDAO filemanagerSysBaseTypeDAO;

    @Override
    public List<FileManagerSysBaseType> listFileManagerSysBaseType(Long codeType, Long codeId) {
        List<FileManagerSysBaseType> fileManagerSysBaseTypeList = null;
        FileManagerSysBaseType fileManagerSysBaseType = null;
        try {
            fileManagerSysBaseType = new FileManagerSysBaseType();
            if(!"".equals(codeType) || codeType != null){
                fileManagerSysBaseType.setCodeType(codeType);
            }
            if(!"".equals(codeId) || codeId != null){
                fileManagerSysBaseType.setCodeId(codeId);
            }
            fileManagerSysBaseTypeList  = filemanagerSysBaseTypeDAO.query(fileManagerSysBaseType);
        } catch (Exception e) {
            log.error("listFileManagerSysBaseType出错",e);
            throw new RuntimeException("listFileManagerSysBaseType出错", e);
        }
        return fileManagerSysBaseTypeList;
    }
}
