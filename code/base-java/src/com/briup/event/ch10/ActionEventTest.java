package com.briup.event.ch10;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ActionEventTest {
	private JFrame frame;
	private JButton but;
	//private JTextField text;
	private JTextArea text;
	public ActionEventTest(){
		init();
		addListener();
	}
	public void init(){
		frame = new JFrame("test");
		frame.setBounds(100,50,500,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		but = new JButton("button");
		//text = new JTextField();
		text = new JTextArea();
		text.setFont(new Font("微软雅黑",Font.BOLD,36));
		but.setFont(new Font("微软雅黑",Font.BOLD,36));
		frame.setLayout(new GridLayout(2,1));
		frame.add(text);
		frame.add(but);
		//frame.pack();
		frame.setVisible(true);
	}
	public void addListener(){
		but.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("action");
				
			}
			
		});
		/*but.addMouseListener(new MouseListener(){//鼠标监听事件

			@Override
			public void mouseClicked(MouseEvent e) { //点击
				System.out.println("mouseClicked");
				System.out.println(e.getButton());
				System.out.println(e.getClickCount());
				System.out.println(e.getClickCount());
				System.out.println(e.getSource());
				System.out.println(e.getXOnScreen());
				e.getClickCount();
				e.getSource();
				e.getX();
				e.getXOnScreen();
			}

			@Override
			public void mousePressed(MouseEvent e) {//按压
				System.out.println("mousePressed");
			}

			@Override
			public void mouseReleased(MouseEvent e) {//释放
				System.out.println("mouseReleased");
			}

			@Override
			public void mouseEntered(MouseEvent e) {//移入
				System.out.println("mouseEntered");
			}

			@Override
			public void mouseExited(MouseEvent e) {//移出
				System.out.println("mouseExited");
			}
			
		});*/
		but.addMouseMotionListener(new MouseMotionListener(){
			public void mouseMoved(MouseEvent e){
			/*	int x = e.getX();
				int y = e.getY();
				text.setText("x:"+x+"y:"+y);*/
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				text.setText("x:"+x+"y:"+y);
			}
		});
		
		text.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				//System.out.println("keytype");
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(" keyPressed");
				System.out.println(e.getKeyChar());
				System.out.println(e.getKeyCode());
				System.out.println(e.getKeyLocation());
				e.getKeyChar();//字符
				e.getKeyCode();//按键编号
				e.getKeyLocation();//按键的位置，当键盘中有重复的按键时对其位置编号
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				 System.out.println("keyReleased");
				
			}
			
		});
		
		//焦点监听
		text.addFocusListener(new FocusListener(){
			public void focusLost(FocusEvent e){//释放光标
				System.out.println("focusLost");
			}
			public void focusGained(FocusEvent e){ //获取光标
				System.out.println("focusGained");
			}
		});
	}
	
	public static void main(String[] args){
		new ActionEventTest();
	}
}
