package learn.activiti.helloWorld;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * HelloWorld程序（模拟流程的执行）
 *
 * @author caojx
 * Created on 2018/2/6 下午3:44
 */
public class ActivitiHelloWord {


    //1.发布流程
    //这里使用RepositoryService部署流程定义
    @Test
    public void deploy() throws Exception {
        /**
         * 这里使用RepositoryService部署流程定义addClasspathResource表示从类路径下加载资源文件，一次只能加载一个文件
         */

        //获取流程引擎
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
        //获取仓库服务实例
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("diagrams/HelloWorld.bpmn")
                .addClasspathResource("diagrams/HelloWorld.png")
                .deploy();

        System.out.println(deployment.getId() + "     " + deployment.getName());
    }

    //2.启动流程
    //这里使用RuntimeService启动流程实例
    @Test
    public void startProcess() throws Exception {
        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //启动流程
        //使用流程定义的key启动流程实例，默认会按照罪行的版本启动流程实例
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey("HelloWorldProcess");
        System.out.println("pid:" + processInstance.getId() + ",activitiId:" + processInstance.getActivityId());
    }

    //3.查看任务
    //这里使用TaskService完成任务的查询
    @Test
    public void queryMyTask() throws Exception {
        //指定任务办理者
        String assignee = "张三";
        //获取流程引擎对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //查询任务的列表
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee)
                .list();

        //遍历集合查看内容
        for (Task task : taskList) {
            System.out.println("taskId" + task.getId() + ",taskName:" + task.getName());
            System.out.println("*********************");
        }
    }

    //4.完成任务
    //这里使用TaskService完成任务的办理
    @Test
    public void completeTask() throws Exception{
        String taskId = "7504";
        //获取流程引擎对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //完成任务
        processEngine.getTaskService()
                .complete(taskId);
        System.out.println("完成任务！");
    }
}
