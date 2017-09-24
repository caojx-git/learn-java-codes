package personal.caojx;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * ProjectName: xml-java
 * ClassName:DOM4JTest.java
 *
 * @Description: Dom4j解析XML
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/9/24 下午9:45
 * @since JDK 1.8
 */
public class DOM4JTest {

    public static void main(String[] args) throws DocumentException {
        //解析books.xml文件
        //创建SAXReader的对象reader
        SAXReader reader = new SAXReader();
        //通过reader对象的read方法加载books.xml文件
        Document document = reader.read(DOM4JTest.class.getClassLoader().getResourceAsStream("books.xml"));
        //通过document对象获取根节点bookstore
        Element bookstore = document.getRootElement();
        Iterator it = bookstore.elementIterator();
        while(it.hasNext()){
            System.out.println("开始遍历某一本书");
            Element book = (Element) it.next();
            //获取book的属性名以及属性值
            List<Attribute> bookAttrs = book.attributes();
            for(Attribute attr: bookAttrs){
                System.out.println("属性名："+attr.getName()+"--属性值："+attr.getValue());
            }

            //获取book的节点名和节点值
            Iterator iterator = book.elementIterator();
            while (iterator.hasNext()){
                Element bookChild = (Element) iterator.next();
                System.out.println("节点名："+bookChild.getName()+"--节点值："+bookChild.getStringValue());
            }
            System.out.println("结束遍历某一本书");
        }

    }
}
