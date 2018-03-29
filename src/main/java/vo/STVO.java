package vo;

import vo.EPVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/3/10.
 */
public class STVO {

    public int id;

    public List<EPVO> EPs = new ArrayList<>();

    public int duration;

    public int startTime;

    public int startingEP;

    public int startingEPId;

    public int startingTask;

    public STVO() {
    }

    public STVO(int id, List<EPVO> EPs, int duration, int startTime, int startingEP, int startingEPId, int startingTask) {
        this.id = id;
        this.EPs = EPs;
        this.duration = duration;
        this.startTime = startTime;
        this.startingEP = startingEP;
        this.startingEPId = startingEPId;
        this.startingTask = startingTask;
    }

}