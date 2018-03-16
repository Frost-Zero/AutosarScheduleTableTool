package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import service.STService;
import service.ServiceFactory;
import service.TaskService;
import vo.EPVO;
import vo.TaskVO;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Frost-D on 18/3/14.
 */
public class EPComboTasksComponentController {

    @FXML
    private Pane paneEPTask;

    @FXML
    private ComboBox comboEPTask;

    private List<Node> nodes = new ArrayList<>();

    private TaskService taskService = ServiceFactory.taskService();
    private STService stService = ServiceFactory.STService();

    private List<TaskVO> Tasks = new ArrayList<>();

    private Hashtable<Integer, Integer> idToIndex = new Hashtable<>();

    private EPVO epvo;

    private static int node_selected = 0;

    private static int taskid_temp = -1;

    @FXML
    public void initialize() {
        comboEPTask.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                int index = node_selected;
                String string = comboEPTask.getSelectionModel().getSelectedItem().toString();
                String result = string.split("\\(")[0].substring(1);
                Integer i = Integer.valueOf(result);
                stService.updateTaskIdInEPs(0,epvo.id,index,i);
            }
        });

    }

    public void onEPComboSelected() {
        nodes = paneEPTask.getParent().getChildrenUnmodifiable();

        for (Node node:nodes) {
            node.setStyle("-fx-background-color: #FFFFFF;");
        }

        paneEPTask.setStyle("-fx-background-color: #DDDDDD;");
        node_selected = nodes.indexOf(paneEPTask);
    }

    public void loadComboTaskMessage(EPVO epvo) {
        this.epvo = epvo;
        Tasks = taskService.findTasks();
        idToIndex.clear();

        for (TaskVO taskvo : Tasks) {
            String string = "T" + taskvo.id + "( " + taskvo.execution + ", " + taskvo.deadline + ", " + taskvo.priority + " )";

            comboEPTask.getItems().add(string);

            idToIndex.put(taskvo.id, comboEPTask.getItems().size() - 1);


            }
        if(taskid_temp > 0) {
            comboEPTask.getSelectionModel().select(findComboIndexByTaskId(taskid_temp));
        }



    }

    public int findComboIndexByTaskId(int taskid) {

        return idToIndex.get(taskid);

    }

    public void setTaskIDTemp(int i) {
        taskid_temp = i;
    }

    public int getTaskIDTemp() {
        return taskid_temp;
    }

    public int getNodeSelected() {
        return node_selected;
    }

    public void setNodeSelected(int i) {
        node_selected = i;
    }
}
