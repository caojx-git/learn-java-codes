package web.servlet.forAndDirect;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 * 重定向,页面显示的内容的地址在地址栏中显示一致
 * */
public class RedirectServlet extends HttpServlet {

	//doGet方法   servlet ---> html(重定向到页面)
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
			page = "forwardA.html";
		}else {
			page = "forwardB.html";
		}
		//重定向到页面
		resp.sendRedirect(page);
		
	}

	//doPost方法   servlet -- > servlet（重定向到servlet）
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
			myServlet = "forwardA";
		}else {
			myServlet = "forwardB";
		}
		//重定向到servlet
		resp.sendRedirect(myServlet);
	
	}
	
}
