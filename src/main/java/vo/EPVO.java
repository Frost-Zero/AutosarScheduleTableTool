package vo;

import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/2/25.
 */
public class EPVO {

    public int id;
    public int offset;

    public List<Integer> TaskIds = new ArrayList<>();

    public EPVO() {
    }

    public EPVO(int id, int offset, List<Integer> taskIds) {
        this.id = id;
        this.offset = offset;
        TaskIds = taskIds;
    }
}
