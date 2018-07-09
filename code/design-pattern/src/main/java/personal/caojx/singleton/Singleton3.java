package personal.caojx.singleton;

/**
 * 单例模式-懒汉模式线程安全问题
 *这里实现了懒汉式的单例，但是熟悉多线程并发编程的朋友应该可以看出，在多线程并发下这样的实现是无法保证实例实例唯一的，
 * 甚至可以说这样的失效是完全错误的，下面我们就来看一下多线程并发下的执行情况，这里为了看到效果，我们对代码做一小点修改
 */
public class Singleton3 {

    //1.将构造方法私有化，不允许外部直接创建对象
    private Singleton3(){
    }

    //2.创建类的唯一实例，使用private staitc
    private static Singleton3 singleton;

    //3.提供一个获取实例的方法，使用public static
    public static  Singleton3 getInstance(){
        try {
            synchronized (Singleton3.class){
                if(singleton == null){
                    //创建实例之前可能会有一些准备性的耗时工作
                    Thread.sleep(300);
                    singleton = new Singleton3();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return singleton;
    }


}
