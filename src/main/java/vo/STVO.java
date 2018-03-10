package vo;

import vo.EPVO;

import java.util.List;

/**
 * Created by Frost-D on 18/3/10.
 */
public class STVO {

    public int id;

    public List<EPVO> EPs;

    public STVO() {
    }

    public STVO(int id, List<EPVO> EPs) {
        this.id = id;
        this.EPs = EPs;
    }

}