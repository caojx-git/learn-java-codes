package caojx.learn.springboothelloword.transactional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 账户Controoler类
 * @author caojx
 * Created on 2018/3/22 下午下午10:21
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountService accountService;

    @RequestMapping("/transfer")
    public String transferAccounts(){
        try{
            accountService.transferAccounts(1, 2, 200);
            return "ok";
        }catch(Exception e){
            return "no";
        }
    }
}
