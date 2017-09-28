package com.briup.spring.chap5.service.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.briup.spring.chap5.dao.AccountDao;
import com.briup.spring.chap5.service.AccountService;

/**
 * 转账业务接口的具体实现
 * 
 * @Transactional中注解的属性：
 * propagation:事务的传播行为
 * isolation:事务的隔离级别
 * readOnly:是否只读
 * rollbackFor：发生那些异常回滚
 * noRollbackFor:发生那些异常不回滚
 * */
@Transactional(propagation=Propagation.REQUIRED,readOnly=true,rollbackFor={RuntimeException.class, Exception.class})
public class AccountServiceImpl2 implements AccountService {
	
	private AccountDao accountDao;
	
	
	private TransactionTemplate transactionTemplate;  //事务管理模板
		

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}



	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}



	public AccountDao getAccountDao() {
		return accountDao;
	}



	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}


	@Override
	public void transfer(final String out, final String in, final Double money) {
		accountDao.outMoney(out, money);
		//int i = 1/0;
		accountDao.inMoney(in, money);
	}

}
