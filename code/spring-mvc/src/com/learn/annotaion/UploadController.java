package com.learn.annotaion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.Iterator;

/**
 * Description:文件上传Controller配置，需要在配置文件中配置multipartResolver
 * i且需要两个jar包
 * 方式一：使用参数CommonsMultipartFile接受的方法
 * Created by caojx on 17-1-2.
 */
@Controller
@RequestMapping("/file")
public class UploadController {

    /**
     * springmvc文件上传
     * Springmvc通过Servlet API的HttpServletRequest接口进行扩展，使其能够很好的处理文件上传，接口扩展后的接口实例名为
     * org.springframework.web.multipart.MultipartHttpServletRequest;
     * 当前台页面上传文件后，就会通过MultipartHttpServletRequest解析后，解析实例化成为一个CommonsMultipartFile
     * @return String 返回文件上传成功的视图路径
     * */
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") CommonsMultipartFile file){//@RequestParam是用来将前台页面的参数与后台方法的参数绑定，当前台页面中的参数名与后台方法的参数名不一致的时候。
        System.out.println("文件名：--》"+file.getOriginalFilename());
        if (!file.isEmpty()){
            try {
                FileOutputStream outputStream = new FileOutputStream("/home/caojx/桌面/"+new Date().getTime()+file.getOriginalFilename());
                InputStream inputStream = file.getInputStream();
                int len = 0;
                while((len=inputStream.read())!=-1){
                    outputStream.write(len);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "/jsp/success";
    }

    /**
     * 文件上传方式二,使用springmvc的MultipartHttpServletRequest，这中方式效率更快。
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/upload2")
    public String upload2(HttpServletRequest httpServletRequest) throws IOException {
        //1.定义解析器,获取request的上下文
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(httpServletRequest.getSession().getServletContext());
        if (multipartResolver.isMultipart(httpServletRequest)){//判断是否是isMultipart类型数据
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest; //转换成MultipartHttpServletRequest
            //获取所有的文件
            Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
            while(iterator.hasNext()){//遍历文件
                //获取其中一个文件
                MultipartFile multipartFile = multipartHttpServletRequest.getFile(iterator.next());
                if (multipartFile != null){
                    String fileName = "demeoUpload"+multipartFile.getOriginalFilename();//定义文件名
                    String path = "/home/caojx/桌面/"+fileName; //定义文件输出路径

                    File localFile = new File(path);
                    multipartFile.transferTo(localFile); //将文件写到本地
                }
            }
        }
        return "jsp/success";
    }

    /**
     * Description:返回上传文件页面视图
     */
    @RequestMapping("/toUpload")
    public String toUpload(){
        return "/jsp/upload";
    }

}
