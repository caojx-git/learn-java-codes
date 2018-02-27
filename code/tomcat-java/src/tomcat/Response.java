package tomcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Response {
	private OutputStream out;
	private FileInputStream fis;
	public Response(OutputStream out){
		this.out = out;
	}
	public void sendData(String path) throws IOException{
		File filePath = new File(path);
		//响应头中的数据
		String status ="HTTP/1.1 ";
		String Server ="Server:McaServer ";
		String type ="Content-Type:";
		String length ="Content-Length:";
		PrintStream ps = new PrintStream(out);
		if(filePath.exists()){
			fis = new FileInputStream(path);
			System.out.println(path+"------------------");
			ps.println(status+"200 OK");
			ps.println(Server);
			ps.println(type+getType(path));
			ps.println(length+fis.available());
			ps.println();
			ps.flush();
			//响应体
			byte[] buffer = new byte[fis.available()];
			int len = fis.read(buffer, 0, fis.available());
			ps.write(buffer, 0, len);
			ps.flush();
		}else{
			fis = new FileInputStream("webContent/notFound.html");
			ps.println(status+"404 Not Found");
			ps.println(Server);
			ps.println(type+"text/html");
			ps.println(length+fis.available());
			ps.println();
			ps.flush();
			//响应体
			byte[] buffer = new byte[fis.available()];
			int len = fis.read(buffer, 0, fis.available());
			ps.write(buffer, 0, len);
			ps.flush();
		}
		fis.close();
	}
	
	public String getType(String path){
		if(path.endsWith(".html")){
			return "text/html";
		}if(path.endsWith(".mp3")){
			return "audio/mpeg";
		}if(path.endsWith(".mp4")){
			return "video/mp4";
		}if(path.endsWith("jpeg")){
			return "image/jpeg";
		}else
			return "text/plain";
	}
}
