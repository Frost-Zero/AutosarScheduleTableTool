package po;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/3/10.
 */
public class STPO {

    private int id;

    private List<EPPO> EPs = new ArrayList<>();

    private int duration;

    public STPO() {
    }

    public STPO(int id, List<EPPO> EPs, int duration) {
        this.id = id;
        this.EPs = EPs;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<EPPO> getEPs() {
        return EPs;
    }

    public void setEPs(List<EPPO> EPs) {
        this.EPs = EPs;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
