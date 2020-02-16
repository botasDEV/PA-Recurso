/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adapter;

import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import digraph.Vertex;
import digraph.interfaces.Edge;
import java.util.List;
import models.Hyperlink;
import models.Page;
import models.Website;

/**
 *
 * @author Rafae
 */
public class GraphEdgleListAdapter extends GraphEdgeList<Page, Hyperlink>{
    private Website website;

    public GraphEdgleListAdapter(Website website) {
        this.website = website;
        init();
    }
    
    private void init() {
        List<Vertex<Page>> vertices = (List<Vertex<Page>>)website.vertices();
        super.insertVertex(((Vertex<Page>)vertices.get(0)).element());
    }

    public void setWebsite(Website website) {
        this.website = website;
    }
    
    public void insertVertices() {
        for(Vertex<Page> vertex : website.vertices()) {
            boolean exists = false;
            for (com.brunomnsilva.smartgraph.graph.Vertex<Page> showVertex : super.vertices()) {
                if (showVertex.element().equals(vertex.element())) {
                    exists = true;
                    break;
                }
            }
            
            if(!exists) super.insertVertex(vertex.element());
        }
    }
    
    public void insertEdges() {
        for(Edge<Hyperlink, Page> edge : website.edges()) {
            boolean exists = false;
            for (com.brunomnsilva.smartgraph.graph.Edge<Hyperlink, Page> showEdge : super.edges()) {
                if(showEdge.element().equals(edge.element())){
                    exists = true;
                    break;
                }
            }
            
            if(!exists) super.insertEdge(edge.vertices()[0].element(), edge.vertices()[1].element(), edge.element());
        }
        
    }
}
