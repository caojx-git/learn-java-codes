package web.servlet.encode;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EncodeServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); //保证post提交方式不会乱码
		resp.setCharacterEncoding("UTF-8"); //保证回应的编码格式
		resp.setContentType("text/html;charset=UTF-8"); //设置浏览器解析的编码格式
		String name = req.getParameter("name");
		System.out.println(name);
		PrintWriter out = resp.getWriter();
		out.println("name="+name);
	}
	
	

}
