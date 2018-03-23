package view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import service.DataCalcService;
import service.STService;
import service.ServiceFactory;
import util.GanttChart;
import vo.CalcDataVO;
import vo.STVO;

import java.io.IOException;
import java.util.*;

/**
 * Created by Frost-D on 18/3/19.
 */
public class SimulationViewController {

    String[] cssSytles = new String[] {"status-green", "status-red", "status-blue", "status-purple"};
    int cssOrder = 0;
    private List<String> machiness = new ArrayList<>();
    private List<XYChart.Series> seriess = new ArrayList<>();
    final NumberAxis xAxis = new NumberAxis();
    final CategoryAxis yAxis = new CategoryAxis();
    private GanttChart<Number, String> chart = new GanttChart<Number, String>(xAxis,yAxis);
    private STService stService = ServiceFactory.STService();
    private DataCalcService dataCalcService = ServiceFactory.dataCalcService();

    private int lowerBound = 0;
    private int upperBound = 10;

    private List<STVO> STs = stService.findSTs();

    private List<CalcDataVO> calcDataVOs = new ArrayList<>();

    private List<CalcDataVO> calcDataVOsTemp = new ArrayList<>();

    private Timer timer;

    private Timeline timeline;

//    private boolean isRun = false;

    @FXML
    private Pane paneGanttChart;

    @FXML
    private VBox vboxStartTime;

    @FXML
    private Button btnSimulateStart;

    @FXML
    public void initialize() {
        vboxStartTime.getChildren().removeAll();
        cssOrder = 0;
        calcDataVOs.clear();
        machiness.clear();
        dataCalcService.init();
        dataFunctions();

        initGanttChart();
        chart.setPrefWidth(700);
        chart.setPrefHeight(600);

        paneGanttChart.getChildren().add(chart);

        for(int i = 0; i < STs.size(); i++) {
            addStartTimeComponent();
        }
    }

    @FXML
    public void onSimulateStartClick() {
        if (btnSimulateStart.getText().equals("Simulate") ) {
            btnSimulateStart.setText("STOP");
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (upperBound < calcDataVOs.size()) {
                        upperBound++;
                        lowerBound++;
                        xAxis.setUpperBound(upperBound);
                        xAxis.setLowerBound(lowerBound);
                    } else if (upperBound >= calcDataVOs.size()) {
                        timer.cancel();
                        timer.purge();
                        timer = null;
                    }
                }
            }, 1000, 200);
        } else if (btnSimulateStart.getText().equals("STOP")) {
            btnSimulateStart.setText("Simulate");
            stopTimerTask();
        }

    }

    public void stopTimerTask() {
        if(timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }

    public void addStartTimeComponent() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/SimulationStartTimeComponent.fxml"));
            Node node = loader.load();

            vboxStartTime.getChildren().add(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initGanttChart() {
        for(int i = 0; i < stService.findSTs().size(); i++) {
            String string = "ST " + (i+1);
            machiness.add(string);
        }

        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(1);
        xAxis.setAutoRanging(false);
        xAxis.setTickUnit(1);
        xAxis.setLowerBound(lowerBound);
        xAxis.setUpperBound(upperBound);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.<String>observableArrayList(machiness));

        chart.setTitle("Gantt Chart");
        chart.setLegendVisible(false);
        chart.setBlockHeight( 50);

        for (String string:machiness) {

            XYChart.Series series = new XYChart.Series();
            seriess.add(series);
            for (CalcDataVO calcdatavo:calcDataVOs) {
                if (calcdatavo.stId == Integer.parseInt(string.substring(3))) {
                    series.getData().add(new XYChart.Data(calcdatavo.exec, string, new GanttChart.ExtraData(1, cssSytles[cssOrder])));
                }
            }
            cssOrder++;
            chart.getData().add(series);
        }

        chart.getStylesheets().add(getClass().getResource("../css/ganttchart.css").toExternalForm());

    }

    private void dataFunctions() {
        STs = stService.findSTs();

        dataCalcService.dataCalc(STs);
        for(int i = 0; i < 200; i++) {
            dataCalcService.execution();
            calcDataVOs.add(dataCalcService.dataListVO());
            dataCalcService.afterExecute();
        }
    }
}
