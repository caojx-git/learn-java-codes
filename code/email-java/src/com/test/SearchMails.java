package com.test;

import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.util.Properties;  
 
import javax.mail.Flags;  
import javax.mail.Folder;  
import javax.mail.Message;  
import javax.mail.Session;  
import javax.mail.Store;  
import javax.mail.search.AndTerm;  
import javax.mail.search.FromStringTerm;  
import javax.mail.search.SearchTerm;  
import javax.mail.search.SubjectTerm;  
 
/**  
 * 使用 java.mail.search 包中的 SearchTerm 类  
 * 在接收邮件时进行组合搜索、过滤  
 * @author haolloyin  
 * 
 * 删除收索邮件
 */ 
public class SearchMails {  
    public static void main(String[] args) throws Exception {  
        // 连接pop3服务器的主机名、协议、用户名、密码  
        String pop3Server = "pop3.yeah.net";  
        String protocol = "pop3";  
        String user = "testhao";  
        String pwd = "123456";  
          
        // 创建一个有具体连接信息的Properties对象  
        Properties props = new Properties();  
        props.setProperty("mail.store.protocol", protocol);  
        props.setProperty("mail.pop3.host", pop3Server);  
          
        // 使用Properties对象获得Session对象  
        Session session = Session.getInstance(props);  
        session.setDebug(true);  
          
        // 利用Session对象获得Store对象，并连接pop3服务器  
        Store store = session.getStore();  
        store.connect(pop3Server, user, pwd);  
          
        // 获得邮箱内的邮件夹Folder对象，以"读-写"打开  
        Folder folder = store.getFolder("inbox");  
        folder.open(Folder.READ_WRITE);  

// 搜索发件人为 test_hao@sina.cn 和主题为"测试1"的邮件  
        SearchTerm st = new AndTerm(  
                new FromStringTerm("test_hao@sina.cn"),  
                new SubjectTerm("测试1"));  
          
//      // 获得邮件夹Folder内的所有邮件Message对象  
//      Message [] messages = folder.getMessages();  
          
        // 不是像上面那样直接返回所有邮件，而是使用Folder.search()方法  
        Message [] messages = folder.search(st);  
        int mailCounts = messages.length;  
        System.out.println("搜索过滤到" + mailCounts + " 封符合条件的邮件！");  
          
        for(int i = 0; i < mailCounts; i++) {  
              
            String subject = messages[i].getSubject();  
            String from = (messages[i].getFrom()[0]).toString();  
              
            System.out.println("第 " + (i+1) + "封邮件的主题：" + subject);  
            System.out.println("第 " + (i+1) + "封邮件的发件人地址：" + from);  
              
            System.out.println("是否删除该邮件(yes/no)?：");  
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
            String input = br.readLine();  
            if("yes".equalsIgnoreCase(input)) {  
                // 直接输出到控制台中  
//              messages[i].writeTo(System.out);  
                // 设置删除标记，一定要记得调用saveChanges()方法  
                messages[i].setFlag(Flags.Flag.DELETED, true);  
                messages[i].saveChanges();  
                System.out.println("成功设置了删除标记！");  
            }             
        }  
        // 关闭连接时设置了删除标记的邮件才会被真正删除，相当于"QUIT"命令  
        folder.close(false);  
        store.close();  
    }  
}
