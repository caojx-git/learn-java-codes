package edu.xnxy.caojx.filemanager.web.listener;

import edu.xnxy.caojx.filemanager.entity.FileManagerSysBaseType;
import edu.xnxy.caojx.filemanager.service.IFileManagerSysBaseTypeService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * Description:
 *
 * Created by caojx on 17-4-28.
 */
public class MyServletContextListener implements ServletContextListener {

    private static Logger log = Logger.getLogger(MyServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            //初始化下来列表数据
            ServletContext servletContext = servletContextEvent.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            IFileManagerSysBaseTypeService fileManagerSysBaseTypeService = (IFileManagerSysBaseTypeService) webApplicationContext.getBean("fileManagerSysBaseTypeService");
            List<FileManagerSysBaseType> collegeList = fileManagerSysBaseTypeService.listFileManagerSysBaseType(1002L,null);
            servletContext.setAttribute("collegeList",collegeList);
        } catch (BeansException e) {
            log.error("MyServletContextListener初始化出错",e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
