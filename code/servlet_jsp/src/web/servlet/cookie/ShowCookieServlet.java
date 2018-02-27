package web.servlet.cookie;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowCookieServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");

		// 获取客户端浏览器带过来的cookie
		Cookie[] cookies = req.getCookies();
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		if (cookies != null) {
			// 遍历数组 拿出key和value
			for (Cookie c : cookies) {
				String name = c.getName();
				String value = c.getValue();
				out.println("<h2>name="+name+"</h2>");
				out.println("<h2>name="+value+"</h2>");
			}
		}
		out.println("</body>");
		out.println("</html>");
		out.flush();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
