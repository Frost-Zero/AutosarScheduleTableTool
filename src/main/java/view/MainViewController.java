package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by Frost-D on 18/1/6.
 */
public class MainViewController {
    @FXML
    private TabPane stTabPane;

    @FXML
    public void initialize() {
        addSTPaneInTab(0);

    }

    private void addSTPaneInTab(int index) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ScheduleTablePane.fxml"));
            Pane pane = loader.load();

            BorderPane borderPane = (BorderPane) stTabPane.getTabs().get(index).getContent();
            borderPane.setCenter(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
