package learn.activiti.history;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

import java.util.List;

/**
 * 流程执行历史记录
 *
 * @author caojx
 * Created on 2018/2/7 上午10:15
 */
public class ProcessHistoryTest {

    public static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //1.历史流程实例查看
    @Test
    public void queryHistoricProcessInstance() throws Exception {
        //获取历史流程实例，返回历史流程实例的集合
        List<HistoricProcessInstance> hpList = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()//创建历史流程实例查询
                .processDefinitionKey("myProcess")//按照流程定义的Key查询
                .orderByProcessInstanceStartTime().desc()//按照流程实例开始时间降序排序
                .list();//返回结果集

        //遍历查看结果
        for (HistoricProcessInstance hpi : hpList) {
            System.out.println("pid:" + hpi.getId());
            System.out.println("pdid:" + hpi.getProcessDefinitionId());
            System.out.println("startTime:" + hpi.getStartTime());
            System.out.println("endTime:" + hpi.getEndTime());
            System.out.println("duration:" + hpi.getDurationInMillis());
        }
    }

    //2.查询历史活动(某一次流程的执行一共经历了多少个活动)
    @Test
    public void queryHistoricActivityInstance() throws Exception {
        String processInstanceId = "12501";
        List<HistoricActivityInstance> haiList = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()//创建历史任务查询
                .processInstanceId(processInstanceId) //使用流程实例Id查询
//        .listPage(firsResult,maxResults) 分页条件
                .orderByHistoricActivityInstanceEndTime().asc()//排序条件
                .list();//执行查询

        for (HistoricActivityInstance hai : haiList) {
            System.out.println("taskId:" + hai.getId());
            System.out.println("name:" + hai.getActivityName());
            System.out.println("pdId:" + hai.getProcessDefinitionId());
            System.out.println("pid:" + hai.getProcessInstanceId());
            System.out.println("assignee:" + hai.getAssignee());
            System.out.println("startTime:" + hai.getStartTime());
            System.out.println("endTime:" + hai.getEndTime());
            System.out.println("duration:" + hai.getDurationInMillis());
        }
    }

    //3.查询历史任务
    //某一次流程的执行一共经历了多少个任务
    @Test
    public void queryHistoryTask() throws Exception {
        //历史任务办理人
        String processInstanceId = "12501";
        //获取流程引擎对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //通过流程实例ID查询流程实例
        List<HistoricTaskInstance> htiList = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()//创建历史任务查询
                .processInstanceId(processInstanceId)//使用流程实例Id查询
                //        .listPage(firsResult,maxResults) 分页条件
                .orderByHistoricTaskInstanceStartTime().asc()//排序条件
                .list();

        if (htiList != null && htiList.size() > 0) {
            for (HistoricTaskInstance hti : htiList) {
                System.out.println("taskId：" + hti.getId());
                System.out.println("name：" + hti.getName());
                System.out.println("pdId：" + hti.getProcessDefinitionId());
                System.out.println("pid：" + hti.getProcessInstanceId());
                System.out.println("assignee：" + hti.getAssignee());
                System.out.println("startTime：" + hti.getStartTime());
                System.out.println("endTime：" + hti.getEndTime());
                System.out.println("duration：" + hti.getDurationInMillis());
            }
        }
    }

    //4.查询历史流程变量
    //某一次流程的执行一共设置的流程变量
    @Test
    public void queryHistoricVariables() throws Exception {
        //历史任务办理人
        String processInstanceId = "12501";
        List<HistoricVariableInstance> hviList = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()//创建历史流程变量传
                .processInstanceId(processInstanceId)//使用流程实例Id查询
                .orderByProcessInstanceId().asc()//排序条件
                .list();
        if (hviList != null && hviList.size() > 0) {
            for (HistoricVariableInstance hvi : hviList) {
                System.out.println("pId:" + hvi.getProcessInstanceId());
                System.out.println("variablesName:" + hvi.getVariableName());
                System.out.println("variablesValue:" + hvi.getValue());
            }
        }
    }

    /**
     * 总结：
     * 由于数据库中保存着历史信息以及正在运行的流程实例信息，在实际项目中对已完成任务的查看频率远不及对代办和可接任务的查看，所以在activiti采用分开管理，把正在运行的交给RuntimeService、TaskService管理，而历史数据交给HistoryService来管理。
     这样做的好处在于，加快流程执行的速度，因为正在执行的流程的表中数据不会很大。

     */
}
