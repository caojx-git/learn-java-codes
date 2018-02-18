package learn.activiti.exclusiveGateWay;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 排他网关（ExclusiveGateWay）
 *
 * @author caojx
 * Created on 2018/2/8 上午11:19
 */
public class ExclusiveGateWayTest {

    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //1.部署流程定义，启动流程实例
    @Test
    public void deploy() throws Exception {
        //1.发布流程
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("排他网关")//添加部署规则的显示别名
                .addClasspathResource("diagrams/exclusiveGateWayProcess.bpmn") //添加规则文件
                .addClasspathResource("diagrams/exclusiveGateWayProcess.png") //添加规则图片
                .deploy();//发布完成

        System.out.println("发布完成："+deployment.getId() + "     " + deployment.getName());
        //2.启动流程
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("exclusiveGateWay");
        System.out.println("pid:"+pi.getId());
    }

    //2.完成我的个人任务
    @Test
    public void completeTask() throws Exception{
        String taskId = "70004";
        //可以从页面上获取报销金额填写的值，设置流程变量，由流程判断应该执行那条连线
        Map<String,Object> variables = new HashMap<String,Object>();
        variables.put("money", 200);
        processEngine.getTaskService().complete(taskId,variables);
        System.out.println("完成任务");
    }

    /**
     * 说明：
     1)	一个排他网关对应一个以上的顺序流
     2)	由排他网关流出的顺序流都有个conditionExpression元素，在内部维护返回boolean类型的决策结果。
     3)	决策网关只会返回一条结果。当流程执行到排他网关时，流程引擎会自动检索网关出口，从上到下检索如果发现第一条决策结果为true或者没有设置条件的(默认为成立)，则流出。
     4)	如果没有任何一个出口符合条件，则抛出异常
     5)	使用流程变量，设置连线的条件，并按照连线的条件执行工作流，如果没有条件符合的条件，则以默认的连线离开。例如：
     */
}
