import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

/**
 * MVC model for DMGUI, determines initial values and passes new output to frontend, observable to frontend
 */
public class DMModel extends Observable{
    private String outputString;
    private String buildScript;
    private String runScript;
    private String user;
    private String address;
    private boolean updateFrontend;
    private boolean updateBackend;
    private String identityPath;
    private String backendFiles;
    private String frontendFiles;

    public DMModel(){
        outputString = "";
        buildScript = "";
        runScript = "";
        user = "";
        address = "";
        updateFrontend = true;
        updateBackend = true;
        identityPath = "";
        backendFiles = "";
        frontendFiles = "";
        this.setChanged();
    }

    public DMModel(String pkp, String u, String a, String fs, String bs, boolean ufs, boolean ubs, String bp, String ff){
        identityPath = pkp;
        user = u;
        address = a;
        buildScript = fs;
        runScript = bs;
        updateFrontend = ufs;
        updateBackend = ubs;
        backendFiles = bp;
        frontendFiles = ff;
        this.setChanged();
    }

    public String getOutputString(){return outputString;}
    public String getBuildScript(){return buildScript;}
    public String getRunScript(){return runScript;}
    public String getUser(){return user;}
    public String getAddress(){return address;}
    public boolean isUpdateFrontend(){return updateFrontend;}
    public boolean isUpdateBackend(){return updateBackend;}
    public String getIdentityPath(){return identityPath;}
    public String getBackendFiles(){return backendFiles;}
    public String getFrontendFiles(){return frontendFiles;}

    public void setBuildScript(String s){
        buildScript = s;
        this.setChanged();
    }

    public void setRunScript(String s){
        runScript = s;
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

    public void setBackendFiles(String bp){
        backendFiles = bp;
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

    @Override
    public void setChanged(){
        try {
            File file = new File(DashManager.OPTIONS_FILE);
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            root.getElementsByTagName("privateKeyPath").item(0).setTextContent(identityPath);
            root.getElementsByTagName("user").item(0).setTextContent(user);
            root.getElementsByTagName("address").item(0).setTextContent(address);
            root.getElementsByTagName("backendFiles").item(0).setTextContent(backendFiles);
            root.getElementsByTagName("frontendFiles").item(0).setTextContent(frontendFiles);
            root.getElementsByTagName("buildScript").item(0).setTextContent(buildScript);
            root.getElementsByTagName("runScript").item(0).setTextContent(runScript);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        }catch(IOException ioException){
            System.err.println("Couldn't find the file you specified");
        }catch(Exception e){
            System.err.println("help.");
        }
        super.setChanged();
    }

}
