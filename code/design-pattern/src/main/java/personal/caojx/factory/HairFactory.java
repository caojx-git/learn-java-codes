package personal.caojx.factory;

import java.util.Map;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: HairFactory
 * @Description: 发型工厂
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/7/2 21:51
 */
public class HairFactory {

    /*
    * 根据类型来创建对象，
    * @param key
    * @return
    */
    public HairInterface getHair(String key) {
        if ("left".equals(key)) {
            return new LeftHair();
        } else if ("right".equals(key)) {
            return new RightHair();
        }
        return null;
    }

    /**
     * 根据类名称来生产对象
     *
     * @param className
     * @return
     */
    public HairInterface getHairByClass(String className) {
        HairInterface hair = null;
        try {
            hair = (HairInterface) Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return hair;
    }

    /**
     * 根据类型名称的key值来产生对象
     * @param key
     * @return
     */
    public HairInterface getHairByClassKey(String key){
        HairInterface hair = null;
        try {
            Map<String,String> map = new PropertiesReader().getProperties();
            hair = (HairInterface) Class.forName(map.get(key)).newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return hair;
    }

}
