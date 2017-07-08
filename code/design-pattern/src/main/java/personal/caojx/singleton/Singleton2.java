package personal.caojx.singleton;

/**
 * Copyright: Copyright (c) 2017 Asiainfo
 *
 * @ClassName: Singleton
 * @Description: 单例模式案例Singleton 饿汉模式
 * 应用场合：有些对象只需要一个就足够了，如古代皇帝
 * 作用：保证整个应用程序中某个实例只有一个
 * 类型：饿汉模式、懒汉模式
 * 饿汉模式：在你没有自己创建对象的时候，就帮我们创建好了，直接使用就好。
 * 区别：饿汉模式的特点是加载类比较慢，因为加载类的时候需要创建对象，但运行时获取对象的速度比较快，并且饿汉模式是线程安全的。
 *       懒汉模式的特点是加载类比较快，因为不需要创建实例，但是在获取对象的时候速度比较慢，懒汉模式线程不安全。
 * @version: v1.0.0
 * @author: caojx
 * @date: 2017/6/29 22:05
 */
public class Singleton2 {

    //1.将构造方法私有化，不允许外部直接创建对象
    private Singleton2(){
    }

    //2.创建类的唯一实例，使用private staitc
    private static Singleton2 singleton = new Singleton2();

    //3.提供一个获取实例的方法，使用public static
    public static Singleton2 getInstance(){
        return singleton;
    }
}
