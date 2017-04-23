package edu.xnxy.caojx.filemanager.web.filter;

import edu.xnxy.caojx.filemanager.entity.UserInfo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Description: 验证用户是否登陆
 *
 * @author caojx
 * Created by caojx on 2017年04月23 下午10:48:48
 */
public class CheckLoginFilter extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        String currentURL = httpServletRequest.getRequestURI();
        if(!("/user/loginPage.do".equals(currentURL)) && userInfo == null){
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        httpServletResponse.sendRedirect("/user/loginPage.do");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }



   /* @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        String currentURL = httpServletRequest.getRequestURI();
        if(!("/user/loginPage.do".equals(currentURL)) && userInfo == null){
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("/user/loginPage.do");
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }*/
}
