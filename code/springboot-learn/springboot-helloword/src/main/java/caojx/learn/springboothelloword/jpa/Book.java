package caojx.learn.springboothelloword.jpa;




import javax.persistence.*;

/**
 * @author caojx
 * Created on 2018/3/22 下午下午7:58
 */

@Entity
@Table(name="t_book")
public class Book {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length=100)
    private String bookName;

    @Column(length = 100)
    private String author;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {

        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
