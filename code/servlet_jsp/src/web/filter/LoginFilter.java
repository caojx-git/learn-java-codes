package web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginFilter implements Filter {

	public LoginFilter() {
		System.out.println("filter创建");
	}
	@Override
	public void destroy() {
		System.out.println("filter销毁");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		//页面跳转
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		HttpSession session = request.getSession();
		String isLogin = (String)session.getAttribute("islogin");
		if("ture".equals(isLogin)) { //符合条件跳转到成功登陆页面
			chain.doFilter(request, response);
			request.getRequestDispatcher("/success.html").forward(request, response);
		}else if("false".equals(isLogin)) {
			response.sendRedirect("login.html");
		}else {
			//...
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("filter初始化");
	}

}
