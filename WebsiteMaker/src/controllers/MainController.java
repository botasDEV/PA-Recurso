/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import factories.WebsiteMakerFactory;
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
    private Website model; 
    private IWindow view;

    public MainController(Website model, IWindow view) {
        this.model = model;
        this.view = view;
        this.view.setTriggers(this);
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
