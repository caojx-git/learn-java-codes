package web.servlet.basic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContextParamServlet  extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<html>");
		out.println("<head><title>读取上下文参数</title></head>");
		out.println("<link rel='stylesheet' type='text/html' href='../css/style.css'>");
		out.println("<body>");
			out.println("<div align='center'><br/>");
				out.println("<fieldest style='width:90%'><legend>所有上下文参数</legend><br/>");
				ServletContext servletContext = getServletConfig().getServletContext(); //获取上下文
				String uploadFolder = servletContext.getInitParameter("upload folder");
				String allowedFileType = servletContext.getInitParameter("allowed file type");
				
				out.println("<div class='line'>");
				out.println("<div align='left' class='leftDiv'>上传文件夹</div>");
				out.println("<div align='left' class='rightDiv'>"+uploadFolder+"</div>");
			out.println("</div>");
			
			out.println("<div class='line'>");
				out.println("<div align='left' class='leftDiv'>实际磁盘路经</div>");
				out.println("<div align='left' class='rightDiv'>"+servletContext.getRealPath(uploadFolder)+"</div>");
				out.println("</div>");
				
				out.println("<div align='left' class='leftDiv'>允许上传的文件类型</div>");
				out.println("<div align='left' class='rightDiv'>"+allowedFileType+"</div>");
				out.println("</div>");
			out.println("</fieldset></div");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}

	
}
