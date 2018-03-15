package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import vo.TaskVO;

/**
 * Created by Frost-D on 18/3/7.
 */
public class TaskConfigController {

    @FXML
    private TextField tfTaskExecution;

    @FXML
    private TextField tfTaskDeadline;

    @FXML
    private TextField tfTaskPriority;

    @FXML
    private Label labelTaskDeadline;

    @FXML
    private Label labelTaskDuration;


    public void setTaskVO(TaskVO vo) {

    }

    public void setTaskVOID(int id) {

    }
}
