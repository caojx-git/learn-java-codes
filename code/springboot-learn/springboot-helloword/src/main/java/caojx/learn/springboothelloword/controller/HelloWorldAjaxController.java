package caojx.learn.springboothelloword.controller;
 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
 
/**
 * 返回ajax json格式
 * @author user
 *
 */
@RestController
@RequestMapping("/ajax")
public class HelloWorldAjaxController {
 
    @RequestMapping("/hello")
    public String say(){
        return "{'message1': 'SpringBoot你大爷','message2','SpringBoot你大爷2'}";
    }
}