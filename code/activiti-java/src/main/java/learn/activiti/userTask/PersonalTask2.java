package learn.activiti.userTask;

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
 * 用户任务-个人任务
 *
 * 分配个人任务方式二（使用流程变量）
 *
 * @author caojx
 * Created on 2018/2/14 下午12:21
 */
public class PersonalTask2 {
    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //1.部署流程定义，启动流程实例
    @Test
    public void deploy() throws Exception {
        //1.发布流程
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("个人任务")//添加部署规则的显示别名
                .addClasspathResource("diagrams/userTask2.bpmn") //添加规则文件
                .addClasspathResource("diagrams/userTask2.png") //添加规则图片
                .deploy();//发布完成

        System.out.println("发布完成："+deployment.getId() + "     " + deployment.getName());
    }

    //2.启动流程实例
    @Test
    public void startProcessInstance() throws Exception{
        //启动流程实例的同时，设置流程变量
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("userID", "张翠三");
        //启动流程
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("taskProcess2",variables);
        System.out.println("流程实例ID:"+pi.getId());
        System.out.println("流程定义ID:"+pi.getProcessDefinitionId());
    }

    //3.查询我的个人任务
    @Test
    public void queryPersonalTask() throws Exception {
        //指定任务办理者
        String assignee = "张翠三";
        //查询任务的列表
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee) //指定个人任务的办理人查询任务
                .orderByTaskCreateTime().desc()//按照任务的创建时间升序排序
                .list();

        System.out.println("============[" + assignee + "]的个人任务列表===============");
        //遍历集合查看内容
        for (Task task : taskList) {
            System.out.println("id:" + task.getId());
            System.out.println("name:" + task.getName());
            System.out.println("createTime:" + task.getCreateTime());
            System.out.println("assignee:" + task.getAssignee());
        }
    }


    //完成任务
    @Test
    public void completeTask(){
        String taskId = "107505";
        processEngine.getTaskService()//
                .complete(taskId);//
        System.out.println("完成任务");
    }

    /**
     说明：
     1）	张翠三是个人任务的办理人
     2）	在开发中，可以在页面中指定下一个任务的办理人，通过流程变量设置下一个任务的办理人
     */

}
