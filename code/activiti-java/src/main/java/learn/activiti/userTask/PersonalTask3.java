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
 * 分配个人任务方式三（使用类）
 *
 * @author caojx
 * Created on 2018/2/14 下午12:21
 */
public class PersonalTask3 {
    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //1.部署流程定义，启动流程实例
    @Test
    public void deploy() throws Exception {
        //1.发布流程
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("个人任务")//添加部署规则的显示别名
                .addClasspathResource("diagrams/userTask3.bpmn") //添加规则文件
                .addClasspathResource("diagrams/userTask3.png") //添加规则图片
                .deploy();//发布完成

        System.out.println("发布完成："+deployment.getId() + "     " + deployment.getName());
    }

    //2.启动流程实例
    @Test
    public void startProcessInstance() throws Exception{
        //启动流程
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("taskProcess3");
        System.out.println("流程实例ID:"+pi.getId());
        System.out.println("流程定义ID:"+pi.getProcessDefinitionId());
    }

    //3.查询我的个人任务
    @Test
    public void queryPersonalTask() throws Exception {
        //指定任务办理者
        String assignee = "灭绝师太";
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
        String taskId = "115004";
        processEngine.getTaskService()//
                .complete(taskId);//
        System.out.println("完成任务");
    }


    //可以分配个人任务从一个人到另一个人（认领任务）
    @Test
    public void setAssigneeTask(){
        //任务ID
        String taskId = "115004";
        //指定认领的办理者
        String userId = "周芷若";
        processEngine.getTaskService()//
                .setAssignee(taskId, userId);
    }


    /**
     *
     说明：
     1）	在类中使用delegateTask.setAssignee(assignee);的方式分配个人任务的办理人，此时张无忌是下一个任务的办理人
     2）	通过processEngine.getTaskService().setAssignee(taskId, userId);将个人任务从一个人分配给另一个人，此时张无忌不再是下一个任务的办理人，而换成了周芷若
     3）	在开发中，可以将每一个任务的办理人规定好，例如张三的领导是李四，李四的领导是王五，这样张三提交任务，就可以查询出张三的领导是李四，通过类的方式设置下一个任务的办理人

     */

    /**
     * 总结：
     *
     * 个人任务及三种分配方式：
     1：在taskProcess.bpmn中直接写 assignee=“张三丰"
     2：在taskProcess.bpmn中写 assignee=“#{userID}”，变量的值要是String的。
     使用流程变量指定办理人
     3，使用TaskListener接口，要使类实现该接口，在类中定义：
     delegateTask.setAssignee(assignee);// 指定个人任务的办理人

     使用任务ID和办理人重新指定办理人：
     processEngine.getTaskService()//
     .setAssignee(taskId, userId);

     */
}
