package web.servlet.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;


public class InitParamServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");   //设置request的编码格式
		response.setCharacterEncoding("UTF-8");  //设置response的编码格式
		
		response.setContentType("text/html");   //设置类型为HTML
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<html>");
		out.println("<head>");
			out.println("<title>请登录查看</title>");
			out.println("<style>body, td, div {font-size:12px;}</style>");
		out.println("</head>");
			out.println("<body>");
				out.println("<form actioin='"+request.getRequestURI()+"'method='post'>");
					out.println("账号:<input type='text' name='username' style='width:200px;'><br/>");
					out.println("密码:<input type='password' name='password' style='width:200px;'><br/>");
					out.println("<input type='submit' value='登录'>");
				out.println("</form>");
			out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username"); //提交的username参数
		String password	= request.getParameter("password");	//提交的password参数
		
		Enumeration params = this.getInitParameterNames(); 	//获取web.xml中的所有初始化参数名称
		
		while(params.hasMoreElements()) { //遍历所有的初始化参数
			String usernameParam = (String) params.nextElement();   //获取用户名
			String passwordParam = getInitParameter(usernameParam);	//获取密码
			if(usernameParam.equalsIgnoreCase(username)&&password.equals(passwordParam)) {  //如果用户名密码正确则返回页面
				request.getRequestDispatcher("WEB-INF/web.xml").forward(request, response);
				return;
			}
		}
		this.doGet(request,response);  //username, password不匹配显示登录页面
	}
	
}
