/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui_user;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import static javafx.scene.paint.Color.TRANSPARENT;


/**
 *
 * @author Black_Shadow
 */
public class UI_user extends Application {
    
    private double xOffset=0;
    private double yOffset=0;
    public static String ui_userID = "ui_user";
    public static String ui_userFile = "ui_user.fxml";
    public static String ui_clientID = "ui_client";
    public static String ui_clientFile = "ui_client.fxml";
    @Override
    public void start(Stage stage) {
        
        
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(UI_user.ui_userID, UI_user.ui_userFile);
        mainContainer.loadScreen(UI_user.ui_clientID, UI_user.ui_clientFile);
        
        mainContainer.setScreen(UI_user.ui_userID);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset=event.getSceneX();
            yOffset=event.getSceneY();
        });
       scene.setFill(TRANSPARENT);
        stage.show();

         
         root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
       /* Scene scene1 = new Scene(root);
        scene1.setFill(TRANSPARENT);
        stage.setScene(scene1);*/
       // stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
