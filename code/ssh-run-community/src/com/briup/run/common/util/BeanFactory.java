package com.briup.run.common.util;

import java.io.IOException;
import java.util.Properties;
import com.briup.run.dao.IMemberDao;
import com.briup.run.dao.IMessengerDao;
import com.briup.run.service.IMemberService;
import com.briup.run.service.IMessengerService;

public class BeanFactory {
	private static IMemberDao memberDao;
	private static IMemberService memberService;
	private static IMessengerDao messengerDao;
	private static IMessengerService messengerService;

	public static String MEMBERDAO = "memberDao";
	public static String MEMBERSERVICE = "memberService";
	public static String MESSENGERDAO = "messengerDao";
	public static String MESSENGERSERVICE = "messengerService";

	private static Properties properties = new Properties();

	static {
		try {
			properties.load(BeanFactory.class
					.getResourceAsStream("bean.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object getBean(String beanName) {// getBean(BeanFactory.MEMBERDAO)
		if (beanName.equals(MEMBERDAO)) {
			memberDao = getMemberDao();
			return memberDao;
		}
		if (beanName.equals(MEMBERSERVICE)) {
			memberService = getMemberService();
			return memberService;
		}
		if (beanName.equals(MESSENGERDAO)) {
			messengerDao = getMessengerDao();
			return messengerDao;
		}
		if (beanName.equals(MESSENGERSERVICE)) {
			messengerService = getMessengerService();
			return messengerService;
		}
		return null;
	}

	synchronized private static IMemberDao getMemberDao() {
		memberDao =(IMemberDao) getInstance("memberDao");
		return memberDao;
	}

	synchronized private static IMemberService getMemberService() {
		memberService =(IMemberService) getInstance("memberService");
		return memberService;
	}

	synchronized private static IMessengerDao getMessengerDao() {
		messengerDao = (IMessengerDao) getInstance("messengerDao");
		return messengerDao;
	}

	synchronized private static IMessengerService getMessengerService() {
		messengerService = (IMessengerService) getInstance("messengerService");
		return messengerService;
	}

	private static Object getInstance(String name) {
		String beanClassName = properties.getProperty(name);
		Object object = null;
		try {
			object = Class.forName(beanClassName).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}

	/*
	 * public static void main(String[] args) {
	 * System.out.println(BeanFactory.getMemberDao()); }
	 */
}
