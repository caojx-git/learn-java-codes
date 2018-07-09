package personal.caojx.singleton;

/**
 * 这里假设在创建实例前有一些准备性的耗时工作要处理
 * 从这里执行结果可以看出，单例的线程安全性并没有得到保证
 */
public class MyThread3 extends Thread {

    @Override
    public void run() {
        System.out.println(Singleton3.getInstance().hashCode());
    }

    public static void main(String[] args) {

        MyThread3[] mts = new MyThread3[10];
        for(int i = 0 ; i < mts.length ; i++){
            mts[i] = new MyThread3();
        }

        for (int j = 0; j < mts.length; j++) {
            mts[j].start();
        }
    }
}
