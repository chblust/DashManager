import javax.swing.*;

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

    public boolean upload(String user, String address, String privateKeyPath, String command){

        return ssh.executeRemoteCommand(user, address, privateKeyPath, command);

    }

    public boolean run(){
        // TODO
        return true;
    }

    public void editFrontendScript(){
        String frontendScript = JOptionPane.showInputDialog(null, "Enter sh Script: ");
        model.setFrontendScript(frontendScript);
    }

    public void editBackendScript(){
        String backendScript = JOptionPane.showInputDialog(null, "Enter sh Script");
        model.setBackendScript(backendScript);
    }
}
