/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Justin
 */
public class IntegerException extends Exception{
        public IntegerException(){}
        public IntegerException(String msg){
            Alert errorMsg = new Alert(Alert.AlertType.ERROR);
                            errorMsg.setTitle("Error");
                            errorMsg.setContentText("Please enter a valid number, no Characters");
                            errorMsg.showAndWait();
        }
}
