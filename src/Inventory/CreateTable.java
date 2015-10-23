package Inventory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author Justin
 */
public class CreateTable {
    private String productType;
    private ArrayList<Product> product = new ArrayList<Product>();
    public TableView<Product> mainTable = new TableView<Product>();
    public ObservableList<Product> products;
    private IntegerProperty index = new SimpleIntegerProperty();
    private final TableColumn<Product, String> alcoholCol = new TableColumn("Item Name");
    private final TableColumn<Product, Number> onHandCol = new TableColumn("On Hand");
    private final TableColumn<Product, Number> soldAmtCol = new TableColumn("Amount Sold"); 
    private final TableColumn<Product, Number> endingAmtCol = new TableColumn("Ending Amount");
    private final TableColumn<Product, Number> costCol = new TableColumn("At Cost");
    private final TableColumn<Product, Number> pricingCol = new TableColumn("Pricing");
    private final TableColumn<Product, Number> percentCostCol = new TableColumn("% of Cost");
    
    //Constructor
    public CreateTable(String productType){
        this.setProductType(productType);
        this.mainTable.setEditable(true);
        this.mainTable.setTableMenuButtonVisible(true);
        this.populateTable(this.productType);
        this.loadProduct();
        this.editCells();
        this.mainTable.getColumns().addAll(alcoholCol, onHandCol, soldAmtCol, 
               endingAmtCol, costCol, pricingCol, percentCostCol);
       
        
    }
    
    //Set and get methods for product type
    private void setProductType(String productType) {
        this.productType = productType;
    }
    public String getProductType(){
        return this.productType;
    }
    
    //Set and get methods for index for remove function
    public final double getIndex() {
        return index.get();
    }
    public final void setIndex(Integer value) {
        index.set(value);
    }
    
    //Function to set table size
    public void setTableSize(Double width){
        this.alcoholCol.setPrefWidth(width*.122);
        this.onHandCol.setPrefWidth(width*.122);
        this.soldAmtCol.setPrefWidth(width*.122);
        this.endingAmtCol.setPrefWidth(width*.122);
        this.costCol.setPrefWidth(width*.122);
        this.pricingCol.setPrefWidth(width*.122);
        this.percentCostCol.setPrefWidth(width*.122);
    }
    
    //Function to Populate the table
    private void populateTable(String productType){
        //NumberFormat nf = new DecimalFormat("#0.00");
        
        this.products = FXCollections.observableList(this.product);
            this.alcoholCol.setCellValueFactory(new PropertyValueFactory<>("itemType"));
            this.alcoholCol.setCellFactory(TextFieldTableCell.<Product>forTableColumn());
            
            this.onHandCol.setCellValueFactory(new PropertyValueFactory<Product, Number>("onHand"));
            this.onHandCol.setCellFactory(TextFieldTableCell.<Product, Number>forTableColumn(new NumberStringConverter()));
            
            this.soldAmtCol.setCellValueFactory(new PropertyValueFactory<Product, Number>("amtSold"));
            this.soldAmtCol.setCellFactory(TextFieldTableCell.<Product, Number>forTableColumn(new NumberStringConverter()));
            
            this.endingAmtCol.setCellValueFactory(new PropertyValueFactory<Product, Number>("endAmt"));
            this.endingAmtCol.setCellFactory(TextFieldTableCell.<Product, Number>forTableColumn(new NumberStringConverter()));
            
            this.costCol.setCellValueFactory(new PropertyValueFactory<Product, Number>("atCost"));
            this.costCol.setCellFactory(TextFieldTableCell.<Product, Number>forTableColumn(new NumberStringConverter()));
            
            this.pricingCol.setCellValueFactory(new PropertyValueFactory<Product, Number>("pricing"));
            this.pricingCol.setCellFactory(TextFieldTableCell.<Product, Number>forTableColumn(new NumberStringConverter()));
            
            this.percentCostCol.setCellValueFactory(new PropertyValueFactory<Product, Number>("percentCost"));
        this.mainTable.setItems(this.products);   
    }
    
