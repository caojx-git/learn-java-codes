package caojx.learn.ioc.beanfactorypostprocessor;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * 假设需要对yyyy/MM/dd形式的日期格式转换提供支持。虽然可以直接让PropertyEditor实现类
 * 去实现java.beans.PropertyEditor接口，不过，通常情况下，我们可以直接继承
 * java.beans.Property.EditorSupport类以避免实现java.beans.PropertyEditor接口的所有方法。
 * 就好像这次，我们仅 仅 让 DatePropertyEditor 完 成 从 String 到 java.util.Date 的 转 换，
 * 只 需 要 实 现 setAsText(String)方法，而其他方法一概不管。该自定义PropertyEditor类定义如代码清单4-44 所示。
 * <p>
 * <p>
 * 如果仅仅是支持单向的从String到相应对象类型的转换，只要覆写方法setAsText(String)即 9 可。如果想支持双向转换，需要同时考虑getAsText()方法的覆写。
 */
public class DatePropertyEditorA extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        //使用BeanFactory添加PostProcess，将PostProcess配置在配置文件中的时候，无法将配置文件中的属性通property注入，所以这里写死yyyy/MM/dd，
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy/MM/dd");
        Date dateValue = dateTimeFormatter.parseDateTime(text).toDate();
        setValue(dateValue);
    }

}
