/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Page;
import models.Website;
import views.IWindow;
import websitemaker.dao.WebsiteFileDAO;

/**
 *
 * @author Rafae
 */
public class DrawController implements IController{
    private Website model; 
    private IWindow view;
    private WebsiteFileDAO dao;

    public DrawController(Website model, IWindow view, WebsiteFileDAO dao) {
        this.model = model;
        this.view = view;
        this.view.setTriggers(this);
        this.dao = dao;
        this.model.addObserver(view);

    }
    
    public boolean createPage(TextField txtFilename, TextField txtFolder, TextArea txtContent) {
        String filename = txtFilename.getText().contains(".html") ? txtFilename.getText() : txtFilename.getText().concat(".html");
        String title = txtFilename.getText();
        String folder = txtFolder.getText();
        String content = txtContent.getText();
        
        try {
            Page newPage = new Page(title, filename, folder, content);
            model.insertVertex(newPage);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
    
    
    public boolean createHyperlink(String filenameA, String filenameB) {
                
        try {
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
