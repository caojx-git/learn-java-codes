package caojx.learn.ioc.applicationcontext.customeventpublication;

import java.util.EventListener;

/**
 * 自定义事件监听器MethodExecutionEventListener定义
 * <p>
 * 实现针对自定义事件类的事件监听器接口（define custom event listener）。
 * 自定义的事件监听 器需要在合适的时机监听自定义的事件，如刚声明的MethodExecutionEvent，
 * 我们可以在方法开始执行的时候发布该事件，也可以在方法执行即将结束之际发布该事件。相应地，自定义的事件监听器
 * <p>
 * 需要提供方法对这两种情况下接收到的事件进行处理 。
 * 代码清单5-13给出了针对MethodExecutionEvent的事件监听器接口定义。
 *
 * @author caojx
 * @version $Id: MethodExecutionEventListener.java,v 1.0 2019-02-15 14:51 caojx
 * @date 2019-02-15 14:51
 */
public interface MethodExecutionEventListener extends EventListener {

    /**
     * 处理方法开始执行的时候发布的MethodExecutionEvent事件
     */
    void onMethodBegin(MethodExecutionEvent evt);

    /**
     * 处理方法执行将结束时候发布的MethodExecutionEvent事件
     */
    void onMethodEnd(MethodExecutionEvent evt);
}