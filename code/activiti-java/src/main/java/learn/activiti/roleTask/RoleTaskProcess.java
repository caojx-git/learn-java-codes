package learn.activiti.roleTask;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * 工作流定义的角色组
 *
 * @author caojx
 * Created on 2018/2/14 下午8:10
 */
public class RoleTaskProcess {

    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //部署流程定义
    @Test
    public void deploy() throws Exception {
        //1.发布流程
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("工作流定义的角色组")//添加部署规则的显示别名
                .addClasspathResource("diagrams/roleTaskProcess.bpmn") //添加规则文件
                .addClasspathResource("diagrams/roleTaskProcess.png") //添加规则图片
                .deploy();//发布完成

        System.out.println("部署ID："+deployment.getId());
        System.out.println("部署名称："+deployment.getName());

        //添加用户角色
        IdentityService identityService = processEngine.getIdentityService();//认证：保存组和用户信息
        //创建角色
        identityService.saveGroup(new GroupEntity("部门经理"));
        identityService.saveGroup(new GroupEntity("总经理"));
        //创建用户
        identityService.saveUser(new UserEntity("张三"));
        identityService.saveUser(new UserEntity("李四"));
        identityService.saveUser(new UserEntity("王五"));

        //建立组和用户关系
        identityService.createMembership("张三", "部门经理");
        identityService.createMembership("李四", "部门经理");
        identityService.createMembership("王五", "总经理");
        System.out.println("添加组织机构成功");
    }

    //2.启动流程实例
    @Test
    public void startProcessInstance() throws Exception {
        //启动流程
        ProcessInstance pi = processEngine.getRuntimeService()//
                .startProcessInstanceByKey("roleTaskProcess");
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }

    //将组任务分配给个人任务（认领任务）
    @Test
    public void claimTask(){
        String taskId = "157504";
        //个人任务的办理人(可以是组任务中的成员，也可以时非组任务的成员)
        String userId = "张三";
        processEngine.getTaskService().claim(taskId, userId);
    }

    //查询我的个人任务列表
    @Test
    public void findMyTaskList() {
        String userId = "张三";
        List<Task> list = processEngine.getTaskService()//
                .createTaskQuery()//
                .taskAssignee(userId)//指定个人任务查询
                .list();
        for (Task task : list) {
            System.out.println("id=" + task.getId());
            System.out.println("name=" + task.getName());
            System.out.println("assinee=" + task.getAssignee());
            System.out.println("assinee=" + task.getCreateTime());
            System.out.println("executionId=" + task.getExecutionId());

        }
    }

    //查询组任务列表
    @Test
    public void findGroupList() {
        String userId = "李四";//张三，李四可以查询结果，王五不可以，因为他不是部门经理
        List<Task> list = processEngine.getTaskService()//
                .createTaskQuery()//
                .taskCandidateUser(userId)//指定组任务查询
                .list();
        for (Task task : list) {
            System.out.println("id=" + task.getId());
            System.out.println("name=" + task.getName());
            System.out.println("assinee=" + task.getAssignee());
            System.out.println("assinee=" + task.getCreateTime());
            System.out.println("executionId=" + task.getExecutionId());
            System.out.println("##################################");

        }
    }

    //查询组任务成员列表
    @Test
    public void findGroupUser() {
        String taskId = "157504";
        List<IdentityLink> list = processEngine.getTaskService()//
                .getIdentityLinksForTask(taskId);
        for (IdentityLink identityLink : list) {
            System.out.println("userId=" + identityLink.getUserId());
            System.out.println("taskId=" + identityLink.getTaskId());
            System.out.println("piId=" + identityLink.getProcessInstanceId());
            System.out.println("######################");
        }
    }

    //完成任务
    @Test
    public void completeTask() {
        String taskId = "157504";
        processEngine.getTaskService()//
                .complete(taskId);//
        System.out.println("完成任务");
    }
}
