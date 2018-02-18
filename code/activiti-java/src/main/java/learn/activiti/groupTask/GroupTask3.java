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
 * 分配个人任务方式三（使用类）
 *
 * @author caojx
 * Created on 2018/2/14 下午12:21
 */
public class GroupTask3 {
    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //1.部署流程定义，启动流程实例
    @Test
    public void deploy() throws Exception {
        //1.发布流程
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("组任务")//添加部署规则的显示别名
                .addClasspathResource("diagrams/groupTask3.bpmn") //添加规则文件
                .addClasspathResource("diagrams/groupTask3.png") //添加规则图片
                .deploy();//发布完成

        System.out.println("发布完成：" + deployment.getId() + "     " + deployment.getName());
    }

    //2.启动流程实例
    @Test
    public void startProcessInstance() throws Exception {
        //启动流程
        ProcessInstance pi = processEngine.getRuntimeService()//
                .startProcessInstanceByKey("groupTaskProcess3");
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }

    //查询我的个人任务列表
    @Test
    public void findMyTaskList(){
        String userId = "孙悟空";
        List<Task> list = processEngine.getTaskService()//
                .createTaskQuery()//
                .taskAssignee(userId)//指定个人任务查询
                .list();
        for(Task task:list ){
            System.out.println("id="+task.getId());
            System.out.println("name="+task.getName());
            System.out.println("assinee="+task.getAssignee());
            System.out.println("assinee="+task.getCreateTime());
            System.out.println("executionId="+task.getExecutionId());

        }
    }

    //查询组任务列表
    @Test
    public void findGroupList(){
        String userId = "孙悟空";
        List<Task> list = processEngine.getTaskService()//
                .createTaskQuery()//
                .taskCandidateUser(userId)//指定组任务查询
                .list();
        for(Task task:list ){
            System.out.println("id="+task.getId());
            System.out.println("name="+task.getName());
            System.out.println("assinee="+task.getAssignee());
            System.out.println("assinee="+task.getCreateTime());
            System.out.println("executionId="+task.getExecutionId());
            System.out.println("##################################");

        }
    }

    //查询组任务成员列表
    @Test
    public void findGroupUser(){
        String taskId = "137504";
        List<IdentityLink> list = processEngine.getTaskService()//
                .getIdentityLinksForTask(taskId);
        for(IdentityLink identityLink:list ){
            System.out.println("userId="+identityLink.getUserId());
            System.out.println("taskId="+identityLink.getTaskId());
            System.out.println("piId="+identityLink.getProcessInstanceId());
            System.out.println("######################");
        }
    }

    //查询组任务成员历史列表
    @Test
    public void findGroupHisUser(){
        String taskId = "4008";
        List<HistoricIdentityLink> list = processEngine.getHistoryService()//
                .getHistoricIdentityLinksForTask(taskId);
        for(HistoricIdentityLink identityLink:list ){
            System.out.println("userId="+identityLink.getUserId());
            System.out.println("taskId="+identityLink.getTaskId());
            System.out.println("piId="+identityLink.getProcessInstanceId());
            System.out.println("######################");
        }
    }

    //将组任务分配给个人任务（认领任务）
    @Test
    public void claimTask(){
        String taskId = "137504";
        //个人任务的办理人(可以是组任务中的成员，也可以时非组任务的成员)
        String userId = "如来";
        processEngine.getTaskService().claim(taskId, userId);
    }

    //可以分配个人任务回退到组任务，（前提之前是个组任务）
    @Test
    public void setAssigneeTask(){
        //任务ID
        String taskId = "137504";
        processEngine.getTaskService()//
                .setAssignee(taskId, null);
    }

    //向组任务中添加成员
    @Test
    public void addGroupUser(){
        String taskId = "137504";
        String userId = "沙和尚";
        processEngine.getTaskService().addCandidateUser(taskId, userId);
    }

    //向组任务中删除成员
    @Test
    public void delGroupUser(){
        String taskId = "4008";
        String userId = "沙和尚";
        processEngine.getTaskService().deleteCandidateUser(taskId, userId);
    }


    //完成任务
    @Test
    public void completeTask() throws Exception {
        String taskId = "137504";
        processEngine.getTaskService()//
                .complete(taskId);//
        System.out.println("完成任务");
    }


    /**
     说明：
     1）	在类中使用delegateTask.addCandidateUser (userId);的方式分配组任务的办理人，此时孙悟空和猪八戒是下一个任务的办理人。
     2）	通过processEngine.getTaskService().claim (taskId, userId);将组任务分配给个人任务，也叫认领任务，即指定某个人去办理这个任务，此时由如来去办理任务。
     注意：认领任务的时候，可以是组任务成员中的人，也可以不是组任务成员的人，此时通过Type的类型为participant来指定任务的办理人
     3）	addCandidateUser()即向组任务添加成员，deleteCandidateUser()即删除组任务的成员。
     4）	在开发中，可以将每一个任务的办理人规定好，例如张三的领导是李四和王五，这样张三提交任务，由李四或者王五去查询组任务，可以看到对应张三的申请，李四或王五再通过认领任务（claim）的方式，由某个人去完成这个任务。

     */

    /**
     * 总结：
     * 组任务及三种分配方式：
     1：在taskProcess.bpmn中直接写 candidate-users=“小A,小B,小C,小D"
     2：在taskProcess.bpmn中写 candidate-users =“#{userIDs}”，变量的值要是String的。
     使用流程变量指定办理人
     Map<String, Object> variables = new HashMap<String, Object>();
     variables.put("userIDs", "大大,小小,中中");
     3，使用TaskListener接口，使用类实现该接口，在类中定义：
     //添加组任务的用户
     delegateTask.addCandidateUser(userId1);
     delegateTask.addCandidateUser(userId2);
     组任务分配给个人任务（认领任务）：
     processEngine.getTaskService().claim(taskId, userId);
     个人任务分配给组任务：
     processEngine.getTaskService(). setAssignee(taskId, null);
     向组任务添加人员：
     processEngine.getTaskService().addCandidateUser(taskId, userId);
     向组任务删除人员：
     processEngine.getTaskService().deleteCandidateUser(taskId, userId);
     个人任务和组任务存放办理人对应的表：
     act_ru_identitylink表存放任务的办理人，包括个人任务和组任务，表示正在执行的任务
     act_hi_identitylink表存放任务的办理人，包括个人任务和组任务，表示历史任务
     区别在于：如果是个人任务TYPE的类型表示participant（参与者）
     如果是组任务TYPE的类型表示candidate（候选者）和participant（参与者）

     */
}
