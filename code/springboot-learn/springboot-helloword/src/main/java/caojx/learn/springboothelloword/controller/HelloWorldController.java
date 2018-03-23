package caojx.learn.springboothelloword.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@RequestMapping("/hello")
	public String say(){
		return "spring boot hello world";
	}
}
