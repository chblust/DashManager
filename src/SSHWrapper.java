import com.jcraft.jsch.*;

import java.io.*;
import java.net.ConnectException;

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
            if(privateKeypath != "") {
                try {
                    jsch.addIdentity(privateKeypath);
                } catch (Exception e) {
                    model.appendOutput("Could not find key file!");
                }
            }

            model.appendOutput("Connecting to Session...");
            Session session = jsch.getSession(user, address, SSH_PORT);
            UserInfo ui = new DashUserInfo(model);
            session.setUserInfo(ui);
            session.connect();

            model.appendOutput("Executing Script...");
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            String line;
            while((line = br.readLine()) != null){
                model.appendOutput(line);
            }
            model.appendOutput("Upload Complete!");
            return true;
        }catch(ConnectException ce){
            model.appendOutput("Connection Refused!");
            return false;
        }catch(JSchException je){
            model.appendOutput("Upload Cancelled.");
            return false;
        }catch(IOException ioe){
            ioe.printStackTrace();
            return false;
        }
    }
}
