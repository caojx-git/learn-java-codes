package caojx.learn.ioc.applicationcontext.customeventpublication;

import java.util.ArrayList;
import java.util.List;

/**
 * MethodExeuctionEventPublisher时间发布者类定义
 * <p>
 * 组合事件类和监听器，发布事件。有了自定义事件和自定义事件监听器，剩下的就是发布事件，
 * 然后让相应的监听器监听并处理事件了。通常情况下，我们会有一个事件发布者（EventPublisher），
 * 它本身作为事件源，会在合适的时点，将相应事件发布给对应的事件监听器。
 * 代码清单5-15给出了针 对MethodExecutionEvent的事件发布者类的定义。
 *
 * @author caojx
 * @version $Id: MethodExeuctionEventPublisher.java,v 1.0 2019-02-15 14:54 caojx
 * @date 2019-02-15 14:54
 */
public class MethodExeuctionEventPublisher {

    private List<MethodExecutionEventListener> listeners = new ArrayList<MethodExecutionEventListener>();

    public void methodToMonitor() {
        MethodExecutionEvent event2Publish = new MethodExecutionEvent(this, "methodToMonitor");
        publishEvent(MethodExecutionStatus.BEGIN, event2Publish);
        // 执行实际的方法逻辑
        // ...
        publishEvent(MethodExecutionStatus.END, event2Publish);
    }

    protected void publishEvent(MethodExecutionStatus status, MethodExecutionEvent methodExecutionEvent) {
        List<MethodExecutionEventListener> copyListeners = new ArrayList<MethodExecutionEventListener>(listeners);
        for (MethodExecutionEventListener listener : copyListeners) {
            if (MethodExecutionStatus.BEGIN.equals(status)) {
                listener.onMethodBegin(methodExecutionEvent);
            } else {
                listener.onMethodEnd(methodExecutionEvent);

            }
        }
    }

    public void addMethodExecutionEventListener(MethodExecutionEventListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(MethodExecutionEventListener listener) {
        if (this.listeners.contains(listener)) {
            this.listeners.remove(listener);
        }
    }

    public void removeAllListeners() {
        this.listeners.clear();
    }

    public static void main(String[] args) {
        MethodExeuctionEventPublisher eventPublisher = new MethodExeuctionEventPublisher();
        eventPublisher.addMethodExecutionEventListener(new SimpleMethodExecutionEventListener());
        eventPublisher.methodToMonitor();
    }
}