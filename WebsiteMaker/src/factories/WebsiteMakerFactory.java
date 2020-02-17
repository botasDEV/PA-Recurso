/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import controllers.DrawController;
import controllers.IController;
import controllers.MainController;
import models.Statistics;
import models.Website;
import models.Websites;
import views.DrawWindow;
import views.IWindow;
import views.MainWindow;
import websitemaker.dao.WebsiteFileDAO;

/**
 *
 * @author Rafae
 */
public class WebsiteMakerFactory {
    
    public static IWindow create(Object model, String viewName) {
        IWindow view;
        IController controller;
        WebsiteFileDAO dao = new WebsiteFileDAO("MyWebsites");
        
        switch(viewName){
            case "main":
                Websites modelList = new Websites();
                view = new MainWindow(modelList);
                controller = new MainController(modelList, view, dao);
                break;
            case "create":
                if (!(model instanceof GraphEdgeList) && !(model instanceof Website)) throw new IllegalArgumentException("Something went wrong."); 
                view = new DrawWindow((Website)model);
                controller = new DrawController((Website)model, new Statistics(), view, dao);
                break;
            default:
                throw new IllegalArgumentException(" this type View does not exist");
        }
        
        return view;
    }
}
