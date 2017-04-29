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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description: 文件管理Controller
 *
 * @author caojx
 *         Created by caojx on 2017年04月19 下午1:47:47
 */
@Controller
@RequestMapping("/filter/file")
public class FileController {

    private static final Logger log = Logger.getLogger(FileController.class);

    @Resource
    private IUserInfoService userInfoService;

    @Resource
    private IFileInfoService fileInfoService;

    @Resource
    private IFileManagerSysBaseTypeService fileManagerSysBaseTypeService;

    /**
     * Description:文件上传页面
     *
     * @return
     */
    @RequestMapping("/fileUploadPage.do")
    public String showFileUpLoadPage() {
        return "fileupload";
    }

    /**
     * Description： 文件上传
     *
     * @param httpServletRequest
     * @param fileInfo
     * @return
     * @throws IOException
     */
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
        return "fileInfoList";
    }


    /**
     * Description: 获取文件信息
     *
     * @param fileName  文件名
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param userName  用戶名
     * @param request
     * @return
     */
    @RequestMapping("/listFileInfo.do")
    public String listFileInfo(String fileName, String startDate, String endDate, Long collegeId, String userName,
                               PageParameter page, HttpServletRequest request) {
        try {
            List<FileInfo> fileInfoList = fileInfoService.listFileInfo(fileName, startDate, endDate, collegeId, userName, page);
            request.setAttribute("fileInfoList", fileInfoList);
            request.setAttribute("page", page);
            request.setAttribute("fileName", fileName);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("collegeId", collegeId);
            request.setAttribute("userName", userName);
        } catch (Exception e) {
            log.error("获取文件列表异常", e);
        }
        return "fileInfoList";
    }

    /**
     * Description: 文件下载
     *
     * @param codeId 文件編號
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
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
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


    /**
     * Description:文件预览，(客户端)浏览器不用下载文件，可以在线预览文件
     *
     * @param fileId 文件編號
     * @return
     */
    @RequestMapping("/preview.do")
    public String preViewFile(String fileId, HttpServletRequest httpServletRequest) {

        try {
            //获取文件名
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileId(Long.valueOf(fileId.trim()));
            List<FileInfo> fileInfoList = null;
            fileInfoList = fileInfoService.listFileInfo(fileInfo);
            String fileName = fileInfoList.get(0).getFileName();
            //获取文件保存路径
            String sourceBasePath = "";
            String pdfBasePath = "";
            List<FileManagerSysBaseType> fileManagerSysBaseTypeList = fileManagerSysBaseTypeService.listFileManagerSysBaseType(1003L, null);
            for (FileManagerSysBaseType fileManagerSysBaseType : fileManagerSysBaseTypeList) {
                if (fileManagerSysBaseType.getCodeId() == 101L) {
                    sourceBasePath = fileManagerSysBaseType.getCodeName().trim();
                }
                if (fileManagerSysBaseType.getCodeId() == 102L) {
                    pdfBasePath = fileManagerSysBaseType.getCodeName().trim();
                }
            }

            //如果是pdf，swf,map3，map4直接返回文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            if(!"pdf".equals(suffixName) || !"swf".equals(suffixName) || !"map3".equals(suffixName)|| !"map4".equals(suffixName)){
                httpServletRequest.setAttribute("previewPath", fileName);
            }else { //其他文件格式转换成pdf
                String sourceFile = sourceBasePath + fileName;
                String destFile = pdfBasePath + fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
                boolean result = fileInfoService.getPDFPath(sourceFile, destFile);
                if (result == true) {
                    httpServletRequest.setAttribute("previewPath", fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf");
                } else {
                    log.error("文件转pdf失败了");
                }
            }
        } catch (Exception e) {
            log.error("文件转pdf失败了", e);
        }
        return "preview";
    }

    /**
     * Description: 删除文件
     *
     * @param fileId 文件编号
     */
    @RequestMapping("/removeFile.do")
    @ResponseBody
    public Map<String, Object> removeFile(String fileId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileId(Long.valueOf(fileId.trim()));
            fileInfo.setRecStatus(0);
            fileInfoService.removeFileInfo(fileInfo);
            resultMap.put("status", 0);
        } catch (Exception e) {
            resultMap.put("status", 1);
            resultMap.put("message", "删除文件出错");
            log.error("删除文件异常", e);
        }
        return resultMap;
    }

}
