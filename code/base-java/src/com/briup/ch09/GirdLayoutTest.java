package com.briup.ch09;

import java.awt.*;
import javax.swing.*;
public class GirdLayoutTest extends JFrame {
	public GirdLayoutTest(){
		setTitle("GirdLyaoutTest");
		setBounds(100,100,500,400);
		setDefaultCloseOperation(3);	//3=JFrame.EXIT_ON_CLOSE
		GridLayout grid = new GridLayout(3,4,10,10);	//3行，4列，10横向间距，5纵向间距
		setLayout(grid);
		for(int i=0;i<12;i++){
			add(new JButton("button"+i));
		}
		//在指定坐标插入一个新的组件
		//add(new JButton("button14"),13);
		setVisible(true);
	}
	public static void main(String[] args){
		new GirdLayoutTest();
	}
}
