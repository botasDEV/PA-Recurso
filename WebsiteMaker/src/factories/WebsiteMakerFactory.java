/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import controllers.IController;
import controllers.MainController;
import models.Website;
import models.Websites;
import views.DrawWindow;
import views.IWindow;
import views.MainWindow;

/**
 *
 * @author Rafae
 */
public class WebsiteMakerFactory {
    public static IWindow create(String viewName) {
        IWindow view;
        IController controller;
        
        switch(viewName){
            case "main":
                Websites modelList = new Websites();
                view = new MainWindow(modelList);
                controller = new MainController(modelList, view);
                ((MainController) controller).initialize();
                break;
            case "create":
                Website model = new Website();
                view = new DrawWindow(null); // TODO: Model as parameter
                break;
            default:
                throw new IllegalArgumentException(" this type View does not exist");
        }
        
        return view;
    }
}
