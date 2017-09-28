package com.briup.spring.chap3;

import org.springframework.transaction.PlatformTransactionManager;

public interface CustomerDao{
	public abstract void saveCustomer(Customer customer);
	public abstract void deleteCustomer(Customer customer);
}
