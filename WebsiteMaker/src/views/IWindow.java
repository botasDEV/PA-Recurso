/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.MainController;
import java.util.Observer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * MVC Pattern
 *  - View Interface for Window
 * @author Rafael Botas
 */
public interface IWindow extends Observer {
    /**
     * Returns the Scene for the Window
     * 
     * @return Scene
     */
    public Scene getScene();
    
    /**
     * Initializes a Pane to set the layout in it
     * 
     * @return Pane
     */
    public Pane initComponents();
    
    /**
     * Sets the event listeners actions
     * 
     * @param controller 
     */
    public void setTriggers(MainController controller);
    
}
