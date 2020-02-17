/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memento;

import digraph.Vertex;
import digraph.interfaces.Edge;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Hyperlink;
import models.Page;

/**
 *
 * @author Rafae
 */
public class WebsiteMemento {
    private Map<Vertex<Page>, List<Edge<Hyperlink, Page>>> adjacenciesMap;

    public WebsiteMemento(Map<Vertex<Page>, List<Edge<Hyperlink, Page>>> adjacenciesMap) {
       this.adjacenciesMap = new HashMap<>(adjacenciesMap);
    }

    public Map<Vertex<Page>, List<Edge<Hyperlink, Page>>> getAdjacenciesMap() {
        return adjacenciesMap;
    }
}
