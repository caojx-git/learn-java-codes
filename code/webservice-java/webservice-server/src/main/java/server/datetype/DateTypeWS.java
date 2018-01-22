package server.datetype;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;
import java.util.Map;

@WebService
public interface DateTypeWS {

    @WebMethod
    public boolean addStudent(Student s);

    @WebMethod
    public Student getStudentById(int id);

    @WebMethod
    public List<Student> getStudentByPrice(float price);

    /**
     * 如果使用的cxf发布报错： com.sun.xml.bind.v2.runtime.IllegalAnnotationsException: 2 counts of IllegalAnnotationExceptions
     * java.util.Map is an interface, and JAXB can't handle interfaces.
     * 原因是不支持map类型数据，解决方案参考如下：
     * 方案一： http://blog.csdn.net/kongxx/article/details/7544640
     * 方案二： 我将将cxf版本换到3.1.7以上就好了
     * @return
     */
    @WebMethod
    public Map<Integer,Student> getAllStudentsMap();
}
