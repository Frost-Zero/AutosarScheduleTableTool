package view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/3/14.
 */
public class EPComboTasksComponentController {

    @FXML
    private Pane paneEPTask;

    private List<Node> nodes = new ArrayList<>();

    private static int node_selected = -1;

    public void onEPComboSelected() {
        nodes = paneEPTask.getParent().getChildrenUnmodifiable();

        for (Node node:nodes) {
            node.setStyle("-fx-background-color: #FFFFFF;");
        }

        paneEPTask.setStyle("-fx-background-color: #DDDDDD;");
        node_selected = nodes.indexOf(paneEPTask);
    }

    public int getNodeSelected() {
        return node_selected;
    }

    public void setNodeSelected(int i) {
        node_selected = i;
    }
}
