package edu.xnxy.caojx.filemanager.service;

import edu.xnxy.caojx.filemanager.entity.FileManagerSysBaseType;

import java.util.List;

/**
 * Description:
 *
 * @author caojx
 * Created by caojx on 2017年04月17 下午7:52:52
 */
public interface IFileManagerSysBaseTypeService {


    public List<FileManagerSysBaseType> listFileManagerSysBaseType(Long codeType, Long codeId);
}
