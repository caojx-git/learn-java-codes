package edu.xnxy.caojx.filemanager.web;

import edu.xnxy.caojx.filemanager.entity.FileInfo;
import edu.xnxy.caojx.filemanager.entity.FileManagerSysBaseType;
import edu.xnxy.caojx.filemanager.entity.UserInfo;
import edu.xnxy.caojx.filemanager.mybatis.mapper.pagination.PageParameter;
import edu.xnxy.caojx.filemanager.service.IFileInfoService;
import edu.xnxy.caojx.filemanager.service.IFileManagerSysBaseTypeService;
import edu.xnxy.caojx.filemanager.service.IUserInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
    private IFileInfoService fileInfoService;

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

    @RequestMapping("/fileUpload.do")
    public String fileUpLoad(HttpServletRequest httpServletRequest, FileInfo fileInfo) throws IOException {
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
                    String fileName = multipartFile.getOriginalFilename();//定义文件名
                    String path = fileManagerSysBaseTypeList.get(0).getCodeName() + fileName; //定义文件输出路径
                    File localFile = new File(path);
                    multipartFile.transferTo(localFile); //将文件写到本地
                    try {
                        fileInfo.setFileName(fileName);
                        fileInfo.setPathCode(101);
                        fileInfo.setRecStatus(1);
                        fileInfo.setCreateDate(new Date());
                        fileInfoService.saveFileInfo(fileInfo);
                    } catch (Exception e) {
                        log.error("文件信息保存异常", e);
                    }
                }
            }
        }
        return "index";
    }


    /**
     * 获取文件信息
     *
     * @param fileName  文件名
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @RequestMapping("/listFileInfo.do")
    public String listFileInfo(@RequestParam("fileName") String fileName,
                               @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
                               @RequestParam("collegeId") String collegeId, @RequestParam("userName") String userName, PageParameter page, HttpServletRequest httpServletRequest) {
        try {
            List<FileInfo> fileInfoList = fileInfoService.listFileInfo(fileName, startDate, endDate, collegeId, userName, page);
            httpServletRequest.setAttribute("fileInfoList", fileInfoList);
            httpServletRequest.setAttribute("page", page);
            httpServletRequest.setAttribute("fileName", fileName);
            httpServletRequest.setAttribute("startDate", startDate);
            httpServletRequest.setAttribute("endDate", endDate);
            httpServletRequest.setAttribute("collegeId", collegeId);
            httpServletRequest.setAttribute("userName", userName);
        } catch (Exception e) {
            log.error("获取文件列表异常", e);
        }
        return "index";
    }

    /**
     * 文件下载
     */
    @RequestMapping("/downloadFile.do")
    public void downloadFile(String fileId, String codeId, HttpServletResponse response) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            //查询文件
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileId(Long.valueOf(fileId.trim()));
            List<FileInfo> fileInfoList = fileInfoService.listFileInfo(fileInfo);
            String fileName = fileInfoList.get(0).getFileName();
            //获取文件保存路径
            List<FileManagerSysBaseType> fileManagerSysBaseTypeList = fileManagerSysBaseTypeService.listFileManagerSysBaseType(1003L, 101L);
            String path = fileManagerSysBaseTypeList.get(0).getCodeName() + fileName;
            //将文件写出到浏览器
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename="+
                    new String(fileName.getBytes("UTF-8"),"ISO-8859-1"));
            inputStream = new FileInputStream(new File(path));
            outputStream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            inputStream.close();
        } catch (Exception e) {
            log.error("文件下载失败", e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                log.error("文件写出失败");
            }
        }
    }

}
