package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import service.STService;
import service.ServiceFactory;
import service.VeriEPService;
import vo.STVO;
import vo.VeriEPVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/3/26.
 */
public class VerificationGraphController {

    private STService stService = ServiceFactory.STService();

    private VeriEPService veriEPService = ServiceFactory.veriEPService();

    private List<STVO> STs;

    private List<VeriEPVO> veriEPVOs = new ArrayList<>();

    private int left = 0;

    private int right = 0;

    private int series = 0;



    private ChangeListener leftTaskChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            stService.updateStartingTaskById(left,comboStartingTaskLeft.getSelectionModel().getSelectedIndex());
        }
    };

    private ChangeListener leftEPChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            String string = comboStartingEPLeft.getSelectionModel().getSelectedItem().toString().substring(2);
            int i = Integer.parseInt(string);
            int startingEP = comboStartingEPLeft.getSelectionModel().getSelectedIndex();
            stService.updateStartingEPByIndex(left, startingEP);
            stService.updateStartingEPIdById(left, i);
        }
    };

    private ChangeListener rightTaskChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            stService.updateStartingTaskById(right, comboStartingTaskRight.getSelectionModel().getSelectedIndex());
        }
    };

    private ChangeListener rightEPChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            String string = comboStartingEPRight.getSelectionModel().getSelectedItem().toString().substring(2);
            int i = Integer.parseInt(string);
            int startingEP = comboStartingEPRight.getSelectionModel().getSelectedIndex();
            stService.updateStartingEPByIndex(right, startingEP);
            stService.updateStartingEPIdById(right, i);
        }
    };

    @FXML
    private Label labelSTLeft;

    @FXML
    private Label labelSTRight;

    @FXML
    private Label labelStartingEPRight;

    @FXML
    private Label labelStartingTaskRight;

    @FXML
    private ComboBox comboStartingEPLeft;

    @FXML
    private ComboBox comboStartingEPRight;

    @FXML
    private ComboBox comboStartingTaskLeft;

    @FXML
    private ComboBox comboStartingTaskRight;

    @FXML
    private HBox hBoxVeriEPComponent;

    @FXML
    private ScrollPane sPaneVeri;

    @FXML
    public void initialize() {
        veriEPService.clearVeriEPPOs();
        series = 0;
        initScroll();
        clearComboBox();
        stService.initStartingEPId();
        STs = stService.findSTs();
        veriEPVOs.clear();
        comboStartingEPRight.getSelectionModel().selectFirst();
        comboStartingTaskLeft.getSelectionModel().selectFirst();
        comboStartingEPLeft.getSelectionModel().selectFirst();
        comboStartingTaskRight.getSelectionModel().selectFirst();
        left = 0;
        if(STs.size() <= 1) {
            labelStartingEPRight.setText("");
            labelStartingTaskRight.setText("");
            labelSTRight.setText("");
            comboStartingEPRight.setVisible(false);
            comboStartingTaskRight.setVisible(false);
            right = -1;
        } else {
            comboStartingEPRight.setVisible(true);
            comboStartingTaskLeft.setVisible(true);
            right = 1;
        }
        refresh();
    }

    @FXML
    public void onLeftClick() {
        if(left >= 1) {
            left--;
            if (right > 0) {
                right--;
            }
        }
        removeListener();
        clearComboBox();
        refresh();
    }

    @FXML
    public void onRightClick() {
        if(left < STs.size()-2) {
            left++;
            if(right > 0) {
                right++;
            }
        }
        removeListener();
        clearComboBox();
        refresh();
    }

    public void addListener() {
        comboStartingEPLeft.getSelectionModel().selectedItemProperty().addListener(leftEPChangeListener);
        comboStartingEPRight.getSelectionModel().selectedItemProperty().addListener(rightEPChangeListener);
        comboStartingTaskLeft.getSelectionModel().selectedItemProperty().addListener(leftTaskChangeListener);
        comboStartingTaskRight.getSelectionModel().selectedItemProperty().addListener(rightTaskChangeListener);
    }

    public void removeListener() {
        comboStartingTaskLeft.getSelectionModel().selectedItemProperty().removeListener(leftTaskChangeListener);
        comboStartingTaskRight.getSelectionModel().selectedItemProperty().removeListener(rightTaskChangeListener);
        comboStartingEPLeft.getSelectionModel().selectedItemProperty().removeListener(leftEPChangeListener);
        comboStartingEPRight.getSelectionModel().selectedItemProperty().removeListener(rightEPChangeListener);
    }

    @FXML
    public void onVerifyClick() {
        STs = stService.findSTs();
        veriEPService.dataCalc(STs);

        VeriEPVO veriEPVO;
        veriEPVO = veriEPService.init();
        veriEPVOs.add(veriEPVO);
        for(int i = 0; i < 200; i++) {
            veriEPVO = veriEPService.execute();
            veriEPVOs.add(veriEPVO);
        }

        //TODO put the data in graph
        if (series == 0) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/VerificationEPComponent.fxml"));
                Node node = loader.load();
                hBoxVeriEPComponent.getChildren().add(node);
                VerificationEPComponentController verificationEPComponentController = loader.getController();
                verificationEPComponentController.setVerificationGraphController(veriEPVOs.get(series));
            } catch (IOException e) {
                e.printStackTrace();
            }
            series++;
        } else if (series >= 1) {
            try {
                FXMLLoader eploader = new FXMLLoader();
                FXMLLoader lineloader = new FXMLLoader();
                eploader.setLocation(getClass().getResource("/view/VerificationEPComponent.fxml"));
                lineloader.setLocation(getClass().getResource("/view/VerificationEPLineComponent.fxml"));
                Node epnode = eploader.load();
                Node eplinenode = lineloader.load();
                hBoxVeriEPComponent.getChildren().add(eplinenode);
                hBoxVeriEPComponent.getChildren().add(epnode);
                VerificationEPComponentController verificationEPComponentController = eploader.getController();
                verificationEPComponentController.setVerificationGraphController(veriEPVOs.get(series));
                VerificationEPLineController verificationEPLineController = lineloader.getController();
                verificationEPLineController.setVerificationGraphController(veriEPVOs.get(series));

            } catch (IOException e) {
                e.printStackTrace();
            }
            series++;
        }

        //test
