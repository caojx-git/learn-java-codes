package com.test;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class Demo2 {

	public static void main(String[] args) throws Exception {
		try {
			Properties props = new Properties();
			props.setProperty("mail.smtp.auth", "true");//客户端需要开启认证，如果是服务端不用开启认证
			props.setProperty("mail.transport.protocol", "smtp");//设置协议
			props.setProperty("mail.smtp.port", "25");//设置协议
			props.setProperty("mail.host", "smtp.yeah.net");
			Session session = Session.getInstance(props,
					new Authenticator()
					{
						protected PasswordAuthentication getPasswordAuthentication()
						{
							return new PasswordAuthentication("CJXiangemail@yeah.net","Wysmtp123");//Wysmtp123是客户端授权码
						}
					}
			);//每次返回原型
			
			//Session session2 = Session.getDefaultInstance(props);//返回单利的的session
			session.setDebug(true); //开启调试模式
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("CJXiangemail@yeah.net"));//发件人可以不写
			message.setSubject("中文主题");//主题可以不写
			//CC抄送 收件人可以看到你抄送给谁了，BCC暗送不可以,设置发送类型
			message.setRecipients(RecipientType.TO, InternetAddress.parse("1103493257@qq.com")); //给多个人发送
			message.setContent("<span style='color:red'>hello uuuuuu</span>", "text/html;charset=gbk");//指定用html代码发送

			//Message msg = new MimeMessage(session,new FileInputStream("resouce\\demo3.eml"));
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
