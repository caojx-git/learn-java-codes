package web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author caojx
 * Created on 2018/2/21 下午7:30
 */
public class LoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("login doGet");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("login doPost");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        System.out.println("userName:"+userName+" password:"+password);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName,password);

        //shiro 会话机制，了解即可
        Session session=subject.getSession();
        System.out.println("sessionId:"+session.getId());
        System.out.println("sessionHost:"+session.getHost());
        System.out.println("sessionTimeout:"+session.getTimeout());
        session.setAttribute("info", "session的数据");

        try{
/*
Shiro提供了记住我（RememberMe）的功能，比如访问如淘宝等一些网站时，关闭了浏览器下次再打开时还是能记住你是谁，下次访问时无需再登录即可访问
            if(subject.isRememberMe()){
                System.out.println("isRememberMe yes");
            }else{
                subject.setRememberMe(true);
            }*/

            subject.login(token);
            //登录成功跳转到success.jsp
            resp.sendRedirect("success.jsp");
        } catch (Exception e){
            //登录失败跳转到登录页面
            req.setAttribute("errorInfo","用户名或密码错误");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
            e.printStackTrace();
        }

    }
}
