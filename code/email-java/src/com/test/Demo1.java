package com.test;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class Demo1 {

	public static void main(String[] args) {
		try{
		Properties props = new Properties();
		 MailSSLSocketFactory sf = new MailSSLSocketFactory();  
		    sf.setTrustAllHosts(true);  
		    //mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory
		props.put("mail.smtp.auth", "true");//开启认证
		props.put("mail.transport.protocol", "smtp");//设置协议
		props.put("mail.smtp.socketFactory", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(props);
		session.setDebug(true); //开启调试模式
	
			Message message = new MimeMessage(session);
			message.setText("hi 巧慧，这是我的第一个邮件程序");
			message.setFrom(new InternetAddress("389715062@qq.com"));//显示的邮件发送人
			Transport transport = session.getTransport();
			transport.connect("smtp.qq.com", 25, "389715062@qq.com", "");
			transport.sendMessage(message,new Address[]{new InternetAddress("m15062606224@163.com")});
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}
