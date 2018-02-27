package com.briup.ch09;

import java.awt.*;
import javax.swing.*;

public class MyFrame {
	public MyFrame(){
		//1.选择容器
		JFrame frame = new JFrame("myFrame");
		//设置大小
		/*frame.setSize(500, 400);
		frame.setLocation(400, 200);*/
		frame.setBounds(400, 200, 500, 400);
		//使程序关闭,如果不添加则点击X只是使程序不可见，程序还处于运行中
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//添加布局管理器
		BorderLayout l = new BorderLayout();
		frame.setLayout(l);
		//添加组件
		JPanel p = new JPanel();
		FlowLayout flow = new FlowLayout(FlowLayout.LEFT,20,10);  //设置间距
		p.setLayout(flow);
		for(int i=0;i<5;i++){
			p.add(new JButton("按钮"+i));
		}
		frame.add(p, BorderLayout.NORTH);
		frame.add(new JButton("button2"), BorderLayout.SOUTH);
		frame.add(new JButton("button3"), BorderLayout.WEST);
		frame.add(new JButton("button4"), BorderLayout.EAST);
		frame.add(new JButton("button5"), BorderLayout.CENTER);
		//添加panel容器
	
		//frame.add(p);
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
		new MyFrame();
	}
}
