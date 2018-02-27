package tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
/*
 * 处理ServerSokcet 接受socket对象
 * 再创建request 和 response
 * */
public class Handler extends Thread{
	private Socket socket;
	public Handler(Socket s){
		socket = s;
	}
	public void run(){
		InputStream in =null;
		OutputStream out = null;
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
			Requst req = new Requst(in);
			req.parseData();
			Response res = new Response(out);
			res.sendData(req.getPath());
		}catch(SocketException e){
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}catch(NullPointerException e){
			
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
				out.close();
				socket.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
