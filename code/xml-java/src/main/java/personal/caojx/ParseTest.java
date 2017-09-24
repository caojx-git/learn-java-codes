package personal.caojx;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * ProjectName: xml-java
 * ClassName:
 *
 * @Description: 4中XML解析方式性能比较
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/9/24 下午10:18
 * @since JDK 1.8
 */
public class ParseTest {

    public static void main(String[] args) throws Exception{
        ParseTest parseTest = new ParseTest();
        System.out.println("开始测试");
        //DOM的性能测试
        long start = System.currentTimeMillis();
        parseTest.domXmlParse();
        System.out.println("DOM:"+(System.currentTimeMillis()-start));

        //SAX性能测试
        start = System.currentTimeMillis();
        parseTest.saxXmlParse();
        System.out.println("SAX:"+(System.currentTimeMillis()-start));

        //JDOM性能测试
        start = System.currentTimeMillis();
        parseTest.jdomXmlParse();
        System.out.println("JDOM:"+(System.currentTimeMillis()-start));

        //DOM4J性能测试
        start = System.currentTimeMillis();
        parseTest.dom4jXmlParse();
        System.out.println("DOM4J:"+(System.currentTimeMillis()-start));

        System.out.println("结束测试");
    }

    //dom解析
    public void domXmlParse(){
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

    //sax解析
    public void saxXmlParse(){
        //1.获取一个SAXParserFactory的实例
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            //2.通过factory获取SAXParser实例
            SAXParser parser = factory.newSAXParser();
            //3.创建SAXParserHandler对象
            SAXParserHandler handler = new SAXParserHandler();
            //使用SAXParserHandler处理解析
            parser.parse(SAXTest.class.getClassLoader().getResourceAsStream("books.xml"),handler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //jdom解析
    public void jdomXmlParse(){
        //对books.xml文件的jdom行解析
        //1.创建一个SAXBuilder对象
        SAXBuilder saxBuilder = new SAXBuilder();
        //3.创建输入流，将xml家在到输入流中
        InputStream in = JDomTest.class.getClassLoader().getResourceAsStream("books.xml");
        try {
            //3.通过saxBuilder的build方法，将输入流加载到saxBuilder中
            org.jdom2.Document document = saxBuilder.build(in);
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

    //DOM4J解析
    public void dom4jXmlParse() throws DocumentException {
        //解析books.xml文件
        //创建SAXReader的对象reader
        SAXReader reader = new SAXReader();
        //通过reader对象的read方法加载books.xml文件
        org.dom4j.Document document = reader.read(DOM4JTest.class.getClassLoader().getResourceAsStream("books.xml"));
        //通过document对象获取根节点bookstore
        org.dom4j.Element bookstore = document.getRootElement();
        Iterator it = bookstore.elementIterator();
        while(it.hasNext()){
            System.out.println("开始遍历某一本书");
            org.dom4j.Element book = (org.dom4j.Element) it.next();
            //获取book的属性名以及属性值
            List<org.dom4j.Attribute> bookAttrs = book.attributes();
            for(org.dom4j.Attribute attr: bookAttrs){
                System.out.println("属性名："+attr.getName()+"--属性值："+attr.getValue());
            }

            //获取book的节点名和节点值
            Iterator iterator = book.elementIterator();
            while (iterator.hasNext()){
                org.dom4j.Element bookChild = (org.dom4j.Element) iterator.next();
                System.out.println("节点名："+bookChild.getName()+"--节点值："+bookChild.getStringValue());
            }
            System.out.println("结束遍历某一本书");
        }
    }
}
