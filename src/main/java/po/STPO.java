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

    private int startingEP = 0;

    private int startingEPId = 0;

    private int startingTask = 0;

    public STPO() {
    }

    public STPO(int id, List<EPPO> EPs, int duration, int start_time, int startingEP, int startingEPId, int startingTask) {
        this.id = id;
        this.EPs = EPs;
        this.duration = duration;
        this.start_time = start_time;
        this.startingEP = startingEP;
        this.startingEPId = startingEPId;
        this.startingTask = startingTask;
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

    public int getStartingEP() {
        return startingEP;
    }

    public void setStartingEP(int startingEP) {
        this.startingEP = startingEP;
    }

    public int getStartingTask() {
        return startingTask;
    }

    public void setStartingTask(int startingTask) {
        this.startingTask = startingTask;
    }

    public int getStartingEPId() {
        return startingEPId;
    }

    public void setStartingEPId(int startingEPId) {
        this.startingEPId = startingEPId;
    }
}
