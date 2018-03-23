package caojx.learn.springboothelloword.transactional;

import javax.persistence.*;

/**
 * @author caojx
 * Created on 2018/3/22 下午下午10:14
 */
@Entity
@Table(name="t_account")
public class Account {

    /**
     * id 编号
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 用户名
     */
    @Column(length=50)
    private String userName;

    /**
     * 余额
     */
    private float balance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
