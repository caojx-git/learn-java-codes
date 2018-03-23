package caojx.learn.springboothelloword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/blog")
public class BlogController {

	/**
	 * @PathVariable 配合 @RequestMapping使用可以获取到路径中的参数
	 * http://localhost:8888/HelloWorld/blog/21  则 id=21
	 * @param id
	 * @return
	 */
	@RequestMapping("/{id}")
	public ModelAndView show(@PathVariable("id") Integer id){
		ModelAndView mav=new ModelAndView();
		mav.addObject("id", id);
		mav.setViewName("blog");
		return mav;
	}

	/**
	 * RequestParam 获取提交的参数
	 *
	 * http://localhost:8888/HelloWorld/blog/query?q=123456 则q = 123456
	 * @param q
	 * @return
	 */
	@RequestMapping("/query")
	public ModelAndView query(@RequestParam(value="q",required=false)String q){
		ModelAndView mav=new ModelAndView();
		mav.addObject("q", q);
		mav.setViewName("query");
		return mav;
	}
}
