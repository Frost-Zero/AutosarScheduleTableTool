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

    private int start_time;

    public STPO() {
    }

    public STPO(int id, List<EPPO> EPs, int duration, int start_time) {
        this.id = id;
        this.EPs = EPs;
        this.duration = duration;
        this.start_time = start_time;
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

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }
}
