package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import vo.VeriEPVO;

/**
 * Created by Frost-D on 18/3/29.
 */
public class VerificationEPLineController {

    @FXML
    private Label labelVeriEPLine;

    public void setVerificationGraphController(VeriEPVO vo) {
        String string = "" + vo.next;
        labelVeriEPLine.setText(string);
    }
}
