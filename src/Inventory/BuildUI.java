package Inventory;



import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;



/**
 *
 * @author Justin
 */
public class BuildUI {
    
        static public void init(Stage primaryStage){
            
            //Create Buttons
            Button btnLiquor = new Button("Liquor");
            Button btnBeer = new Button("Beer");
            Button btnExit = new Button("Exit");
            Button btnAdd = new Button("Add Item");
            Button btnRemove = new Button("Remove Item");
           
            //Button Size
            btnLiquor.setMinSize(100, 30);
            btnBeer.setMinSize(100, 30);
            btnExit.setMinSize(100, 30);
            btnAdd.setMinSize(100, 30);
            btnRemove.setMinSize(100, 30);
            

            //Create table
            CreateTable beerTable = new CreateTable("Beer");
            CreateTable liquorTable = new CreateTable("Liquor");
            
            //BorderPane set-up
            BorderPane border = new BorderPane();
            HBox headerHBox = addHbox();
            HBox bottomHBox = addHBox(btnAdd, btnRemove);
            VBox leftVBox = addVBox(btnLiquor, btnBeer, btnExit);
            border.setTop(headerHBox);
            border.setLeft(leftVBox);
           
            //Adjust size of program based on screen resolution
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth();
            double height = screenSize.getHeight();       
            Scene sceneMain = new Scene(border, width * 0.6, height * 0.6);
            sceneMain.getStylesheets().add("/Inventory/customCSS.css");
            
            //Create the view of the tables for liquor and beer
            VBox vboxBeer = new VBox();
            vboxBeer.getChildren().add(beerTable.mainTable);
            beerTable.setTableSize(border.getWidth());
            vboxBeer.setPadding(new Insets(10,10,0,10));
            VBox vboxLiquor = new VBox();
            vboxLiquor.getChildren().add(liquorTable.mainTable);
            liquorTable.setTableSize(border.getWidth());
            vboxLiquor.setPadding(new Insets(10,10,0,10));
            vboxBeer.getStyleClass().add("vboxTable");
            vboxLiquor.getStyleClass().add("vboxTable");
            
            
            
            //Set Stage and show
            primaryStage.setTitle("Beer and Liquor Inventory");
            primaryStage.setScene(sceneMain);
            primaryStage.show();
           
            //Liquor button action
            btnLiquor.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                   
                  vboxLiquor.getChildren().add(bottomHBox);
                   border.setCenter(vboxLiquor);
                   
                }
            });
            
            //Beer button action
            btnBeer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                   vboxBeer.getChildren().add(bottomHBox);
                   border.setCenter(vboxBeer);
                }
            });
            
            //Add button Action
            btnAdd.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //Get liquor or beer
                   if (border.getCenter() == vboxBeer){
                       beerTable.addProduct(primaryStage);
                   } else if (border.getCenter() == vboxLiquor){
                       liquorTable.addProduct(primaryStage);
                   } 
                }
            });
            
            //Remove button Action
            btnRemove.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //Get liquor or beer
                    if (border.getCenter() == vboxBeer){
                       beerTable.removeProduct();
                   } else if (border.getCenter() == vboxLiquor){
                       liquorTable.removeProduct();
                   }   
                }
            });
            
            //Exit button action
            btnExit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    beerTable.writeToFile();
                    liquorTable.writeToFile();
                    System.exit(0);
                }
            });
        }//End of init
        
        //Beginning of Methods
        //HBox header code
        static public HBox addHbox() {
            HBox hbox = new HBox(10);
            hbox.setPadding(new Insets(30));
            hbox.getStyleClass().add("hbox");
            Text txtHeader = new Text("Beer and Liquor Inventory Costs");
            txtHeader.getStyleClass().add("textHeader");
            hbox.getChildren().add(txtHeader);
            return hbox;
        }
        
        //VBox code
        static public VBox addVBox(Button btnLiquor, Button btnBeer, Button btnExit) {
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(30,20,30,20));
            vbox.setSpacing(10);
            vbox.getStyleClass().add("vbox");
            vbox.getChildren().addAll(btnLiquor, btnBeer, btnExit);
            return vbox;
        }
       
        //HBox add/remove button code
        static public HBox addHBox(Button btnAdd, Button btnRemove){
            HBox hbox = new HBox(10);
            hbox.setPadding(new Insets(30,20,30,20));
            hbox.getChildren().addAll(btnAdd, btnRemove);
            hbox.getStyleClass().add("hboxBottom");
            return hbox;
        }
}
