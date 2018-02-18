package learn.activiti.receiveTask;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * 接收活动（receiveTask，即等待活动）
 *
 * @author caojx
 * Created on 2018/2/8 下午3:00
 */
public class ReceiveTaskTest {


    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //1.部署流程定义，启动流程实例
    @Test
    public void deploy() throws Exception {
        //1.发布流程
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("接收活动")//添加部署规则的显示别名
                .addClasspathResource("diagrams/receiveTaskProcess.bpmn") //添加规则文件
                .addClasspathResource("diagrams/receiveTaskProcess.png") //添加规则图片
                .deploy();//发布完成

        System.out.println("发布完成："+deployment.getId() + "     " + deployment.getName());
    }

    //启动流程实例+设置流程变量+获取流程变量+向后执行一步
    @Test
    public void startProcessInstance() throws Exception{
        //启动流程
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("receiveTaskProcess");
        System.out.println("流程实例ID:"+pi.getId());
        System.out.println("流程定义ID:"+pi.getProcessDefinitionId());

        //查询执行对象ID
        Execution e1 = processEngine.getRuntimeService()//
                .createExecutionQuery()//
                .processInstanceId(pi.getId())//使用流程实例ID差查询
                .activityId("receivetask1")//当前活动ID，对应receiveTask.bpmn文件中的id属性值
                .singleResult();

        //使用流程变量设置当日销售额，用来传递业务参数
        processEngine.getRuntimeService().setVariable(e1.getId(), "汇总当日销售额",  21000);

        //向后执行一步，如果流程处于等待状态，使得流程继续执行
        processEngine.getRuntimeService().signal(e1.getId());

        //从流程变量中获取汇总当日销售额的值
       Integer value = (Integer) processEngine.getRuntimeService().getVariable(e1.getId(),"汇总当日销售额");

        System.out.println("给老板发送短信：金额时："+value);

        //向后执行一步，如果流程处于等待状态，使得流程继续执行
        processEngine.getRuntimeService().signal(e1.getId());

    }

    /**
     * 总结：
     * 说明：
     1）	当前任务（一般指机器自动完成，但需要耗费一定时间的工作）完成后，向后推移流程，可以调用runtimeService.signal(executionId)，传递接收执行对象的id。

     接收任务是一个简单任务，它会等待对应消息的到达。 当前，官方只实现了这个任务的java语义。 当流程达到接收任务，流程状态会保存到数据库中。
     在任务创建后，意味着流程会进入等待状态， 直到引擎接收了一个特定的消息， 这会触发流程穿过接收任务继续执行。

     */
}
