/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adapter;

import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
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
public class GraphEdgleListAdapter extends GraphEdgeList<String, String>{
    private Website website;
    private final String rootStyling = "-fx-fill: gold; -fx-stroke: brown;";
    private final String nodeStyling = "-fx-fill: orange;";
    

    public GraphEdgleListAdapter(Website website) {
        this.website = website;
        init();
    }
    
    private void init() {
        List<Vertex<Page>> vertices = (List<Vertex<Page>>)website.vertices();
        super.insertVertex(((Vertex<Page>)vertices.get(0)).element().getFilename());
    }

    public void setWebsite(Website website) {
        this.website = website;
    }
    
    public com.brunomnsilva.smartgraph.graph.Vertex<String> insertVertex() {
        for(Vertex<Page> vertex : website.vertices()) {
            boolean exists = false;
            for (com.brunomnsilva.smartgraph.graph.Vertex<String> showVertex : super.vertices()) {
                if (showVertex.element().equals(vertex.element().getFilename())) {
                    exists = true;
                    break;
                }
            }
            
            if(!exists) return super.insertVertex(vertex.element().getFilename());
        }
        
        return null;
    }
    
    public void insertEdges() {
        for(Edge<Hyperlink, Page> edge : website.edges()) {
            boolean exists = false;
            for (com.brunomnsilva.smartgraph.graph.Edge<String, String> showEdge : super.edges()) {
                if(showEdge.element().equals(edge.element().getText())){
                    exists = true;
                    break;
                }
            }
            
            if(!exists) super.insertEdge(edge.vertices()[0].element().getFilename(), edge.vertices()[1].element().getFilename(), edge.element().getText());
        }
        
    }
}
