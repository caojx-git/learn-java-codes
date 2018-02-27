package com.briup.ch09;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CardLayoutTest {
	private JFrame frame;
	private CardLayout card;
	private JButton[] buttons;
	
	public CardLayoutTest(){
		frame = new JFrame("cardlayouttest");
		buttons = new JButton[10];
		for(int i=0;i<buttons.length;i++){
			buttons[i]= new JButton("button"+i);
		}
		card = new CardLayout();
		init();
	}
	public void init(){
		frame.setBounds(200, 100, 500, 400);
		frame.setDefaultCloseOperation(3);
		frame.setLayout(card);
		for(int i=0;i<buttons.length;i++){
			buttons[i].addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					card.next(frame.getContentPane()); //循环换卡
				}
			});
			//frame.add(buttons[i]);
			frame.add("button"+i,buttons[i]);
		}
		//card.last(frame.getContentPane());//初始化为最后按钮9
		//
		card.show(frame.getContentPane(), "button5");//初始化为按钮5
		frame.setVisible(true);
	}
	public static void main(String[] args){
		new CardLayoutTest();
	}
}
