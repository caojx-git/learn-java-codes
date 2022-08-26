package caojx.learn.ioc.applicationcontext.customeventpublication;

import java.util.EventObject;

/**
 * 自定义事件-针对方法执行事件的自定义事件类型定义
 * <p>
 * 给出自定义事件类型（define your own event object）。为了针对具体场景可以区分具体的事件 类型，
 * 我们需要给出自己的事件类型的定义，通常做法是扩展java.util.EventObject类来实现自定 义的事件类型。
 * 我们此次定义的自定义事件类型见代码清单5-12。
 *
 * @author caojx
 * @version $Id: MethodExecutionEvent.java,v 1.0 2019-02-15 14:49 caojx
 * @date 2019-02-15 14:49
 */
public class MethodExecutionEvent extends EventObject {

    private static final long serialVersionUID = -71960369269303337L;
    private String methodName;

    public MethodExecutionEvent(Object source) {
        super(source);
    }

    public MethodExecutionEvent(Object source, String methodName) {
        super(source);
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}