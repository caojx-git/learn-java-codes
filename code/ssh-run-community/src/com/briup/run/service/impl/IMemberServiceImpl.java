package com.briup.run.service.impl;

import java.util.Date;
import java.util.List;

import com.briup.run.common.bean.Blackrecord;
import com.briup.run.common.bean.Friendrecord;
import com.briup.run.common.bean.Graderecord;
import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.bean.Memberspace;
import com.briup.run.common.bean.Pointaction;
import com.briup.run.common.bean.Pointrecord;
import com.briup.run.common.exception.DataAccessException;
import com.briup.run.common.exception.MemberServiceException;
import com.briup.run.common.transaction.HibernateTransaction;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.common.util.MD5;
import com.briup.run.common.util.RandomChar;
import com.briup.run.dao.IMemberDao;
import com.briup.run.dao.IMessengerDao;
import com.briup.run.service.IMemberService;
import com.briup.run.service.IMessengerService;

public class IMemberServiceImpl implements IMemberService {

	private IMemberDao memberDao;
	private IMessengerDao messageDao;
	
	public IMemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(IMemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public IMessengerDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(IMessengerDao messageDao) {
		this.messageDao = messageDao;
	}

	/*
	 * 注册
	 */
	@Override
	public void registerMemberinfo(Memberinfo memberinfo)
			throws MemberServiceException {
		try {
			Memberinfo memberinfo2 = findMemberinfoByName(memberinfo.getNickname());
			Date date = new Date();
			if (memberinfo2 == null) {
				//密码进行md5加密
				String password = memberinfo.getPassword();
				password = MD5.getMD5Str(password);
				memberinfo.setPassword(password);
				//获取注册积分
				Pointaction pointaciont =  findPointactionByPointAction("REGISTER");
				Long point = pointaciont.getPoint();
				//设置注册积分
				memberinfo.setPoint(point);
				//获取注册日期
				memberinfo.setRegisterdate(date);
				//创建积分记录
				Pointrecord pointrecord = new Pointrecord(pointaciont, memberinfo.getNickname(), date);
				//保存积分记录
				save(pointrecord);
				//获取用户积分等级
				Graderecord graderecord = findMemberinfoLevel(point);
				memberinfo.setGraderecord(graderecord);
				memberDao.saveOrUpdateMemberinfo(memberinfo);
			} else {
				throw new MemberServiceException("用户已经被注册");
			}
			//是否有推荐人
			String recName = memberinfo.getRecommender();
			Memberinfo recommender = findMemberinfoByName(recName);
			if(recommender != null){
				//判断推荐人的动作的积分
				Pointaction pointaciont2 = findPointactionByPointAction("RECOMMEND");
				//获取总积分
				Long spoint = pointaciont2.getPoint()+recommender.getPoint();
				recommender.setPoint(spoint);
				//创建积分记录
				Pointrecord pointrecord2 = new Pointrecord(pointaciont2, recName, date);
				//保存积分记录
				save(pointrecord2);
				//获取用户积分等级
				Graderecord graderecord2 = findMemberinfoLevel(spoint);
				recommender.setGraderecord(graderecord2);
				//更新推荐人
				saveOrUpdate(recommender);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
	}

	/*
	 * 通过名字查找会员
	 */
	@Override
	public Memberinfo findMemberinfoByName(String nickname)
			throws MemberServiceException {
		Memberinfo memberinfo = null;
		try {
			memberinfo = memberDao.findMemberinfoByName(nickname);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
		return memberinfo;
	}

	/*
	 * 登录
	 */
	@Override
	public Memberinfo login(String username, String passwd)
			throws MemberServiceException {
		Memberinfo memberinfo = null;
		try {
			memberinfo = memberDao.findMemberinfoByName(username);
			if (memberinfo != null) {
				if (memberinfo.getPassword().equals(passwd)) {// passwd是加密后传入的
					//设置登录时间
					Date date = new Date();
					Date date2 = memberinfo.getLatestdate();
					//判断是否同一天登陆
					if(date2==null||(date.getDate()!=date2.getDate())){
						//设置积分
						Pointaction pointaction = findPointactionByPointAction("LOGIN");
						long spoint = memberinfo.getPoint()+pointaction.getPoint();
						memberinfo.setPoint(spoint);
						//设置积分记录
						Pointrecord pointrecord = new Pointrecord(pointaction, memberinfo.getNickname(), date);
						save(pointrecord);
						//设置积分等级
						Graderecord graderecord = findMemberinfoLevel(spoint);
						memberinfo.setGraderecord(graderecord);
					}
					memberinfo.setIsonline(1L);
					memberinfo.setLatestdate(date);
					//更新用户信息
					saveOrUpdate(memberinfo);
					return memberinfo;
				} else {
					throw new MemberServiceException("密码错误");
				}
			} else {
				throw new MemberServiceException("用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
	}

	/*
	 * 退出--下线
	 */
	@Override
	public void logout(String nickname) throws MemberServiceException {
		try {
			Memberinfo memberinfo = memberDao.findMemberinfoByName(nickname);
			memberinfo.setIsonline(0L);
			memberDao.saveOrUpdateMemberinfo(memberinfo);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}

	}
	// 查找前几名用户
	@Override
	public List<Memberinfo> findMemberinfoByNum(int number)
			throws MemberServiceException {
		List<Memberinfo> list = null;
		try {
			list = memberDao.findMemberinfoByNum(number);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
		return list;
	}

	/*
	 * 查找在线人数
	 */
	@Override
	public int findMemberinfoOnline() throws MemberServiceException {
		int number = 0;
		try {
			number = memberDao.findMemberinfoOnline();
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
		return number;
	}

	/*
	 * 查看用户等级
	 */
	@Override
	public Graderecord findMemberinfoLevel(Long point)
			throws MemberServiceException {
		Graderecord graderecord = null;
		try {
			graderecord = memberDao.findMemberinfoLevel(point);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
		return graderecord;
	}

	/*
	 * 保存或者更新用户 oldPasswd是加密后的密码
	 */
	@Override
	public Memberinfo saveOrUpDate(Memberinfo memberinfo, String oldPasswd)
			throws MemberServiceException {
		Memberinfo memberinfo2 = null;
		try {
			System.out.println(oldPasswd+"---------------------");
			System.out.println(memberinfo);
			oldPasswd = MD5.getMD5Str(oldPasswd);//对旧密码加密
			memberinfo2 = memberDao.findMemberinfoByName(memberinfo
					.getNickname());
			if (memberinfo2 == null) {
				throw new MemberServiceException("用户不存在");
			}
			if (memberinfo2.getPassword().equals(oldPasswd)) { // 密码验证正确
				String newpwd = memberinfo.getPassword();
				memberinfo2.setPassword(MD5.getMD5Str(newpwd));
				memberinfo2.setIsonline(0L);
				memberinfo2.setPasswordquestion(memberinfo.getPasswordquestion());
				memberinfo2.setPasswordanswer(memberinfo.getPasswordanswer());
				memberinfo2.setEmail(memberinfo.getEmail());
				memberinfo2.setGender(memberinfo.getGender());
				memberinfo2.setProvincecity(memberinfo.getProvincecity());
				memberinfo2.setPhone(memberinfo.getPhone());
				memberinfo2.setAddress(memberinfo.getAddress());
				memberDao.saveOrUpdateMemberinfo(memberinfo2);
				memberinfo2 = memberDao.findMemberinfoByName(memberinfo.getNickname());
			}else {
				throw new MemberServiceException("旧密码验证出错");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
		return memberinfo2;
	}

	/*
	 * 通过提示问题和答案来获取密码
	 */
	@Override
	public String getBackPasswd(String nickname, String pwdQuestion,
			String pwdAnswer) throws MemberServiceException {
		Memberinfo memberinfo = null;
		String newPasword = null;
		try {
			memberinfo = memberDao.findMemberinfoByName(nickname);
			if (memberinfo == null) {
				throw new MemberServiceException("用户不存在");
			} else if (!memberinfo.getPasswordquestion().equals(pwdQuestion)
					&& memberinfo.getPasswordanswer().equals(pwdAnswer)) {// 判断密保是否正确
				throw new MemberServiceException("密保错误");
			} else {
				newPasword = RandomChar.getChars(RandomChar.RANDOM_NUMBERS, 6);
				System.out.println("-----新密码：" + newPasword);
				String npwd = MD5.getMD5Str(newPasword);
				memberinfo.setPassword(npwd);
				memberDao.saveOrUpdateMemberinfo(memberinfo);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
		return newPasword;
	}

	/*
	 * 保存或更新用户信息
	 * */
	@Override
	public void saveOrUpdate(Memberinfo memberinfo)
			throws MemberServiceException {
		try {
			memberDao.saveOrUpdateMemberinfo(memberinfo);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
	}

	/*
	 * 保存或跟新用户好友
	 * */
	@Override
	public void saveOrUpdate(String selfname, String friendname)
			throws MemberServiceException {
		try {
			Friendrecord friendrecord = memberDao.findfriend(selfname, friendname);
			memberDao.saveOrUpdateFriend(friendrecord);
		} catch (Exception e) {
			throw new MemberServiceException("message", e.getCause());
		}
	}

	/*
	 * 列出用户所有的好友
	 * */
	@Override
	public List<Memberinfo> listFriend(String selfname)
			throws MemberServiceException {
		List<Memberinfo> list = null;
		try {
			list = memberDao.listFriend(selfname);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
		return list;
	}

	/*
	 * 好友转义到黑名单 先将好友从好友列表删除 在加入黑名单
	 */
	@Override
	public void moveToBlack(String selfname, String blackname)
			throws MemberServiceException {
		try {
			Friendrecord friendrecord = memberDao.findfriend(selfname,blackname);
			//移入黑名单后将好友从从好友名单中删除
			memberDao.deleleFriend(friendrecord);
			Blackrecord blackrecord = new Blackrecord(selfname, blackname);
			memberDao.saveOrUpdateFriend(blackrecord);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}

	}

	// 获取黑名单人员
	@Override
	public List<Memberinfo> listBlack(String selfname)
			throws MemberServiceException {
		List<Memberinfo> list = null;
		try {
			list = memberDao.listBlack(selfname);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
		return list;
	}

	// 查找好友
	@Override
	public Friendrecord findFriend(String selfname,String friend) throws MemberServiceException {
		Friendrecord friendrecord = null;
		try {
			friendrecord = memberDao.findfriend(selfname, friend);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
		return friendrecord;
	}
	//判断是否有个人空间
	@Override
	public Boolean isMemberspace(Long id) throws MemberServiceException {
		try {
			Memberspace memberspace = memberDao.findSpace(id);
			if (memberspace != null) {
				return true;
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
		return false;
	}

	/*
	 * 转到好友
	 */
	@Override
	public void moveToFriend(String selfName, String name_searching)
			throws MemberServiceException {
		try {
			//正常加好友
			Friendrecord friendrecord = memberDao.findfriend(selfName, name_searching);
			if(friendrecord == null){
				Friendrecord friendrecord2 = new Friendrecord(selfName, name_searching);
				memberDao.saveOrUpdateFriend(friendrecord2);
			}else {
				throw new MemberServiceException("该用户已经是我们的好友");
			}
			//如果是从黑名单中移入好友
			Blackrecord findBlack = memberDao.findBlack(selfName, name_searching);
			if (findBlack != null) {
				memberDao.deleleBlack(findBlack);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
	}

	//删除黑名单
	@Override
	public void deleleBlack(String selfName, String black)
			throws MemberServiceException {
		try {
			Blackrecord blackrecord = memberDao.findBlack(selfName, black);
			memberDao.deleleBlack(blackrecord);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("memsage", e.getCause());
		}

	}

	//删除好友
	@Override
	public void deleleFriend(String selfName, String friend)
			throws MemberServiceException {
		try {
			Friendrecord friendrecord = memberDao.findfriend(selfName, friend);
			if(friendrecord != null){
				memberDao.deleleFriend(friendrecord);
			}else {
				throw new MemberServiceException("好友不存在");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("memsage", e.getCause());
		}

	}

	@Override
	public void delSpace(Long id) throws MemberServiceException {

		try {
			Memberspace memberspace = memberDao.findSpace(id);
			memberDao.delSpace(memberspace);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("memsage", e.getCause());
		}

	}

	/*
	 * 通过用户动作名查找响应的Pointaction
	 */
	@Override
	public Pointaction findPointactionByPointAction(String actionName)
			throws MemberServiceException {
		Pointaction pointaction = null;
		try {
			pointaction = memberDao.findPointactionByPointAction(actionName);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
		return pointaction;
	}

	/*
	 * 保存积分记录
	 */
	@Override
	public void save(Pointrecord pointrecord) throws MemberServiceException {
		try {
			memberDao.saveOrUpdatePointrecord(pointrecord);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new MemberServiceException("message", e.getCause());
		}
	}

}
