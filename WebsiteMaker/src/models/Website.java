/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import digraph.InvalidEdgeException;
import digraph.InvalidVertexException;
import digraph.MyEdge;
import digraph.MyVertex;
import digraph.Vertex;
import digraph.interfaces.Edge;
import digraph.interfaces.Graph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 *
 * @author Rafae
 */
public class Website extends Observable implements Graph<Page, Hyperlink>{
    private String name;
    private Map<Vertex<Page>, List<Edge<Hyperlink, Page>>> adjacenciesMap; // Works as Vertex

    public Website() {
        adjacenciesMap = new HashMap<>();
    }
    
    public Website(String name) {
        this();
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public int numVertices() {
        return vertices().size();
    }

    @Override
    public int numEdges() {
        return edges().size();
    }

    @Override
    public Collection<Vertex<Page>> vertices() {
        List<Vertex<Page>> pages = new ArrayList<>();
        
        for (Vertex vertex : adjacenciesMap.keySet()) {
            pages.add((Vertex<Page>)vertex);
        }
        
        return pages;
    }

    @Override
    public Collection<Edge<Hyperlink, Page>> edges() {
        List<Edge<Hyperlink, Page>> hyperlinks = new ArrayList<>();
        for(List<Edge<Hyperlink, Page>> edges : adjacenciesMap.values()) {
            if (edges != null) {
                for(Edge<Hyperlink, Page> edge : edges) {
                    if (!hyperlinks.contains(edge)) {
                        hyperlinks.add((Edge<Hyperlink, Page>) edge);   
                    }
                }
            }
        }
        
        return hyperlinks;
    }

    @Override
    public Collection<Edge<Hyperlink, Page>> incidentEdges(Vertex<Page> v) throws InvalidVertexException {
        
        if (!adjacenciesMap.containsKey(v)) throw new InvalidVertexException();
        
        List<Edge<Hyperlink, Page>> incidentEdges = new ArrayList<>();
        
        adjacenciesMap.get(v).forEach((edge) -> {
            if(edge.vertices()[1].element() == v.element()){
                incidentEdges.add(edge);
            }
        });
        
        return incidentEdges;
    }

    @Override
    public Vertex<Page> opposite(Vertex<Page> v, Edge<Hyperlink, Page> e) throws InvalidVertexException, InvalidEdgeException {
        if (!adjacenciesMap.containsKey(v)) throw new InvalidVertexException();
        
        List<Edge<Hyperlink, Page>> edges = adjacenciesMap.get(v);
        if(!edges.contains(e)) throw new InvalidEdgeException();
        
        Edge<Hyperlink, Page> edge = edges.get(edges.indexOf(e));
        
        return (edge.vertices()[0] == v ? edge.vertices()[1] : edge.vertices()[0]);
    }

    @Override
    public boolean areAdjacent(Vertex<Page> u, Vertex<Page> v) throws InvalidVertexException {
        if (!adjacenciesMap.containsKey(v) || !adjacenciesMap.containsKey(u)) throw new InvalidVertexException();
        
        List<Edge<Hyperlink, Page>> uEdges = adjacenciesMap.get(u);
        List<Edge<Hyperlink, Page>> vEdges = adjacenciesMap.get(v);
        
        for(Edge uEdge : uEdges) {
            for(Edge vEdge : vEdges) {
                if(uEdge.equals(vEdge)) return true;
            }
        }
        return false;
    }

    @Override
    public Vertex<Page> insertVertex(Page vElement) throws InvalidVertexException {
        for (Vertex<Page> vertex : adjacenciesMap.keySet()){
            if(vertex.element().equals(vElement)) throw new InvalidVertexException();
        }
        
        MyVertex newVertex = new MyVertex(vElement);
        adjacenciesMap.put(newVertex, new ArrayList<>());
        
        setChanged();
        notifyObservers(this);
        
        return newVertex;
    }

    @Override
    public Edge<Hyperlink, Page> insertEdge(Vertex<Page> u, Vertex<Page> v, Hyperlink edgeElement) throws InvalidVertexException, InvalidEdgeException {
        checkVertex(v);
        checkVertex(u);
        List<Edge<Hyperlink, Page>> edges  = adjacenciesMap.get(v);
        for (Edge<Hyperlink, Page> edge : edges) {
            if(edge.vertices()[0].equals(v) && edge.vertices()[1].equals(u)
            || edge.vertices()[0].equals(u) && edge.vertices()[1].equals(v)) {
                throw new InvalidEdgeException();
            }
        }
        MyEdge newEdge = new MyEdge(edgeElement, v, u);
        adjacenciesMap.get(v).add(newEdge);
        adjacenciesMap.get(u).add(newEdge);
        
        setChanged();
        notifyObservers(this);
        return newEdge;
    }

    @Override
    public Edge<Hyperlink, Page> insertEdge(Page vElement1, Page vElement2, Hyperlink edgeElement) throws InvalidVertexException, InvalidEdgeException {
        Vertex<Page> vertex1 = null;
        Vertex<Page> vertex2 = null;
        for(Vertex<Page> page : adjacenciesMap.keySet()) {
            if(page.element().equals(vElement1)) vertex1 = page;
            if(page.element().equals(vElement2)) vertex2 = page;
        }
        
        if (vertex1 == null || vertex2 == null) throw new InvalidVertexException();
        MyEdge newEdge = new MyEdge(edgeElement, vertex1, vertex2);
        List<Edge<Hyperlink, Page>> adjacentEdges = adjacenciesMap.get(vertex1);
        adjacentEdges.add(newEdge);
        adjacentEdges = adjacenciesMap.get(vertex2);
        adjacentEdges.add(newEdge);
        
        return newEdge;
    }

    @Override
    public Page removeVertex(Vertex<Page> v) throws InvalidVertexException {
        MyVertex vertex = checkVertex(v);
        if(adjacenciesMap.get(vertex).size() > 0) throw new InvalidVertexException();
        adjacenciesMap.remove(vertex);
        return vertex.element();
    }

    @Override
    public Hyperlink removeEdge(Edge<Hyperlink, Page> e) throws InvalidEdgeException {
        checkEdge(e);
        
        adjacenciesMap.values().forEach(edges -> {
            if(edges.contains(e)) {
                edges.remove(e);
            }
        });
        
        return e.element();
    }

    @Override
    public Page replace(Vertex<Page> v, Page newElement) throws InvalidVertexException {
        MyVertex vertex = checkVertex(v);
        Page page = vertex.element();

        vertex.setElem(newElement);

        return page;
    }

    @Override
    public Hyperlink replace(Edge<Hyperlink, Page> e, Hyperlink newElement) throws InvalidEdgeException {
        MyEdge edge = checkEdge(e);
        Hyperlink hyperlink = (Hyperlink) edge.element();
        edge.setElem(newElement);
        
        return hyperlink;
    }
    
    // CHECKS
    private MyEdge checkEdge(Edge<Hyperlink, Page> edge) throws InvalidEdgeException {
        if (edge == null) throw new InvalidEdgeException();
        for(List<Edge<Hyperlink, Page>> edges : adjacenciesMap.values()) {
            if(!edges.contains(edge)) throw new InvalidEdgeException();
        }

        try {
            return (MyEdge) edge;
        } catch (Exception ex) {
            throw new InvalidEdgeException();
        }
    }
    
    private MyVertex checkVertex(Vertex<Page> vertex) throws InvalidVertexException {
        if (vertex == null) throw new InvalidVertexException();
        if (!adjacenciesMap.containsKey(vertex)) throw new InvalidVertexException();
        
        try {
            return (MyVertex) vertex;
        } catch (Exception ex) {
            throw new InvalidVertexException();
        }
    }
    
    public GraphEdgeList toGraphEdgeList(){
        GraphEdgeList<Page, Hyperlink> graphEdgeList =  new GraphEdgeList();
        
        for(Vertex<Page> vertex : this.vertices()) {
            graphEdgeList.insertVertex(vertex.element());
        }
        
        for (Edge<Hyperlink, Page> edge : this.edges()) {
            Vertex<Page> vertex1 = edge.vertices()[0];
            Vertex<Page> vertex2 = edge.vertices()[1];
            graphEdgeList.insertEdge(vertex1.element(), vertex2.element(), edge.element());
        }
        
        
        return graphEdgeList;
    }
    
    
}
