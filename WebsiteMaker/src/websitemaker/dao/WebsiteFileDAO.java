/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websitemaker.dao;

import controllers.MainController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Website;

/**
 *
 * @author Rafae
 */
public class WebsiteFileDAO implements IWebsiteDAO {
    private String basePath;
        
    public WebsiteFileDAO(String basePath) {
        this.basePath = basePath;
        this.initialize();
    }
    
    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
    
    private void initialize() {
        Path path = Paths.get(basePath);
        
        if(!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public List<Website> list() {
        List<Website> websitesList = new ArrayList<>();
        
        File folder = new File(basePath);
        if (folder.list().length > 0) {
            for(String name : folder.list()) {
                websitesList.add(new Website(name));
            }
        }
        
        return websitesList;
    }

    @Override
    public boolean save(Website website) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Website get(String websiteName) {
        File file = new File(basePath + "/" + websiteName);
        return (file.isDirectory() ? new Website(websiteName) : null);
    }

    @Override
    public boolean alreadyExists(String websiteName) {
        return (new File(basePath + "/" + websiteName)).isDirectory();
    }
    
}
