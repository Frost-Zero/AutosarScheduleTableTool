package po;

import java.util.List;

/**
 * Created by Frost-D on 18/3/10.
 */
public class STPO {

    private int id;

    private List<EPPO> EPs;

    public STPO() {
    }

    public STPO(int id, List<EPPO> EPs) {
        this.id = id;
        this.EPs = EPs;
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
}
