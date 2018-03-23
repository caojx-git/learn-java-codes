package caojx.learn.springboothelloword.valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 学生控制类
 * @author caojx
 * Created on 2018/3/23 下午上午11:13
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    /**
     * 添加学生
     *
     * add方法里 实体前要加@Valid 假如字段验证不通过，信息绑定到后面定义的BindingResult；
     * @param student
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @PostMapping(value="/add")
    public String add(@Valid Student student, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return bindingResult.getFieldError().getDefaultMessage();
        }else{
            studentService.add(student);
            return "添加成功！";
        }
    }
}
