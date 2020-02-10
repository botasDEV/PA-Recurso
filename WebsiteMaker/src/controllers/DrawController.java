/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

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
    
    
}
