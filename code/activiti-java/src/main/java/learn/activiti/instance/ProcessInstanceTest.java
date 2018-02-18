package learn.activiti.instance;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * 流程实例、任务的执行
 *
 * @author caojx
 * Created on 2018/2/6 下午7:18
 */
public class ProcessInstanceTest {

    //1.部署定义流程
    @Test
    public void deployZIP() throws Exception {

        //获取流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //获取输入流
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/HelloWorld.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        //获取仓库服务实例，从类路径下完成部署
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("请假流程")//添加部署规则的显示别名
                .addZipInputStream(zipInputStream) //使用zip的输入流完成部署
                .deploy();//发布完成

        System.out.println(deployment.getId() + "     " + deployment.getName());
    }

    //2.启动流程实例

    /**
     * 当流程到达一个节点时，会在act_ru_execution表中产生1条数据
     * 如果当前节点时用户任务几诶但，这是会在act_run_task表表中产生一条数据（任务的办理人，任务创建时间）
     *
     * @throws Exception
     */
    @Test
    public void startProcess() throws Exception {
        //按照流程定义的id启动流程
        //通过流程定义的key启动流程能，会启动版本最高的流程
        ProcessInstance processInstance = ProcessEngines.getDefaultProcessEngine()
                .getRuntimeService()//获取正在执行的Service
                .startProcessInstanceByKey("HelloWorldProcess");//按照

        System.out.println("pid:" + processInstance.getId() + ",activitiId:" + processInstance.getActivityId() + ",pdId:" + processInstance.getProcessDefinitionId());
    }

    //3.查询我的个人任务

