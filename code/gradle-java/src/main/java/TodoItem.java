/**
 * @author caojx
 * Created on 2018/1/30 下午9:47
 */
public class TodoItem {
    //代办事项名称
    private String name;
    //已完成
    private boolean hasDone;

    public TodoItem(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasDone() {
        return hasDone;
    }

    public void setHasDone(boolean hasDone) {
        this.hasDone = hasDone;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "name='" + name + '\'' +
                ", hasDone=" + hasDone +
                '}';
    }
}
