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
import java.util.List;

/**
 * Created by Frost-D on 18/1/6.
 */

public class MainViewController {
    @FXML
    private TabPane stTabPane;

    private STService stService = ServiceFactory.STService();

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
            Tab tab = stTabPane.getTabs().get(i);
            tab.setText("ST" + vo.id);
        }

//        System.out.println("tab has been added!!");
    }

    private void addTab() {
        Tab tab = new Tab();
        BorderPane borderPane = new BorderPane();
        tab.setContent(borderPane);

        stTabPane.getTabs().add(tab);
        addSTPaneInTab(tab);

    }

    private void addSTPaneInTab(Tab tab) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ScheduleTablePane.fxml"));
            Pane pane = loader.load();

            BorderPane borderPane = (BorderPane) tab.getContent();
            borderPane.setCenter(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}