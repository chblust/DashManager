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
    private Session session;
    public SSHWrapper(DMModel model){
        jsch = new JSch();
        this.model = model;
    }

    public boolean executeRemoteCommand(String user, String address, String privateKeypath, String command){
        try {
            model.appendOutput("Executing Script...");
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            String line;
            while((line = br.readLine()) != null){
                model.appendOutput(line);
            }

            channel.disconnect();
            model.appendOutput("Upload Complete!");
            return true;
        }catch(ConnectException ce){
            model.appendOutput("Connection Refused!");
            return false;
        }catch(JSchException je){
            je.printStackTrace();
            model.appendOutput("Upload Cancelled.");
            return false;
        }catch(IOException ioe){
            ioe.printStackTrace();
            return false;
        }
    }

    public boolean transferFiles(String user, String address, String privateKeyPath, String frontendFiles, String backendFiles) {
        String[] ffiles = frontendFiles.split(" ");
        String[] bfiles = backendFiles.split(" ");

        try {
            ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();

            channel.cd("f26dash/frontend");
            for (String fileName : ffiles) {
                channel.put(fileName, fileName);
                System.out.println(fileName);
            }

            channel.cd("../backend/src");
            for(String fileName : bfiles){
                channel.put("../backend/backend/src/" + fileName,fileName);
                System.out.println(fileName);
            }
            channel.disconnect();

            model.appendOutput("Done with Transfer!");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean connect(String user, String ip, String key){
        try {
            jsch.addIdentity(key);
            session = jsch.getSession(user,ip,SSH_PORT);
            session.setUserInfo(new DashUserInfo(model));
            session.connect();
            System.out.println("Connected");
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
