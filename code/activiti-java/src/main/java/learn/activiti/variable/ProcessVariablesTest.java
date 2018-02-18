package learn.activiti.variable;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 流程变量
 * <p>
 * 流程变量在整个工作流中扮演很重要的作用。例如：请假流程中有请假天数、请假原因等一些参数都为流程变量的范围。
 * 流程变量的作用域范围是只对应一个流程实例。也就是说各个流程实例的流程变量是不相互影响的。
 * 流程实例结束完成以后流程变量还保存在数据库中。
 *
 * @author caojx
 * Created on 2018/2/6 下午8:21
 */
public class ProcessVariablesTest {

    public static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //1.部署流程定义

    /**
     * 输入流加载资源文件的三种方式
     * InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("diagrams/testVariables.bpmn");//从类加载器中加载指定名称文件
     * InputStream inputStream = this.getClass().getResourceAsStream("diagrams/testVariables.bpmn");//从当前包下加载指定名称文件
     * InputStream inputStream = this.getClass().getResourceAsStream("/diagrams/testVariables.bmpn");//从classpath更目录下加载指定名称文件
     *
     * @throws Exception
     */
    @Test
    public void deploy_inputstream() throws Exception {

        InputStream inputStream = this.getClass().getResourceAsStream("/diagrams/testVariables.bpmn");
        InputStream inputStreamPng = this.getClass().getResourceAsStream("/diagrams/testVariables.png");
        System.out.println(inputStream + "---" + inputStreamPng);

        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("审批流程（流程变量）")
                .addInputStream("testVariables1.bpmn", inputStream)//指定资源名称
                .addInputStream("testVariables1.png", inputStreamPng)//指定资源名称
                .deploy();//发布流程
        System.out.println(deployment.getId());
    }

