package com.briup.spring.ioc.bean;

import org.springframework.beans.propertyeditors.PropertiesEditor;

public class AddressEdit extends PropertiesEditor {//×ª»»Æ÷±ØÐë¼Ì³ÐPropertiesEditor

	@Override
	public void setAsText(String str) throws IllegalArgumentException {
		String[] s = str.split(",");
		Address address = new Address();
		address.setCountry(s[0]);
		address.setProvince(s[1]);
		address.setCity(s[2]);
		setValue(address);
	}

}
