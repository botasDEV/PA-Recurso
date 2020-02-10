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
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Page;
import models.Website;
import models.Websites;
import views.DrawWindow;
import views.IWindow;
import websitemaker.dao.WebsiteFileDAO;

/**
 *
 * @author Rafae
 */
public class MainController implements IController{
    private Websites model; 
    private IWindow view;
    private WebsiteFileDAO dao;
            
    public MainController(Websites model, IWindow view, WebsiteFileDAO dao) {
        this.view = view;
        this.dao = dao;
        this.model = new Websites();
        this.model.addObserver(view);     
        this.view.setTriggers(this);
        initialize();
    }
    
    private void initialize() {
        List<Website> websitesList = dao.list();
        model.addWebsites(websitesList);
    }
        
    public void createWebsite() {
        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.setMaximized(true);
        stage.setTitle("Create Website");
        
        
        Optional<String> result = null;
        Website newWebsite = null;
        do {
            newWebsite = null;
            TextInputDialog titleDialog = new TextInputDialog();
            titleDialog.setTitle("Website Title");
            titleDialog.setHeaderText("Insert a title for your  website:");
            titleDialog.setHeaderText("Title");
            
            result = titleDialog.showAndWait();
            
            if (result.isPresent()) {
                String projectName = result.get();
                newWebsite = new Website(projectName);
            }
        } while (newWebsite != null && dao.alreadyExists(newWebsite.getName()));
        
        if (newWebsite != null) {
            createDummyWebsite(newWebsite);
            System.out.println(newWebsite);

            DrawWindow drawWindow = (DrawWindow)WebsiteMakerFactory.create(newWebsite, "create");
            Scene scene = drawWindow.getScene();
            stage.setScene(scene);
            stage.show();

            drawWindow.getPanel().init();
        }
    }    
    
    
    private void createDummyWebsite(Website website) {
        String pageName = "Index";
        String filename = "index.html";
        Page newPage = new Page(pageName, "index.html");
        newPage.setContent("<html>"
                + "<head><title>"
                + pageName
                + "</title></head>"
                + "<body></body>"                        
                + "</html>");
        newPage.setFolder(dao.getBasePath() + website.getName() + "/" + filename);
        website.insertVertex(newPage);
    }
}
