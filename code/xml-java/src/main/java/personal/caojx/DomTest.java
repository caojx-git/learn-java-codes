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
		//1.创建一个DocumentBuilderFactory对象
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		//2.创建一个DocumentBuilder对象
		try {
			DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
			//3.通过DocumentBuilder的parse方法读取xml文件返回Document对象
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
				//解析book节点的子节点
				NodeList childNodes = book.getChildNodes();
				//遍历childNodes获取每个节点的节点名和节点值,注意空白也会当成节点
				System.out.println("第"+(i+1)+"个节点共有"+childNodes.getLength()+"个子节点");
				for(int k = 0; k < childNodes.getLength(); k++){
					//由于空白也会被当成text节点，所有我们这里进行排除text节点
					if(childNodes.item(k).getNodeType() == Node.ELEMENT_NODE){
						//获取element类型节点中的节点名
						System.out.print("第"+(k+1)+"个节点名："+childNodes.item(k).getNodeName());
						//获取节点值方式1，会获取第一个子节点的节点值
						// System.out.println("--节点值是："+childNodes.item(k).getFirstChild().getNodeValue());
						//获取节点值方式2，会获取该节点的所有子节点值
						System.out.println("--节点值是："+childNodes.item(k).getTextContent());

					}
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
