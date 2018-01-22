package client;

import server.datetype.DateTypeWS;
import server.datetype.DateTypeWSImplService;
import server.datetype.GetAllStudentsMapResponse;
import server.datetype.Student;

import java.util.List;

/**
 * 使用cxf框架后测试支持的数据类型
 */
public class DateTypeClientTest {

    public static void main(String[] args) {
        DateTypeWSImplService factory = new DateTypeWSImplService();
        DateTypeWS dateTypeWS = factory.getDateTypeWSImplPort();

        Student student = new Student();
        student.setId(12);
        student.setName("abc");
        student.setPrice(23);
        boolean success = dateTypeWS.addStudent(student);

        System.out.println("client "+success);

        List<Student> list = dateTypeWS.getStudentByPrice(23);

        System.out.println(list);

        GetAllStudentsMapResponse.Return r = dateTypeWS.getAllStudentsMap();
        List<GetAllStudentsMapResponse.Return.Entry> entries = r.getEntry();
        for(GetAllStudentsMapResponse.Return.Entry entry :entries){
            Integer id = entry.getKey();
            Student student1 = entry.getValue();
            System.out.println(id +"_"+student1); //不知原因，客户端中没有生成toString()方法
            System.out.println(student1.getId()+"-"+student1.getName()+"-"+student1.getPrice());
        }
    }
}
