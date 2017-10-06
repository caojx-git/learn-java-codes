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
