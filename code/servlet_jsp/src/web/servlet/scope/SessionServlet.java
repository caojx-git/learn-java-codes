package web.servlet.scope;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		// 获取Session对象
		HttpSession session = req.getSession(); // req.getSession(true)表示每次访问创建一个新的session

		String sessionid = session.getId(); // 获取JSESSIONID
		boolean b = session.isNew(); // 判断是否为最新的session
		System.out.println("id=" + sessionid);
		System.out.println("isNew=" + b);
		Integer count = (Integer) session.getAttribute("num"); // 第一次访问num=null
		if (count == null) {
			count = 1;
			session.setAttribute("num", count);
		}
		PrintWriter out = resp.getWriter();
		String path = "session"; //
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>session:count=" + count + "</h1>");
		out.println("<a href=" + resp.encodeUrl(path) + ">url重写</a>");// 在客户端禁用cookie时，也可以记录会话
		out.println("</body>");
		out.println("</html>");
		out.flush();
		// session.invalidate(); //session失效
		session.setAttribute("num", ++count);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
