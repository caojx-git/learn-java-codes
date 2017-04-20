package edu.xnxy.caojx.filemanager.web;

import edu.xnxy.caojx.filemanager.entity.FileManagerSysBaseType;
import edu.xnxy.caojx.filemanager.entity.UserInfo;
import edu.xnxy.caojx.filemanager.service.IFileManagerSysBaseTypeService;
import edu.xnxy.caojx.filemanager.service.IUserInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * Description: 文件管理Controller
 *
 * @author caojx
 *         Created by caojx on 2017年04月19 下午1:47:47
 */
@Controller
@RequestMapping("/file")
public class FileController {

    private static final Logger log = Logger.getLogger(FileController.class);

    @Resource
    private IUserInfoService userInfoService;

    @Resource
    private IFileManagerSysBaseTypeService fileManagerSysBaseTypeService;

    @RequestMapping("/fileUploadPage.do")
    public String showFileUpLoadPage(HttpServletRequest request) {
        List<UserInfo> userInfoList = null;
        List<FileManagerSysBaseType> userCollegeList = null;
        try {
            userInfoList = userInfoService.listUserInfo(new UserInfo());
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
        }
        try {
            userCollegeList = fileManagerSysBaseTypeService.listFileManagerSysBaseType(1002L, null);
        } catch (Exception e) {
            log.error("获取学院信息失败", e);
        }
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("userCollegeList", userCollegeList);
        return "fileupload";
    }

    @RequestMapping("fileUpload.do")
    public String fileUpLoad(HttpServletRequest httpServletRequest) throws IOException {
        //获取文件保存路径
        List<FileManagerSysBaseType> fileManagerSysBaseTypeList = fileManagerSysBaseTypeService.listFileManagerSysBaseType(1003L, 101L);
        //1.定义解析器,获取request的上下文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(httpServletRequest.getSession().getServletContext());
        if (multipartResolver.isMultipart(httpServletRequest)) {//判断是否是isMultipart类型数据
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest; //转换成MultipartHttpServletRequest
            //获取所有的文件
            Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
            while (iterator.hasNext()) {//遍历文件
                //获取其中一个文件
                MultipartFile multipartFile = multipartHttpServletRequest.getFile(iterator.next());
                if (multipartFile != null) {
                    String fileName = new Date().getTime()+multipartFile.getOriginalFilename();//定义文件名
                    String path = fileManagerSysBaseTypeList.get(0).getCodeName() + fileName; //定义文件输出路径
                    File localFile = new File(path);
                    multipartFile.transferTo(localFile); //将文件写到本地
                }
            }
        }
        return "";
    }


}
