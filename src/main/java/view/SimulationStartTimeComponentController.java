package view;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.STService;
import service.ServiceFactory;

/**
 * Created by Frost-D on 18/3/23.
 */
public class SimulationStartTimeComponentController {

    @FXML
    private Label labelStartTime;

    @FXML
    private TextField tfStartTime;

    private SimulationViewController simulationViewController;

    private STService stService = ServiceFactory.STService();

    private int stid;

    @FXML
    public void initialize() {


    }

    public void setSimulationViewController(SimulationViewController simulationViewController) {
        this.simulationViewController = simulationViewController;
    }

    public void init(int STId) {
        this.stid = STId;
        labelStartTime.setText("ST " + stid + ":");

        tfStartTime.setText("" + stService.findStartTimeBySTId(stid));
    }

    @FXML
    public void onStartTimeChanged() {
        String string = tfStartTime.getText();
        int startTime = Integer.parseInt(string);

        stService.updateStartTimeBySTId(stid, startTime);
    }
}
