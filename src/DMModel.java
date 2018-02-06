import java.util.ArrayList;
import java.util.Observable;

/**
 * MVC model for DMGUI, determines initial values and passes new output to frontend, observable to frontend
 */
public class DMModel extends Observable{
    private String outputString;
    private String frontendScript;
    private String backendScript;
    private String user;
    private String address;
    private boolean updateFrontend;
    private boolean updateBackend;
    private String identityPath;
    private String backendPath;
    private String frontendFiles;

    public DMModel(){
        outputString = "";
        frontendScript = "";
        backendScript = "";
        user = "";
        address = "";
        updateFrontend = true;
        updateBackend = true;
        identityPath = "";
        backendPath = "";
        frontendFiles = "";
        this.setChanged();
    }

    public DMModel(String pkp, String u, String a, String fs, String bs, boolean ufs, boolean ubs, String bp, String ff){
        identityPath = pkp;
        user = u;
        address = a;
        frontendScript = fs;
        backendScript = bs;
        updateFrontend = ufs;
        updateBackend = ubs;
        backendPath = bp;
        frontendFiles = ff;
        this.setChanged();
    }

    public String getOutputString(){return outputString;}
    public String getFrontendScript(){return frontendScript;}
    public String getBackendScript(){return backendScript;}
    public String getUser(){return user;}
    public String getAddress(){return address;}
    public boolean isUpdateFrontend(){return updateFrontend;}
    public boolean isUpdateBackend(){return updateBackend;}
    public String getIdentityPath(){return identityPath;}
    public String getBackendPath(){return backendPath;}
    public String getFrontendFiles(){return frontendFiles;}

    public void setFrontendScript(String s){
        frontendScript = s;
        this.setChanged();
    }

    public void setBackendScript(String s){
        backendScript = s;
        this.setChanged();
    }

    public void setUser(String u){
        user = u;
        this.setChanged();
    }

    public void setAddress(String a){
        address = a;
        this.setChanged();
    }

    public void setUpdateFrontend(boolean uf){
        updateFrontend = uf;
        this.setChanged();
    }

    public void setUpdateBackend(boolean ub){
        updateBackend = ub;
        this.setChanged();
    }

    public void setIdentityPath(String ip){
        identityPath = ip;
        this.setChanged();
    }

    public void setBackendPath(String bp){
        backendPath = bp;
        this.setChanged();
    }

    public void setFrontendFiles(String ff){
        frontendFiles = ff;
        this.setChanged();
    }

    public void appendOutput(String s){
        this.outputString += s + "\n";
        this.setChanged();
        this.notifyObservers();
    }

}
