# Java的XML写入

## 一、DOM生成XML

1.DomTest2.java
```java
package personal.caojx;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName: DomTest2
 * @Description: Dom生成XML
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/9/05 13:07
 */
public class DomTest2 {

	public static void main(String[] args){
		//1.创建一个DocumentBuilderFactory对象
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		//2.创建一个DocumentBuilder对象
		try {
			DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
			//3.创建一个document对象
			Document document = documentBuilder.newDocument();
			document.setXmlStandalone(true);//不包含dtd
			//4.创建根节点
			Element bookstore = document.createElement("bookstore");
			//5.向bookstore根节点中添加子节点
			Element book = document.createElement("book");

			Element name = document.createElement("name");
			name.setTextContent("张三");
			bookstore.appendChild(book);
			//6.向book中添加属性
			book.setAttribute("id","1");

			//7.向book中添加子节点
			book.appendChild(name);

			//将根节点添加到dom中
			document.appendChild(bookstore);

			//通过transformerFactory将dom树写入到xml中
			//创建TransformerFactory
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			//创建Transformer
			Transformer transformer = transformerFactory.newTransformer();
			//是否换行
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");
			//缩进4个空格
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			//将dom树写入到xml中
			transformer.transform(new DOMSource(document),new StreamResult(new File("dom.xml")));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
```

生成结果:
```xml
<?xml version="1.0" encoding="UTF-8"?><bookstore>
    <book id="1">
        <name>张三</name>
    </book>
</bookstore>

```

## 二、SAX生成XML
1.SAXTest2.java
```java
package personal.caojx;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import sun.jvm.hotspot.oops.OopUtilities;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName: SAXTest2
 * @Description: SAX生成XML
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/8/25 13:07
 */
public class SAXTest2 {

    public static void main(String[] args){
        //1.创建SAXTransformerFactory
        SAXTransformerFactory saxTransformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        try {
            //2.通过SAXTransformerFactory对象创建一个transformerHandler
            TransformerHandler transformerHandler = saxTransformerFactory.newTransformerHandler();
            //3.通过transformerHandler对象创建一个transformer
            Transformer transformer = transformerHandler.getTransformer();
            //4.通过Transformer对生成的xml格式进行设置
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            //5.创建一个Result
            File file = new File("sax.xml");
            //6.创建result对象，并且使其与handler关联
            Result result = new StreamResult(file);
            transformerHandler.setResult(result);

            //7.利用handler对xml的文件内容进行编写
            //打开document
            transformerHandler.startDocument();
            //创建bookstore节点
            AttributesImpl attributes = new AttributesImpl();
            transformerHandler.startElement("","", "bookstore", attributes);
            //添加book节点和属性
            attributes.clear();
            attributes.addAttribute("","","id","","1");
            transformerHandler.startElement("","","book",attributes);
            //添加name节点和文本
            attributes.clear();
            transformerHandler.startElement("","","name",attributes);
            transformerHandler.characters("张三".toCharArray(),0,"张三".length());
            transformerHandler.endElement("","","name");

            transformerHandler.endElement("","","book");

            transformerHandler.endElement("","","bookstore");
            transformerHandler.endDocument();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
```

生成结果:
```xml
<?xml version="1.0" encoding="UTF-8"?><bookstore>
    <book id="1">
        <name>张三</name>
    </book>
</bookstore>
```

## 三、JDOM生成XML

1.JDomTest2.java
```java
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
```
生成结果:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rss version="2.0">
  <channel>
    <title>&lt;![CDATA[国内最新新闻]]&gt;</title>
  </channel>
</rss>
```

## 三、DOM4J生成XML

1.DOM4JTest2.java
```java
package personal.caojx;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.Iterator;
import java.util.List;

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
```
生成结果:
```xml
<?xml version="1.0" encoding="GBK"?>
<rss version="2.0">
  <channel>
    <title><![CDATA[国内最新新闻]]></title>
  </channel>
</rss>

```