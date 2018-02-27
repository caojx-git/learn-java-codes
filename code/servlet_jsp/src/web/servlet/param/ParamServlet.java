package web.servlet.param;

import java.io.IOException;



import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.tribes.util.Arrays;

public class ParamServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		//方式一
		String name = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println(name+"="+password);
		//方式二
		
		String[] hobby = req.getParameterValues("hobby");
		System.out.println(Arrays.toString(hobby));
		
	
		String sex = req.getParameter("sex");
		System.out.println(sex);
	
	
		String city = req.getParameter("city");
		System.out.println(city);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//方法四
	  /*Enumeration names = req.getParameterNames();
		while(names.hasMoreElements()) {
			String name = (String) names.nextElement();
			if("hobby".equals(name)) {
				String[] hobby = req.getParameterValues("hobby");
				System.out.println(Arrays.toString(hobby));
			}else {
				String value = req.getParameter(name);
				System.out.println(value);
			}
		}*/
		
		//方法五
		Map<String, String[]> map = req.getParameterMap();
		for(String s:map.keySet()) {
			String[] value = map.get(s);
			System.out.println(Arrays.toString(value));
		}
	}
	
}
