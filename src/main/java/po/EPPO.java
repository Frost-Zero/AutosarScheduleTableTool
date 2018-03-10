package po;

import java.util.List;

/**
 * Created by Frost-D on 18/3/7.
 */
public class EPPO {

    private int id;

    private int offset;

    private List<TaskPO> Tasks;

    public EPPO() {
    }

    public EPPO(int id, int offset, List<TaskPO> tasks) {
        this.id = id;
        this.offset = offset;
        Tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<TaskPO> getTasks() {
        return Tasks;
    }

    public void setTasks(List<TaskPO> tasks) {
        Tasks = tasks;
    }
}
