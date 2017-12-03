package personal.caojx;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

/**
 * ProjectName: xml-java
 * ClassName:DOM4JTest.java
 *
 * @Description: Dom4j生成XML
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/9/24 下午9:45
 * @since JDK 1.8
 */
public class DOM4JTest2 {

    public static void main(String[] args) throws DocumentException {
        //1.创建document对象，代表整个xml文档
        Document document = DocumentHelper.createDocument();
        //2.创建根节点
        Element rss = document.addElement("rss");
        //3.向rss节点中添加version属性
        rss.addAttribute("version","2.0");
        //4.生成子节点和节点内容
        Element channel = rss.addElement("channel");
        Element title = channel.addElement("title");
        title.setText("<![CDATA[国内最新新闻]]>");
        //设置生成xml的格式
        OutputFormat outputFormat = OutputFormat.createPrettyPrint();
        outputFormat.setEncoding("GBK");
        //5.生成xml文件
        File file = new File("dom4j.xml");
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileOutputStream(file), outputFormat);
            //设置对于特殊字符是否转义,默认是true转义
            writer.setEscapeText(false);
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
