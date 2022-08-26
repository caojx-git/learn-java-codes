package caojx.learn.ioc.beanfactorypostprocessor;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

import java.beans.PropertyEditor;

/**
 * Spring 2.0之前通常是通过CustomEditorConfigurer的customEditors属性来指定自定义的 PropertyEditor。
 * 2.0之后，比较提倡使用propertyEditorRegistrars属性来指定自定义的PropertyEditor。
 * 不过，这样我们就需要再多做一步工作，就是给出一个org.springframework.beans.
 * PropertyEditorRegistrar的实现。这也很简单，代码清单4-47给出了相应的实例。
 */
public class DatePropertyEditorRegistrar implements PropertyEditorRegistrar {

    private PropertyEditor propertyEditor;

    public void registerCustomEditors(PropertyEditorRegistry peRegistry) {
        peRegistry.registerCustomEditor(java.util.Date.class, getPropertyEditor());
    }

    public PropertyEditor getPropertyEditor() {
        return propertyEditor;
    }

    public void setPropertyEditor(PropertyEditor propertyEditor) {
        this.propertyEditor = propertyEditor;
    }
}
