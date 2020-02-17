/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Rafae
 */
public class Statistics extends Observable {
    
    private int internals;
    private int externals;
    private int hyperlinks;
    private XYChart.Series<String,Number> hyperlinksValues;
    private XYChart.Series<String,Number> referencedValues;

    public Statistics() {
        this(1,0,0);
    }
    
    public Statistics(int internals, int externals, int hyperlinks) {
        this.internals = internals;
        this.externals = externals;
        this.hyperlinks = hyperlinks;
        this.hyperlinksValues = new XYChart.Series<>();
        this.referencedValues = new XYChart.Series<>();
    }

    public XYChart.Series<String,Number> getHyperlinksValues() {
        return hyperlinksValues;
    }

    public void setHyperlinksValues(XYChart.Series<String,Number> hyperlinksValues) {
        this.hyperlinksValues = hyperlinksValues;
        notifyObjects();
    }

    public XYChart.Series<String,Number> getReferencedValues() {
        return referencedValues;
    }

    public void setReferencedValues(XYChart.Series<String,Number> referencedValues) {
        this.referencedValues = referencedValues;
        notifyObjects();
    }

    public int getInternals() {
        return internals;
    }

    public void setInternals(int internals) {
        this.internals = internals;
        notifyObjects();
    }

    public int getExternals() {
        return externals;
    }

    public void setExternals(int externals) {
        this.externals = externals;
        notifyObjects();
    }

    public int getHyperlinks() {
        return hyperlinks;
    }

    public void setHyperlinks(int hyperlinks) {
        this.hyperlinks = hyperlinks;
        notifyObjects();
    }
    
    private void notifyObjects() {
        setChanged();
        notifyObservers(this);
    }
    
}
