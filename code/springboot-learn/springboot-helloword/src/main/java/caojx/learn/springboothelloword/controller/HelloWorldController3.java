package caojx.learn.springboothelloword.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用自定义属性
 */
@RestController
public class HelloWorldController3 {

	@Value("${helloWorld}")
	private String helloWorld;

	@Value("${mysql.jdbcName}")
	private String jdbcName;

	@Value("${mysql.dbUrl}")
	private String dbUrl;

	@Value("${mysql.userName}")
	private String userName;

	@Value("${mysql.password}")
	private String password;

	@RequestMapping("/showJdbc")
	public String showJdbc(){
		return "mysql.jdbcName:"+jdbcName+"<br/>"
				+"mysql.dbUrl:"+dbUrl+"<br/>"
				+"mysql.userName:"+userName+"<br/>"
				+"mysql.password:"+password;

	}
}
