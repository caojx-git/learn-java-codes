package caojx.learn.ioc.applicationcontext.customeventpublication;

/**
 * 自定义事件监਀器具体实现类SimpleMethodExecutionEventListener的定义
 *
 * @author caojx
 * @version $Id: SimpleMethodExecutionEventListener.java,v 1.0 2019-02-15 14:53 caojx
 * @date 2019-02-15 14:53
 */
public class SimpleMethodExecutionEventListener implements MethodExecutionEventListener {
    /**
     * 处理方法开始执行的时候发布的MethodExecutionEvent事件
     *
     * @param evt
     */
    @Override
    public void onMethodBegin(MethodExecutionEvent evt) {
        String methodName = evt.getMethodName();
        System.out.println("start to execute the method[" + methodName + "].");
    }

    /**
     * 处理方法执行将结束时候发布的MethodExecutionEvent事件
     *
     * @param evt
     */
    @Override
    public void onMethodEnd(MethodExecutionEvent evt) {
        String methodName = evt.getMethodName();
        System.out.println("finished to execute the method[" + methodName + "].");
    }
}