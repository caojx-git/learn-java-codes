package tomcat;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Server {
	public static void main(String[] args){
		new Server();
	}
	public ServerSocket server;
	public boolean flag = true;
	public Server(){
		init();
		addListener();
	}
	public void startServer(int port){
		try {
			flag = true;
			server = new ServerSocket(port);
			while(flag){
				Socket s = server.accept();
				Handler h = new Handler(s);
				h.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				server.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	public void stopServer(){
		flag = false;
		if(server!=null){
			try{
				server.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	private JFrame frame;
	private JTextField field;
	private JButton start;
	private JButton stop;
	private JPanel panel;
	public void init(){
		frame = new JFrame("server");
		frame.setBounds(200, 100, 400, 200);
		frame.setDefaultCloseOperation(3);
		panel = new JPanel();
		field = new JTextField(5);
		start = new JButton("start");
		stop = new JButton("stop");
		panel.add(field);
		panel.add(start);
		panel.add(stop);
		frame.add(panel, BorderLayout.NORTH);
		frame.setVisible(true);
	}
	public void addListener(){
		start.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				final int port = Integer.parseInt(field.getText().trim());
				Thread thread = new Thread(new Runnable(){
					@Override
					public void run() {
						startServer(port);
					}
				});
				thread.start();
			}
		});
		stop.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				stopServer();
			}
			
		});
	}
}
