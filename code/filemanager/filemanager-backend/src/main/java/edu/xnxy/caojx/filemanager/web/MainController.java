package edu.xnxy.caojx.filemanager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description:
 *
 * @author caojx
 *         Created by caojx on 2017年04月24 上午9:50:50
 */
@Controller
@RequestMapping("/main")
public class MainController {


    @RequestMapping("/indexPage.do")
    public String showIndexPage() {
        return "index";
    }

}
