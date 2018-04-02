package vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/4/2.
 */
public class automationVO {

    public int stId;

    public List<Integer> epIds = new ArrayList<>();

    public List<Integer> delays = new ArrayList<>();

    public automationVO() {
    }

    public automationVO(int stId, List<Integer> epIds, List<Integer> delays) {
        this.stId = stId;
        this.epIds = epIds;
        this.delays = delays;
    }
}
