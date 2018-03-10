package base;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author caojx
 * Created on 2018/3/8 下午下午7:07
 */
public class IFTest {
    public static void main(String[] args) throws Exception {
        //创建Freemarker配置实例
        Configuration cfg = new Configuration();

        //设置模板加载路径
        cfg.setDirectoryForTemplateLoading(new File("/Users/caojx/code/learn/code/freemarker-java/src/main/resources/templates"));

        //创建数据模型
        Map root = new HashMap();
        root.put("user", "老高");
        root.put("random", new Random().nextInt(100));

        //加载模板文件
        Template t1 = cfg.getTemplate("if.ftl");

        //显示生成的数据,//将合并后的数据打印到控制台
        Writer out = new OutputStreamWriter(System.out);
        t1.process(root, out);
        out.flush();
    }
}
