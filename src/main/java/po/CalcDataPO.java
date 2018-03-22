package po;

/**
 * Created by Frost-D on 18/3/21.
 */
public class CalcDataPO {

    private int stId;

    private int epId;

    private int taskId;

    private int duration;

    private int todo;

    private int execution;

    private int deadline;

    private int priority;

    private boolean isDelayed;

    private int releaseTime;

    public CalcDataPO() {
    }

    public CalcDataPO(int stId, int epId, int taskId, int duration, int todo, int execution, int deadline, int priority, boolean isDelayed, int releaseTime) {
        this.stId = stId;
        this.epId = epId;
        this.taskId = taskId;
        this.duration = duration;
        this.todo = todo;
        this.execution = execution;
        this.deadline = deadline;
        this.priority = priority;
        this.isDelayed = isDelayed;
        this.releaseTime = releaseTime;
    }

    public int getStId() {
        return stId;
    }

    public void setStId(int stId) {
        this.stId = stId;
    }

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTodo() {
        return todo;
    }

    public void setTodo(int todo) {
        this.todo = todo;
    }

    public int getExecution() {
        return execution;
    }

    public void setExecution(int execution) {
        this.execution = execution;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isDelayed() {
        return isDelayed;
    }

    public void setDelayed(boolean delayed) {
        isDelayed = delayed;
    }

    public int getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(int releaseTime) {
        this.releaseTime = releaseTime;
    }
}