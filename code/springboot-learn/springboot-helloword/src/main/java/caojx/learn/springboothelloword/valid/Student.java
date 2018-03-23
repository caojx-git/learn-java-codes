package caojx.learn.springboothelloword.valid;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author caojx
 * Created on 2018/3/23 下午上午11:06
 */
@Entity
@Table(name="t_student")
public class Student {
    @Id
    @GeneratedValue
    private Integer id;

    @NotEmpty(message="姓名不能为空！")
    @Column(length=50)
    private String name;

    @NotNull(message="年龄不能为空！")
    @Min(value=18,message="年龄必须大于18岁！")
    @Column(length=50)
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
