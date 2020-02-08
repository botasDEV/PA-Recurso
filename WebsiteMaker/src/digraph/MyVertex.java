/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digraph;

import models.Page;

/**
 *
 * @author Rafael Botas
 */
public class MyVertex implements Vertex<Page>{

    private Page elem;

    public MyVertex(Page elem) {
       this.elem = elem; 
    }
    
    @Override
    public Page element() {
        return this.elem;
    }

    public void setElem(Page elem) {
        this.elem = elem;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Folder: ");
        sb.append(elem.getFolder());
        sb.append("\nFilename: ");
        sb.append(elem.getFilename());
        sb.append("\nTitle: ");
        sb.append(elem.getTitle());
        sb.append("\nContent: ");
        sb.append(elem.getContent());
        
        return sb.toString();
    }
    
    
    
}
