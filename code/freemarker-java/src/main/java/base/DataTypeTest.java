package base;

import entity.Address;
import entity.User;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;

/**
 * @author caojx
 * Created on 2018/3/10 下午上午10:55
 */
public class DataTypeTest {
    public static void main(String[] args) throws Exception {
        //创建Freemarker配置实例
        Configuration cfg = new Configuration();

        //设置模板加载路径
        cfg.setDirectoryForTemplateLoading(new File("/Users/caojx/code/learn/code/freemarker-java/src/main/resources/templates"));

        //创建数据模型
        List userList = new ArrayList<User>();
        Map root = new HashMap();
        userList.add(new User(1L,"tom1",18));
        userList.add(new User(2L,"tom2",19));
        userList.add(new User(3L,"tom3",20));
        userList.add(new User(4L,"tom4",21));

        root.put("user",new User(1L,"freemarker_javabean",30));
        root.put("userList",userList);
        //加载模板文件
        Template t1 = cfg.getTemplate("data_type.ftl");

        //显示生成的数据,//将合并后的数据打印到控制台
        Writer out = new OutputStreamWriter(System.out);
        t1.process(root, out);
        out.flush();
    }
}
