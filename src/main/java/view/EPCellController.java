package view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import vo.EPVO;

/**
 * Created by Frost-D on 18/1/6.
 */
public class EPCellController {

    @FXML
    private Label labelEPCId;

    @FXML
    private Label labelEPCOffset;

    @FXML
    public void onEPClick(Event event) {
        Node node = (Node) event.getSource();
        node.getParent().toFront();
    }

    public void setEPVO(EPVO ep){
        labelEPCId.setText("EP"+ep.id);
        labelEPCOffset.setText(""+ep.offset);
    }
}
