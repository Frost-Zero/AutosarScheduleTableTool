package vo;

import java.util.List;

/**
 * Created by Frost-D on 18/3/10.
 */
public class TaskVO {

    public int id;
    public int priority;
    public int deadline;
    public int execution;

    public TaskVO() {
    }

    public TaskVO(int id, int priority, int deadline, int execution) {
        this.id = id;
        this.priority = priority;
        this.deadline = deadline;
        this.execution = execution;
    }
}
