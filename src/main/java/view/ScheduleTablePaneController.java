package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
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

    public static final double EPLISTLAYOUT_X = 15;
    public static final double EPLISTLAYOUT_Y = 220;

    @FXML
    private Pane EPListPane;

    @FXML
    private Pane STAxisPane;

    private STAxisController stAxisController;

    private List<EPVO> EPVOs = new ArrayList<>();
    private Hashtable<Integer, FXMLLoader> EPLoaders = new Hashtable<>();
    private Hashtable<Integer, Node> EPNodes = new Hashtable<>();

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

    private void layoutEP(EPVO ep){
        int maxDuration = stAxisController.getDuration();
        System.out.println(maxDuration+ "!!!");
        Node node = EPNodes.get(ep.id);

        node.setLayoutX(EPLISTLAYOUT_X + (float) ep.offset / maxDuration * Constant.STAXIS_WIDTH);
        node.setLayoutY(EPLISTLAYOUT_Y);

    }
}
