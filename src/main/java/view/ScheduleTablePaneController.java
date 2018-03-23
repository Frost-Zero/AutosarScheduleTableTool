package view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import po.EPPO;
import service.STService;
import service.ServiceFactory;
import service.TaskService;
import vo.EPVO;
import util.Constant;
import vo.STVO;
import vo.TaskVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Frost-D on 18/1/6.
 */

public class ScheduleTablePaneController {

    public static final double EPLISTLAYOUT_X = 90 - 40;
    public static final double EPLISTLAYOUT_Y = 220;

    @FXML
    private Pane EPListPane;

    @FXML
    private Pane STAxisPane;


    private STVO stvo;
    private int stvo_id;
    private TaskVO taskvo;
    private int taskvo_id;
    private STAxisController stAxisController;
    private EPManagementViewController epManagementViewController;
    private EPCellController epCellController;
    private TaskManagementViewController taskManagementViewController;

    private STService stService = ServiceFactory.STService();
    private TaskService taskService = ServiceFactory.taskService();

    private Hashtable<Integer, FXMLLoader> EPLoaders = new Hashtable<>();
    private Hashtable<Integer, Node> EPNodes = new Hashtable<>();

    private Stage TMStage;
    private Stage EPMStage;
    private Stage SimuStage;
    private Stage VeriStage;

    @FXML
    public void initialize() {
        try {
            FXMLLoader axisLoader = new FXMLLoader();
            axisLoader.setLocation(getClass().getResource("/view/STAxis.fxml"));
            Pane pane = axisLoader.load();
            stAxisController = axisLoader.getController();
            stAxisController.setScheduleTablePaneController(this);

            STAxisPane.getChildren().add(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(taskService.findTasks().size() == 0)
            taskService.createTask0();

    }


    public void setSTVO(STVO stvo) {
        this.stvo = stvo;
        stAxisController.setSTVO(stvo);

        List<EPVO> epvos = stvo.EPs;

        EPNodes.forEach((key, node) -> {EPListPane.getChildren().remove(node);});
        EPNodes.clear();
        for (EPVO epvo:epvos) {
            addEPCell(epvo);
        }

        refreshEPLayout();
    }

    public void setSTVOID(int id) {
        this.stvo_id = id;
    }

//    public void setTaskVO(TaskVO taskvo) {
//        this.taskvo = taskvo;
//
//    }

//    public void setTaskVOID(int id) {
//        this.taskvo_id = id;
//    }

    public void refreshSTVO(STVO stvo) {
        this.stvo = stvo;

        refreshEPLayout();

    }

    public void addEPCell(EPVO ep) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EPCell.fxml"));


            Node node = loader.load();
            EPLoaders.put(ep.id, loader);
            EPNodes.put(ep.id, node);

            EPListPane.getChildren().add(node);
            epCellController = loader.getController();
            epCellController.setEPVO(ep);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void refreshEPLayout() {

        for (EPVO ep : stvo.EPs) {
            layoutEP(ep);
        }
    }

    private void layoutEP(EPVO ep) {
        int maxDuration = stvo.duration;
        Node node = EPNodes.get(ep.id);

        node.setLayoutX(EPLISTLAYOUT_X + (float) ep.offset / maxDuration * Constant.STAXIS_WIDTH);
        node.setLayoutY(EPLISTLAYOUT_Y);

    }

    @FXML
    public void onTMClick() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/TaskManagementView.fxml"));
            VBox vbox = loader.load();
            taskManagementViewController = loader.getController();
            taskManagementViewController.setTaskVO(taskService.findTaskById(taskvo_id));

            TMStage = new Stage();
//            TMStage.initModality(Modality.APPLICATION_MODAL);
            TMStage.initStyle(StageStyle.UTILITY);
            TMStage.setScene(new Scene(vbox));
            TMStage.setOnCloseRequest((event) -> {
                TMStage = null;
            });
            TMStage.show();
            TMStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent event) {
                    TMStage.close();
                    TMStage = null;
//                    taskManagementViewController.onTMDeleteClick();
                    taskvo = taskService.findTaskById(taskvo_id);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onEPMClick() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EPManagementView.fxml"));
            VBox vbox = loader.load();
            epManagementViewController = loader.getController();
            epManagementViewController.setSTVO(stService.findSTById(stvo_id));

            EPMStage = new Stage();
//            EPMStage.initModality(Modality.APPLICATION_MODAL);
            EPMStage.initStyle(StageStyle.UTILITY);
            EPMStage.setScene(new Scene(vbox));
            EPMStage.setOnCloseRequest((event) -> {
                EPMStage = null;
            });
            EPMStage.show();
            EPMStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent event) {
                    EPMStage.close();
                    EPMStage = null;
                    stvo = stService.findSTById(stvo_id);
                    setSTVO(stvo);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSimuClick(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/SimulationView.fxml"));
            VBox vbox = loader.load();

            SimuStage = new Stage();
            SimuStage.initModality(Modality.APPLICATION_MODAL);
            SimuStage.initStyle(StageStyle.UTILITY);
            SimuStage.setScene(new Scene(vbox));
            SimuStage.setOnCloseRequest((event) -> {
                SimuStage = null;
            });
            SimuStage.setWidth(900);
            SimuStage.setHeight(600);
            SimuStage.show();
//            Popup popup = new Popup();
//            popup.setWidth(900);
//            popup.setHeight(600);
//            popup.show(SimuStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onVeriClick(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/VerificationView.fxml"));
            VBox vbox = loader.load();

            VeriStage = new Stage();
            VeriStage.initModality(Modality.APPLICATION_MODAL);
            VeriStage.initStyle(StageStyle.UTILITY);
            VeriStage.setScene(new Scene(vbox));
            VeriStage.setOnCloseRequest((event) -> {
                VeriStage = null;
            });
            VeriStage.show();
            Popup popup = new Popup();
            popup.show(VeriStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
