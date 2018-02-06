import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
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
    private TextField privateKeyPathTextField;
    private CheckBox updateFrontend;
    private CheckBox updateBackend;
    private Button upload;
    private Button run;
    private Button editFrontendScript;
    private String frontendScript;
    private String backendScript;
    private Button editBackendScript;
    private TextArea output;

    private DMController controller;

    public DMGUI(){
        mainPane = new BorderPane();
        userTextField = new TextField();
        userTextField.setPrefColumnCount(40);
        privateKeyPathTextField = new TextField();
        privateKeyPathTextField.setPrefColumnCount(40);
        addressTextField = new TextField();
        addressTextField.setPrefColumnCount(40);

        updateFrontend = new CheckBox();
        updateBackend = new CheckBox();

        controller = new DMController();

        upload = new Button("Upload");
        upload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.upload(userTextField.getText(), addressTextField.getText(),
                        privateKeyPathTextField.getText(),
                        updateFrontend.isSelected(),
                        updateBackend.isSelected());
            }
        });

        run = new Button("Run");
        run.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.run();
            }
        });

        editFrontendScript = new Button("Edit Frontend Script");
        editFrontendScript.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.editFrontendScript();
            }
        });

        editBackendScript = new Button("Edit Backend Script");
        editBackendScript.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.editBackendScript();
            }
        });

        frontendScript = "";
        backendScript = "";
    }

    public void update(Observable observable, Object arg){

    }
}
