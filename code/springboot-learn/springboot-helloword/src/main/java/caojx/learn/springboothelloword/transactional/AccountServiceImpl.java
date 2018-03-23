package caojx.learn.springboothelloword.transactional;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * 帐号Service实现类
 * @author caojx
 * Created on 2018/3/22 下午下午10:19
 */
@Transactional
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;

    /**
     * 转账
     *
     * @param fromUserId
     * @param toUserId
     * @param account
     */
    @Override
    public void transferAccounts(int fromUserId, int toUserId, float account) {
        Account fromUserAccount=accountDao.getOne(fromUserId);
        fromUserAccount.setBalance(fromUserAccount.getBalance()-account);
        accountDao.save(fromUserAccount); // fromUser扣钱

        Account toUserAccount=accountDao.getOne(toUserId);
        toUserAccount.setBalance(toUserAccount.getBalance()+account);

        //假设转账的时候假如出现异常，业务类或业务方法中没有使用@Transactional控制事务，则会出现钱转出了，收钱人没有收到的情况
        int zero = 1/0;
        accountDao.save(toUserAccount); // toUser加钱
    }
}
