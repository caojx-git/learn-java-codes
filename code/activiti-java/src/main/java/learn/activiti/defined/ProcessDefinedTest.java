package learn.activiti.defined;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * 管理流程定义
 * Deployment   部署对象
 * 1、一次部署的多个文件的信息。对于不需要的流程可以删除和修改。
 * 2、对应的表：
 *  act_re_deployment：部署对象表
 *  act_re_procdef：流程定义表
 *  act_ge_bytearray：资源文件表
 *  act_ge_property：主键生成策略表
 *
 * ProcessDefinition 流程定义
 * 1、解析.bpmn后得到的流程定义规则的信息，工作流系统就是按照流程定义的规则执行的。
 *
 * @author caojx
 * Created on 2018/2/6 下午5:06
 */
public class ProcessDefinedTest {

    //1.部署流程定义（classpath路径加载文件）

    /**
     * 1)	先获取流程引擎对象：在创建时会自动加载classpath下的activiti.cfg.xml
     * 2)	首先获得默认的流程引擎，通过流程引擎获取了一个RepositoryService对象（仓库对象）
     * 3)	由仓库的服务对象产生一个部署对象配置对象，用来封装部署操作的相关配置。
     * 4)	这是一个链式编程，在部署配置对象中设置显示名，上传流程定义规则文件
     * 5)	向数据库表中存放流程定义的规则信息。
     * 6)	这一步在数据库中将操作三张表：
     * a)	act_re_deployment（部署对象表）
     * 存放流程定义的显示名和部署时间，每部署一次增加一条记录
     * b)	act_re_procdef（流程定义表）
     * 存放流程定义的属性信息，部署每个新的流程定义都会在这张表中增加一条记录。
     * 注意：当流程定义的key相同的情况下，使用的是版本升级
     * c)	act_ge_bytearray（资源文件表）
     * 存储流程定义相关的部署信息。即流程定义文档的存放地。每部署一次就会增加两条记录，一条是关于bpmn规则文件的，
     * 一条是图片的（如果部署时只指定了bpmn一个文件，activiti会在部署时解析bpmn文件内容自动生成流程图）。
     * 两个文件不是很大，都是以二进制形式存储在数据库中。
     *
     * @throws Exception
     */
    @Test
    public void deploy() throws Exception {

        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("请假流程名称")//添加部署规则的显示别名
                .addClasspathResource("diagrams/HelloWorld.bpmn") //添加规则文件
                .addClasspathResource("diagrams/HelloWorld.png") //添加规则图片
                .deploy();//发布完成

        System.out.println(deployment.getId() + "     " + deployment.getName());
    }

    //2. 部署流程定义（zip格式文件）
    @Test
    public void deployZIP() throws Exception {

        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取输入流
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/HelloWorld.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("请假流程")//添加部署规则的显示别名
                .addZipInputStream(zipInputStream) //使用zip的输入流完成部署
                .deploy();//发布完成

        System.out.println(deployment.getId() + "     " + deployment.getName());
    }

    //3. 查看流程定义

    /**
     * id:  {key}:{version}:{随机值}
     * name:对流程文件process节点的name属性
     * key：对流程文件的process节点的id属性
     * version:发布时自动生成的，如果第一发布的流程，version 默认从1开始；
     * 如果当前流程引擎中已存在相同key的流程，则找到当前key对应的最高版本号，在最高版本号上加上1
     * <p>
     * <p>
     * 流程定义的Id是【key：版本：生成ID】
     * 说明：
     * 1)	流程定义和部署对象相关的Service都是RepositoryService。
     * 2)	创建流程定义查询对象，可以在ProcessDefinitionQuery上设置查询的相关参数
     * 3)	调用ProcessDefinitionQuery对象的list方法，执行查询，获得符合条件的流程定义列表
     * 4)	由运行结果可以看出：
     * Key和Name的值为：bpmn文件process节点的id和name的属性值
     * <process id="HelloWorldProcess" name="HelloWorldProcess" isExecutable="true">
     * <p>
     * 5)	key属性被用来区别不同的流程定义。
     * 6)	带有特定key的流程定义第一次部署时，version为1。之后每次部署都会在当前最高版本号上加1
     * 7)	Id的值的生成规则为:{processDefinitionKey}:{processDefinitionVersion}:{generated-id}, 这里的generated-id是一个自动生成的唯一的数字
     * 8)	重复部署一次，deploymentId的值以一定的形式变化
     * 规则act_ge_property表生成
     *
     * @throws Exception
     */
    @Test
    public void queryProcessDefinition() throws Exception {
        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取仓库服务对象，使用版本的升序排序，查询列表
        List<ProcessDefinition> pdList = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                //添加查询条件
                //.processDefinitionName(processDefinitionName)
                //.processDefinitionId(processDefinitionId)
                //.processDefinitionKey(processDefinitionKey)
                //排序
                .orderByProcessDefinitionVersion().asc()
                //查询的结果集
                //.count() 返回结果集数量
                //.listPage(firstResult,maxResults)//分页查询
                //.singleResult()//唯一结果
                .list();//总的结果数量

        //编辑接货，查看内容
        for (ProcessDefinition pd : pdList) {
            System.out.println("id:" + pd.getId());
            System.out.println("name:" + pd.getName());
            System.out.println("key:" + pd.getKey());
            System.out.println("version:" + pd.getVersion());
            System.out.println("resourceName:" + pd.getDiagramResourceName());
            System.out.println("*********");
        }
    }

