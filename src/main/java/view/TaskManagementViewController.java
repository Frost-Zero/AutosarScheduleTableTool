package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by Frost-D on 18/2/25.
 */
public class TaskManagementViewController {

    private int i = 2;

    @FXML
    private TabPane tmTabPane;

    @FXML
    private Button btnTMDelete;

    @FXML
    public void initialize() {
        BorderPane borderPane = new BorderPane();
        tmTabPane.getTabs().get(0).setContent(borderPane);
        addTaskPaneInTab(0);
        System.out.println("ok!!");
        refreshTMBtnDelete();
    }

    private void addTaskPaneInTab(int index) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/TaskConfigView.fxml"));
            Pane pane = loader.load();

            BorderPane borderPane = (BorderPane) tmTabPane.getTabs().get(index).getContent();
            borderPane.setCenter(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTMConfig() {
        Tab tab = new Tab();
        int order = tmTabPane.getTabs().size();
        tab.setText("T"+ i);
        BorderPane borderPane = new BorderPane();
        tmTabPane.getTabs().add(tab);
        tmTabPane.getTabs().get(order).setContent(borderPane);
        System.out.println("tab has been added!!");
        addTaskPaneInTab(order);
        i++;
        refreshTMBtnDelete();
    }

    public void onTMDeleteClick(){
        SingleSelectionModel<Tab> selectionModel = tmTabPane.getSelectionModel();
        tmTabPane.getTabs().remove(selectionModel.getSelectedItem());
        if(selectionModel.getSelectedItem() != tmTabPane.getTabs().get(tmTabPane.getTabs().size()-1))
            selectionModel.select(selectionModel.getSelectedIndex()+1);
        refreshTMBtnDelete();
    }

    private void refreshTMBtnDelete(){
        if(tmTabPane.getTabs().size() == 1)
            btnTMDelete.setDisable(true);
        else if(tmTabPane.getTabs().size() >= 2)
            btnTMDelete.setDisable(false);
    }
}
