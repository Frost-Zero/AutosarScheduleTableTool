package view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


/**
 * Created by Frost-D on 18/1/6.
 */
public class STAxisController {

    @FXML
    private TextField tfDuration;

    private int duration;
    private ScheduleTablePaneController scheduleTablePaneController;

    @FXML
    public void initialize() {

        setDuration(10);

        tfDuration.textProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println(oldValue + " " + newValue);
        });
    }

    public void setScheduleTablePaneController(ScheduleTablePaneController scheduleTablePaneController) {
        this.scheduleTablePaneController = scheduleTablePaneController;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        tfDuration.setText(duration + "");
    }

    @FXML
    public void onTFDurationAction(Event event) {
        String text = tfDuration.getText();
        int num = Integer.parseInt(text);
        System.out.println(tfDuration.getText());
        // mock
        int min = 5;
        if (num < min) {
            num = min;
        }
        setDuration(num);
        scheduleTablePaneController.refreshEPLayout();

    }


}
