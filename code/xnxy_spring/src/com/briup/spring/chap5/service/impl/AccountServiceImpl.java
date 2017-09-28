package com.briup.spring.chap5.service.impl;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.briup.spring.chap5.dao.AccountDao;
import com.briup.spring.chap5.service.AccountService;

/**
 * 转账业务接口的具体实现
 * */
public class AccountServiceImpl implements AccountService {
	
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
	/*	accountDao.outMoney(out, money);
		int i = 1/0;
		accountDao.inMoney(in, money);
		*/
		
		//使用事务模板
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				accountDao.outMoney(out, money);
				int i = 1/0;
				accountDao.inMoney(in, money);
			}
		});
	}

}
