package learn.activiti.groupTask;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * 组任务
 * <p>
 * 分配组任务方式一（直接指定办理人）
 *
 * @author caojx
 * Created on 2018/2/14 下午12:21
 */
public class GroupTask {
    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //1.部署流程定义，启动流程实例
    @Test
    public void deploy() throws Exception {
        //1.发布流程
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("组任务")//添加部署规则的显示别名
                .addClasspathResource("diagrams/groupTask1.bpmn") //添加规则文件
                .addClasspathResource("diagrams/groupTask1.png") //添加规则图片
                .deploy();//发布完成

        System.out.println("发布完成：" + deployment.getId() + "     " + deployment.getName());
    }

    //2.启动流程实例
    @Test
    public void startProcessInstance() throws Exception {
        //启动流程
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("groupTaskProcess1");
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }

    //3 查询我的个人任务列表
    @Test
    public void findMyTaskList() throws Exception {
        String assignee = "小A";
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
        String candidateUser = "小A";
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
        String taskId = "120004";
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
        String taskId = "120004";
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
        String taskId = "120004";
        //分配的办理人(可以是组任务中的成员，也可以时非组任务的成员)
        String userId = "大F";
        processEngine.getTaskService().claim(taskId, userId);
    }

    //将个人任务退回到组任务，前提，之前一定时组任务
    @Test
    public void setAssignee() throws Exception {
        //任务ID
        String taskId = "120004";
        processEngine.getTaskService().setAssignee(taskId, null);
    }

    //向任务中添加成员
    @Test
    public void addGroupUser() throws Exception {
        //任务ID
        String taskId = "120004";
        //成员办理人
        String userId = "大H";
        processEngine.getTaskService().addCandidateUser(taskId, userId);
    }

    //从任务中删除成员
    @Test
    public void delGroupUser() throws Exception {
        //任务ID
        String taskId = "120004";
        //成员办理人
        String userId = "大H";
        //删除后candidate（候选者）信息不在了，但是participant（参与者）信息还在
        processEngine.getTaskService().deleteCandidateUser(taskId, userId);
    }


    //完成任务
    @Test
    public void completeTask() throws Exception {
        String taskId = "120004";
        processEngine.getTaskService()//
                .complete(taskId);//
        System.out.println("完成任务");
    }


    /**
     * 说明：
     1）	小A，小B，小C，小D是组任务的办理人
     2）	但是这样分配组任务的办理人不够灵活，因为项目开发中任务的办理人不要放置XML文件中。
     3）	act_ru_identitylink表存放任务的办理人，包括个人任务和组任务，表示正在执行的任务
     4）	act_hi_identitylink表存放任务的办理人，包括个人任务和组任务，表示历史任务
     区别在于：如果是个人任务TYPE的类型表示participant（参与者）
     如果是组任务TYPE的类型表示candidate（候选者）和participant（参与者）

     */
}
