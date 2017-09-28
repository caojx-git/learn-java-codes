/*package com.briup.spring.ioc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class ClassPathXmlApplicationContext implements BeanFactory {

	private Map<String, Object> beans = new HashMap<String, Object>();
	
	public ClassPathXmlApplicationContext() {}
	public ClassPathXmlApplicationContext(String path) {
		//jdom解析器兼容了sax解析和dom解析
		try {
			//建立解析器
			SAXBuilder builder = new SAXBuilder();
			//建立解析文档
			Document document = builder.build(path);
			//获取根节点
			Element root = document.getRootElement();
			//获取下一级节点
			List<Element> list = root.getChildren("bean");
			for(Element e:list){
				String name = e.getAttributeValue("name");
				String cls = e.getAttributeValue("class");
				//newInstance()实例化操作
				Object object = Class.forName(cls).newInstance();
				beans.put(name, object);
				//获取下一级节点
				List<Element> list2 = e.getChildren("property");
				for(Element e2:list2){
					String name2 = 	e2.getAttributeValue("name");
					String ref = e2.getAttributeValue("ref");
					//构建setXxxx方法名        setUserDao
					String setMethodName = "set"+name2.substring(0, 1).toUpperCase()+name2.substring(1);
					//o UserDao类实例
					Object o = beans.get(ref);  
					//反射object.getClass().getMethod()指出方法来源于那个类
					//方法可能有重载现象，指出具体包含那个个参数的方法
					//第二个参数，方法的参数											
					Method method = object.getClass().getMethod(setMethodName, o.getClass());
					 //执行方法invoke
					//第一个参数 谁执行
					//第二个参数 执行谁  
					method.invoke(object, o);
				}
			}
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public Object getBean(String name) {
		return beans.get(name);  
	}
}
*/