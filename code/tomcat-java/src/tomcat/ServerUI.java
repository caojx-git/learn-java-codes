package tomcat;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerUI {
	JFrame frame;
	JLabel label;	//xxx服务器
	JPanel panel;	//放置服务器关闭按钮跟端口号设置
	JPanel panel2;	//用于删除指定的客户端
	JTextField text; // 输入目录服务器的端口号
	JTextField text2; // 输入需要断开连接的客户端ip
	JTextArea textArea;//显示当前选中额客户端的连接日期
	JScrollPane srollPane;//放置文本域
	JButton but; // 关闭服务器
	JButton but2; // 关闭客户端指定的客户端连接
	JButton but3; //启动服务器
	File floder;
	String[] files;
	ServerSocket server;
	Socket s;
	static boolean onoff=false;
	public static void main(String[] args){
		ServerUI ui = new ServerUI();
		ui.initGui();
		ui.control(ui);
	}

	public void initGui() {
		// 创建窗体
		frame = new JFrame("服务器");
		// 创建面板添加添加刷新按钮和路径文本显示框
		panel = new JPanel();
		panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		label = new JLabel("服务器端口号");
		text = new JTextField(20);
		text2 = new JTextField(26);
		// 建立文本域,显示目录下的文件或目录名
		textArea = new JTextArea();
		srollPane = new JScrollPane(textArea);
		frame.add(srollPane, BorderLayout.CENTER);
		but = new JButton("关闭服务器");
		// 监听刷新按钮，获取当前路径下的目录名或文件名
		but.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				ServerUI ui = new ServerUI();
				try {
					server.close();
					new JOptionPane().showMessageDialog(null, "服务器已关闭");
					textArea.setText("");
					System.out.println("服务器已关闭");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		});
		but2 = new JButton("断开该客户端");
		but2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				text2.getText().trim();
				new JOptionPane().showMessageDialog(null, "删除成功");
			}

		});
		but3 =new JButton("启动服务器");
		but3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				ServerUI ui = new ServerUI();
				ui.setOnOff(true);
			}

		});
		panel.add(label, FlowLayout.LEFT);
		panel.add(text, FlowLayout.CENTER);
		panel.add(but, FlowLayout.RIGHT);
		panel.add(but3);
		panel2.add(text2);
		panel2.add(but2);
		// 添加面板
		frame.add(panel, BorderLayout.NORTH);
		frame.add(panel2, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(250, 150, 600, 400);
		frame.setVisible(true);
	}
	public String getText(){
		return text.getText().trim();
	}
	public static boolean setOnOff(boolean flag){
		return onoff=flag;
	}
	public void control(ServerUI ui){
		while(true){
			if(onoff){
				try {
					onoff = false;
					int port = Integer.parseInt(ui.getText());
					System.out.println("服务器启动"+port);
					server = new ServerSocket(port);
					while (true) {
						// 并建立连接 ，返回socket对象
						s = server.accept();
						if(s!=null){
							ui.textArea.setText(ui.textArea.getText()+"\n"+s.getInetAddress()+new Date().toString());
						}
						Handler h = new Handler(s);
						h.start();
					}
				}catch(SocketException e2){
					control(ui);
				}catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}

}
