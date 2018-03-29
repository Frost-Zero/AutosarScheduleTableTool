package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by Frost-D on 18/3/26.
 */
public class VerificationViewController {

    private Stage GraphStage;
    private VerificationGraphController verificationGraphController;

    @FXML
    public void onReVerifyClick() {

    }

    @FXML
    public void onGraphClick() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/VerificationGraphView.fxml"));
            VBox vbox = loader.load();
//            verificationGraphController = loader.getController();
//            verificationGraphController.setVeirificationGraphView(this);
            GraphStage = new Stage();
            GraphStage.initModality(Modality.APPLICATION_MODAL);
            GraphStage.initStyle(StageStyle.UTILITY);
            GraphStage.setScene(new Scene(vbox));
            GraphStage.setOnCloseRequest((event) -> {
                GraphStage = null;
            });
            GraphStage.setWidth(1080);
            GraphStage.setHeight(800);
            GraphStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
