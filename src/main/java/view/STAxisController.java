package view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import service.STService;
import service.ServiceFactory;
import vo.STVO;

import java.util.List;


/**
 * Created by Frost-D on 18/1/6.
 */
public class STAxisController {

    @FXML
    private TextField tfDuration;

    private STService stService = ServiceFactory.STService();

    private ScheduleTablePaneController scheduleTablePaneController;

    private STVO stvo;


    @FXML
    public void initialize() {

        tfDuration.textProperty().addListener((observable, oldValue, newValue) -> {

        });
    }

    public void setScheduleTablePaneController(ScheduleTablePaneController scheduleTablePaneController) {
        this.scheduleTablePaneController = scheduleTablePaneController;
    }



    public void setSTVO(STVO stvo) {
        this.stvo = stvo;

        setTfDuration(stvo.duration);
    }

    private void refreshSTVO() {
        STVO newST = stService.findSTById(stvo.id);
        this.stvo = newST;
        setTfDuration(stvo.duration);
        scheduleTablePaneController.refreshSTVO(newST);
    }

    @FXML
    public void onTFDurationAction(Event event) {

        String text = tfDuration.getText();
        int num = Integer.parseInt(text);

//        System.out.print(stvo.duration);
        stvo.duration = num;
        stService.updateSTById(stvo.id, stvo);

        refreshSTVO();

    }

    private void setTfDuration(int duration) {
        tfDuration.setText(duration + "");
    }

}
