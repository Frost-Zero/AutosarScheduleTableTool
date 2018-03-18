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

//    private List<Node> nodes = new ArrayList<>();

    private EPConfigComponentController epConfigComponentController;

    private TaskService taskService = ServiceFactory.taskService();
    private STService stService = ServiceFactory.STService();

    private List<TaskVO> tasks = new ArrayList<>();

//    private Hashtable<Integer, Integer> idToIndex = new Hashtable<>();

    private int epId;
    private int stId;
    private int index;

//    private static int node_selected = 0;
//
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

//        comboEPTask.getSelectionModel().select(taskService.);

//        TaskVO taskVO = taskService.findTaskById(taskId);

        int taskIndex = -1;

        for (int i=0; i< tasks.size(); i++) {
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

//                int index = node_selected;
                String string = comboEPTask.getSelectionModel().getSelectedItem().toString();
                String result = string.split("\\(")[0].substring(1);
                Integer id = Integer.valueOf(result);
//                TaskVO newVO = (TaskVO) o
                stService.updateTaskIdInEPs(STId, EPId, index, id);
            }
        });
    }

    public void onEPComboSelected() {

//        node_selected = nodes.indexOf(paneEPTask);


        epConfigComponentController.setSelectedIndex(this.index);
    }

    public void initAllTasks() {
//        this.epvo = epvo;
        tasks = taskService.findTasks();
//        idToIndex.clear();

        for (TaskVO taskvo : tasks) {
            String string = "T" + taskvo.id + "( " + taskvo.execution + ", " + taskvo.deadline + ", " + taskvo.priority + " )";
            comboEPTask.getItems().add(string);

//            idToIndex.put(taskvo.id, comboEPTask.getItems().size() - 1);

            }
//        if(taskid_temp > 0) {
//            comboEPTask.getSelectionModel().select(findComboIndexByTaskId(taskid_temp));
//        }

    }

//    public int findComboIndexByTaskId(int taskid) {

//        return idToIndex.get(taskid);

//    }



//    public int getTaskIDTemp() {
//        return taskid_temp;
//    }
//
//    public int getNodeSelected() {
//        return node_selected;
//    }
//
//    public void setNodeSelected(int i) {
//        node_selected = i;
//    }
}
