package edu.xnxy.caojx.filemanager.web.filter;


import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Description: 验证用户是否登陆,对于没有登录的用户跳转到登录页面
 *
 * @author caojx
 *         Created by caojx on 2017年04月23 下午10:48:48
 */
public class CheckLoginFilter implements Filter {

    private static Logger log = Logger.getLogger(CheckLoginFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String currentURL = request.getRequestURI();
        String ctxPath = request.getContextPath();

        //除掉项目名称时访问页面当前路径
        String targetURL = currentURL.substring(ctxPath.length());
        //如果当前session存在，直接返回，不存在返回null
        HttpSession session = request.getSession(false);

        if (!("/user/loginPage.do".equals(targetURL))) {
            log.info("targetURL：" + targetURL + " ctxPath:" + ctxPath + " currentURL:" + currentURL);
            //在不为登陆页面时，再进行判断，如果不是登陆页面也没有session则跳转到登录页面，
            if (session == null || session.getAttribute("userInfo") == null) {
                //跳转到登录页面
                response.sendRedirect("/user/loginPage.do");
                return;
            }
        }
        //这里表示如果当前页面是登陆页面，跳转到登陆页面
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    /*@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object obj, ModelAndView mav) throws Exception {
        response.sendRedirect("/user/loginPage.do");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object obj) throws Exception {
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if(userInfo == null){
            return true;
        }
        return false;
    }*/
}
