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
