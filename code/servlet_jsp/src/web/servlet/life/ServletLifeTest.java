package web.servlet.life;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletLifeTest extends HttpServlet {

	public ServletLifeTest() {
		System.out.println("ServletLife构造函数调用");
	}
	
	//其实tomcat调用的是service方法，但是HttpServlet所实现的Service方法中
	//通过对当前servlet访问的方式（get、psot...）进行判断，从而调用到doXxx方法
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			System.out.println("doGet方法调用");
	}

	@Override
	public void destroy() {
		System.out.println("destroy方法调用");
	}

	@Override
	public void init() throws ServletException {
		System.out.println("servletLifeTest初始化方法调用");
	}

/*	@Override
	public void init(ServletConfig config) throws ServletException {
	
		super.init(config);
	}*/

	
}
