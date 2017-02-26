package com.imooc.servlet;

import com.imooc.bean.Message;
import com.imooc.service.MessageService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:页面列表初始化
 * Created by caojx on 17-2-1.
 */
public class ListServlet extends HttpServlet {

    //使用log4j进行日志输出，这里简单的配置一样就好
    private static Logger logger = Logger.getLogger(ListServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try {
            //获取查询参数
            req.setCharacterEncoding("UTF-8");
            String command = req.getParameter("command");
            String description = req.getParameter("description");
            logger.info("queryMessageList开始，查询参数为"+command+","+description);
            //查询消息列表
            List<Message> messages = new ArrayList<Message>();
            MessageService messageService = new MessageService();
            messages = messageService.queryMessageList(command,description);

            //将参数和消息列表传回给页面
            req.setAttribute("command",command);
            req.setAttribute("description",description);
            req.setAttribute("messages", messages);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Message查询出错",e);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/back/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
