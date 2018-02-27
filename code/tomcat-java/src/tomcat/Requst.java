package tomcat;
/*
 * 处理浏览器发送过来的请求
 * */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Requst {
	//从输入流中获取数据输入流
	private InputStream in;
	private BufferedReader br;
	//资源路径
	private String path;
	//协议
	private String  protocol;
	//方法
	private String method;
	public Requst(InputStream in){
		this.in = in;
	}
	//获取浏览器中的输入请求
	public void parseData() throws IOException, NullPointerException{
		br = new BufferedReader(new InputStreamReader((in)));
		String line="";
		line=br.readLine();
		String li="";
	/*	while((li=br.readLine())!=null){
			System.out.println(li+"=========");
		}*/
		//GET /index.html HTTP/1.1
		String[] ss = line.split(" ");
		method = ss[0];
		path = "webContent"+ss[1];
		System.out.println(path);
		protocol = ss[2];
	}
	public String getPath() {
		return path;
	}
	public String getMethod(){
		return method;
	}
	public String getProtocol() {
		return protocol;
	}
}
