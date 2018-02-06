import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    public DMGUI(BorderPane main){
        mainPane = main;
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
                String command = "";
                if(updateFrontend.isSelected()){
                    command += frontendScript;
                }
                if(updateBackend.isSelected()){
                    command += backendScript;
                }

                if(!command.equals("")){
                    controller.upload(
                            userTextField.getText(),
                            addressTextField.getText(),
                            privateKeyPathTextField.getText(),
                            command);
                }
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

        output = new TextArea();
        output.setPrefSize(300,300);

        SetupGUI();
    }

    public void SetupGUI(){
        HBox privKeyBox = new HBox();
        privKeyBox.getChildren().addAll(new Label("Private Key Path"), privateKeyPathTextField);

        HBox userBox = new HBox();
        userBox.getChildren().addAll(new Label("User"), userTextField);

        HBox addressBox = new HBox();
        addressBox.getChildren().addAll(new Label("Address"), addressTextField);

        HBox frontendBox = new HBox();
        frontendBox.getChildren().addAll(new Label("Update Frontend"), updateFrontend);

        HBox backendBox = new HBox();
        backendBox.getChildren().addAll(new Label("Update Backend"), updateBackend);

        HBox editButtons = new HBox();
        editButtons.getChildren().addAll(editFrontendScript, editBackendScript);

        HBox runButtons = new HBox();
        runButtons.getChildren().addAll(upload, run);

        VBox controls = new VBox();
        controls.getChildren().addAll(privKeyBox,
                userBox,
                addressBox,
                frontendBox,
                backendBox,
                editButtons,
                runButtons);

        mainPane.setCenter(controls);
        mainPane.setBottom(output);
    }

    public void update(Observable observable, Object arg){

    }
}
