/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import controllers.IController;
import controllers.MainController;
import java.util.ArrayList;
import java.util.List;
import models.Website;
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
                view = new MainWindow();
                List<Website> modelList = new ArrayList<>();
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
