package personal.caojx.singleton;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: Singleton
 * @Description: 单例模式案例Singleton 懒汉模式
 * 应用场合：有些对象只需要一个就足够了，如古代皇帝
 * 作用：保证整个应用程序中某个实例只有一个
 * 类型：饿汉模式、懒汉模式
 * 懒汉模式：在需要的时候取看类中有没有，没有的话创建，有的话直接使用。
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/6/29 22:05
 */
public class Singleton {

    //1.将构造方法私有化，不允许外部直接创建对象
    private Singleton(){
    }

    //2.创建类的唯一实例，使用private staitc
    private static Singleton singleton;

    //3.提供一个获取实例的方法，使用public static
    public static  Singleton getInstance(){
        if(singleton == null){
            singleton = new Singleton();
        }
        return singleton;
    }
}
