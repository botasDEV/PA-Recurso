/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import digraph.Vertex;
import digraph.interfaces.Edge;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import memento.WebsiteCareTaker;
import models.Hyperlink;
import models.Page;
import models.Statistics;
import models.Website;
import views.IWindow;
import websitemaker.dao.WebsiteFileDAO;

/**
 * Controller Implementation for the DrawWindow View
 * 
 * @author Rafael Botas
 */
public class DrawController implements IController{
    private Website modelWebsite;
    private Statistics modelStats;
    private WebsiteCareTaker careTaker;
    private IWindow view;
    private WebsiteFileDAO dao;

    public DrawController(Website modelWebsite, Statistics modelStats, IWindow view, WebsiteFileDAO dao) {
        careTaker = new WebsiteCareTaker();
        this.modelWebsite = modelWebsite;
        this.modelStats = modelStats;
        this.view = view;
        this.view.setTriggers(this);
        this.dao = dao;
        this.modelWebsite.addObserver(view);
        this.modelStats.addObserver(view);
        this.careTaker.addObserver(view);
    }
    
    public boolean createPage(TextField txtFilename, TextField txtFolder, TextArea txtContent) {
        String filename = txtFilename.getText();
        filename = isURL(filename) || filename.contains(".html") ? 
                txtFilename.getText() : txtFilename.getText().concat(".html");
        
        String title = txtFilename.getText();
        String folder = txtFolder.getText();
        String content = txtContent.getText();
        
        try {
            careTaker.save(modelWebsite);
            Page newPage = new Page(title, filename, folder, content, isURL(filename));
            
            modelWebsite.insertVertex(newPage);
            updateStats();
            return true;
        } catch(Exception e) {
            return false;
        }
    }
    
    /**
     * Checks if the Vertex is an external URL
     * 
     * @param filename
     * @return true|false
     */
    public boolean isURL(String filename){
        try {
            URL url = new URL(filename);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean createHyperlink(String filenameA, String filenameB, String hyperlinkText) {
                
        try {
            careTaker.save(modelWebsite);
            Hyperlink newHyperlink = new Hyperlink(hyperlinkText);
            Vertex<Page> vertexA = findPage(filenameA);
            Vertex<Page> vertexB = findPage(filenameB);
            modelWebsite.insertEdge(vertexA, vertexB, newHyperlink);
            updateStats();
            return true;
        } catch(Exception e) {
            return false;
        }
    }
    
    /**
     * Finds the Page on the Graph
     * 
     * @param filename
     * @return null|Vertex
     */
    public Vertex<Page> findPage(String filename){
        for(Vertex<Page> vertex : modelWebsite.vertices()){
            if(vertex.element().getFilename().equals(filename)) {
                return vertex;
            }
        }
        return null;
    }
    
    /**
     * Finds the Hyperlink on the Graph
     * 
     * @param text
     * @return 
     */
    public Edge<Hyperlink, Page> findHyperlink(String text){
        for(Edge<Hyperlink, Page> edge : modelWebsite.edges()){
            if(edge.element().getText().equals(text)) {
                return edge;
            }
        }
        return null;
    }
    
    private void updateStats(){
        int internals = 0;
        int externals = 0;
        int hyperlinks = modelWebsite.numEdges();
        
        for (Vertex<Page> vertex : modelWebsite.vertices()) {
            if (isURL(vertex.element().getFilename())) {
                externals++;
            } else {
                internals++;
            }
        }
        
        modelStats.setInternals(internals);
        modelStats.setExternals(externals);
        modelStats.setHyperlinks(hyperlinks);
        modelStats.setHyperlinksValues(getHyperlinksValues());
    }
    
    /**
     * Retrives the Data for the Chart that refers to the hyperlinks
     * 
     * @return XYChart.Series
     */
    private XYChart.Series<String,Number> getHyperlinksValues() {
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        List<XYChart.Data<String,Number>> graphValues = new ArrayList<>();
        Map<String, Integer> newMap = new HashMap<>();
                
        for (Map.Entry<Vertex<Page>, List<Edge<Hyperlink, Page>>> entry : modelWebsite.getAdjacenciesMap().entrySet()) {
            Vertex<Page> vertex = entry.getKey();
            List<Edge<Hyperlink, Page>> edges = entry.getValue();
            
            if (newMap.size() < 5) newMap.put(vertex.element().getFilename(), edges.size());
            else {
                String key = "";
                Map.Entry<String, Integer> entryToPut = null;
                for (Map.Entry<String, Integer> newEntry : newMap.entrySet()) {
                    if (newEntry.getValue() < edges.size()) {
                        key = newEntry.getKey();
                        entryToPut = newEntry;
                        break;
                    }
                }
                
                if (!key.isEmpty() && entryToPut != null) {
                    newMap.remove(key);
                    newMap.put(entryToPut.getKey(), entryToPut.getValue());
                }
            }
        }
        
        feedGraphValues(graphValues, newMap);
        series.getData().addAll(graphValues);
        return series;
    }
    
    private void feedGraphValues(List<XYChart.Data<String,Number>> graphValues, Map<String, Integer> newMap) {
        for(Map.Entry<String, Integer> entry : newMap.entrySet()) {
            graphValues.add(new XYChart.Data(entry.getKey(), entry.getValue()));
            if (graphValues.size() == 5) break;
        }
    }
    
    public boolean removePage(String filename) {
        try {
            careTaker.save(modelWebsite);
            Vertex<Page> vertex = findPage(filename);
            modelWebsite.removeVertex(vertex);
            updateStats();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public boolean removeHyperlink(String text) {
        try {
            careTaker.save(modelWebsite);
            Edge<Hyperlink,Page> edge = findHyperlink(text);
            modelWebsite.removeEdge(edge);
            updateStats();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
    public void undo() {
        careTaker.restore(modelWebsite);
    }
}
