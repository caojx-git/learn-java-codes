package web.servlet.forAndDirect;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForWordServlet extends HttpServlet {

	//doGet方法   servlet ---> html
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); //保证post提交方式不会乱码
		resp.setCharacterEncoding("UTF-8"); //保证回应的编码格式
		resp.setContentType("text/html;charset=UTF-8"); //设置浏览器解析的编码格式
		
		String name = req.getParameter("username");
		System.out.println(name);
		String page="";
		if("briup".equals(name)) {
			page = "/forwardA.html";
		}else {
			page = "/forwardB.html";
		}
		
		//获取到跳转到page页面的请求转发器
		RequestDispatcher dispatcher = req.getRequestDispatcher(page);
		//跳转到page
		dispatcher.forward(req, resp);
		
	}

	//doPost方法   servlet -- > servlet
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); //保证post提交方式不会乱码
		resp.setCharacterEncoding("UTF-8"); //保证回应的编码格式
		resp.setContentType("text/html;charset=UTF-8"); //设置浏览器解析的编码格式
		
		String name = req.getParameter("username");
		System.out.println(name);
		String myServlet="";
		if("briup".equals(name)) {
			myServlet = "/forwardA";
		}else {
			myServlet = "/forwardB";
		}
		
		//获取到跳转到myServlet页面的请求转发器
		RequestDispatcher dispatcher = req.getRequestDispatcher(myServlet);
		//跳转到page
		dispatcher.forward(req, resp);
	
	}
	
}
