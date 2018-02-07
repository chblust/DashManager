import javafx.scene.control.TextInputDialog;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * Handles user input from the DMGUI
 * @author Chris Blust
 */
public class DMController {
    private DMModel model;
    private SSHWrapper ssh;
    public DMController(DMModel model){
        this.model = model;
        ssh = new SSHWrapper(model);
    }

    public static Optional<String> showInputDialog(String title, String prompt, String defaultText){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText("");
        dialog.setGraphic(null);
        dialog.getEditor().setText(defaultText);
        dialog.setContentText(prompt);
        return dialog.showAndWait();

    }

    public boolean upload(String user, String address, String privateKeyPath, String backendPath, String frontendFiles,
                          String command){

        return ssh.transferFiles(user, address, privateKeyPath, frontendFiles)
         && ssh.executeRemoteCommand(user, address, privateKeyPath, command);
    }

    public boolean run(String user, String address, String privateKeyPath, String command){
        return ssh.executeRemoteCommand(user, address, privateKeyPath, command);
    }

    public void editBuildScript(){
        showInputDialog("Frontend Script", "Enter sh Script: ", model.getBuildScript()).ifPresent(
                (str) -> model.setBuildScript(str));
    }

    public void editRunScript(){
        showInputDialog("Backend Script", "Enter sh Script: ", model.getRunScript()).ifPresent(
                (str) -> model.setRunScript(str));
    }
}
