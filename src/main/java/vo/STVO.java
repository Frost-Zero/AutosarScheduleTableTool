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

    public STVO() {
    }

    public STVO(int id, List<EPVO> EPs, int duration, int startTime) {
        this.id = id;
        this.EPs = EPs;
        this.duration = duration;
        this.startTime = startTime;
    }

}