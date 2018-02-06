import com.jcraft.jsch.UserInfo;

import javax.swing.*;

/**
 * Defines behavior of the SSH library
 */
public class DashUserInfo implements UserInfo{

    @Override
    public String getPassphrase(){
        return JOptionPane.showInputDialog(null,  "Enter Passphrase:");
    }

    @Override
    public void showMessage(String s) {
        JOptionPane.showMessageDialog(null,s);
    }

    @Override
    public boolean promptPassphrase(String s){
        return true;
    }

    @Override
    public boolean promptPassword(String s) {
        return true;
    }

    @Override
    public String getPassword() {
        return JOptionPane.showInputDialog(null, "Enter Password:");
    }

    @Override
    public boolean promptYesNo(String s) {
        Object[] options = {"yes","no"};
        return 0==JOptionPane.showConfirmDialog(null, options);
    }
}
