import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Frost-D on 17/12/31.
 */
public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader rootLoader = new FXMLLoader();
        rootLoader.setLocation(getClass().getResource("/view/MainView.fxml"));
        Pane root = rootLoader.load();


//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("ASTT");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

//        MainUIController controller = rootLoader.getController();
//        controller.showUtilView();
    }
}
