/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui_user;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import buvette.*;
import client.*;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.util.ResourceBundle;

/**
 *
 * @author Black_Shadow
 */
public class ui_userController implements Initializable, ControlledScreen {
    ScreensController myController;
    private ClientManager clmanager;
    
     @FXML
     private Label lbl_attention;
     @FXML
     private ImageView img_attention;
     @FXML
     private JFXButton btn_valider;
     @FXML
     private JFXTextField email;
     @FXML
     private JFXPasswordField pwd;
     
    
    
    @FXML
    private void handleClose(MouseEvent event) {
        System.exit(0);
    }
    @Override
    public void setScreenParent(ScreensController screenParent){
        myController=screenParent;
    }
    @FXML
     private void handleValider(MouseEvent event) throws IOException{
         if(email.getText()!=null && pwd.getText()!=null){
             Client c = new Client(email.getText(), pwd.getText());
             if (c.login()){
                ClientTransfert.set(c);
                ClientTransfert.get().getInfo();
                System.out.println(ClientTransfert.get());
                myController.setScreen(UI_user.ui_clientID);
                
                img_attention.setVisible(false);
                lbl_attention.setVisible(false);
                email.setText("");
                pwd.setText("");
                
             }else{
                // myController.setScreen(UI_user.ui_clientID); // A supprimer
                img_attention.setVisible(true);
                lbl_attention.setVisible(true);
                
             }
         }
     }
     

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    
}
