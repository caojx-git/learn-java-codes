package com.briup.run.service;

import java.util.List;

import com.briup.run.common.bean.Friendrecord;
import com.briup.run.common.bean.Graderecord;
import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.bean.Pointaction;
import com.briup.run.common.bean.Pointrecord;
import com.briup.run.common.exception.MemberServiceException;

public interface IMemberService {
	// 用户注册
	void registerMemberinfo(Memberinfo memberinfo) throws MemberServiceException;
	
	// 按照姓名查找用户
	Memberinfo findMemberinfoByName(String nickname) throws MemberServiceException;
	
	// 通过用户名登录
	Memberinfo login(String username, String passwd) throws MemberServiceException;
	
	// 退出
	void logout(String nickname) throws MemberServiceException;
	
	// 查找前几名用户
	List<Memberinfo> findMemberinfoByNum(int number) throws MemberServiceException;
	
	// 查找在线用户
	int findMemberinfoOnline() throws MemberServiceException;
	
	// 按照积分查找等级
	Graderecord findMemberinfoLevel(Long point) throws MemberServiceException;
	
	// 保存或者更新用户
	Memberinfo saveOrUpDate(Memberinfo memberinfo, String oldPasswd) throws MemberServiceException;
	
	// 通过提示问题和答案来获取密码
	String getBackPasswd(String nickname, String pwdQuestion, String pwdAnswer) throws MemberServiceException;
	
	// 保存或修改用户信息
	void saveOrUpdate(Memberinfo memberinfo) throws MemberServiceException;
	
	// 保存或修改用户信息
	void saveOrUpdate(String selfname, String friendname) throws MemberServiceException;
	
	// 查找好友
	List<Memberinfo> listFriend(String selfname) throws MemberServiceException;
	
	// 转到黑名单
	void moveToBlack(String selfname, String blackname) throws MemberServiceException;
	
	// 获取黑名单人员
	List<Memberinfo> listBlack(String selfname) throws MemberServiceException;
	
	// 查找好友
	Friendrecord findFriend(String selfname,String friend) throws MemberServiceException;
	
	// 判断是否有个人空间
	Boolean isMemberspace(Long id) throws MemberServiceException;
	
	// 转到好友
	void moveToFriend(String selfName, String name_searching) throws MemberServiceException;
	
	// 删除黑名单
	void deleleBlack(String selfName, String black) throws MemberServiceException;
	
	// 删除好友
	void deleleFriend(String selfName, String friend) throws MemberServiceException;
	
	// 删除空间
	void delSpace(Long id) throws MemberServiceException;

	// 按照名字超找积分动作
	Pointaction findPointactionByPointAction(String actionName)throws MemberServiceException;

	// 保存加积分的记录
	void save(Pointrecord pointrecord) throws MemberServiceException;
}
