package po;

import java.util.List;

/**
 * Created by Frost-D on 18/3/29.
 */
public class VeriEPPO {

    private int epId;

    private int todo;

    private int duration;

    public VeriEPPO() {
    }

    public VeriEPPO(int epId, int todo, int duration) {
        this.epId = epId;
        this.todo = todo;
        this.duration = duration;
    }

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    public int getTodo() {
        return todo;
    }

    public void setTodo(int todo) {
        this.todo = todo;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