    //Function to load the file into an array list
    private void loadProduct(){
        File productData = new File(this.productType.toLowerCase() + ".txt");
        Scanner productScanner = null;
        
        //Trys to open the file
        try {
            productScanner = new Scanner(productData);
            while(productScanner.hasNextLine()){
                String fileLine = productScanner.nextLine();
                String[] parts = fileLine.split(",");
                
                //Loads data from the file and puts it into the array list.
                product.add(new Product(parts[0],Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),Double.parseDouble(parts[4]),Double.parseDouble(parts[5])));
            }  
        } catch (FileNotFoundException e){
            System.out.println("Error: " + this.productType + ".txt file not found");   
        }
    }

    //Function to remove a product.
    public void removeProduct() {
       this.products.remove(index.get());
       this.mainTable.getSelectionModel().clearSelection();
    }
    
    //Function to add a product. Creates a new scene in a new window.
    public void addProduct(Stage primaryStage) {
               
        //Create Buttons and Text Fields
        Button confirm = new Button("Select");
        Button cancel = new Button("Cancel");
        TextField nameTxt = new TextField();
        TextField onHandTxt = new TextField();
        TextField amtSoldTxt = new TextField();
        TextField endAmtTxt = new TextField();
        TextField atCostTxt = new TextField();
        TextField pricingTxt = new TextField();
        
        //Create stage for box to display
        final Stage addProductStage = new Stage();
        addProductStage.setTitle("Add " + this.getProductType());
        addProductStage.initModality(Modality.NONE);
        addProductStage.initOwner(primaryStage);
        BorderPane productAddBorder = new BorderPane();
        HBox hboxProAdd = productAddHbox(confirm, cancel);
        GridPane gridProAdd = productAddGrid(nameTxt, onHandTxt, amtSoldTxt,endAmtTxt, atCostTxt, pricingTxt);    
        productAddBorder.setCenter(gridProAdd);
        productAddBorder.setBottom(hboxProAdd);
        Scene addProduct = new Scene(productAddBorder, 300, 250);
        addProductStage.setScene(addProduct);
        addProductStage.show();
        
        //Cancel actions
        cancel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    addProductStage.close();
                }
            });
        
        //Confirm actions
        confirm.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    processAdditions(nameTxt.getText(), onHandTxt.getText(), amtSoldTxt.getText(),
                                    endAmtTxt.getText(), atCostTxt.getText(), pricingTxt.getText());
                    addProductStage.close();
                }
            });
    }
    
    //Function to create the box in which the buttons are located
    private HBox productAddHbox(Button confirm, Button cancel){
        HBox hboxProAdd = new HBox(10);
            hboxProAdd.setPadding(new Insets(0,0,10,0));
            hboxProAdd.setStyle("-fx-alignment: top-center;");
            hboxProAdd.getChildren().addAll(confirm, cancel);
        return hboxProAdd;
    }
    
    //Function to create the pop up box to add a product
    private GridPane productAddGrid(TextField nameTxt, TextField onHandTxt, TextField amtSoldTxt,
                                    TextField endAmtTxt, TextField atCostTxt, TextField pricingTxt){
      
        //Set up a grid for all the fields
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(5, 5, 5, 5));
        
        //Set text fields
        Text name = new Text("Name :");
        grid.add(name,0,0);
        Text onHand = new Text("On Hands: ");
        grid.add(onHand,0,1);
        Text amtSold = new Text("Amount Sold: ");
        grid.add(amtSold,0,2);
        Text endAmt = new Text("End Amount: ");
        grid.add(endAmt,0,3);
        Text atCost = new Text("At-Cost: ");
        grid.add(atCost,0,4);
        Text pricing = new Text("Price: ");
        grid.add(pricing,0,5);
        
        //Set text fields
        grid.add(nameTxt, 1, 0);
        grid.add(onHandTxt, 1, 1);
        grid.add(amtSoldTxt, 1, 2);
        grid.add(endAmtTxt, 1, 3);
        grid.add(atCostTxt, 1, 4);
        grid.add(pricingTxt, 1, 5);
        return grid;
    }
    
   //Function to process the addition of a new item
   private void processAdditions(String name, String onHand, String amtSold,
                                 String endAmt, String atCost, String pricing ){
       this.product.add(new Product(name, Integer.parseInt(onHand), Integer.parseInt(amtSold), Integer.parseInt(endAmt),
                                    Double.parseDouble(atCost), Double.parseDouble(pricing)));
       this.populateTable(this.productType);
   }
   
   //Process the addition of updated cells
   private void processUpdates(){
       this.populateTable(this.productType);
   }
   
   //Function to write data to the file
   public void writeToFile(){
       PrintWriter writer = null;
        try{
            writer = new PrintWriter(this.getProductType() + ".txt", "UTF-8");
            for (Product p : product){
                writer.write(p.toString() + "\n");
            }
        } catch (IOException e){
            System.err.println("Error Writing File: ");
            e.printStackTrace();
        }
       writer.close();
   }
   
   //Function to edit Cells dynamically
   private void editCells(){
       //Change value for alcohol column
       this.alcoholCol.setOnEditCommit(
               new EventHandler<CellEditEvent<Product, String>>(){
                   @Override
                   public void handle(CellEditEvent<Product, String> cell){
                       ((Product) cell.getTableView().getItems().get(
                            cell.getTablePosition().getRow())).setItemType(cell.getNewValue());
                        
                   }
               }
       );
       
       //Change value for on hands
       this.onHandCol.setOnEditCommit(
               new EventHandler<CellEditEvent<Product, Number>>(){
                   @Override
                   public void handle(CellEditEvent<Product, Number> cell){
                       ((Product) cell.getTableView().getItems().get(
                            cell.getTablePosition().getRow())).setOnHand(cell.getNewValue());
                       processUpdates();
                   }
               }
       );
       
       //Change value for sold amount column
       this.soldAmtCol.setOnEditCommit(
               new EventHandler<CellEditEvent<Product, Number>>(){
                   @Override
                   public void handle(CellEditEvent<Product, Number> cell){
                       ((Product) cell.getTableView().getItems().get(
                            cell.getTablePosition().getRow())).setOnHand(cell.getNewValue()); 
                   }
               }
       );
       
       //Change value for the ending amount of items
       this.endingAmtCol.setOnEditCommit(
               new EventHandler<CellEditEvent<Product, Number>>(){
                   @Override
                   public void handle(CellEditEvent<Product, Number> cell){
                       ((Product) cell.getTableView().getItems().get(
                            cell.getTablePosition().getRow())).setOnHand(cell.getNewValue());
                   }
               }
       );
       
       //Change value for the cost of each item
       this.costCol.setOnEditCommit(
               new EventHandler<CellEditEvent<Product, Number>>(){
                   @Override
                   public void handle(CellEditEvent<Product, Number> cell){
                       ((Product) cell.getTableView().getItems().get(
                            cell.getTablePosition().getRow())).setOnHand(cell.getNewValue());
                   }
               }
       );
       
       //Change value for the pricing Column
       this.pricingCol.setOnEditCommit(
                
               new EventHandler<CellEditEvent<Product, Number>>(){
                   
                   @Override
                   public void handle(CellEditEvent<Product, Number> cell){
                        ((Product) cell.getTableView().getItems().get(  
                            cell.getTablePosition().getRow())).setOnHand(cell.getNewValue());
                    }
               }
       );
       
       //Code for delete item
        this.mainTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.mainTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                setIndex(products.indexOf(newValue));
            }
        });
   }
   
 
   
}
