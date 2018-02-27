package com.briup.run.service.impl;

import java.util.List;
import java.util.Random;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.bean.Messagerecord;
import com.briup.run.common.exception.DataAccessException;
import com.briup.run.common.exception.MessengerServiceException;
import com.briup.run.common.transaction.HibernateTransaction;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.dao.IMemberDao;
import com.briup.run.dao.IMessengerDao;
import com.briup.run.service.IMessengerService;

public class IMessagerServiceImpl implements IMessengerService {

	private IMessengerDao messageDao;
	
	public IMessengerDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(IMessengerDao messageDao) {
		this.messageDao = messageDao;
	}

	// 通过名字查找新邮件个数
	@Override
	public Integer findNewMessageNum(String nickname)
			throws MessengerServiceException {
		int num = 0;
		try {
			num = messageDao.findNewMessageNum(nickname);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessengerServiceException("message", e.getCause());
		}
		return num;
	}

	// 查找一个用户
	@Override
	public List<Memberinfo> findOneMemberinfo() throws MessengerServiceException {
		List<Memberinfo> list = null;
		try {
			 int num = messageDao.findMemberinfoNum();
			 if(num==1){
				 throw new MessengerServiceException("没有更多的用户");
			 }
			 Random random = new Random();
			 int rownum = random.nextInt(num);
			 if(rownum==0){
				 rownum = 1;
			 }
			 System.out.println(rownum);
			 list = messageDao.findOneMemberinfo(rownum);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessengerServiceException("message", e.getCause());
		}
		return list;
	}
	// 通过年龄，性别，城市查找好友
	@Override
	public List<Memberinfo> findFriends(String age, String gender, String city)
			throws MessengerServiceException {
		List<Memberinfo> list = null;
		try {
			 list = messageDao.findFriends(age, gender, city);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessengerServiceException("message", e.getCause());
		}
		return list;
	}

	// 保存短信
	@Override
	public void saveMessage(Messagerecord message)
			throws MessengerServiceException {
		try {
			//设置状态
			message.setStatus(0L);
			messageDao.saveMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessengerServiceException("message", e.getCause());
		}

	}

	// 发件箱
	@Override
	public List<Messagerecord> listSendMessage(String senderName)
			throws MessengerServiceException {
		List<Messagerecord> list = null;
		try {
			list = messageDao.listSendMessage(senderName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessengerServiceException("message", e.getCause());
		}
		return list;
	}

	// 收件箱
	@Override
	public List<Messagerecord> listRecieveMessage(String recieveName)
			throws MessengerServiceException {
		List<Messagerecord> list = null;
		try {
			list = messageDao.listRecieveMessage(recieveName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessengerServiceException("message", e.getCause());
		}
		return list;
	}

	// 通过ID查找短信
	@Override
	public Messagerecord findMessage(Long id) throws MessengerServiceException {
		Messagerecord messagerecord = null;
		try {
			messagerecord = messageDao.findMessage(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessengerServiceException("message", e.getCause());
		}
		return messagerecord;
	}

	// 通过ID删除收到短息
	@Override
	public void delRecieveMessage(Long id) throws MessengerServiceException {
		try {
			messageDao.deleteRecieveMessage(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessengerServiceException("message", e.getCause());
		}

	}

	// 通过ID删除发送短息
	@Override
	public void delSendMessage(Long id) throws MessengerServiceException {
		try {
			messageDao.deleteSendMessage(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MessengerServiceException("message", e.getCause());
		}

	}

	//查找会员个数
	@Override
	public Integer findMemberinfoNum() throws DataAccessException {
		int num = 0;
		try {
			num = messageDao.findMemberinfoNum();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException("message", e.getCause());
		}
		return num;
	}

}
