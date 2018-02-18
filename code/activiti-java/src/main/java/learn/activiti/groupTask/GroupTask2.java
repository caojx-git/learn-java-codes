package learn.activiti.groupTask;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组任务
 * <p>
 * 分配个人任务方式二（使用流程变量）
 *
 * @author caojx
 * Created on 2018/2/14 下午12:21
 */
public class GroupTask2 {
    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //1.部署流程定义，启动流程实例
    @Test
    public void deploy() throws Exception {
        //1.发布流程
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("组任务")//添加部署规则的显示别名
                .addClasspathResource("diagrams/groupTask2.bpmn") //添加规则文件
                .addClasspathResource("diagrams/groupTask2.png") //添加规则图片
                .deploy();//发布完成

        System.out.println("发布完成：" + deployment.getId() + "     " + deployment.getName());
    }

    //2.启动流程实例
    @Test
    public void startProcessInstance() throws Exception {
        //启动流程
        //启动流程实例，同时设置流程变量，用来指定组任务的办理人
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("userIDs", "大大,小小,中中");
        ProcessInstance pi = processEngine.getRuntimeService()//
                .startProcessInstanceByKey("groupTaskProcess2", variables);
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }

    //3 查询我的个人任务列表
    @Test
    public void findMyTaskList() throws Exception {
        String assignee = "大大";
        List<Task> list = processEngine.getTaskService()//
                .createTaskQuery()//
                .taskAssignee(assignee)//指定个人任务查询
                .list();
        for (Task task : list) {
            System.out.println("id=" + task.getId());
            System.out.println("name=" + task.getName());
            System.out.println("assinee=" + task.getAssignee());
            System.out.println("createTime=" + task.getCreateTime());
            System.out.println("executionId=" + task.getExecutionId());

        }
    }


    //4.查询组任务列表
    @Test
    public void findGroupTask() throws Exception {
        //指定任务办理者
        String candidateUser = "大大";
        //查询任务的列表
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                //.taskAssignee(assignee) //指定个人任务的办理人查询任务
                .taskCandidateUser(candidateUser)
                .orderByTaskCreateTime().desc()//按照任务的创建时间升序排序
                .list();

        System.out.println("============[" + candidateUser + "]的个人任务列表===============");
        //遍历集合查看内容
        for (Task task : taskList) {
            System.out.println("id=" + task.getId());
            System.out.println("name=" + task.getName());
            System.out.println("assinee=" + task.getAssignee());
            System.out.println("createTime =" + task.getCreateTime());
            System.out.println("executionId=" + task.getExecutionId());
            System.out.println("##################################");
        }
    }

    //5 查询组任务成员列表
    @Test
    public void findGroupUser() throws Exception {
        String taskId = "132505";
        List<IdentityLink> list = processEngine.getTaskService()//
                .getIdentityLinksForTask(taskId);
        //List<IdentityLink> list = processEngine.getRuntimeService()//
        //				.getIdentityLinksForProcessInstance(instanceId);
        for (IdentityLink identityLink : list) {
            System.out.println("userId=" + identityLink.getUserId());
            System.out.println("taskId=" + identityLink.getTaskId());
            System.out.println("piId=" + identityLink.getProcessInstanceId());
            System.out.println("######################");
        }
    }

    //6 查询组任务成员历史列表
    @Test
    public void findGroupHisUser() throws Exception {
        String taskId = "132505";
        List<HistoricIdentityLink> list = processEngine.getHistoryService()//
                .getHistoricIdentityLinksForTask(taskId);
        // List<HistoricIdentityLink> list = processEngine.getHistoryService()//
        // .getHistoricIdentityLinksForProcessInstance(processInstanceId);
        for (HistoricIdentityLink identityLink : list) {
            System.out.println("userId=" + identityLink.getUserId());
            System.out.println("taskId=" + identityLink.getTaskId());
            System.out.println("piId=" + identityLink.getProcessInstanceId());
            System.out.println("######################");
        }
    }

    /**
     * 将组任务分配给个人任务，拾取任务
     */
    //由1个人去完成任务
    @Test
    public void claim() throws Exception {
        //任务ID
        String taskId = "132505";
        //分配的办理人(可以是组任务中的成员，也可以时非组任务的成员)
        String userId = "大大";
        processEngine.getTaskService().claim(taskId, userId);
    }

    //将个人任务退回到组任务，前提，之前一定时组任务
    @Test
    public void setAssignee() throws Exception {
        //任务ID
        String taskId = "132505";
        processEngine.getTaskService().setAssignee(taskId, null);
    }

    //向任务中添加成员
    @Test
    public void addGroupUser() throws Exception {
        //任务ID
        String taskId = "132505";
        //成员办理人
        String userId = "小B";
        processEngine.getTaskService().addCandidateUser(taskId, userId);
    }

    //从任务中删除成员
    @Test
    public void delGroupUser() throws Exception {
        //任务ID
        String taskId = "120004";
        //成员办理人
        String userId = "小B";
        //删除后candidate（候选者）信息不在了，但是participant（参与者）信息还在
        processEngine.getTaskService().deleteCandidateUser(taskId, userId);
    }


    //完成任务
    @Test
    public void completeTask() throws Exception {
        String taskId = "132505";
        processEngine.getTaskService()//
                .complete(taskId);//
        System.out.println("完成任务");
    }


    /**
     说明：
     1）	大大，中中，小小是组任务的办理人
     2）	在开发中，可以在页面中指定下一个组任务的办理人，通过流程变量设置下一个任务的办理人
     */
}
