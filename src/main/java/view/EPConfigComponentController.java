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
import vo.EPVO;
import vo.STVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
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

    private STService stService = ServiceFactory.STService();

    private List<EPVO> epvos = new ArrayList<>();

    private EPVO epvo;

    private EPComboTasksComponentController epComboTasksComponentController;



    @FXML
    public void initialize() {

        tfEPOffset.textProperty().addListener((observable, oldValue, newValue) -> {

        });

        refreshBtnDeleteEP();

    }

    public void setEPVO(EPVO epvo){
        this.epvo = epvo;

//        tfEPOffset.setText(""+epvo.offset);
        setTFEPOffset(epvo.offset);
        //TODO getSTID
        labelEPDuration.setText("      "+stService.findSTs().get(0).duration+"        )");

    }

    public void refreshEPVO(){
        //TODO getSTID
        EPVO newEP = stService.findEPinSTById(0,epvo.id);
        this.epvo = newEP;
        setTFEPOffset(epvo.offset);

    }


    private void setTFEPOffset(int offset) {
        tfEPOffset.setText("" + offset);
    }

    @FXML
    public void onEPOffsetAction() {

        String text = tfEPOffset.getText();
        int num = Integer.parseInt(text);

        System.out.print(epvo.offset);
        epvo.offset = num;
        //TODO getSTID
        stService.updateEPById(0,epvo.id,num);

        refreshEPVO();
    }


    public void onAddEPTaskClick() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EPComboTasksComponent.fxml"));
            Node node = loader.load();
//            EPLoaders.put(ep.id,loader);
//            EPNodes.put(ep.id,node);
            epComboTasksComponentController=loader.getController();
//            epComboTasksComponentController.setEPVO(ep);

            VBoxEPTasks.getChildren().add(node);
            refreshBtnDeleteEP();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDeleteEPTaskClick() {
        int index;

        index = epComboTasksComponentController.getNodeSelected();

        if(index<VBoxEPTasks.getChildren().size() && index != -1) {
            VBoxEPTasks.getChildren().remove(index);
            epComboTasksComponentController.setNodeSelected(-1);
        }
        refreshBtnDeleteEP();
    }

    public void refreshBtnDeleteEP() {
        if(VBoxEPTasks.getChildren().size()<=1) {
            btnEPDeleteTask.setDisable(true);
        } else if(VBoxEPTasks.getChildren().size()>=2){
            btnEPDeleteTask.setDisable(false);
        }

    }

}
