package personal.caojx;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * @ClassName: SAXTest
 * @Description: SAX解析XML
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/8/25 13:07
 */
public class SAXTest {

    public static void main(String[] args){
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
}
