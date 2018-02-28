import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;


/**
 * GUI Interface for deploying Dashboard software to F26 via SSH
 */
public class DashManager extends Application {

    public static String OPTIONS_FILE = "deploy_options.xml"; // path of configuration xml

    /**
     * Read from config or start fresh to create model, create MVC relationship
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();

        DMModel model = createModel();

        DMController controller = new DMController(model);
        DMGUI gui = new DMGUI(root, controller);
        model.addObserver(gui);
        model.notifyObservers();
        primaryStage.setTitle("DashManager");
        primaryStage.setScene(new Scene(root, 800, 700));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                controller.disconnect();
            }
        });
        primaryStage.show();
    }

    public DMModel createModel(){
        File file = new File("deploy_options.xml");
        if(file.exists()){
            try {
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
                doc.getDocumentElement().normalize();
                Element root = doc.getDocumentElement();
                String privateKeyPath = root.getElementsByTagName("privateKeyPath").item(0).getTextContent();
                String user = root.getElementsByTagName("user").item(0).getTextContent();
                String address = root.getElementsByTagName("address").item(0).getTextContent();
                String backendPath = root.getElementsByTagName("backendFiles").item(0).getTextContent();
                String frontendFiles = root.getElementsByTagName("frontendFiles").item(0).getTextContent();
                String buildScript = root.getElementsByTagName("buildScript").item(0).getTextContent();
                String runScript = root.getElementsByTagName("runScript").item(0).getTextContent();
                boolean updateFrontend = root.getElementsByTagName("updateFrontend").item(0).getTextContent().equals("true");
                boolean updateBackend = root.getElementsByTagName("updateBackend").item(0).getTextContent().equals("true");
                return new DMModel(privateKeyPath,
                        user,
                        address,
                        buildScript,
                        runScript,
                        updateFrontend,
                        updateBackend,
                        backendPath,
                        frontendFiles);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return new DMModel();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
