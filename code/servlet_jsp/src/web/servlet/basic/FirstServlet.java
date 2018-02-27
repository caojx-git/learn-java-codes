package web.servlet.basic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FirstServlet implements Servlet{

	public FirstServlet() {
		System.out.println("firstServlet创建");
	}
	@Override
	public void destroy() {
		
		
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		
	}

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		/*
		 * 浏览器向服务器发送过来的请求封装成了一个SerletRequset类型的对象
		 * 将服务器返回给浏览器的响应封装成了一个SerletResponse累心的对象
		 * */
		PrintWriter out = response.getWriter();

		out.println("hello servlet1");
		out.flush();
		out.close();
	}

}
