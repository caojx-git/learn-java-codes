package caojx.learn.springboothelloword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 返回到freemaker视图
 * @author caojx
 * Created on 2018/3/22 下午下午5:29
 */
@Controller
@RequestMapping("/freemarker")
public class HelloWorldFreemakerController {

    /**
     * 设置数据，返回到freemarker视图
     * @return
     */
    @RequestMapping("/say")
    public ModelAndView say(){
        ModelAndView mav=new ModelAndView();
        mav.addObject("message", "SpringBoot 大爷你好！");
        mav.setViewName("helloWorld");
        return mav;
    }

}
