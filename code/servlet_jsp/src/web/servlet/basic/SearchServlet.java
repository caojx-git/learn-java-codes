package web.servlet.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yahoo.search.ImageSearchRequest;
import com.yahoo.search.ImageSearchResult;
import com.yahoo.search.ImageSearchResults;
import com.yahoo.search.SearchClient;


public class SearchServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
	
		PrintWriter out = resp.getWriter();
		
		String type = req.getParameter("type"); //搜索类型
		System.out.println("----------------------------------------------"+type);
		String allowedAdult = req.getParameter("allowedAdult"); 
		String word=req.getParameter("word");  //搜索关键字
		System.out.println(word+"000000000");
		//是否允许成人内容Checkboc的特性， 如果选中，则为"true",否则为null
		boolean adultOk = "true".equals(allowedAdult);  //转化为boolan类型
		out.println("<!DOCTYPE html PUBLIC -//W3C//DTD HTML 4.01 Transitional//EN>");
		out.println("<html>");
		out.println("<head><title>word搜索结果</title></head>");
		out.println("<body>");
			out.println("<div style='float : left; height : 40px;'><img src='images/yahoo.jpg' style='margin: 25px; width :'350px'; height : '80px';'></img></div");
			out.println("<form action='"+req.getRequestURI()+"' method='get'>");//
				out.println("<div style='height : 40px;'>");
					out.println("<input type='radio' name='type' value='web'"+(type.equals("web")?"checked":"")+">网页");
					out.println("<input type='radio' name='type' value='news'"+(type.equals("news")?"checked":"")+">新闻");
					out.println("<input type='radio' name='type' value='image'"+(type.equals("image")?"checked":"")+">图像");
					out.println("<input type='radio' name='type' value='video'"+(type.equals("video")?"checked":"")+">视频");
					out.println("<input type='checkbox' name='allowedAdult' value='true'"+(adultOk?"checked":"")+"允许成人内容<br/>");
					out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					out.println("<input type='text' name='word' value='' style='width: 300px;'>");
					out.println("<input type='submit' value='用雅虎收索' style='width: 100px;'>");
				out.println("</div>");
			out.println("</form>");
		out.println("</body>");
		out.println("</html>");

		SearchClient client = new SearchClient("appid");

		try{
			   if("image".equals(type)){
		            ImageSearchRequest searchRequest = new ImageSearchRequest(URLEncoder.encode(word, "UTF-8"));
		            // 查询记录数
		            searchRequest.setResults(20);
		            // 从第 0 条记录开始显示
		            searchRequest.setStart(BigInteger.valueOf(0));
		             
		            double startTime = System.currentTimeMillis();
		            ImageSearchResults results = client.imageSearch(searchRequest);
		            double endTime = System.currentTimeMillis();
		 
		            out.println("<div align=right style='width:100%; background: #FFDDDD; height:25px; padding:2px; border-top:1px solid #FF9999; margin-bottom:5px; '>");
		            out.println("    总共 " + results.getTotalResultsAvailable() + " 条数据，总用时 " + ( endTime - startTime )/1000 + " 秒。");
		            out.println("</div>");
		             
		            for(ImageSearchResult result : results.listResults()){
		                out.println("<div class='imgDiv'>");
		                out.println("    <div align='center'><a href=\"" + result.getClickUrl() + "\" target=_blank><img width=160 height=120 src=\"" + result.getThumbnail().getUrl() + "\" border='0'></a></div>");
		                out.println("    <div align='center'><a href=\"" + result.getRefererUrl() + "\" target=_blank>" + result.getTitle() + "</a></div>");
		                out.println("    <div align='center'>" + result.getWidth() + "x" + result.getHeight() + " " + result.getFileFormat() + "</div>");
		                out.println("    <div>" + (result.getSummary()==null ? "" : result.getSummary()) + "</div>");
		                out.println("</div>");
		            }
		        }else if("web".equals("type")) { //....网页搜索，代码略
													//新闻，视频，代码略
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}

	
}
