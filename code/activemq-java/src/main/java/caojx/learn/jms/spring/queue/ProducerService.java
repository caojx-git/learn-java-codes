package caojx.learn.jms.spring.queue;

/**
 * @author caojx
 * Created on 2018/3/13 下午下午9:25
 * 接收消息业务接口
 */
public interface ProducerService {

    void sendMessage(String message);
}
