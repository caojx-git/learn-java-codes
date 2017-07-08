package personal.caojx.factory;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: PropertiesReader
 * @Description: properties文件读取类
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/7/2 22:08
 */
public class PropertiesReader {

    public Map<String,String> getProperties(){
        Properties properties = new Properties();
        Map<String,String> map = new HashMap<String, String>();
        try{
            InputStream in = PropertiesReader.class.getClassLoader().getResourceAsStream("type.properties");
            properties.load(in);
            Enumeration en = properties.propertyNames();
            while (en.hasMoreElements()){
                String key = (String) en.nextElement();
                String property = properties.getProperty(key);
                map.put(key,property);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
