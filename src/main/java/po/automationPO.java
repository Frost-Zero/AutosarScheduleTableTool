package po;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/4/2.
 */
public class automationPO {

    private int stId;

    private int epId;

    private int offset;

    private int duration;

//    private List<Integer> epIds = new ArrayList<>();
//
//    private List<Integer> delays = new ArrayList<>();

    public automationPO() {
    }

    public automationPO(int stId, int epId, int offset, int duration) {
        this.stId = stId;
        this.epId = epId;
        this.offset = offset;
        this.duration = duration;
//        this.epIds = epIds;
//        this.delays = delays;
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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
