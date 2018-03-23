package caojx.learn.springboothelloword.valid;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 学生Dao接口
 * @author caojx
 * Created on 2018/3/23 下午上午11:10
 */
public interface StudentDao extends JpaRepository<Student, Integer> {
}
