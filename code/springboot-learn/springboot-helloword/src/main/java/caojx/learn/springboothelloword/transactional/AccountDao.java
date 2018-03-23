package caojx.learn.springboothelloword.transactional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 账户Dao接口
 * @author caojx
 * Created on 2018/3/22 下午下午10:17
 */
public interface AccountDao extends JpaRepository<Account, Integer> {
}
