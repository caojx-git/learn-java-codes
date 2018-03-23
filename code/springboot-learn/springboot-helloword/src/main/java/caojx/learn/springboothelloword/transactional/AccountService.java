package caojx.learn.springboothelloword.transactional;

/**
 * 帐号Service接口
 * @author caojx
 * Created on 2018/3/22 下午下午10:18
 */
public interface AccountService {

    /**
     * 转账
     * @param fromUserId
     * @param toUserId
     * @param account
     */
    public void transferAccounts(int fromUserId,int toUserId,float account);
}
