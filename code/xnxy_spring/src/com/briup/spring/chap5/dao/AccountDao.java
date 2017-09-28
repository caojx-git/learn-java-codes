package com.briup.spring.chap5.dao;

/**
 * 转账的dao层接口
 * */
public interface AccountDao {
	
	/**
	 * @param out 转出账户
	 * @param money 转账金额
	 * */
	public void outMoney(String out,Double money);
	
	/**
	 * @param in 转入账户
	 * @param money 转账金额
	 * */
	public void inMoney(String in,Double money);

}
