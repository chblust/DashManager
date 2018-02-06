/**
 * Handles user input from the DMGUI
 * @author Chris Blust
 */
public class DMController {
    private DMModel model;
    private SSHWrapper ssh;
    public DMController(){
        model = new DMModel();
    }

    public boolean upload(String user, String address, String privateKeyPath, boolean updateFrontend,
                                       boolean updateBackend){

        // TODO
        return true;

    }

    public boolean run(){
        // TODO
        return true;
    }

    public void editFrontendScript(){
        // TODO
    }

    public void editBackendScript(){
        // TODO
    }
}
