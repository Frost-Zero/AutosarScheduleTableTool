package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import vo.EPVO;
import util.Constant;

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


    private STAxisController stAxisController;

    private List<EPVO> EPVOs = new ArrayList<>();
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

        // test
        EPVO ep1 = new EPVO(0, 1);
        addEPCell(ep1);

        EPVO ep2 = new EPVO(1, 5);
        addEPCell(ep2);

        EPVO ep3 = new EPVO(2, 7);
        addEPCell(ep3);

        EPVO ep4 = new EPVO(3, 8);
        addEPCell(ep4);
    }

    public List<EPVO> getEPVOs() {
        return EPVOs;
    }

    public void addEPCell(EPVO ep) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EPCell.fxml"));


            Node node = loader.load();
            EPVOs.add(ep);
            EPLoaders.put(ep.id, loader);
            EPNodes.put(ep.id, node);

            EPListPane.getChildren().add(node);
            layoutEP(ep);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void refreshEPLayout() {
        for (EPVO ep : EPVOs) {
            layoutEP(ep);
        }
    }

    private void layoutEP(EPVO ep) {
        int maxDuration = stAxisController.getDuration();
        System.out.println(maxDuration + "!!!");
        Node node = EPNodes.get(ep.id);

        node.setLayoutX(EPLISTLAYOUT_X + (float) ep.offset / maxDuration * Constant.STAXIS_WIDTH);
        node.setLayoutY(EPLISTLAYOUT_Y);

    }

    @FXML
    public void onTMClick() {
        if (TMStage != null) {
            TMStage.requestFocus();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/TaskManagementView.fxml"));
            VBox vbox = loader.load();

            TMStage = new Stage();
            TMStage.setScene(new Scene(vbox));
            TMStage.setOnCloseRequest((event) -> {
                TMStage = null;
            });
            TMStage.show();
            Popup popup = new Popup();
            popup.show(TMStage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onEPMClick() {
        if (EPMStage != null) {
            EPMStage.requestFocus();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EPManagementView.fxml"));
            VBox vbox = loader.load();

            EPMStage = new Stage();
            EPMStage.setScene(new Scene(vbox));
            EPMStage.setOnCloseRequest((event) -> {
                EPMStage = null;
            });
            EPMStage.show();
            Popup popup = new Popup();
            popup.show(EPMStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSimuClick(){
        if (SimuStage != null) {
            SimuStage.requestFocus();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/SimulationView.fxml"));
            VBox vbox = loader.load();

            SimuStage = new Stage();
            SimuStage.setScene(new Scene(vbox));
            SimuStage.setOnCloseRequest((event) -> {
                SimuStage = null;
            });
            SimuStage.show();
            Popup popup = new Popup();
            popup.setWidth(800);
            popup.setHeight(600);
            popup.show(SimuStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onVeriClick(){
        if (VeriStage != null) {
            VeriStage.requestFocus();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/VerificationView.fxml"));
            VBox vbox = loader.load();

            VeriStage = new Stage();
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
