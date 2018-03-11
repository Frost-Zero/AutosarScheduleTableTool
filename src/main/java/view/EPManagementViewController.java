package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import service.STService;
import service.ServiceFactory;
import vo.EPVO;
import vo.STVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
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
    private EPConfigComponentController epConfigComponentController;

    private Hashtable<Integer, FXMLLoader> EPLoaders = new Hashtable<>();
    private Hashtable<Integer, Node> EPNodes = new Hashtable<>();

    @FXML
    public void initialize() {

        List<EPVO> vos = stService.findEPs();
        if(vos.size() == 0)
            addEPConfig();
        else
            refreshEPMTabs();

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
                addTab(vo);
            }
            Tab tab = EPmTabPane.getTabs().get(i); // i

            tab.setText("EP" + vo.id);

        }
    }

    public void addTab(EPVO ep){
        Tab tab = new Tab();
        BorderPane borderPane = new BorderPane();
        tab.setContent(borderPane);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EPConfigComponent.fxml"));
            Node node = loader.load();
            EPLoaders.put(ep.id,loader);
            EPNodes.put(ep.id,node);
            epConfigComponentController=loader.getController();
//            epConfigComponentController.setEPManagementViewController(this);

            borderPane.setCenter(node);

        } catch (IOException e) {
            e.printStackTrace();
        }

        EPmTabPane.getTabs().add(tab);

        refreshEPMBtnDelete();
    }

    public void onEPMDeleteClick(){

        SingleSelectionModel<Tab> selectionModel = EPmTabPane.getSelectionModel();
        String string = EPmTabPane.getSelectionModel().getSelectedItem().getText().substring(2);
        int i = Integer.parseInt(string);

        EPmTabPane.getTabs().remove(EPmTabPane.getSelectionModel().getSelectedItem());
        if(selectionModel.getSelectedItem() != EPmTabPane.getTabs().get(EPmTabPane.getTabs().size()-1))
            selectionModel.select(selectionModel.getSelectedIndex()+1);

        stService.removeEP(i);

        refreshEPMBtnDelete();
    }

    private void refreshEPMBtnDelete(){
        if(EPmTabPane.getTabs().size() == 1)
            btnEPMDelete.setDisable(true);
        else if(EPmTabPane.getTabs().size() >= 2)
            btnEPMDelete.setDisable(false);
    }

}
