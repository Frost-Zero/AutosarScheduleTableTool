package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import service.STService;
import service.ServiceFactory;
import vo.EPVO;
import vo.STVO;

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

    private STService stService = ServiceFactory.STService();

    private List<EPVO> epvo = new ArrayList<>();

    private EPManagementViewController epManagementViewController;

    @FXML
    public void initialize() {
        updateFunction();
    }

    public void updateFunction(){

        List<EPVO> vos = stService.findEPs();

        for (EPVO ep:stService.findEPs()) {
            tfEPOffset.setText(""+ep.offset);
            System.out.println(tfEPOffset.getText());
         }

        for (STVO st:stService.findSTs()) {
            labelEPDuration.setText("      "+st.duration+"        )");
        }
    }

//    public void setEPManagementViewController(EPManagementViewController epManagementViewController) {
//        this.EPManagementViewController = epManagementViewController;
//    }

}