//        if(hBoxVeriEPComponent.getChildren().size() == 0) {
//            try {
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(getClass().getResource("/view/VerificationEPComponent.fxml"));
//                Node node = loader.load();
//                hBoxVeriEPComponent.getChildren().add(node);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            try {
//                FXMLLoader eploader = new FXMLLoader();
//                FXMLLoader lineloader = new FXMLLoader();
//                eploader.setLocation(getClass().getResource("/view/VerificationEPComponent.fxml"));
//                lineloader.setLocation(getClass().getResource("/view/VerificationEPLineComponent.fxml"));
//                Node epnode = eploader.load();
//                Node eplinenode = lineloader.load();
//
//                hBoxVeriEPComponent.getChildren().add(eplinenode);
//                hBoxVeriEPComponent.getChildren().add(epnode);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void clearComboBox() {
        comboStartingEPLeft.getItems().clear();
        comboStartingEPRight.getItems().clear();
        comboStartingTaskLeft.getItems().clear();
        comboStartingTaskRight.getItems().clear();
    }

    //refresh the label and comboBox
    public void refresh() {
        STs = stService.findSTs();
        //refresh label
        labelSTLeft.setText("ST"+STs.get(left).id);
        if(right > 0) {
            labelSTRight.setText("ST"+STs.get(right).id);
        }
        //refresh EP comboBox
        for(int i = 0; i < STs.get(left).EPs.size(); i++) {
            comboStartingEPLeft.getItems().add("EP" + STs.get(left).EPs.get(i).id);
        }
        if(right > 0) {
            for(int i = 0; i < STs.get(right).EPs.size(); i++) {
                comboStartingEPRight.getItems().add("EP" + STs.get(right).EPs.get(i).id);
            }
        }
        comboStartingEPLeft.getSelectionModel().select(STs.get(left).startingEP);
        if(right > 0) {
            comboStartingEPRight.getSelectionModel().select(STs.get(right).startingEP);
        }
        //refresh Task comboBox
        for(int i = 0; i < STs.get(left).EPs.size(); i++) {
            for(int j = 0; j < STs.get(left).EPs.get(i).TaskIds.size(); j++) {
                comboStartingTaskLeft.getItems().add("Task" + STs.get(left).EPs.get(i).TaskIds.get(j) + "(EP" + STs.get(left).EPs.get(i).id + ")");
            }
        }
        if(right > 0) {
            for(int i = 0; i < STs.get(right).EPs.size(); i++) {
                for(int j = 0; j < STs.get(right).EPs.get(i).TaskIds.size(); j++) {
                    comboStartingTaskRight.getItems().add("Task" + STs.get(right).EPs.get(i).TaskIds.get(j) + "(EP" + STs.get(right).EPs.get(i).id + ")");
                }
            }
        }
        comboStartingTaskLeft.getSelectionModel().select(STs.get(left).startingTask);
        if(right > 0) {
            comboStartingTaskRight.getSelectionModel().select(STs.get(right).startingTask);
        }

        addListener();
    }

    //keep scroll to the end
    public void initScroll() {
        DoubleProperty wProperty = new SimpleDoubleProperty();
        wProperty.bind(hBoxVeriEPComponent.widthProperty()); // bind to Hbox width chnages
        wProperty.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                //when ever Hbox width chnages set ScrollPane Hvalue
                sPaneVeri.setHvalue(sPaneVeri.getHmax());
            }
        });
    }
}
