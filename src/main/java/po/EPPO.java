package po;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/3/7.
 */
public class EPPO {

    private int id;

    private int offset;

    private List<Integer> TaskIds = new ArrayList<>();

    public EPPO() {
    }

    public EPPO(int id, int offset, List<Integer> taskIds) {
        this.id = id;
        this.offset = offset;
        TaskIds = taskIds;
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

    public List<Integer> getTaskIds() {
        return TaskIds;
    }

    public void setTaskIds(List<Integer> taskIds) {
        TaskIds = taskIds;
    }
}
