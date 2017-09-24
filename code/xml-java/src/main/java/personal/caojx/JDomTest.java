package personal.caojx;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * ProjectName: xml-java
 * ClassName: JDomTest.java
 *
 * @Description: JDOM解析XML
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/9/24 下午9:12
 * @since JDK 1.8
 */
public class JDomTest {

   public static void main(String[] args){
       //对books.xml文件的jdom行解析
       //1.创建一个SAXBuilder对象
       SAXBuilder saxBuilder = new SAXBuilder();
       //3.创建输入流，将xml家在到输入流中
       InputStream in = JDomTest.class.getClassLoader().getResourceAsStream("books.xml");
       try {
           //3.通过saxBuilder的build方法，将输入流加载到saxBuilder中
           Document document = saxBuilder.build(in);
           //通过Document对象获取xml文件的根节点
           Element rootElement = document.getRootElement();
           //获取根节点下的子节点
           List<Element> bookList = rootElement.getChildren();

           //继续进行解析
           for(Element book: bookList){
               System.out.println("开始解析第"+(bookList.indexOf(book)+1)+"书");
               //解析book的属性，获取book的所有属性
               List<Attribute> attributes = book.getAttributes();
               //遍历属性(不清楚属性名和属性值)
               for(Attribute attribute: attributes){
                   //获取属性名
                  String attrName =  attribute.getName();
                   //获取属性值
                   String attrValue = attribute.getValue();
                   System.out.println("属性名："+attrName+"--属性值"+attrValue);
               }
               //对book节点的子节点的节点名称和节点值的遍历
               List<Element> bookChilds = book.getChildren();
               for(Element child: bookChilds){
                   System.out.println("节点名:"+child.getName()+"--节点值:"+child.getValue());
               }
               System.out.println("结束解析第"+(bookList.indexOf(book)+1)+"书");
           }
       } catch (JDOMException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}
