import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

/**
 * Defines the GUI for DashManager, serves as the MVC view, observes the model
 */
public class DMGUI implements Observer{
    private BorderPane mainPane;
    private TextField userTextField;
    private TextField addressTextField;
    private TextField privateKeyPathTextField;
    private TextField backendPath;
    private TextField frontendFiles;
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

    public DMGUI(BorderPane main, DMController con){
        mainPane = main;
        userTextField = new TextField();
        userTextField.setPrefColumnCount(40);
        privateKeyPathTextField = new TextField();
        privateKeyPathTextField.setPrefColumnCount(40);
        addressTextField = new TextField();
        addressTextField.setPrefColumnCount(40);
        backendPath = new TextField();
        backendPath.setPrefColumnCount(40);
        frontendFiles = new TextField();
        frontendFiles.setPrefColumnCount(40);

        updateFrontend = new CheckBox();
        updateBackend = new CheckBox();

        controller = con;

        upload = new Button("Upload");
        upload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String command = "";
                if(updateFrontend.isSelected()){
                    command += frontendScript;
                }
                if(updateBackend.isSelected()){
                    command += ";" + backendScript;
                }

                if(!command.equals("")){
                    controller.upload(
                            userTextField.getText(),
                            addressTextField.getText(),
                            privateKeyPathTextField.getText(),
                            command);
                }else{
                    JOptionPane.showMessageDialog(null, "Must Specify Scripts!");
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
        output.setEditable(false);
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

        HBox backendPathBox = new HBox();
        backendPathBox.getChildren().addAll(new Label("Backend Directory"), backendPath);

        HBox frontendFilesBox = new HBox();
        frontendFilesBox.getChildren().addAll(new Label("Frontend Files"), frontendFiles);

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
                frontendFilesBox,
                backendPathBox,
                frontendBox,
                backendBox,
                editButtons,
                runButtons);

        mainPane.setCenter(controls);
        mainPane.setBottom(output);
    }

    public void update(Observable observable, Object arg) {
        DMModel model = (DMModel) observable;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                output.setText(model.getOutputString());
                output.selectPositionCaret(output.getLength());
                output.deselect();

                privateKeyPathTextField.setText(model.getIdentityPath());
                userTextField.setText(model.getUser());
                addressTextField.setText(model.getAddress());
                updateFrontend.setSelected(model.isUpdateFrontend());
                updateBackend.setSelected(model.isUpdateBackend());
                frontendScript = model.getFrontendScript();
                backendScript = model.getBackendScript();
                backendPath.setText(model.getBackendPath());
                frontendFiles.setText(model.getFrontendFiles());
            }
        });
    }


}
