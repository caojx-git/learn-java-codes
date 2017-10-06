package personal.caojx;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.EscapeStrategy;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * ProjectName: xml-java
 * ClassName: JDomTest.java
 *
 * @Description: JDOM生成XML
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/9/24 下午9:12
 * @since JDK 1.8
 */
public class JDomTest2 {

   public static void main(String[] args){
      //1.生成一个根节点
       Element rss = new Element("rss");
       rss.setAttribute("version","2.0");
       //2.生成document
       Document document = new Document(rss);
       Element channel = new Element("channel");
       rss.addContent(channel);
       Element title = new Element("title");
       title.setText("<![CDATA[国内最新新闻]]>");
       channel.addContent(title);

       Format format = Format.getPrettyFormat();
       format.setEscapeStrategy(new EscapeStrategy() {
           public boolean shouldEscape(char c) {
               return false;
           }
       });
       //3.创建XMLOutputter
       XMLOutputter outputter = new XMLOutputter(format);
       //4.利用outputter将document对象转换成xml
       try {
           outputter.output(document, new FileOutputStream(new File("jdom.xml")));
       } catch (IOException e) {
           e.printStackTrace();
       }

   }
}
