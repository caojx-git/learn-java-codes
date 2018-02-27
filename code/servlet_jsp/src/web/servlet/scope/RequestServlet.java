package web.servlet.scope;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//范围对象request
public class RequestServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//主动向request范围内放入age的值20
		req.setAttribute("age", 20);
		
		Integer age = (Integer) req.getAttribute("age");
		
		System.out.println("age="+age);
		//内部跳转
		/*req.getRequestDispatcher("/forwardA").forward(req, resp);*/
		//重定向
		resp.sendRedirect("forwardA");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
