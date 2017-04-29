package edu.xnxy.caojx.filemanager.web.util;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Description:邮箱工具类
 * JavaMail发送邮件三大步骤
 * 1. 创建会话(javax.mail.Session)
 * 2. 编写消息(javax.mail.Message)
 * 3.发送消息(javax.mail.Transport)
 * Created by caojx on 17-4-29.
 */
public class MailUtil {

    private static Logger log = Logger.getLogger(MailUtil.class);

    /**
     * Description:发送邮件
     *
     * @param mailContent  发送内容
     * @param receiverMail 接收者邮箱
     */
    public static  void sendMail(String mailContent, String receiverMail) throws Exception{
        try {
            //1.创建Session会话
            // 创建一个有具体连接信息的Properties对象
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.host", "smtp.qq.com");
            props.setProperty("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");
            props.setProperty("mail.debug", "true");
            //创建session，每次返回原型
            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("389715062@qq.com", "bmavbwumszchbgic");
                        }
                    }
            );
            //2创建消息Message
            Message message = new MimeMessage(session);
            //设置发邮件的原地址
            message.setFrom(new InternetAddress("389715062@qq.com"));
            //设置接收人
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiverMail));
            message.setSubject("找回密码!");
            message.setText(mailContent);

            //3.发送邮件
            Transport transport = session.getTransport();
            transport.send(message);

        } catch (MessagingException e) {
            log.error("邮件发送失败",e);
            throw new Exception("邮件发送失败",e);
        }
    }

}
