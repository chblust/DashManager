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

    public DMModel(){
        outputString = "";
        frontendScript = "";
        backendScript = "";
        user = "";
        address = "";
        updateFrontend = true;
        updateBackend = true;
        identityPath = "";
        this.setChanged();
        this.notifyObservers();
    }

    public String getOutputString(){return outputString;}
    public String getFrontendScript(){return frontendScript;}
    public String getBackendScript(){return backendScript;}
    public String getUser(){return user;}
    public String getAddress(){return address;}
    public boolean isUpdateFrontend(){return updateFrontend;}
    public boolean isUpdateBackend(){return updateBackend;}
    public String getIdentityPath(){return identityPath;}

    public void setFrontendScript(String s){
        frontendScript = s;
        this.setChanged();
        this.notifyObservers();
    }

    public void setBackendScript(String s){
        backendScript = s;
        this.setChanged();
        this.notifyObservers();
    }

    public void appendOutput(String s){
        this.outputString += s;
        this.setChanged();
        this.notifyObservers();
    }
}
