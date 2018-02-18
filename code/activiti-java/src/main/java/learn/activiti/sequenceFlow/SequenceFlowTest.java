package learn.activiti.sequenceFlow;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 连线
 * 流程图：sequenceFlow.bpmn
 * @author caojx
 * Created on 2018/2/7 下午4:00
 */
public class SequenceFlowTest {

    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //1.部署流程定义，启动流程实例
    @Test
    public void deploy() throws Exception {
        //1.发布流程
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("连线")//添加部署规则的显示别名
                .addClasspathResource("diagrams/sequencesFlow.bpmn") //添加规则文件
                .addClasspathResource("diagrams/sequencesFlow.png") //添加规则图片
                .deploy();//发布完成

        System.out.println("发布完成："+deployment.getId() + "     " + deployment.getName());
        //2.启动流程
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("sequneceFlow");
        System.out.println("pid:"+pi.getId());
    }

    //2.查询我的个人任务
    @Test
    public void findMyTaskList() throws Exception{
        String assigneeName = "赵六";
        List<Task> list = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assigneeName)//指定个人任务查询
                .list();
        for (Task task : list) {
            System.out.println("id="+task.getId());
            System.out.println("name="+task.getName());
            System.out.println("assignee="+task.getAssignee());
            System.out.println("executionId="+task.getExecutionId());
            System.out.println("createTime="+task.getCreateTime());
        }
    }

    //3.完成任务

    /**
     * 说明：
     1）使用流程变量，设置连线需要的流程变量的名称message，并设置流程变量的值,流程才会按照指定的连线完成任务。
     * @throws Exception
     */
    @Test
    public void completeTask() throws Exception{
        String taskId = "37508";
        //可以从页面上获取 重要/不重要 的选项，设置流程变量
        Map<String,Object> variables = new HashMap<String,Object>();
        variables.put("message","不重要");
        processEngine.getTaskService().complete(taskId,variables);
        System.out.println("完成任务");
    }

    /**
     * 总结：
     * 1、一个活动中可以指定一个或多个SequenceFlow（Start中有一个，End中没有）。
     * 开始活动中有一个SequenceFlow 。
     * 结束活动中没有SequenceFlow 。
     * 其他活动中有1条或多条SequenceFlow
     2、如果只有一个，则可以不使用流程变量设置codition的名称；

     如果有多个，则需要使用流程变量设置codition的名称。message表示流程变量的名称，‘不重要’表示流程变量的值，${}中间的内容要使用boolean类型的表达式，用来判断应该执行的连线。
     */
}
