/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import factories.WebsiteMakerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Website;
import views.DrawWindow;
import views.IWindow;

/**
 *
 * @author Rafae
 */
public class MainController implements IController{
    private List<Website> modelList; 
    private IWindow view;
    private Path basePath; //TODO: Set this variable to be a Singleton

    public MainController(List<Website> modelList, IWindow view) {
        this.modelList = modelList;
        this.view = view;
        this.view.setTriggers(this);
        basePath = Paths.get("MyWebsites");
    }
    
    public void initialize() {
        System.out.println("###########################");
        System.out.println("###########################");
        System.out.println("Initializing");
        System.out.println("###########################");
        System.out.println("###########################");
        
        if(!Files.exists(basePath)) {
            try {
                Files.createDirectory(basePath);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    
    public void createWebsite() {
        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.setMaximized(true);
        stage.setTitle("Create Website");
        
        DrawWindow drawWindow = (DrawWindow)WebsiteMakerFactory.create("create");
        
        
        Scene scene = drawWindow.getScene();
        
        stage.setScene(scene);
        stage.show();
        
        drawWindow.getPanel().init();
    }    
    
}
