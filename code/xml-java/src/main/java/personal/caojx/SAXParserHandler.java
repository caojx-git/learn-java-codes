package personal.caojx;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * ProjectName: xml-java
 * ClassName: SAXParserHandler.java
 *
 * @Description: SAX解析Handler类
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/9/21 下午9:58
 * @since JDK 1.8
 */
public class SAXParserHandler extends DefaultHandler {

    private int bookIndex;

    /**
     * 用来解析xml元素，开始
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //调用DefaultHandler类的startElement
        super.startElement(uri, localName, qName, attributes);
        //开始解析book元素的属性
        if(qName.equals("book")){
            bookIndex++;
            System.out.println("开始遍历第"+bookIndex+"本书的内容");
            //1.已知book元素的属性名，根据属性名获取属性值
            //String value = attributes.getValue("id");
            //System.out.println(value);
            //2.不知到book下的属性名以及个数，如果获取属性名以及属性值
            int num = attributes.getLength();
            for(int i = 0; i < num; i++){
                System.out.print("book元素的第"+(i+1)+"个属性名是："+attributes.getQName(i));
                System.out.println("--属性值是："+attributes.getValue(i));
            }
        }else if(!qName.equals("book") && !qName.equals("bookstore")){
            System.out.print("节点名是："+qName+" ");
        }


    }

    /**
     * 用来解析xml元素，结束
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //endElement
        super.endElement(uri, localName, qName);
        //判断是否针对一本书已经遍历结束
        if(qName.equals("book")){
            System.out.println("结束遍历第"+bookIndex+"本书的内容");
        }
    }

    /**
     * 获取节点值
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String value = new String(ch, start, length);
        //去掉空格和换行
        if(!"".equals(value.trim())){
            System.out.println("节点值是："+value);
        }
    }

    /**
     * 用来标示文件解析开始
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("sax解析开始");
    }

    /**
     * 用来标示文件解析结束
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("sax解析结束");
    }
}