    //4.删除流程定义

    /**
     * 说明：
     * 1)	因为删除的是流程定义，而流程定义的部署是属于仓库服务的，所以应该先得到RepositoryService
     * 2)	如果该流程定义下没有正在运行的流程，则可以用普通删除。如果是有关联的信息，用级联删除。项目开发中使用级联删除的情况比较多，删除操作一般只开放给超级管理员使用。
     *
     * @throws Exception
     */
    @Test
    public void deleteDeployment() throws Exception {
        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //删除发布信息
        String deploymentId = "1";
        //获取仓库服务对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //普通删除，如果当前规则下有真在执行的流程，则抛出异常
        //repositoryService.deleteDeployment(deploymentid);
        //级联删除，会删除和当前规则相关的所有信息，正在执行的信息，也包括历史信息
        //相当于：repositoryService.deleteDeploymentCascade(deploymentid);
        repositoryService.deleteDeployment(deploymentId, true);
    }

    //5.获取流程定义文档的资源（查看流程图附件）

    /**
     * 说明：
     * 1)	deploymentId为流程部署ID
     * 2)	resourceName为act_ge_bytearray表中NAME_列的值
     * 3)	使用repositoryService的getDeploymentResourceNames方法可以获取指定部署下得所有文件的名称
     * 4)	使用repositoryService的getResourceAsStream方法传入部署ID和资源图片名称可以获取部署下指定名称文件的输入流
     * 5)	最后的有关IO流的操作，使用FileUtils工具的copyInputStreamToFile方法完成流程流程到文件的拷贝，将资源文件以流的形式输出到指定文件夹下
     *
     * @throws Exception
     */
    @Test
    public void viewImage() throws Exception {
        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //从仓库中药展示的文件
        String deploymentId = "15001";
        List<String> names = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);

        String imageName = null;
        for (String name : names) {
            System.out.println("name:" + name);
            if (name.indexOf(".png") >= 0) {
                imageName = name;
            }
        }
        System.out.println("imageName:" + imageName);
        if (imageName != null) {
            File file = new File("/Users/caojx/code/learn/code/activiti-java/src/" + imageName);
            //铜鼓部署ID和文件名称得到文件的输入流
            InputStream inputStream = processEngine.getRepositoryService().getResourceAsStream(deploymentId, imageName);
            FileUtils.copyInputStreamToFile(inputStream, file);
        }
    }

    //6.查看最新版本的流程定义
    @Test
    public void queryALLLatestVersions() throws Exception {
        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //查询，把最大的版本都排在后边
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().asc()
                .list();
        //过滤出最新版本
        Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
        for (ProcessDefinition definition : list) {
            map.put(definition.getKey(), definition);
        }

        //显示
        for (ProcessDefinition pd:map.values()){
            System.out.println("id："+pd.getId());//格式：（key)-(version)
            System.out.println("name:"+pd.getName());;//HelloWorld.bpmn根元素的name属性的值
            System.out.println("key:"+pd.getKey());//HelloWorld.bpmn根元素的key属性的值，如果不写，默认为name属性的值
            System.out.println("version:"+pd.getVersion());//默认自动维护，第一个时1，以后相同key都会自动加1
            System.out.println("deploymentId:"+pd.getDeploymentId()); //所属的某个Deployment对象
        }
    }

    //7.删除流程定义（使用流程定义的key删除）
    @Test
    public void deleteByKey() throws Exception{
        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //1.查询指定的key的所有版本的流程定义
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .processDefinitionKey("HelloWorldProcess")//指定流程定义的key查询
                .list();
        //2.循环删除
        for (ProcessDefinition pd:list){
            processEngine.getRepositoryService().deleteDeployment(pd.getDeploymentId(),true);
        }
        System.out.println("删除成功！");
    }

    /**
     * 总结：
     *
     *  Deployment   部署对象
        1、一次部署的多个文件的信息。对于不需要的流程可以删除和修改。
        2、对应的表：
            act_re_deployment：部署对象表
            act_re_procdef：流程定义表
            act_ge_bytearray：资源文件表
            act_ge_property：主键生成策略表

        ProcessDefinition 流程定义
        1、解析.bpmn后得到的流程定义规则的信息，工作流系统就是按照流程定义的规则执行的。

     */
}
