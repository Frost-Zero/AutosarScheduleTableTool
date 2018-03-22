package vo;

/**
 * Created by Frost-D on 18/3/21.
 */
public class CalcDataVO {

    public int stId;

    public int epId;

    public int taskId;

    public int exec;

    public int substain;

    public boolean isDelayed;

    public CalcDataVO() {
    }

    public CalcDataVO(int stId, int epId, int taskId, int exec, int substain, boolean isDelayed) {
        this.stId = stId;
        this.epId = epId;
        this.taskId = taskId;
        this.exec = exec;
        this.substain = substain;
        this.isDelayed = isDelayed;
    }
}
