package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import service.STService;
import service.ServiceFactory;
import vo.STVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/1/6.
 */

public class MainViewController {
    @FXML
    private TabPane stTabPane;

    private STService stService = ServiceFactory.STService();

    List<FXMLLoader> loaders = new ArrayList<>();


    @FXML
    public void initialize() {
        onMVClick();
    }

    @FXML
    public void onMVClick(){
        STVO stvo = new STVO();
        stService.createST(stvo);

        refreshSTTabs();
    }

    private void refreshSTTabs() {
        List<STVO> vos = stService.findSTs();

        for (int i = 0; i < vos.size(); i++) {
            STVO vo = vos.get(i);
            if (i >= stTabPane.getTabs().size()) {
                addTab();
            }
            Tab tab = stTabPane.getTabs().get(i); // i
            FXMLLoader loader = loaders.get(i); // i
            ScheduleTablePaneController controller = loader.getController();
            controller.setSTVO(vo);
            controller.setSTVOID(vo.id);

            tab.setText("ST" + vo.id);
        }

//        System.out.println("tab has been added!!");
    }

    private void addTab() {
        Tab tab = new Tab();

        BorderPane borderPane = new BorderPane();
        tab.setContent(borderPane);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ScheduleTablePane.fxml"));
            Pane pane = loader.load();
            loaders.add(loader);

            borderPane.setCenter(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }

        stTabPane.getTabs().add(tab);

    }

}