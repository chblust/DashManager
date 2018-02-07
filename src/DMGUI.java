import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.*;
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
    private TextField backendFiles;
    private TextField frontendFiles;
    private Button upload;
    private Button run;
    private Button editBuildScript;
    private String buildScript;
    private String runScript;
    private Button editRunScript;
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
        backendFiles = new TextField();
        backendFiles.setPrefColumnCount(40);
        frontendFiles = new TextField();
        frontendFiles.setPrefColumnCount(40);

        controller = con;

        upload = new Button("Upload");
        upload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String command = "";
                /*
                if(updateFrontend.isSelected()){
                    command += buildScript;
                }
                if(updateBackend.isSelected()){
                    command += ";" + runScript;
                }
                */
                command = buildScript;
                if(!command.equals("")){
                    controller.upload(
                            userTextField.getText(),
                            addressTextField.getText(),
                            privateKeyPathTextField.getText(),
                            backendFiles.getText(),
                            frontendFiles.getText(),
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
                controller.run(
                        userTextField.getText(),
                        addressTextField.getText(),
                        privateKeyPathTextField.getText(),
                        runScript
                );
            }
        });

        editBuildScript = new Button("Edit Build Script");
        editBuildScript.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.editBuildScript();
            }
        });

        editRunScript = new Button("Edit Run Script");
        editRunScript.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.editRunScript();
            }
        });

        buildScript = "";
        runScript = "";

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

        HBox backendFilesBox = new HBox();
        backendFilesBox.getChildren().addAll(new Label("Backend Files"), backendFiles);

        HBox frontendFilesBox = new HBox();
        frontendFilesBox.getChildren().addAll(new Label("Frontend Files"), frontendFiles);

        HBox editButtons = new HBox();
        editButtons.getChildren().addAll(editBuildScript, editRunScript);

        HBox runButtons = new HBox();
        runButtons.getChildren().addAll(upload, run);

        VBox controls = new VBox();
        controls.getChildren().addAll(privKeyBox,
                userBox,
                addressBox,
                frontendFilesBox,
                backendFilesBox,
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
                output.setScrollTop(Double.MAX_VALUE);

                privateKeyPathTextField.setText(model.getIdentityPath());
                userTextField.setText(model.getUser());
                addressTextField.setText(model.getAddress());
                buildScript = model.getBuildScript();
                runScript = model.getRunScript();
                backendFiles.setText(model.getBackendFiles());
                frontendFiles.setText(model.getFrontendFiles());
            }
        });
    }


}
