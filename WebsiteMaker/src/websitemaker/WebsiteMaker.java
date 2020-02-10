/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websitemaker;

import factories.WebsiteMakerFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.IWindow;

/**
 *
 * @author Rafae
 */
public class WebsiteMaker extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Website Maker");
        primaryStage.setResizable(false);
                
        IWindow view = WebsiteMakerFactory.create(null, "main");
        Scene scene = view.getScene();
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
