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

    private int duration;
    private ScheduleTablePaneController scheduleTablePaneController;

    private STVO stvo;


    @FXML
    public void initialize() {

//        setDuration(10);

        tfDuration.textProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println(oldValue + " " + newValue);
        });
    }

    public void setScheduleTablePaneController(ScheduleTablePaneController scheduleTablePaneController) {
        this.scheduleTablePaneController = scheduleTablePaneController;
    }

//    public int getDuration() {
//        return duration;
//    }
//
//    public void setDuration(int duration) {
//        this.duration = duration;

//    }

    public void setSTVO(STVO stvo) {
        this.stvo = stvo;

        setTfDuration(stvo.duration);
    }

    private void refreshSTVO() {
        STVO newST = stService.findSTById(stvo.id);
        this.stvo = newST;
        setTfDuration(stvo.duration);
//        System.out.print("!!!" + newST.duration);
        scheduleTablePaneController.refreshSTVO(newST);
    }
    @FXML
    public void onTFDurationAction(Event event) {

        String text = tfDuration.getText();
        int num = Integer.parseInt(text);

        System.out.print(stvo.duration);
        stvo.duration = num;
        stService.updateSTById(stvo.id, stvo);

        refreshSTVO();
//        tfDuration.setText(duration + "");

//        System.out.println(tfDuration.getText());
//        // mock
//        int min = 5;
//        if (num < min) {
//            num = min;
//        }
//        setDuration(num);
//        scheduleTablePaneController.refreshEPLayout();

    }

    private void setTfDuration(int duration) {
        tfDuration.setText(duration + "");
    }

}
