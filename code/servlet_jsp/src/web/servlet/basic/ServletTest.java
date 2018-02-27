package web.servlet.basic;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletTest extends HttpServlet{

	
	/*
	 * 以GET方式访问页面是执行该函数
	 * 执行doGet方式前会先执行getLastModified,如果发现getLastModified返回的数值
	 * 与上一次访问时返回idea数值相同，则认为该文档没有更新，浏览器采用缓存而不执行doGet
	 * 如果getLastModified返回-1则认为是时刻更新的，总是执行该函数
	 * */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			this.log("执行doGet方法");  //调用Servlet自带的日志输出工具
			this.execute(req,resp);
	}

	/*
	 * 以POST方式访问页面时执行该函数，执行前不会执行getLastModified
	 * */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.log("执行doPost方法");  //调用Servlet自带的日志输出工具
		this.execute(req,resp);
	}
	
	//执行方法
	private void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setCharacterEncoding("UTF-8");  //设置resp的编码格式
		req.setCharacterEncoding("UTF-8");	//设置req的编码格式
		String reqURI = req.getRequestURI(); //访问Servlet的URI
		String param = req.getParameter("param");//客户端提交参数param值
		System.out.println(param);
		String method = req.getMethod();
		
		resp.setContentType("text/html");	//设置文档类型为html
		PrintWriter out = resp.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<html>");
		out.println("<head><title></title></head>");
		out.println("<body>");
			out.println("以"+method+"方式访问该页面。遇到的param参数为"+param+"<br/>");
			out.println("<form action='"+reqURI+"' method='get'>"
					+ "<input type='text' name='prama' value='prama String'>"
					+ "<input type='submit' value='以GET方式查询页面'"+reqURI+"'>"
							+ "</form>");
			out.println("<form action='"+reqURI+"' method='post'>"
					+ "<input type='text' name='prama' value='prama String'>"
					+ "<input type='submit' value='以Post方式查询页面'"+reqURI+"'>"
							+ "</form>");
			//有客户端浏览器读取该文档的更新时间
			out.println("<script>document.write('本页面最后更新时间：'+document.lastModified);</script>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}
	
}
