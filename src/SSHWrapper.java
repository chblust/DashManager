import com.jcraft.jsch.JSch;

/**
 * Wraps the remote connection functionality (using JSch)
 * @author Chris Blust
 */
public class SSHWrapper {
    private JSch jsch;
    private DMModel model;
    public SSHWrapper(DMModel model){
        jsch = new JSch();
        this.model = model;
    }

    public void executeRemoteCommand(String user, String address, String privateKeypath, String command){

    }
}
