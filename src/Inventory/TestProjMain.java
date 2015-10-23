package Inventory;



import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 *
 * @author Justin
 */
public class TestProjMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        BuildUI.init(primaryStage);
       
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
   
    
}
