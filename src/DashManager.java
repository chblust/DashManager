import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * GUI Interface for deploying Dashboard software to F26 via SSH
 */
public class DashManager extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();
        DMModel model = new DMModel();
        DMController controller = new DMController(model);
        DMGUI gui = new DMGUI(root, controller);
        model.addObserver(gui);
        primaryStage.setTitle("DashManager");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