    //2.启动流程实例
    @Test
    public void startProcess() throws Exception {
        String processDefinitionKey = "myProcess";
        //在启动时设置流程变量
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey);
        System.out.println("pid:" + pi.getId());
    }

    //3.设置流程变量

    /**
     * 说明：
     * 1)	流程变量的作用域就是流程实例，所以只要设置就行了，不用管在哪个阶段设置
     * 2)	基本类型设置流程变量，在taskService中使用任务ID，定义流程变量的名称，设置流程变量的值。
     * 3)	Javabean类型设置流程变量，需要这个javabean实现了Serializable接口
     * 4)	设置流程变量的时候，向act_ru_variable这个表添加数据
     *
     * @throws Exception
     */
    @Test
    public void setVariables() throws Exception {
        //获取执行的service
        TaskService taskService = processEngine.getTaskService();
        //指定办理人
        String assigneeUser = "张三";
        //流程实例ID
        String processinstanceId = "12501";
        Task task = taskService.createTaskQuery()//创建任务查询
                .taskAssignee(assigneeUser)//指定办理人
                .processInstanceId(processinstanceId)//指定流程实例ID
                .singleResult();

        //一：变量中存放基本数据类型
        taskService.setVariable(task.getId(), "请假人", "小明");//使用流程变量名称和流程变量的值设置流程变量，一次只能设置一个值
        taskService.setVariableLocal(task.getId(), "请假天数", 6);
        taskService.setVariable(task.getId(), "请假日期", new Date());

        //二：变量中存放javabean对象，前提，让javabean实现java.io.Serializable
        Person person = new Person();
        person.setId(1L);
        person.setName("翠花");
        taskService.setVariable(task.getId(), "人员信息", person);
    }

    //4. 获取流程变量

    /**
     * 说明：
     * 1）	流程变量的获取针对流程实例（即1个流程），每个流程实例获取的流程变量时不同的
     * 2）	使用基本类型获取流程变量，在taskService中使用任务ID，流程变量的名称，获取流程变量的值。
     * 3）	Javabean类型设置获取流程变量，除了需要这个javabean实现了Serializable接口外，还要求流程变量对象的属性不能发生变化，否则抛出异常。
     * 解决方案，在javabean对象中添加：
     * private static final long serialVersionUID = 5341610004178940035L;
     *
     * @throws Exception
     */
    @Test
    public void getVariables() throws Exception {
        //获取执行的service
        TaskService taskService = processEngine.getTaskService();
        //指定办理人
        String assigneeUser = "张三";
        //流程实例ID
        String processInstanceId = "12501";
        Task task = taskService.createTaskQuery()//创建任务查询
                .taskAssignee(assigneeUser)//指定办理人
                .processInstanceId(processInstanceId)//指定流程实例ID
                .singleResult();

        //一:获取存放的基本数据了类型
        String stringValue = (String) taskService.getVariable(task.getId(), "请假人");
        Integer integerValue = (Integer) taskService.getVariableLocal(task.getId(), "请假天数");
        Date dateValue = (Date) taskService.getVariable(task.getId(), "请假日期");
        System.out.println(stringValue + "  " + integerValue + "  " + dateValue);

        //二：变量中存放javabean对象，前提：让javabean对象实现java.io.Serializable

        /**
         * 获取流程变量时注意:流程变量如果是javabean对象，除了要求实现Serializable之外，同时要求流程变量对象的属性不能发生变化，否则抛出异常
         * 解决方案：在设置流程变量的时候，在javabean对象中使用
         * private static final long serialVersionUID = 5341610004178940035L;
         */
        Person p = (Person) taskService.getVariable(task.getId(), "人员信息");
        System.out.println(p.getId());
        System.out.println(p.getName());
    }

    //5. 模拟流程变量的设置和获取的场景

    /**
     * 说明：
     *  1）	RuntimeService对象可以设置流程变量和获取流程变量
     *  2）	TaskService对象可以设置流程变量和获取流程变量
     *  3）	流程实例启动的时候可以设置流程变量
     *  4）	任务办理完成的时候可以设置流程变量
     *  5）	流程变量可以通过名称/值的形式设置单个流程变量
     *  6）	流程变量可以通过Map集合，同时设置多个流程变量
     *  Map集合的key表示流程变量的名称
     *  Map集合的value表示流程变量的值
     *
     */
    @Test
    public void setAndGetVariables() throws Exception{
        RuntimeService runtimeService = processEngine.getRuntimeService();
        TaskService taskService = processEngine.getTaskService();
        //========================
        //设置变量的方法
        //通过Execution设置一个变量
//        runtimeService.setVariable(executionId, name, value);
        //通过Execution设置一个多个变量
//        runtimeService.setVariable(executionId, name, variablesMap);
        //通过Task设置一个变量
//        runtimeService.setVariable(taskId, name, value);
        //通过Task设置多个变量
//        runtimeService.setVariable(taskId, name, variablesMap);

        //启动流程是实例时，同时也设置一些流程变量
//        runtimeService.startProcessInstanceByKey(processDefinitionKey,variables);
        //在完成任务时，同时也设置一些流程变量
//        taskService.complete(taskId, variables);

        //==========================
        //获取变量的方法
        //通过Execution获取一个变量
//        runtimeService.getVariable(executionId, variableName);
        //通过Execution获取所有的变量信息，存放到Map集合中
//        runtimeService.getVariables(executionId);
        //通过Execution获取指定的流程变量名称的变量值的信息，存放到Map集合中
//        runtimeService.getVariables(executionId, variableNames);

        //通过Task获取一个变量
//        taskService.getVariable(executionId, variableName);
        //通过Task获取所有的变量信息，存放到Map集合中
//        taskService.getVariables(executionId);
        //通过Task获取指定的流程变量名称的变量值的信息，存放到Map集合中
//        taskService.getVariables(executionId, variableNames);

    }

    //6.查询历史的流程变量

    /**
     * 说明：
     * 1）历史的流程变量查询，指定流程变量的名称，查询act_hi_varinst表（也可以针对，流程实例ID，执行对象ID，任务ID查询）
     *
     * @throws Exception
     */
    @Test
    public void getHisVariables() throws Exception{
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .variableName("请假天数")//指定流程变量的名称查询
                .list();
        if(list != null && list.size() > 0){
            for(HistoricVariableInstance hvi: list){
                System.out.println(hvi.getVariableName()+"  "+hvi.getValue());
            }
        }
    }

    /**
     * 总结：
     *
     * •	1：流程变量
     在流程执行或者任务执行的过程中，用于设置和获取变量，使用流程变量在流程传递的过程中传递业务参数。
     对应的表：
     act_ru_variable：正在执行的流程变量表
     act_hi_varinst：流程变量历史表

     •	2：扩展知识：setVariable和setVariableLocal的区别
     setVariable：设置流程变量的时候，流程变量名称相同的时候，后一次的值替换前一次的值，而且可以看到TASK_ID的字段不会存放任务ID的值
     setVariableLocal：
        1：设置流程变量的时候，针对当前活动的节点设置流程变量，如果一个流程中存在2个活动节点，对每个活动节点都设置流程变量，即使流程变量的名称相同，后一次的版本的值也不会替换前一次版本的值，它会使用不同的任务ID作为标识，存放2个流程变量值，而且可以看到TASK_ID的字段会存放任务ID的值

        2：还有，使用setVariableLocal说明流程变量绑定了当前的任务，当流程继续执行时，下个任务获取不到这个流程变量（因为正在执行的流程变量中没有这个数据），所有查询正在执行的任务时不能查询到我们需要的数据，此时需要查询历史的流程变量。
     */
}