    /**
     * 说明：
     * 1)	因为是任务查询，所以从processEngine中应该得到TaskService
     * 2)	使用TaskService获取到任务查询对象TaskQuery
     * 3)	为查询对象添加查询过滤条件，使用taskAssignee指定任务的办理者（即查询指定用户的代办任务），同时可以添加分页排序等过滤条件
     * 4)	调用list方法执行查询，返回办理者为指定用户的任务列表
     * 5)	任务ID、名称、办理人、创建时间可以从act_ru_task表中查到。
     * 6)	Execution与ProcessInstance见5.6和5.7章节的介绍。在这种情况下，ProcessInstance相当于Execution
     * 7)	如果assignee属性为部门经理，结果为空。因为现在流程只到了”填写请假申请”阶段，后面的任务还没有执行，即在数据库中没有部门经理可以办理的任务，所以查询不到。
     * 8)	一个Task节点和Execution节点是1对1的情况，在task对象中使用Execution_来表示他们之间的关系
     * 9)	任务ID在数据库表act_ru_task中对应“ID_”列
     * <p>
     * 附加：
     * 在activiti任务中，主要分为两大类查询任务（个人任务和组任务）：
     * 1.确切指定了办理者的任务，这个任务将成为指定者的私有任务，即个人任务。
     * 2.无法指定具体的某一个人来办理的任务，可以把任务分配给几个人或者一到	多个小组，让这个范围内的用户可以选择性（如有空余时间时）来办理这类任务，即组任务。
     * 先知道个人任务的查询和办理，组任务的操作后面讲
     *
     * @throws Exception
     */
    @Test
    public void queryPersonalTask() throws Exception {
        //指定任务办理者
        String assignee = "张三";
        //获取流程引擎对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
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

    //4.办理任务（完成任务）

    /**
     * 说明：
     * 1)	是办理任务，所以从ProcessEngine得到的是TaskService。
     * 2)	当执行完这段代码，再以员工的身份去执行查询的时候，会发现这个时候已经没有数据了，因为正在执行的任务中没有数据。
     * 3)	对于执行完的任务，activiti将从act_ru_task表中删除该任务，下一个任务会被插入进来。
     * 4)	以”部门经理”的身份进行查询，可以查到结果。因为流程执行到部门经理审批这个节点了。
     * 5)	再执行办理任务代码，执行完以后以”部门经理”身份进行查询，没有结果。
     * 6)	重复第3和4步直到流程执行完。
     *
     * @throws Exception
     */
    @Test
    public void complete() throws Exception {
        String taskId = "5004";
        //获取流程引擎对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //完成任务
        processEngine.getTaskService().complete(taskId);
    }

    //5.查询流程状态（判断流程正在执行，还是结束）

    /**
     * 在流程执行的过程中，创建的流程实例ID在整个过程中都不会变，当流程结束后，流程实例将会在正在执行的执行对象表中（act_ru_execution）被删除
     * 说明：
     * 1)	因为是查询流程实例，所以先获取runtimeService
     * 2)	创建流程实例查询对象，设置实例ID过滤参数
     * 3)	由于一个流程实例ID只对应一个实例，使用singleResult执行查询返回一个唯一的结果，如果结果数量大于1，则抛出异常
     * 4)	判断指定ID的实例是否存在，如果结果为空，则代表流程结束，实例在正在执行的执行对象表中已被删除，转换成历史数据。
     *
     * @throws Exception
     */
    @Test
    public void queryProcessState() throws Exception {
        String processInstanceId = "20004";
        //获取流程引擎对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //通过流程实例ID查询流程实例
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .createProcessInstanceQuery()//创建流程实例产训，查询正在执行的流程实例
                .processInstanceId(processInstanceId)//按照流程实例ID查询
                .singleResult();//返回唯一的结果集
        if (processInstance != null) {
            System.out.println("当前流程在：" + processInstance.getActivityId());
        } else {
            System.out.println("流程已经结束！");
        }
    }

    //6.查询历史任务
    @Test
    public void queryHistoryTask() throws Exception {
        //历史任务办理人
        String taskAssignee = "张三";
        //获取流程引擎对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //通过流程实例ID查询流程实例
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()//创建历史任务查询
                .taskAssignee(taskAssignee)//指定办理人查询历史任务
                .list();

        if (list != null && list.size() > 0) {
            for (HistoricTaskInstance historicTaskInstance : list) {
                System.out.println("任务ID："+historicTaskInstance.getId());
                System.out.println("流程实例ID："+historicTaskInstance.getProcessInstanceId());
                System.out.println("任务办理人："+historicTaskInstance.getAssignee());
                System.out.println("执行对象ID："+historicTaskInstance.getExecutionId());
                System.out.println(historicTaskInstance.getStartTime()+" "+historicTaskInstance.getEndTime()+"  "+historicTaskInstance.getDurationInMillis());
            }
        }
    }

    //7.插叙历史流程实例
    @Test
    public void queryHistoryProcessInstance() throws Exception{
        String processInstanceId = "5001";
        //获取流程引擎对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()//创建历史流程实查询
                .processInstanceId(processInstanceId)//使用流程实例Id查询
                .singleResult();
        System.out.println("流程定义ID："+historicProcessInstance.getProcessDefinitionId());
        System.out.println("流程实例ID："+historicProcessInstance.getId());
        System.out.println(historicProcessInstance.getStartTime()+"  "+historicProcessInstance.getEndTime()+" "+historicProcessInstance.getDurationInMillis());
    }


    /**
     * 总结：
     * Execution   执行对象
     * 按流程定义的规则执行一次的过程.
     * 对应的表：
     *      act_ru_execution： 正在执行的信息
     *      act_hi_procinst：已经执行完的历史流程实例信息
     *      act_hi_actinst：存放历史所有完成的活动
     * ProcessInstance  流程实例
     * 特指流程从开始到结束的那个最大的执行分支，一个执行的流程中，流程实例只有1个。
     *
     * 注意
     *      （1）如果是单例流程，执行对象ID就是流程实例ID
     *      （2）如果一个流程有分支和聚合，那么执行对象ID和流程实例ID就不相同
     *      （3）一个流程中，流程实例只有1个，执行对象可以存在多个。
     *
     * Task 任务
     * 执行到某任务环节时生成的任务信息。
     * 对应的表：
     *       act_ru_task：正在执行的任务信息
     *       act_hi_taskinst：已经执行完的历史任务信息
     *
     */
}
