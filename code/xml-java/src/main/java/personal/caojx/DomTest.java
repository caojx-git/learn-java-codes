package personal.caojx;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @ClassName: DomTest
 * @Description: Dom解析XML
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/8/25 13:07
 */
public class DomTest {

	public static void main(String[] args){
		//1.创建一个DocumentBuilderFactory对应
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		//2.创建一个DocumentBuilder对应
		try {
			DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
			//3.通过DocumentBuilder的parse方法读取xml文件返回Document对应
			Document document = documentBuilder.parse(DomTest.class.getClassLoader().getResourceAsStream("books.xml"));

			//获取所有的book节点
			NodeList bookList = document.getElementsByTagName("book");
			//获取所有的book的节点的长度
			System.out.println("一共有"+bookList.getLength()+"本书");
			//遍历所有的book节点
			for(int i = 0; i < bookList.getLength(); i++){
				//通过索引获取一个book节点，nodeLis的索引值从0开始
				Node book =bookList.item(i);
				//获取book节点的所有属性的集合
				NamedNodeMap attrs = book.getAttributes();
				System.out.println("第"+(i+1)+"本书共有"+attrs.getLength()+"个属性");
				//遍历book的属性
				for(int j = 0; j < attrs.getLength(); j++){
					System.out.println("下边开始遍历第"+(i+1)+"本书的内容");
					//通过item方法获取book节点的某一个属性
					Node attr = attrs.item(j);
					//获取属性名
					System.out.println("属性名："+attr.getNodeName());
					//获取属性值
					System.out.println("属性值："+attr.getNodeValue());
					System.out.println("结束遍历第"+(i+1)+"本书的内容");
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
