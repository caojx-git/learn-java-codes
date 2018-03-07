package learn.ehcache.spring.entity;

/**
 * @author caojx
 * Created on 2018/3/7 下午下午8:59
 */
public class User {

    /**
     * 用户编号
     */
    private Long id;

    /**
     * 用户名
     */
    private String name;

    public User(){
        super();
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
