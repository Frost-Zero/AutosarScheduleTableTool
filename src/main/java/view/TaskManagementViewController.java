package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.TaskService;
import vo.TaskVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/2/25.
 */
public class TaskManagementViewController {

    private int i = 2;

    @FXML
    private TabPane tmTabPane;

    @FXML
    private Button btnTMDelete;

    @FXML
    private Button btnTMConfirm;


    private TaskVO taskvo;

    private TaskService taskService = ServiceFactory.taskService();

    private TaskConfigController taskConfigController;

    List<FXMLLoader> loaders = new ArrayList<>();

    @FXML
    public void initialize() {
        tmTabPane.getTabs().removeAll();

        refreshTMBtnDelete();
    }

    public void setTaskVO(TaskVO taskVO) {
        this.taskvo = taskVO;
//
//        if(taskService.findTasks().size() == 0)
//            taskService.createTask0();

        refreshTMConfig();
    }

    //btnAddTaskConfig
    public void addTMConfig() {
        TaskVO taskvo = new TaskVO();
        taskService.createTask(taskvo);

        refreshTMConfig();
    }


    public void refreshTMConfig() {
        List<TaskVO> vos = taskService.findTasks();

        for (int i = 0; i < vos.size(); i++) {
            TaskVO vo = vos.get(i);
            if (i >= tmTabPane.getTabs().size()) {
                addTaskTab();
            }
            Tab tab = tmTabPane.getTabs().get(i);
            FXMLLoader loader = loaders.get(i);
            taskConfigController = loader.getController();
            taskConfigController.setTaskVO(vo);
            taskConfigController.setTaskVOID(vo.id);

            tab.setText("T" + vo.id);
        }

    }

    public void addTaskTab() {
        Tab tab = new Tab();
        BorderPane borderPane = new BorderPane();
        tab.setContent(borderPane);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/TaskConfigView.fxml"));
            Pane pane = loader.load();
            loaders.add(loader);

//            BorderPane borderPane = (BorderPane) tmTabPane.getTabs().get(index).getContent();
            borderPane.setCenter(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }

        tmTabPane.getTabs().add(tab);

        refreshTMBtnDelete();
    }

    //btnDeleteTaskConfig
    public void onTMDeleteClick() {
        SingleSelectionModel<Tab> selectionModel = tmTabPane.getSelectionModel();
        String string = tmTabPane.getSelectionModel().getSelectedItem().getText().substring(1);
        int i = Integer.parseInt(string);

        tmTabPane.getTabs().remove(selectionModel.getSelectedItem());
        loaders.remove(selectionModel.getSelectedIndex());
        if(selectionModel.getSelectedItem() != tmTabPane.getTabs().get(tmTabPane.getTabs().size()-1) &&
                selectionModel.getSelectedItem() != tmTabPane.getTabs().get(0))
            selectionModel.select(selectionModel.getSelectedIndex()+1);
        refreshTMBtnDelete();

        taskService.removeTask(i);
    }

    private void refreshTMBtnDelete() {
        if(tmTabPane.getTabs().size() == 1)
            btnTMDelete.setDisable(true);
        else if(tmTabPane.getTabs().size() >= 2)
            btnTMDelete.setDisable(false);
    }

    public void onTMConfirmClick() {
        taskConfigController.updateTasks();

        Stage stage = (Stage) btnTMConfirm.getScene().getWindow();
        stage.close();
    }

}
