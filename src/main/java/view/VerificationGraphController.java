package view;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import service.AutomationService;
import service.STService;
import service.ServiceFactory;
import service.VeriEPService;
import util.GraphViz;
import vo.EPVO;
import vo.STVO;
import vo.VeriEPVO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Frost-D on 18/3/26.
 */
public class VerificationGraphController {

    private STService stService = ServiceFactory.STService();

    private VeriEPService veriEPService = ServiceFactory.veriEPService();

    private AutomationService automationService = ServiceFactory.automationService();

    private List<STVO> STs;

    private List<VeriEPVO> veriEPVOs = new ArrayList<>();

    private int left = 0;

    private int right = 0;

    private int series = 0;

    private long superPeriod = 20;

    private Timer timer;

    private ChangeListener scrollChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            sPaneVeri.setHvalue(sPaneVeri.getHmax());
        }
    };

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

            removeListener();
            clearComboBox();
            refresh();
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

            removeListener();
            clearComboBox();
            refresh();
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
    private Button btnVerify;

    @FXML
    private Button btnVeriGenerate;

    @FXML
    private ImageView imageLeft;

    @FXML
    private ImageView imageRight;

    @FXML
    public void initialize() {
        createEPsInSTGraph();
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
        initScroll();
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
        initScroll();
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
    public void onGenerateClick() {
        veriEPService.clearVeriEPPOs();
        hBoxVeriEPComponent.getChildren().clear();
        series = 0;
        veriEPVOs.clear();
        STs = stService.findSTs();
        veriEPService.dataCalc(STs);

        VeriEPVO veriEPVO;
        veriEPVO = veriEPService.init();
        veriEPVOs.add(veriEPVO);
        for(int i = 0; i < 200; i++) {
            veriEPVO = veriEPService.execute();
            veriEPVOs.add(veriEPVO);
        }
        superPeriod = 10;

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
    }

    @FXML
    public void onVerifyClick() {

        //TODO timer
        if (btnVerify.getText().equals("Verify") ) {
            btnVeriGenerate.setDisable(true);
            btnVerify.setText("STOP");
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        addVerificationComponent();
                    });
                }
            }, 1000, 2000);

        } else if (btnVerify.getText().equals("STOP")) {
            btnVeriGenerate.setDisable(false);
            btnVerify.setText("Verify");
            pauseTimerTask();
        }
    }

    private void addVerificationComponent() {
        if (series < superPeriod) {
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
        } else if (series >= superPeriod) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
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
        String leftEP = comboStartingEPLeft.getSelectionModel().getSelectedItem().toString().substring(2);
        int leftEPId = Integer.parseInt(leftEP);
        EPVO epvoLeft = stService.findEPinSTById(STs.get(left).id, leftEPId);
        for(int i = 0; i<epvoLeft.TaskIds.size(); i++) {
            comboStartingTaskLeft.getItems().add("Task" + epvoLeft.TaskIds.get(i) + "(EP" + epvoLeft.id + ")");
        }
        if(right > 0) {
            String rightEP = comboStartingEPRight.getSelectionModel().getSelectedItem().toString().substring(2);
            int rightEPId = Integer.parseInt(rightEP);
            EPVO epvoRight = stService.findEPinSTById(STs.get(right).id, rightEPId);
            for(int i = 0; i<epvoRight.TaskIds.size(); i++) {
                comboStartingTaskRight.getItems().add("Task" + epvoRight.TaskIds.get(i) + "(EP" + epvoRight.id + ")");
            }
        }
        comboStartingTaskLeft.getSelectionModel().select(STs.get(left).startingTask);
        if(right > 0) {
            comboStartingTaskRight.getSelectionModel().select(STs.get(right).startingTask);
        }
        //refresh imageView
        File leftFile = new File("ST"+STs.get(left).id+".png");
        Image leftImage = new Image(leftFile.toURI().toString());
        imageLeft.setImage(leftImage);
        if(leftImage.getHeight()<imageLeft.getFitHeight() && leftImage.getWidth()<imageLeft.getFitWidth()) {
            imageLeft.setFitHeight(leftImage.getHeight());
            imageLeft.setFitWidth(leftImage.getWidth());
        } else {
            imageLeft.setFitWidth(490);
            imageLeft.setFitHeight(200);
        }

        if(right > 0) {
            File rightFile = new File("ST"+STs.get(right).id+".png");
            Image rightImage = new Image(rightFile.toURI().toString());
            imageRight.setImage(rightImage);
            if(rightImage.getHeight()<imageRight.getFitHeight() && rightImage.getWidth()<imageRight.getFitWidth()) {
                imageRight.setFitHeight(rightImage.getHeight());
                imageRight.setFitWidth(rightImage.getWidth());
            } else {
                imageRight.setFitWidth(490);
                imageRight.setFitHeight(200);
            }
        }

        addListener();
    }

    //keep scroll to the end
    public void initScroll() {
        DoubleProperty wProperty = new SimpleDoubleProperty();
        wProperty.bind(hBoxVeriEPComponent.widthProperty()); // bind to Hbox width changes
        wProperty.addListener(scrollChangeListener);
    }

    public void pauseTimerTask() {
        if(timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    //create automation graph
    public static void createDotGraph(String dotFormat,String fileName)
    {
        GraphViz gv=new GraphViz();
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        // String type = "gif";
        String type = "png";
        // gv.increaseDpi();
        gv.decreaseDpi();
        gv.decreaseDpi();
        File out = new File(fileName+"."+ type);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
    }

    public void createEPsInSTGraph() {
        STs = stService.findSTs();
        automationService.dataCollect(STs);
        automationService.dataCalc(STs);


        String dotFormat = "EP1->EP2[label=\"1\"];EP2->EP3[label=\"3\"];EP3->EP1[label=\"5\"]";
        createDotGraph(dotFormat,"ST1");

        dotFormat = "EP4->EP5[label=\"3\"];EP5->EP6[label=\"5\"];EP6->EP7[label=\"7\"];EP7->EP4[label=\"9\"]";
        createDotGraph(dotFormat,"ST2");

        dotFormat = "EP8->EP8[label=\"10\"]";
        createDotGraph(dotFormat,"ST3");

        //TODO createDotFormatByRealData

    }
}
