package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import vo.VeriEPVO;

/**
 * Created by Frost-D on 18/3/29.
 */
public class VerificationEPComponentController {

    @FXML
    private Label labelVeriEP;

    private VerificationGraphController verificationGraphController;

    public void setVerificationGraphController(VeriEPVO vo) {
        String string = "";
        for (Integer i:vo.epIds) {
            string = string + ("EP" + i + " ");
        }
        labelVeriEP.setText(string);
    }

}
