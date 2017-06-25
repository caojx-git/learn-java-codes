/*
 * $Id: CustomDateSerializer.java,v 1.2 2014/08/20 09:33:07 fuqiang Exp $
 *
 * Copyright 2014 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package edu.xnxy.caojx.filemanager.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huangdy
 * @version $Id: CustomDateSerializer.java,v 1.2 2014/08/20 09:33:07 fuqiang Exp $
 * Created on 2014年7月30日 下午7:32:06  主要将日期格式进行转换，将Longoing转成Date类型前端显示
 * @version 只要在entity类get属性前面加上 @JsonSerialize(using = CustomDateSerializer.class) 就可以
 * @version 例如
 *	@JsonSerialize(using = CustomDateSerializer.class)
 *	public Date getSupportDate() {
 *	return supportDate;
	}
 */
public class CustomDateSerializer extends JsonSerializer<Date> {

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         String formattedDate = formatter.format(value);
         jgen.writeString(formattedDate);
	}
}
