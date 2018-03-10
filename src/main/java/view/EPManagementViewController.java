package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import service.ServiceFactory;

import java.io.IOException;

/**
 * Created by Frost-D on 18/3/7.
 */
public class EPManagementViewController {

    private int i = 2;

    private EPService epService = ServiceFactory.EPService();

    @FXML
    private TabPane EPmTabPane;

    @FXML
    private Button btnEPMDelete;

    @FXML
    public void initialize() {
        BorderPane borderPane = new BorderPane();
        EPmTabPane.getTabs().get(0).setContent(borderPane);
        addTaskPaneInTab(0);
        System.out.println("ok!!");
        refreshEPMBtnDelete();
    }

    private void addTaskPaneInTab(int index) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EPConfigComponent.fxml"));
            Pane pane = loader.load();

            BorderPane borderPane = (BorderPane) EPmTabPane.getTabs().get(index).getContent();
            borderPane.setCenter(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEPConfig() {
        Tab tab = new Tab();
        int order = EPmTabPane.getTabs().size();
        tab.setText("EP"+ i);
        BorderPane borderPane = new BorderPane();
        EPmTabPane.getTabs().add(tab);
        EPmTabPane.getTabs().get(order).setContent(borderPane);
        System.out.println("tab has been added!!");
        addTaskPaneInTab(order);
        i++;
        refreshEPMBtnDelete();
    }

    public void onEPMDeleteClick(){
        SingleSelectionModel<Tab> selectionModel = EPmTabPane.getSelectionModel();
        EPmTabPane.getTabs().remove(EPmTabPane.getSelectionModel().getSelectedItem());
        if(selectionModel.getSelectedItem() != EPmTabPane.getTabs().get(EPmTabPane.getTabs().size()-1))
            selectionModel.select(selectionModel.getSelectedIndex()+1);
        refreshEPMBtnDelete();
    }

    private void refreshEPMBtnDelete(){
        if(EPmTabPane.getTabs().size() == 1)
            btnEPMDelete.setDisable(true);
        else if(EPmTabPane.getTabs().size() >= 2)
            btnEPMDelete.setDisable(false);
    }
}
