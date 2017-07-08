package personal.caojx.factory;

import junit.framework.TestCase;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: HairFactoryTest
 * @Description: 发型工厂测试类
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/7/2 21:56
 */
public class HairFactoryTest extends TestCase {

    public static void main(String[] args){

        //1.通过工厂创建实例实例一
        HairFactory factory = new HairFactory();
        HairInterface left = factory.getHair("left");
        HairInterface right = factory.getHair("right");
        left.draw();
        right.draw();

        //2.通过工厂创建实例二
        HairInterface left2 = factory.getHairByClass("personal.caojx.factory.LeftHair");
        left2.draw();

        //2.通过工厂创建实例三
        HairInterface left3 = factory.getHairByClassKey("left");
        left3.draw();


    }
}
