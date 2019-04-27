/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Black_Shadow
 */
public class ui_refusController {
    
      @FXML
    private TextArea input ;
      
      
      @FXML
    private JFXButton btn_ok ;

    public JFXButton getBtn_ok() {
        return btn_ok;
    }
     public TextArea getInput() {
        return input;
    } 
      
    /*
    private final ReadOnlyObjectWrapper<String> currentInput = new ReadOnlyObjectWrapper<>();
    
    public ReadOnlyObjectProperty<String> currentInputProperty() {
    return currentInput.getReadOnlyProperty() ;
    }
    
    public String getCurrentCustomer() {
    return currentInput.get();
    }
    
    
    public void handleOK(){
    currentInput.set(input.getText());
    System.out.println("retour");
    
    }*/
    public void initialize() {
        // set up double click on table:


    } 
    
}
