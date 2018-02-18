package learn.activiti.groupTask;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author caojx
 * Created on 2018/2/14 下午12:41
 */
public class TaskListenerImpl implements TaskListener {

    //指定个人任务和组任务的办理人
    @Override
    public void notify(DelegateTask delegateTask){
        String userId1 = "孙悟空";
        String userId2 = "猪八戒";

        //指定组任务
        delegateTask.addCandidateUser(userId1);
        delegateTask.addCandidateUser(userId2);
    }
}
