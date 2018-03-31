package caojx.learn.base;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * @author caojx
 * Created on 2018/3/29 下午下午1:02
 */
public class ConnectTest {

    private static String host="192.168.1.108"; // 服务器地址
    private static int port=9300; // 端口

    public static void main(String[] args) throws Exception{
        //这里有个Settings 等后面讲到集群再详解
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ConnectTest.host),ConnectTest.port));
        System.out.println(client);
        client.close();
    }
}
