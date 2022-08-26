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
public class DatePropertyEditor extends PropertyEditorSupport {


    private String datePattern;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(getDatePattern());
        Date dateValue = dateTimeFormatter.parseDateTime(text).toDate();
        setValue(dateValue);
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public String getDatePattern() {
        return datePattern;
    }


}
