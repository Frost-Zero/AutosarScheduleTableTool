package view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;

/**
 * Created by Frost-D on 18/1/6.
 */
public class EPCellController {

    @FXML
    public void onEPClick(Event event) {
        Node node = (Node) event.getSource();
        node.getParent().toFront();
    }
}
