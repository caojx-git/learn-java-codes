package com.test;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class Demo3 {

	public static void main(String[] args) throws Exception{
		Session session = Session.getInstance(new Properties());
		MimeMessage msg = new MimeMessage(session);
		//MimeUtility.encodeText() 解决中文命名的问题
		//下边这种方式，友好的显示发件人
		msg.setFrom(new InternetAddress(MimeUtility.encodeText("传智播客") + " <itcast_test@sina.com>"));
		msg.setSubject("你们的Java培训真的是最牛的吗？");		
		//回件地址
		msg.setReplyTo(new Address[]{new InternetAddress("lili@126.com")});
		//收件人
		msg.setRecipients(RecipientType.TO,InternetAddress.parse(MimeUtility.encodeText("黎活明") + " <llm@itcast.cn>," + MimeUtility.encodeText("张孝祥") + " <zxx@itcast.cn>"));
		//复杂类型邮件
		MimeMultipart msgMultipart = new MimeMultipart("mixed");
		msg.setContent(msgMultipart);

		MimeBodyPart attch1 = new MimeBodyPart();		
		MimeBodyPart attch2 = new MimeBodyPart();		
		MimeBodyPart content = new MimeBodyPart();
		msgMultipart.addBodyPart(attch1);		
		msgMultipart.addBodyPart(attch2);		
		msgMultipart.addBodyPart(content);
		//设置附件1
		DataSource ds1 = new FileDataSource("E:\\笔记\\email.txt");
		DataHandler dh1 = new DataHandler(ds1 );
		attch1.setDataHandler(dh1);
		//给附件取名
		attch1.setFileName(MimeUtility.encodeText(MimeUtility.encodeText("java_email开发.txt")));
		//设置附件2
		DataSource ds2 = new FileDataSource("src/hel.png");
		DataHandler dh2 = new DataHandler(ds2 );
		attch2.setDataHandler(dh2);	
		//给附件取名
		attch2.setFileName("hel.png");
		
		//设置内容
		MimeMultipart bodyMultipart = new MimeMultipart("related");
		content.setContent(bodyMultipart);
		MimeBodyPart htmlPart = new MimeBodyPart();		
		MimeBodyPart gifPart = new MimeBodyPart();		
		bodyMultipart.addBodyPart(htmlPart);
		bodyMultipart.addBodyPart(gifPart);		

		DataSource gifds = new FileDataSource("src\\hel.png");
		DataHandler gifdh = new DataHandler(gifds);		
		gifPart.setDataHandler(gifdh);
		//"hel.png" 是 img 标签中的src中的名字
		gifPart.setHeader("Content-Location", "hel.png");
		htmlPart.setContent("我自己用程序生成和发送的邮件哦！<img src='hel.png'>"
					, "text/html;charset=gbk");
		
		msg.saveChanges();//设置好后保存
		
		OutputStream out = new FileOutputStream("E:\\demo3.eml");
		msg.writeTo(out);//将生成的邮件写到一个文件中
		out.close();
		
		
	}

}
