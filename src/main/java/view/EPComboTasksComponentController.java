package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import service.STService;
import service.ServiceFactory;
import service.TaskService;
import vo.TaskVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/3/14.
 */
public class EPComboTasksComponentController {

    @FXML
    private Pane paneEPTask;

    @FXML
    private ComboBox comboEPTask;

    private EPConfigComponentController epConfigComponentController;

    private TaskService taskService = ServiceFactory.taskService();
    private STService stService = ServiceFactory.STService();

    private List<TaskVO> tasks = new ArrayList<>();

    private int epId;
    private int stId;
    private int index;

    private int taskId;

    @FXML
    public void initialize() {


    }

    public void setEpConfigComponentController(EPConfigComponentController epConfigComponentController) {
        this.epConfigComponentController = epConfigComponentController;
    }

    public void init(int STId, int EPId, int index, int taskId) {
        this.stId = STId;
        this.epId = EPId;
        this.index = index;
        this.taskId = taskId;

        initAllTasks();

        int taskIndex = -1;

        for (int i = 0; i < tasks.size(); i++) {
            TaskVO taskVO = tasks.get(i);
            if (taskId == taskVO.id) {
                taskIndex = i;
            }
        }

        comboEPTask.getSelectionModel().select(taskIndex);

        // TODO
        comboEPTask.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                String string = comboEPTask.getSelectionModel().getSelectedItem().toString();
                String result = string.split("\\(")[0].substring(1);
                Integer id = Integer.valueOf(result);
                stService.updateTaskIdInEPs(STId, EPId, index, id);
            }
        });
    }

    public void onEPComboSelected() {

        epConfigComponentController.setSelectedIndex(this.index);
    }

    public void initAllTasks() {
        tasks = taskService.findTasks();

        for (TaskVO taskvo : tasks) {
            String string = "T" + taskvo.id + "( " + taskvo.execution + ", " + taskvo.deadline + ", " + taskvo.priority + " )";
            comboEPTask.getItems().add(string);

        }
    }
}
