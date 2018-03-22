package view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import po.STPO;
import service.DataCalcService;
import service.STService;
import service.ServiceFactory;
import service.TaskService;
import util.GanttChart;
import vo.CalcDataVO;
import vo.STVO;
import vo.TaskVO;

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

    private List<STVO> STs = stService.findSTs();

    private List<CalcDataVO> calcDataVOs = new ArrayList<>();

    private List<CalcDataVO> calcDataVOsTemp = new ArrayList<>();

    private Timer timer = new Timer();

    private int temp = 0;

    @FXML
    private Pane paneGanttChart;


    @FXML
    public void initialize() {
        cssOrder = 0;
        calcDataVOs.clear();
        machiness.clear();
        dataCalcService.init();
        dataFunctions();

        initGanttChart();
        chart.setPrefWidth(700);
        chart.setPrefHeight(600);

        paneGanttChart.getChildren().add(chart);
    }


    public void initGanttChart() {
        for(int i = 0; i < stService.findSTs().size(); i++) {
            String string = "ST " + (i+1);
            machiness.add(string);
        }

        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(1);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);
//        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(machines)));
        yAxis.setCategories(FXCollections.<String>observableArrayList(machiness));

        chart.setTitle("Gantt Chart");
        chart.setLegendVisible(false);
        chart.setBlockHeight( 50);

        //TODO readData once per 1000ms
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                //get calcDataVOsTemp
//                calcDataVOsTemp.clear();
//                if (temp + 6 < calcDataVOs.size()) {
//                    for (int i = temp; i < temp + 6; i++) {
//                        calcDataVOsTemp.add(calcDataVOs.get(i));
//                    }
//                    temp++;
//                } else {
//                    for (int i = (calcDataVOs.size() - 6); i<calcDataVOs.size(); i++) {
//                        calcDataVOsTemp.add(calcDataVOs.get(i));
//                    }
//                }
//
//                //init the chart
//                for (XYChart.Series series:seriess) {
//                    series.getData().clear();
//                }
//                seriess.clear();
//                chart.getData().clear();
//                cssOrder = 0;
//
//                //read data to the chart
//                for (String string:machiness) {
//                    XYChart.Series series = new XYChart.Series();
//                    seriess.add(series);
//                    for (CalcDataVO calcdatavo:calcDataVOsTemp) {
//                        if (calcdatavo.stId == Integer.parseInt(string.substring(3))) {
//                            series.getData().add(new XYChart.Data(calcdatavo.exec, string, new GanttChart.ExtraData(1, cssSytles[cssOrder])));
//                        }
//                    }
//                    cssOrder++;
//                    chart.getData().add(series);
//                }
//                chart.getStylesheets().add(getClass().getResource("../css/ganttchart.css").toExternalForm());
//            }
//        }, 0, 1000);

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
