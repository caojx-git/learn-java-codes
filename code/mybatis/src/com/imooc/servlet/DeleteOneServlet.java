package com.imooc.servlet;

import com.imooc.service.MessageService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author caojx 实现消息单条删除
 * Created by caojx on 2017年02月26 下午7:12:12
 */
public class DeleteOneServlet extends HttpServlet{

    private Logger logger = Logger.getLogger(DeleteOneServlet.class);

    MessageService messageService = new MessageService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try{
            //获取页面参数
            String id = req.getParameter("id");
            System.out.println(id);
            if(id!=null&&!"".equals(id)){
                messageService.deleteOne(Integer.parseInt(id));
            }
            req.setAttribute("retMsg", "删除成功");
        }catch(Exception e){
            logger.error("消息删除出错",e);
            req.setAttribute("retMsg", "删除失败");
        }finally{
            //向页面跳转list.action实现第页面进行刷新
            resp.sendRedirect("/list.action");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
