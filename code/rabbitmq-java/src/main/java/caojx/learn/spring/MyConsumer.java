package caojx.learn.spring;

public class MyConsumer {

    //执行具体的业务方法
    public void listen(String foo) {
        System.out.println("消费者："+foo);
    }
    
}
