package caojx.learn.springboothelloword.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用自定义属性
 */
@RestController
public class HelloWorldController4 {

	@Autowired
	private MysqlProperties mysqlProperties;

	@RequestMapping("/showJdbc2")
	public String showJdbc(){
		return "mysql.jdbcName:"+mysqlProperties.getJdbcName()+"<br/>"
				+"mysql.dbUrl:"+mysqlProperties.getDbUrl()+"<br/>"
				+"mysql.userName:"+mysqlProperties.getUserName()+"<br/>"
				+"mysql.password:"+mysqlProperties.getPassword();

	}

}
