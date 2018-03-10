package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import service.STService;
import service.ServiceFactory;
import vo.EPVO;
import vo.STVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/3/7.
 */
public class EPManagementViewController {


    @FXML
    private TabPane EPmTabPane;

    @FXML
    private Button btnEPMDelete;

    private STService stService = ServiceFactory.STService();

    List<FXMLLoader> loaders = new ArrayList<>();

    @FXML
    public void initialize() {
        addEPConfig();

        refreshEPMBtnDelete();
    }

    public void addEPConfig() {
        EPVO epvo = new EPVO();
        stService.createEP(epvo);
        refreshEPMTabs();
    }

    public void refreshEPMTabs() {
        List<EPVO> vos = stService.findEPs();

        for (int i = 0; i < vos.size(); i++) {
            EPVO vo = vos.get(i);
            if (i >= EPmTabPane.getTabs().size()) {
                addTab();
            }
            Tab tab = EPmTabPane.getTabs().get(i); // i

            tab.setText("EP" + vo.id);
        }
    }

    public void addTab(){
        Tab tab = new Tab();
        BorderPane borderPane = new BorderPane();
        tab.setContent(borderPane);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EPConfigComponent.fxml"));
            Pane pane = loader.load();
            loaders.add(loader);

            borderPane.setCenter(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }

        EPmTabPane.getTabs().add(tab);

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
