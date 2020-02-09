/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import factories.WebsiteMakerFactory;
import java.io.File;
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
import models.Websites;
import views.DrawWindow;
import views.IWindow;

/**
 *
 * @author Rafae
 */
public class MainController implements IController{
    private Websites model; 
    private IWindow view;
    private String basePath; //TODO: Set this variable to be a Singleton

    public MainController(Websites model, IWindow view) {
        this.model = model;
        this.view = view;
        this.view.setTriggers(this);
        basePath = "MyWebsites";
    }
    
    public void initialize() {
        
        Path path = Paths.get(basePath);
        
        if(!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        File folder = new File(basePath);
        if (folder.list().length > 0) {
            this.model.getWebsites().add(new Website(folder.getName()));
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
