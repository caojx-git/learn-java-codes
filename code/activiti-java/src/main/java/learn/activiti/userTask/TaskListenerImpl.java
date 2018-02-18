package learn.activiti.userTask;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author caojx
 * Created on 2018/2/14 下午12:41
 */
public class TaskListenerImpl implements TaskListener {

    //用来指定任务的办理人
    @Override
    public void notify(DelegateTask delegateTask){
        //指定个人任务的办理人，也可以指定组任务的办理人

        //个人任务：通过类取查询数据库，将下一个任务的办理人查询获取，然后通过setAssignee（）的方法指定任务的办理人
        delegateTask.setAssignee("灭绝师太");

    }
}
