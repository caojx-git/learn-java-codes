package client;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import javax.xml.namespace.QName;


/**
 * 动态调用webservice，无需生成客户端代码
 * 静态调用需要生成客户端代码
 * 参考：https://blog.csdn.net/qq_26562641/article/details/71534715
 */
public class WsDynamicClient {
    
    public static void main(String[] args) {
        
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        // url为调用webService的wsdl地址
        org.apache.cxf.endpoint.Client client = dcf.createClient("http://127.0.0.1:8989/ws01/hellows?wsdl");
        // namespaceURL是命名空间对应wsdl中的targetNamespace，localPart是方法名
        QName name = new QName("http://ws01.server/", "sayHello");
        // paramvalue为参数值
        String xmlStr = "tom";
        Object[] objects;
        try {
            objects = client.invoke(name, xmlStr);
            System.out.println(objects[0].toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
