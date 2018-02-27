package com.briup.run.dao;

import java.util.List;

import com.briup.run.common.exception.DataAccessException;
import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.bean.Messagerecord;

public interface IMessengerDao {
	//查找新邮件数量
	Integer findNewMessageNum(String nickname) throws DataAccessException;
	
	//查找会员个数
	Integer findMemberinfoNum() throws DataAccessException;
	
	//查找指定数量的会员
	List<Memberinfo> findOneMemberinfo(int sum) throws DataAccessException;
	
	//通过年龄，性别，城市查找朋友
	List findFriends(String age, String gender, String city) throws DataAccessException;
	
	//保存短信
	void saveMessage(Messagerecord message) throws DataAccessException;
	
	//获取已发的邮件
	List<Messagerecord> listSendMessage(String senderName) throws DataAccessException;
	
	//获取已收的邮件
	List<Messagerecord> listRecieveMessage(String recieveName) throws DataAccessException;
	
	//查看邮件
	Messagerecord findMessage(Long id) throws DataAccessException;
	
	//删除收到的邮件
	void deleteRecieveMessage(Long id) throws DataAccessException;
	
	//删除已发的邮件
	void deleteSendMessage(Long id) throws DataAccessException;
}
