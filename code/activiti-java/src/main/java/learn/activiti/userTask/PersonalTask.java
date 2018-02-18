package learn.activiti.userTask;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * 用户任务-个人任务
 *
 * 分配个人任务方式一（直接指定办理人）
 *
 * @author caojx
 * Created on 2018/2/14 下午12:21
 */
public class PersonalTask {
    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //1.部署流程定义，启动流程实例
    @Test
    public void deploy() throws Exception {
        //1.发布流程
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("个人任务")//添加部署规则的显示别名
                .addClasspathResource("diagrams/userTask.bpmn") //添加规则文件
                .addClasspathResource("diagrams/userTask.png") //添加规则图片
                .deploy();//发布完成

        System.out.println("发布完成："+deployment.getId() + "     " + deployment.getName());
    }

    //2.启动流程实例
    @Test
    public void startProcessInstance() throws Exception{
        //启动流程
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("taskProcess");
        System.out.println("流程实例ID:"+pi.getId());
        System.out.println("流程定义ID:"+pi.getProcessDefinitionId());
    }

    //3.查询我的个人任务
    @Test
    public void queryPersonalTask() throws Exception {
        //指定任务办理者
        String assignee = "张三丰";
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
        String taskId = "100004";
        processEngine.getTaskService()//
                .complete(taskId);//
        System.out.println("完成任务");
    }

    /**
     * 说明：
     1）	张三丰是个人任务的办理人
     2）	但是这样分配任务的办理人不够灵活，因为项目开发中任务的办理人不要放置XML文件中。
     */

}
