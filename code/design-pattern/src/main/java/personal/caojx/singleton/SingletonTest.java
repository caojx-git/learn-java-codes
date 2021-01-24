package personal.caojx.singleton;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: SingletonTest
 * @Description: 单例模式测试类
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/6/29 22:11
 */
public class SingletonTest {

    public static void main(String[] args){
        //饿汉模式
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        if(s1==s2){
            System.out.printf("s1和s2是同一实例");
        }else {
            System.out.printf("s1和s2不是是同一实例");
        }

        //懒汉模式
        Singleton2 s3 = Singleton2.getInstance();
        Singleton2 s4 = Singleton2.getInstance();
        if(s3==s4){
            System.out.printf("s3和s4是同一实例");
        }else {
            System.out.printf("s3和s4不是是同一实例");
        }
    }
}
