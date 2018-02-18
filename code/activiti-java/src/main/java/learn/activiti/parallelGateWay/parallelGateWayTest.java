package learn.activiti.parallelGateWay;

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
 * 并行网关(parallelGateWay)
 *
 * @author caojx
 * Created on 2018/2/8 下午3:00
 */
public class parallelGateWayTest {


    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //1.部署流程定义，启动流程实例
    @Test
    public void deploy() throws Exception {
        //1.发布流程
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("并行网关")//添加部署规则的显示别名
                .addClasspathResource("diagrams/parallelGateWayProcess.bpmn") //添加规则文件
                .addClasspathResource("diagrams/parallelGateWayProcess.png") //添加规则图片
                .deploy();//发布完成

        System.out.println("发布完成："+deployment.getId() + "     " + deployment.getName());
        //2.启动流程
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("parallelGateWay");
        System.out.println("pid:"+pi.getId());
    }


    //2.查询我的个人任务
    @Test
    public void findMyTaskList() throws Exception{
        String assigneeName = "商家";
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
    @Test
    public void completeTask() throws Exception{
        String taskId = "85002";
        processEngine.getTaskService().complete(taskId);
        System.out.println("完成任务");
    }

    /**
     * 说明：
     1）	一个流程中流程实例只有1个，执行对象有多个
     2）	并行网关的功能是基于进入和外出的顺序流的：
     分支(fork)： 并行后的所有外出顺序流，为每个顺序流都创建一个并发分支。
     汇聚(join)： 所有到达并行网关，在此等待的进入分支， 直到所有进入顺序流的分支都到达以后， 流程就会通过汇聚网关。
     3）	并行网关的进入和外出都是使用相同节点标识
     4）	如果同一个并行网关有多个进入和多个外出顺序流， 它就同时具有分支和汇聚功能。 这时，网关会先汇聚所有进入的顺序流，然后再切分成多个并行分支。
     5）	并行网关不会解析条件。 即使顺序流中定义了条件，也会被忽略。
     6）	并行网关不需要是“平衡的”（比如， 对应并行网关的进入和外出节点数目不一定相等）。如图中标示是合法的：
     */
}
