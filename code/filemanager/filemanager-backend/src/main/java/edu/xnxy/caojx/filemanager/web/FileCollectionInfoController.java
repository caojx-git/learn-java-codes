package edu.xnxy.caojx.filemanager.web;

import edu.xnxy.caojx.filemanager.entity.FileCollectionInfo;
import edu.xnxy.caojx.filemanager.entity.UserInfo;
import edu.xnxy.caojx.filemanager.mybatis.mapper.pagination.PageParameter;
import edu.xnxy.caojx.filemanager.service.FileCollectionInfoServiceImpl;
import edu.xnxy.caojx.filemanager.service.IFileCollectionInfoService;
import edu.xnxy.caojx.filemanager.service.IUserInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:收藏夹控制器
 *
 * Created by caojx on 17-4-26.
 */
@Controller
@RequestMapping("/filter/fileCollection")
public class FileCollectionInfoController {

    private static Logger log = Logger.getLogger(FileCollectionInfoServiceImpl.class);

    @Resource
    private IFileCollectionInfoService fileCollectionInfoService;

    @Resource
    private IUserInfoService userInfoService;

    @RequestMapping("/collectionPage.do")
    public String showCollectionPage() {
        return "collection";
    }

    /**
     * Description: 获取收藏文件信息,带分页
     *@param userId 用户编号
     * @param fileName  文件名
     * @param uploader  上传者名
     * @param collegeId 学院编号
     * @param page      分页参数
     * @return
     */
    @RequestMapping("/listCollectionInfo.do")
    public String listCollectionInfo(Long userId,String fileName, String uploader,Long collegeId, PageParameter page, HttpServletRequest httpServletRequest) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            FileCollectionInfo fileCollectionInfo = new FileCollectionInfo();
            if (userId !=null){
                fileCollectionInfo.setUserId(userId);
            }
            if (fileName != null && !"".equals(fileName)) {
                fileCollectionInfo.setFileName(fileName);
            }
            if (uploader != null && !"".equals(uploader)) {
                fileCollectionInfo.setUploader(uploader);
            }
            if (collegeId != null) {
                fileCollectionInfo.setCollegeId(collegeId);
            }
            List<FileCollectionInfo> fileCollectionInfoList = fileCollectionInfoService.listFileCollectionInfo(fileCollectionInfo, page);
            resultMap.put("page", page);
            resultMap.put("fileCollectionInfo", fileCollectionInfo);
            resultMap.put("fileCollectionInfoList", fileCollectionInfoList);
            httpServletRequest.setAttribute("page", page);
            httpServletRequest.setAttribute("fileCollectionInfo", fileCollectionInfo);
            httpServletRequest.setAttribute("fileCollectionInfoList", fileCollectionInfoList);
        } catch (Exception e) {
            log.error("获取收藏文件信息出错", e);
        }
        return "collection";
    }

    /**
     * Description:文件收藏
     *
     * @param fileId             文件编号
     * @param fileName      文件名
     * @param uploadId       上传者编号
     * @param collegeId       学院编号
     * @param request
     * @return
     */
    @RequestMapping("/saveCollectionFile.do")
    @ResponseBody
    public Map<String, Object> saveCollectionFile(Long fileId, String fileName, Long uploadId, Long collegeId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(uploadId);
            userInfo = userInfoService.getUserInfo(userInfo);

            HttpSession session = request.getSession();
            UserInfo userInfo1 = (UserInfo) session.getAttribute("userInfo");
            FileCollectionInfo fileCollectionInfo = new FileCollectionInfo();
            fileCollectionInfo.setUploader(userInfo.getUserName());
            if (fileId != null) {
                fileCollectionInfo.setFileId(fileId);
            }
            if (fileName != null && !"".equals(fileName)) {
                fileCollectionInfo.setFileName(fileName);
            }

            if (collegeId != null) {
                fileCollectionInfo.setCollegeId(collegeId);
            }
            fileCollectionInfo.setUserId(userInfo1.getUserId());
            fileCollectionInfo.setCreateDate(new Date());
            fileCollectionInfoService.saveFileCollectionInfo(fileCollectionInfo);
            resultMap.put("status", 0);
        } catch (Exception e) {
            resultMap.put("status", 1);
            resultMap.put("message", e.getMessage());
            log.error("收藏失败", e);
        }
        return resultMap;
    }


    /**
     * Description：删除收藏文件信息
     * @param userId 用户编号
     * @param fileId 文件编号
     * @return
     */
    @RequestMapping("/removeFileCollection.do")
    @ResponseBody
    public Map<String,Object> removeFileCollection(@RequestParam("userId") Long userId, @RequestParam("fileId") Long fileId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            fileCollectionInfoService.removeFileCollectionInfo(userId, fileId);
            resultMap.put("status", 0);
        } catch (Exception e) {
            resultMap.put("status", 1);
            resultMap.put("message", "删除失败");
            log.error("收藏失败", e);
        }
        return resultMap;
    }


}
