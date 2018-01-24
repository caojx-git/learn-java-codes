package client;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionTest {

    public static void main(String[] args) throws Exception{
        String date = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:sayHello xmlns:ns2=\"http://interceptor1.cxf.ws02.server/\"><arg0>jack</arg0></ns2:sayHello></soap:Body></soap:Envelope>";
        String path = "http://127.0.0.1:8989/ws02/interceptor1";
        doPost(path,date);
    }

    public static void doPost(String path, String data) throws Exception {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type","text/xml;charset=utf-8");

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data.getBytes("utf-8"));
        outputStream.flush();

        int responseCode = connection.getResponseCode();
        if(responseCode == 200){
            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            StringBuilder strBuild=new StringBuilder();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = bufferedInputStream.read(buffer)) != -1 ){
                strBuild.append(new String(buffer,0,len));
            }
            System.out.println("文件的内容："+strBuild.toString());
        }
    }
}
