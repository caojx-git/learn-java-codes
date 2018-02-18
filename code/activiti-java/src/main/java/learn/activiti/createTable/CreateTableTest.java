package learn.activiti.createTable;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.Assert.*;

/**
 * 初始化数据库，在Activiti中，在创建核心的流程引擎对象时会自动建立23张表。
 * @author caojx
 * Created on 2018/2/5 下午4:25
 */
public class CreateTableTest {


    /**
     *连接mariadb测试，mariadb与mysql用法一样
     */
    @Test
    public void connnectionMariadb() {
        try {
            String driver = "org.mariadb.jdbc.Driver";
            //从配置参数中获取数据库url
            String url = "jdbc:mariadb://192.168.46.129:3306/activiti_learn?useUnicode=true&characterEncoding=utf-8&useSSL=false";
            //从配置参数中获取用户名
            String user = "root";
            //从配置参数中获取密码
            String pass = "root";

            //注册驱动
            Class.forName(driver);
            //获取数据库连接
            Connection conn = DriverManager.getConnection(url, user, pass);
            //创建Statement对象
            Statement stmt = conn.createStatement();
            System.out.println("Success!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建activiti工作流的23张表
     * 在Activiti中，在创建核心的流程引擎对象时会自动建表。如果程序正常执行，mariadb会自动建库，然后创建23张表。
     * 注意：在连接远程数据库时需要设置mysql或mariadb支持远程连接，测试时先关闭一下远程主机的防火墙
     */
    @Test
    public void createTableTest() {
        //1.创建Activiti配置对象实例
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        //2.设置数据库的连接信息
        //设置数据库地址
        configuration.setJdbcUrl("jdbc:mariadb://192.168.46.130:3306/activiti_learn?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8&useSSL=false");
        //数据库驱动
        configuration.setJdbcDriver("org.mariadb.jdbc.Driver");
        //用户名
        configuration.setJdbcUsername("root");
        //密码
        configuration.setJdbcPassword("root");

        //设置数据库建表策略

        /**
         * DB_SCHEMA_UPDATE_TRUE:如果表不存在就建表，存在就直接使用
         * DB_SCHEMA_UPDATE_FALSE:如果不存在表就抛出依赖
         * DB_SCHEMA_UPDATE_CREATE_DROP：每次都先删除表，再创建新的表
         */
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP);
        //3.使用配置对象创建流程引擎实例（检查数据库链接等环境信心是否正确）
        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println(processEngine);
    }

    /**
     * 创建activiti工作流的23张表,通过配置文件建立数据
     * 如果都像上面那样写一大段代码会非常麻烦，所以我们可以把数据库连接配置写入配置文件
     */
    @Test
    public void CreateTableByProperties(){
        //1.加载classpath下名为activiti.cfg.xml文件，创建核心流程引擎对象，系统数据库会自动创建表
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
        System.out.println(processEngine);

    }

}