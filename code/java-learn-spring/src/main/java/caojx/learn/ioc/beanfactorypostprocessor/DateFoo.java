package caojx.learn.ioc.beanfactorypostprocessor;

import java.util.Date;

/**
 * 如果有类似于DateFoo这样的类对java.util.Date类型的依赖声明，通常情况下，会以代码清单 4-45所示的形式声明并将该类配置到容器中。
 */
public class DateFoo {

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
