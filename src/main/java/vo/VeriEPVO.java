package vo;

import java.util.List;

/**
 * Created by Frost-D on 18/3/29.
 */
public class VeriEPVO {

    public List<Integer> epIds;

    public int next;

    public VeriEPVO() {
    }

    public VeriEPVO(List<Integer> epIds, int next) {
        this.epIds = epIds;
        this.next = next;
    }
}
