import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * Wraps the remote connection functionality (using JSch)
 * @author Chris Blust
 */
public class SSHWrapper {
    private static final int SSH_PORT = 22;

    private JSch jsch;
    private DMModel model;
    public SSHWrapper(DMModel model){
        jsch = new JSch();
        this.model = model;
    }

    public boolean executeRemoteCommand(String user, String address, String privateKeypath, String command){
        try {
            jsch.addIdentity(privateKeypath);

            Session session = jsch.getSession(user, address, SSH_PORT);
            UserInfo ui = new DashUserInfo();
            session.setUserInfo(ui);
            session.connect();

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.connect();

            return true;
        }catch(Exception e){
            System.err.println(e.getStackTrace());
            return false;
        }
    }
}
