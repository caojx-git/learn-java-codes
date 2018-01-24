package server.ws02.cxf.interceptor2;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;

/**
 * 检查用户的拦截器
 */
public class CheckUserInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private String name;
    private String password;


    public CheckUserInterceptor() {
        super(Phase.PRE_PROTOCOL); //准备协议化的时候调用，还有其他的常量见Phase
    }

    /**
     * <Envelope>
     *      <head>
     *          <test>
     *              <name></name>
     *              <password></password>
     *          </test>
     *          <test>
     *              <name></name>
     *              <password></password>
     *          </test>
     *      </head>
     *      <body>
     *          <sayHello>
     *              <arg0></arg0>
     *          </sayHello>
     *      </body>
     * </Envelope>
     * @param soapMessage
     * @throws Fault
     */
    @SuppressWarnings("deprecation")
    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {

        /**
         * <test>
         *     <name></name>
         *     <password></password>
         * <test/>
         */
       Header header = soapMessage.getHeader(new QName("test"));
       if(header != null){
           Element testEle = (Element) header.getObject();
           String name = testEle.getElementsByTagName("name").item(0).getTextContent();
           String password = testEle.getElementsByTagName("password").item(0).getTextContent();
           if("tom".equals(name) && "123".equals(password)){
               System.out.println("server 通过拦截器。。。。");
               return;
           }
           //不能通过
           System.out.println("没有通过拦截器。。。");
           throw  new Fault(new RuntimeException("请求需要一个正确的用户名或密码!"));
       }
        System.out.println("server handleMessage()");
    }
}
