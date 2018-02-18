package learn.activiti.variable;

import java.io.Serializable;

/**
 * @author caojx
 * Created on 2018/2/6 下午8:52
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 5341610004178940035L;

    /**
     * id
     */
    private Long id;

    /**
     * 名字
     */
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
