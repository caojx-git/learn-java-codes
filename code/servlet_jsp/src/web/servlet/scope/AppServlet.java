package web.servlet.scope;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AppServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//获取application对象
	/*	方法一
	 * 	HttpSession session = req.getSession();   
	 *	ServletContext application = session.getServletContext();
	 */
		//方法二
		ServletContext application = req.getServletContext();
		Integer count = (Integer)application.getAttribute("num"); //第一次访问num=null
		if(count == null) {
			count = 1;
			application.setAttribute("num", count);
		}
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>application:count="+count+"</h1>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		application.setAttribute("num", ++count);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	
}
