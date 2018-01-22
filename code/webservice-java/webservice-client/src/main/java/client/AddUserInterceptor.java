package client;

import com.sun.org.apache.xml.internal.utils.DOMHelper;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * 客户端自定义拦截器
 */
public class AddUserInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private String name;
    private String password;


    public AddUserInterceptor(String name, String password) {
        super(Phase.PRE_PROTOCOL); //准备协议化的时候调用，还有其他的常量见Phase
        this.name = name;
        this.password = password;
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
    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {

        /**
         * <test>
         *     <name></name>
         *     <password></password>
         * <test/>
         */
        List<Header> headerList = soapMessage.getHeaders();
        Document document = DOMHelper.createDocument();
        Element rootEle = document.createElement("test");
        Element nameEle = document.createElement("name");
        nameEle.setTextContent(name);
        rootEle.appendChild(nameEle);
        Element passwordEle = document.createElement("password");
        passwordEle.setTextContent(password);
        rootEle.appendChild(passwordEle);
        headerList.add(new Header(new QName("test"), rootEle));

        System.out.println("client handleMessage()");
    }
}
