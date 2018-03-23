package caojx.learn.springboothelloword.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 图书Dao接口，要求实现JpaRepository
 * @author caojx
 * Created on 2018/3/22 下午下午8:19
 */
public interface BookDao extends JpaRepository<Book,Integer>, JpaSpecificationExecutor<Book> {

    /**
     * 使用SQL查询
     * @param name
     * @return
     */
    @Query("select b from Book b where b.bookName like %?1%")
    public List<Book> findByName(String name);

    /**
     * 使用SQL查询
     * @param n
     * @return
     */
    @Query(value="select * from t_book order by RAND() limit ?1",nativeQuery=true)
    public List<Book> randomList(Integer n);
}
