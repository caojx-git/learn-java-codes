package learn.activiti.start;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * 开始活动节点
 *
 * @author caojx
 * Created on 2018/2/8 下午3:00
 */
public class StartTest {


    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //1.部署流程定义，启动流程实例
    @Test
    public void deploy() throws Exception {
        //1.发布流程
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("开始活动节点")//添加部署规则的显示别名
                .addClasspathResource("diagrams/startProcess.bpmn") //添加规则文件
                .addClasspathResource("diagrams/startProcess.png") //添加规则图片
                .deploy();//发布完成

        System.out.println("发布完成："+deployment.getId() + "     " + deployment.getName());

    }

    @Test
    public void startProcessInstance(){
        //1.启动流程
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("startProcess");
        System.out.println("pid:"+pi.getId());

        //2.判断流程是否结束，查询正在执行的对象表
        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery()
                .processInstanceId(pi.getId())
                .singleResult();

        //说明流程结束了
        if(processInstance == null){
            //查询历史，获取流程相关的信息
            HistoricProcessInstance hpi = processEngine.getHistoryService()
                    .createHistoricProcessInstanceQuery()
                    .processInstanceId(pi.getId())//使用流程实例ID查询
                    .singleResult();

            System.out.println(hpi.getId()+"  "+hpi.getStartTime()+"  "+hpi.getEndTime()+"  "+hpi.getDurationInMillis());
        }
    }


    /**
     * 总结
     * 1）：结束节点没有出口
     2）：其他节点有一个或多个出口。
     如果有一个出口，则代表是一个单线流程；
     如果有多个出口，则代表是开启并发流程。

     */
}
