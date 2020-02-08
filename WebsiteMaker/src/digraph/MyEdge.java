/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digraph;

import digraph.interfaces.Edge;
import models.Hyperlink;
import models.Page;

/**
 * 
 * @author Rafae
 */
public class MyEdge implements Edge<Hyperlink, Page> {
    private Hyperlink elem;
    private Vertex<Page> vertexA, vertexB;

    public MyEdge(Hyperlink elem, Vertex<Page> vertexA, Vertex<Page> vertexB) {
        this.elem = elem;
        this.vertexA = vertexA;
        this.vertexB = vertexB;
    }
    
    @Override
    public Hyperlink element() {
        return elem;
    }

    @Override
    public Vertex<Page>[] vertices() {
        return new Vertex[]{
            vertexA,
            vertexB
        };
    }

    public void setElem(Hyperlink elem) {
        this.elem = elem;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Text: ");
        sb.append(elem.getText());        
        
        return sb.toString();    
    }
    
    
}
