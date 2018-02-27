package com.briup.run.web.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.briup.run.common.bean.Memberinfo;
import com.briup.run.common.exception.MemberServiceException;
import com.briup.run.common.util.BeanFactory;
import com.briup.run.service.IMemberService;

public class SessionListener implements HttpSessionListener{
	private IMemberService iMemberService = (IMemberService) BeanFactory.getBean(BeanFactory.MEMBERSERVICE);
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		//session销毁，用户下线
		System.out.println("sesson即将被销毁");
		if(session!=null){
			Memberinfo memberinfo = (Memberinfo) session.getAttribute("memberinfo");
			try {
				iMemberService.logout(memberinfo.getNickname());
				System.out.println("session 销毁，用户被下线");
			} catch (MemberServiceException e) {
				e.printStackTrace();
			}
		}
	}

}
