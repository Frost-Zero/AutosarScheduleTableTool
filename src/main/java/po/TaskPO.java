package po;

/**
 * Created by Frost-D on 18/3/10.
 */
public class TaskPO {

    private int id;

    private int priority;

    private int deadline;

    private int execution;

    public TaskPO() {
    }

    public TaskPO(int id, int priority, int deadline, int execution) {
        this.id = id;
        this.priority = priority;
        this.deadline = deadline;
        this.execution = execution;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getExecution() {
        return execution;
    }

    public void setExecution(int execution) {
        this.execution = execution;
    }
}
