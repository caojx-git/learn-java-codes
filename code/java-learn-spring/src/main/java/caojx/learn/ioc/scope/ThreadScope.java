package caojx.learn.ioc.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义scope
 * <p>
 * 在Spring 2.0之后的版本中，容器提供了对scope的扩展点，这样，你可以根据自己的需要或者应 用的场景，来添加自定义的scope类型。
 * 需要说明的是，默认的singleton和prototype是硬编码到代码中 的，而request、session和global session，包括自定义scope类型，
 * 则属于可扩展的scope行列，它们都实 现了org.springframework.beans.factory.config.Scope接口
 * <p>
 * <p>
 * 自定义scope->注册到容器
 * <p>
 * 注册scope方式1：
 * 有了Scope的实现类之后，我们需要把这个Scope注册到容器中，才能供相应的bean定义使用。通 常情况下，我们可以使用ConfigurableBeanFactory
 * 的以下方法注册自定义scope
 * void registerScope(String scopeName, Scope scope);
 * <p>
 * 比如：
 * Scope threadScope = new ThreadScope();
 * beanFactory.registerScope("thread",threadScope);
 * <p>
 * 注册scope方式2：
 * 除了直接编码调用ConfigurableBeanFactory的registerScope来注册scope，Spring还提供了一个专门用于统一注册自定义scope的 BeanFactoryPostProcessor实现
 * 即org.springframework.beans.factory.config.CustomScopeConfigurer。对于ApplicationContext来说，因为它可以自动识别并加载BeanFactoryPostProcessor，
 * 所以我们就可以直接在配置文件中，通过这个CustomScopeConfigurer注册来ThreadScope
 */
public class ThreadScope implements Scope {

    private final ThreadLocal threadScope = new ThreadLocal() {
        protected Object initialValue() {
            return new HashMap();
        }
    };

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map scope = (Map) threadScope.get();
        Object object = scope.get(name);
        if (object == null) {
            object = objectFactory.getObject();
            scope.put(name, object);
        }
        return object;
    }

    @Override
    public Object remove(String name) {
        Map scope = (Map) threadScope.get();
        return scope.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable runnable) {

    }

    @Override
    public Object resolveContextualObject(String name) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
