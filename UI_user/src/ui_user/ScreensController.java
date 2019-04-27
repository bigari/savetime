/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui_user;

import client.*;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


public class ScreensController  extends StackPane {
    //Holds the screens to be displayed

    private HashMap<String, Node> screens = new HashMap<>();
    
    public ScreensController() {
        super();
    }

    //Add the screen to the collection
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    //Returns the Node with the appropriate name
    public Node getScreen(String name) {
        return screens.get(name);
    }

    //Loads the fxml file, add the screen to the screens collection and
    //finally injects the screenPane to the controller.
    public boolean loadScreen(String name, String resource) {
        try {
            
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            
            Parent loadScreen = (Parent) myLoader.load();
            
            ControlledScreen myScreenControler = ((ControlledScreen) myLoader.getController());
            
            myScreenControler.setScreenParent(this);
            
            addScreen(name, loadScreen);
            System.out.println("loaded");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    
    public boolean setScreen(final String name) {       
        if (screens.get(name) != null) {   //screen loaded
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {    //if there is more than one screen
                if (name==UI_user.ui_clientID)
                {
                    System.out.println("setClient: "+ClientTransfert.get());
                    AnchorPane win= (AnchorPane)screens.get(name);
                    AnchorPane com= (AnchorPane)win.getChildren().get(1);
                    Label sold= (Label)com.getChildren().get(5);
                    sold.setText(String.valueOf(ClientTransfert.get().getSolde()));
                }
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(50), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        getChildren().remove(0);                    //remove the displayed screen
                        getChildren().add(0, screens.get(name));     //add the screen
                        Timeline fadeIn = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                new KeyFrame(new Duration(40), new KeyValue(opacity, 1.0)));
                        fadeIn.play();
                    }
                }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));       //no one else been displayed, then just show
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(10), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("screen hasn't been loaded!!! \n");
            return false;
        }


    }

    //This method will remove the screen with the given name from the collection of screen
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }
}