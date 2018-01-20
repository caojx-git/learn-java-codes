package server.datetype;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebService
public class DateTypeWSImpl implements DateTypeWS {

    public boolean addStudent(Student s) {
        System.out.println("server addStudent() " + s);
        return true;
    }

    public Student getStudentById(int id) {
        System.out.println("server getStudentById() " + id);
        return new Student(id, "CAT", 1000);
    }

    public List<Student> getStudentByPrice(float price) {
        System.out.println("server getStudentByPrice() " + price);
        List<Student> list = new ArrayList<Student>();
        list.add(new Student(1, "TOM1", price+1));
        list.add(new Student(2, "TOM1", price+2));
        list.add(new Student(3, "TOM1", price+3));
        return list;
    }

    /**
     * 发布报错： com.sun.xml.bind.v2.runtime.IllegalAnnotationsException: 2 counts of IllegalAnnotationExceptions
     * java.util.Map is an interface, and JAXB can't handle interfaces.
     * 由于默认不支持map类型数据，解决方案参考如下：
     * http://blog.csdn.net/kongxx/article/details/7544640
     * @return
     */
   /* public Map<Integer, Student> getAllStudentsMap() {
        System.out.println("server getAllStudentsMap() ");
        Map<Integer, Student> map = new HashMap<Integer, Student>();
        map.put(1,new Student(1, "JACK1", 100));
        map.put(2,new Student(1, "JACK1", 200));
        map.put(3,new Student(1, "JACK1", 300));
        return map;
    }*/
}
