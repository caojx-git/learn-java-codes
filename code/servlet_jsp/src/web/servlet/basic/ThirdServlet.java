package web.servlet.basic;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThirdServlet extends HttpServlet{

	public ThirdServlet() {
		System.out.println("ThirdServlet´´½¨");
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			resp.setContentType("image/jpeg");
			ServletOutputStream out = resp.getOutputStream();
			InputStream in = this.getClass().getResourceAsStream("language.jpg");
			byte[] buf = new byte[1024];
			int len = 0;
			while((len=in.read(buf))!=-1) {
				out.write(buf, 0, len);
			}
			out.flush();
			in.close();
			out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	}
	
}
