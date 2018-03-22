package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import service.STService;
import service.ServiceFactory;
import service.TaskService;
import vo.EPVO;
import vo.STVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/3/10.
 */
public class EPConfigComponentController {

    @FXML
    private TextField tfEPOffset;

    @FXML
    private Label labelEPDuration;

    @FXML
    private VBox VBoxEPTasks;

    @FXML
    private Button btnEPDeleteTask;

    @FXML
    private Button btnEPAddTask;

    private STService stService = ServiceFactory.STService();
    private TaskService taskService = ServiceFactory.taskService();

    private EPVO epvo;

    private int selectedIndex;

    @FXML
    public void initialize() {

//        tfEPOffset.textProperty().addListener((observable, oldValue, newValue) -> {
//
//        });

        refreshBtn();

    }

    public void setEPVO(EPVO epvo){
        this.epvo = epvo;

        setTFEPOffset(epvo.offset);

        labelEPDuration.setText("      "+stService.findSTs().get(0).duration+"        )");

        for(int i = 0; i<epvo.TaskIds.size(); i++) {
            int taskId = epvo.TaskIds.get(i);
            onAddEPTask(epvo.stId, epvo.id, i, taskId);
        }

    }

    public void refreshEPVO(){
        EPVO newEP = stService.findEPinSTById(this.epvo.stId, this.epvo.id);
        this.epvo = newEP;

        setTFEPOffset(epvo.offset);

        VBoxEPTasks.getChildren().clear();

        for(int i = 0; i<epvo.TaskIds.size(); i++) {
            int taskId = epvo.TaskIds.get(i);
            onAddEPTask(epvo.stId, epvo.id, i, taskId);
        }

        this.selectedIndex = -1;
    }


    private void setTFEPOffset(int offset) {
        tfEPOffset.setText("" + offset);
    }

    public void setSelectedIndex(int selectedIndex) {
        List<Node> nodes = VBoxEPTasks.getChildrenUnmodifiable();
        if(this.selectedIndex >= 0) {
            nodes.get(this.selectedIndex).setStyle("-fx-background-color: #FFFFFF;");
        }
        nodes.get(selectedIndex).setStyle("-fx-background-color: #CCCCCC;");
        this.selectedIndex = selectedIndex;


    }

    @FXML
    public void onEPOffsetAction() {

        String text = tfEPOffset.getText();
        int num = Integer.parseInt(text);

        System.out.print(epvo.offset);
        epvo.offset = num;
        //TODO getSTINDEX
        stService.updateEPById(epvo.stId, epvo.id, num);

        refreshEPVO();
    }


    public void onAddEPTaskClick() {

        stService.addTaskIdInEPs(epvo.stId, epvo.id);

        refreshEPVO();
    }

    public void onDeleteEPTaskClick() {
        if(this.selectedIndex >= 0) {
            stService.removeTaskIdInEPs(epvo.stId, epvo.id, this.selectedIndex);
        }
        refreshEPVO();

    }

    public void onAddEPTask(int STId, int EPId, int index, int taskId) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EPComboTasksComponent.fxml"));
            Node node = loader.load();
//            EPLoaders.put(ep.id,loader);
//            EPNodes.put(ep.id,node);
            EPComboTasksComponentController epComboTasksComponentController = loader.getController();
            epComboTasksComponentController.setEpConfigComponentController(this);

            epComboTasksComponentController.init(STId, EPId, index, taskId);
            VBoxEPTasks.getChildren().add(node);
            refreshBtnDeleteEP();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void refreshBtn() {
        refreshBtnDeleteEP();
        refreshBtnAddEP();
    }

    public void refreshBtnDeleteEP() {
        if(VBoxEPTasks.getChildren().size()<=1) {
            btnEPDeleteTask.setDisable(true);
        } else if(VBoxEPTasks.getChildren().size()>=2){
            btnEPDeleteTask.setDisable(false);
        }
    }

    public void refreshBtnAddEP() {
        if(taskService.findTasks().size() <= 1) {
            btnEPAddTask.setDisable(true);
        } else {
            btnEPAddTask.setDisable(false);
        }
    }

//    public void updateEPs() {
//
//    }
//
//    public void cancelUpdateEPs() {
//
//    }

}
