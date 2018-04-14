package view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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

    @FXML
    private Button btnEPMConfirm;


    @FXML
    private Label labelSTId;


    private STService stService = ServiceFactory.STService();

    private STVO stvo;

    private EPConfigComponentController epConfigComponentController;

    private Hashtable<Integer, FXMLLoader> EPLoaders = new Hashtable<>();
    private Hashtable<Integer, Node> EPNodes = new Hashtable<>();

    @FXML
    public void initialize() {

        EPmTabPane.getTabs().removeAll();

        refreshEPMBtnDelete();
    }


    public void setSTVO(STVO stvo){
        this.stvo = stvo;

        List<EPVO> epvos = stvo.EPs;
//        System.out.println("epvos.size:"+epvos.size());
        if(epvos.size()<1)
            addEPConfig();
        for (EPVO epvo:epvos) {
            EPConfig(epvo);
        }
        labelSTId.setText("ST"+stvo.id+" :");
    }

    //btnAddEPConfig
    public void addEPConfig() {
        EPVO epvo = new EPVO();
        epvo = stService.createEPInST(stvo.id,epvo);
        refreshEPMTabs(epvo);
    }

    public void EPConfig(EPVO epvo){
        refreshEPMTabs(epvo);
    }

    public void refreshEPMTabs(EPVO vo) {

        addTab(vo);

        Tab tab = EPmTabPane.getTabs().get(EPmTabPane.getTabs().size()-1); // i

        tab.setText("EP" + vo.id);

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
            epConfigComponentController.setEPVO(ep);

            borderPane.setCenter(node);

        } catch (IOException e) {
            e.printStackTrace();
        }

        EPmTabPane.getTabs().add(tab);

        refreshEPMBtnDelete();
    }


    //btnEPDelete
    public void onEPMDeleteClick(){

        SingleSelectionModel<Tab> selectionModel = EPmTabPane.getSelectionModel();
        String string = EPmTabPane.getSelectionModel().getSelectedItem().getText().substring(2);
        int i = Integer.parseInt(string);

        EPmTabPane.getTabs().remove(EPmTabPane.getSelectionModel().getSelectedItem());
        if(selectionModel.getSelectedItem() != EPmTabPane.getTabs().get(EPmTabPane.getTabs().size()-1))
            selectionModel.select(selectionModel.getSelectedIndex()+1);

        //TODO setSTINDEX
        stService.removeEPinST(stvo.id,i);

        refreshEPMBtnDelete();
    }

    private void refreshEPMBtnDelete(){
        if(EPmTabPane.getTabs().size() == 1)
            btnEPMDelete.setDisable(true);
        else if(EPmTabPane.getTabs().size() >= 2)
            btnEPMDelete.setDisable(false);
    }

    public void onEPMConfirmClick() {
//        epConfigComponentController.updateEPs();

        Stage stage = (Stage) btnEPMConfirm.getScene().getWindow();
        stage.close();
    }

}
