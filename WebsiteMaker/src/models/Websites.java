/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Rafae
 */
public class Websites extends Observable {
    List<Website> websites;
    
    public Websites() {
        websites = new ArrayList<>();
    }
    
    public List<Website> getWebsites() {
        return websites;
    }

}
