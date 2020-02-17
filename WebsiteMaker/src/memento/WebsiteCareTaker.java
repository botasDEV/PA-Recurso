/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memento;

import java.util.Observable;
import java.util.Stack;
import models.Website;

/**
 *
 * @author Rafae
 */
public class WebsiteCareTaker extends Observable {
    private Stack<WebsiteMemento> mementos;

    public WebsiteCareTaker() {
        mementos = new Stack<>();
    }
    
    public void save(Website website) {
        WebsiteMemento memento = website.createMemento();
        this.mementos.add(memento);
        
        setChanged();
        notifyObservers(mementos);
    }
    
    public void restore(Website website) {
        if(mementos.isEmpty()) return;
        WebsiteMemento memento = mementos.pop();
        website.setMemento(memento);
        
        setChanged();
        notifyObservers(mementos);
    }
}
