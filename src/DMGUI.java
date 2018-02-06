import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.util.Observable;
import java.util.Observer;

/**
 * Defines the GUI for DashManager, serves as the MVC view, observes the model
 */
public class DMGUI implements Observer{
    private BorderPane mainPane;
    private TextField userTextField;
    private TextField addressTextField;
    private CheckBox updateFrontend;
    private CheckBox updateBackend;
    private Button upload;
    private Button run;
    private Button editFrontendScript;
    private Button editBackendScript;

    public DMGUI(){
        mainPane = new BorderPane();

        userTextField = new TextField();
        userTextField.setPrefColumnCount(40);
        addressTextField = new TextField();
        addressTextField.setPrefColumnCount(40);

        updateFrontend = new CheckBox();
        updateBackend = new CheckBox();

        upload = new Button("Upload");
        upload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                uploadButtonPressed();
            }
        });

        run = new Button("Run");
        run.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                runButtonPressed();
            }
        });

        editFrontendScript = new Button("Edit Frontend Script");
        editFrontendScript.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                editFrontendScriptButtonPressed();
            }
        });

        editBackendScript = new Button("Edit Backend Script");
        editBackendScript.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                editBackendScriptButtonPressed();
            }
        });
    }

    private void uploadButtonPressed(){

    }

    private void runButtonPressed(){

    }

    private void editFrontendScriptButtonPressed(){

    }

    private void editBackendScriptButtonPressed(){

    }

    public void update(Observable observable, Object arg){

    }
}
