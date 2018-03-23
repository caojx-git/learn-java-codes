package caojx.learn.springboothelloword.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用自定义属性
 */
@RestController
public class HelloWorldController2 {


	//注入 application.properties中定义的属性
	@Value("${helloWorld}")
	private String helloWorld;

	@RequestMapping("/hello2")
	public String say(){
		return helloWorld;
	}
}
