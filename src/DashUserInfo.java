import com.jcraft.jsch.UserInfo;

import javax.swing.*;

/**
 * Defines behavior of the SSH library
 */
public class DashUserInfo implements UserInfo{
    private DMModel model;

    private String password;
    private String passphrase;

    public DashUserInfo(DMModel model){
        super();
        this.model = model;
    }

    @Override
    public String getPassphrase(){
        return passphrase;
    }

    @Override
    public void showMessage(String s) {
        model.appendOutput(s);
    }

    @Override
    public boolean promptPassphrase(String s){
        DMController.showInputDialog("Passphrase", "Enter Passphrase: ", "").ifPresent(
                (str) -> passphrase = str
        );
        return true;
    }

    @Override
    public boolean promptPassword(String s) {
        DMController.showInputDialog("Passphrase", "Enter Passphrase: ", "").ifPresent(
                (str) -> password = str
        );
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean promptYesNo(String s) {
        return true;
    }
}
