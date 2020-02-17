/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import adapter.GraphEdgleListAdapter;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartGraphVertexNode;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartRandomPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartStylableNode;
import controllers.DrawController;
import controllers.IController;
import digraph.Vertex;
import digraph.interfaces.Edge;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Hyperlink;
import models.Page;
import models.Statistics;
import models.Website;

/**
 *
 * @author Rafae
 */
public final class DrawWindow implements IWindow {

    private Website website;
    private Statistics stats;
    
    private Scene scene;
    private GraphEdgleListAdapter graphEdgleListAdapter;
    private SmartGraphPanel graphPanel;
    
    private int titleX = 5;
    private int btnX1 = 10;
    private int btnX2 = 95;
    private int chartHeight = 250;
    private int chartWidth = 200;
    private int axisFontSize = 10;
    private Button createPage;
    private Button deletePage;
    private Button createHyperlink;
    private Button deleteHyperlink;
    private Button saveWebsite;
    private Button exitDraw;
    private Text txtInternals;
    private Text txtExternals;
    private Text txtHyperlinks;
    private BarChart<String, Number> chartHyperlinks;
    private BarChart<String, Number> chartRefereced;
    private ValueAxis referenceValueAxis;
    private ValueAxis hyperlinksValueAxis;
    private XYChart.Series references;
    private XYChart.Series hyperlinks;
    
    public DrawWindow(Website website) {
        this.website = website;
        graphEdgleListAdapter = new GraphEdgleListAdapter(website);
        Pane root = initComponents();
        scene = new Scene(root, 1300, 1000);
        root.setStyle("-fx-font: 12px \"Courier New\"");
    }
    
    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public Pane initComponents() {
        SmartPlacementStrategy strategy = new SmartRandomPlacementStrategy();
        graphPanel = new SmartGraphPanel<>(graphEdgleListAdapter, strategy);
        if (graphEdgleListAdapter.numVertices() > 0) {
            graphPanel.getStylableVertex("index.html").setStyle("-fx-fill: cyan; -fx-stroke: blue; -fx-stroke-width: 3; -fx-radius: 5;");
        }        
        
        stats = new Statistics();
        
        Pane vBox = new Pane();
        vBox.setPadding(new Insets(20, 50, 10, 0));
        
        Text txt1 = new Text("Page");
        txt1.setLayoutX(titleX);
        txt1.setLayoutY(35);
        txt1.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 25.0));
        
        createPage = new Button();
        createPage.setGraphic(new ImageView("websitemaker/resources/images/add.png"));
        createPage.setLayoutX(btnX1);
        createPage.setLayoutY(50);
        
        deletePage = new Button();
        deletePage.setGraphic(new ImageView("websitemaker/resources/images/delete.png"));
        deletePage.setLayoutX(btnX2);
        deletePage.setLayoutY(50);
        
        Text txt2 = new Text("Hyperlink");
        txt2.setLayoutX(titleX);
        txt2.setLayoutY(160);
        txt2.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 25.0));
                
        createHyperlink = new Button();
        createHyperlink.setGraphic(new ImageView("websitemaker/resources/images/add.png"));
        createHyperlink.setLayoutX(btnX1);
        createHyperlink.setLayoutY(175);
        
        deleteHyperlink = new Button();
        deleteHyperlink.setGraphic(new ImageView("websitemaker/resources/images/delete.png"));
        deleteHyperlink.setLayoutX(btnX2);
        deleteHyperlink.setLayoutY(175);
        
        // Sets the statistics
        Text txtStats = new Text("Statistics");
        txtStats.setLayoutX(titleX);
        txtStats.setLayoutY(310);
        txtStats.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 25.0));
        
        txtInternals = new Text("NO. Internals: ".concat(String.valueOf(stats.getInternals())));
        txtInternals.setLayoutX(titleX);
        txtInternals.setLayoutY(325);
        txtExternals = new Text("NO. Externals: ".concat(String.valueOf(stats.getExternals())));
        txtExternals.setLayoutX(titleX);
        txtExternals.setLayoutY(340);
        txtHyperlinks = new Text("NO. Hyperlinks: ".concat(String.valueOf(stats.getHyperlinks())));
        txtHyperlinks.setLayoutX(titleX);
        txtHyperlinks.setLayoutY(355);
        
        // Build the Charts
        CategoryAxis categoryHyperlinkAxis = new CategoryAxis();
        categoryHyperlinkAxis.tickLabelFontProperty().set(Font.font(axisFontSize));
        categoryHyperlinkAxis.setPrefWidth(chartWidth + 100);
        
        hyperlinksValueAxis = new NumberAxis();
        hyperlinksValueAxis.tickLabelFontProperty().set(Font.font(axisFontSize));
        hyperlinksValueAxis.setLabel("Hyperlinks");
        hyperlinksValueAxis.setLowerBound(0);
        hyperlinksValueAxis.setUpperBound(10);
        
        chartHyperlinks = new BarChart(categoryHyperlinkAxis, hyperlinksValueAxis);
        chartHyperlinks.setPrefWidth(chartWidth);
        chartHyperlinks.setLegendVisible(false);
        chartHyperlinks.setPrefHeight(chartHeight);
        hyperlinks = new XYChart.Series();
        
        hyperlinks.getData().add(new XYChart.Data("index.html", 0));
        
        chartHyperlinks.getData().addAll(hyperlinks);
        
        CategoryAxis categoryRefAxis = new CategoryAxis();
        categoryRefAxis.tickLabelFontProperty().set(Font.font(axisFontSize));
        categoryRefAxis.setPrefWidth(chartWidth);
        
        referenceValueAxis = new NumberAxis();
        referenceValueAxis.tickLabelFontProperty().set(Font.font(axisFontSize));
        referenceValueAxis.setLabel("References");        
        referenceValueAxis.setLowerBound(0);
        referenceValueAxis.setUpperBound(10);
        chartRefereced = new BarChart(categoryRefAxis, referenceValueAxis);
        chartRefereced.setLegendVisible(false);
        chartRefereced.setPrefWidth(chartWidth);
        chartRefereced.setPrefHeight(chartHeight);
        references = new XYChart.Series();
        references.getData().add(new XYChart.Data("index.html", 0));
        
        chartRefereced.getData().addAll(references);

        // Grid Pane to set the Charts
        GridPane gridPaneCharts = new GridPane();
        gridPaneCharts.setLayoutY(375);
        gridPaneCharts.add(chartHyperlinks, 0, 0);
        gridPaneCharts.add(chartRefereced, 0, 2);
        
        
        Text txt3 = new Text("Website");
        txt3.setLayoutX(titleX);
        txt3.setLayoutY(900);
        txt3.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 25.0));
        
        saveWebsite = new Button();
        saveWebsite.setGraphic(new ImageView("websitemaker/resources/images/save.png"));
        saveWebsite.setLayoutX(btnX1);
        saveWebsite.setLayoutY(915);

        exitDraw = new Button();
        exitDraw.setGraphic(new ImageView("websitemaker/resources/images/cancel.png"));
        exitDraw.setLayoutX(btnX2);
        exitDraw.setLayoutY(915);
        
        vBox.getChildren().add(txt1);
        vBox.getChildren().add(createPage);
        vBox.getChildren().add(deletePage);
        vBox.getChildren().add(txt2);
        vBox.getChildren().add(createHyperlink);
        vBox.getChildren().add(deleteHyperlink);
        vBox.getChildren().add(txt3);
        vBox.getChildren().add(saveWebsite);
        vBox.getChildren().add(exitDraw);
        vBox.getChildren().add(txtStats);
        vBox.getChildren().add(txtInternals);
        vBox.getChildren().add(txtExternals);
        vBox.getChildren().add(txtHyperlinks);
        vBox.getChildren().add(gridPaneCharts);
        
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
                
        RowConstraints rowConstraints1 = new RowConstraints();
        rowConstraints1.setPercentHeight(100);
        gridPane.getRowConstraints().addAll(rowConstraints1);
        
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setPercentWidth(75);
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints2.setPercentWidth(25);
        gridPane.getColumnConstraints().addAll(columnConstraints1, columnConstraints2);
        
        gridPane.add(graphPanel, 0, 0);
        gridPane.add(vBox, 1, 0);
        
        return gridPane;
    }
    
    public SmartGraphPanel getPanel() {
        return graphPanel;
    }

    @Override
    public void update(Observable o, Object arg) {
                
        if (arg instanceof Website) updateWebsite((Website)arg);
        if (arg instanceof Statistics) updateStats((Statistics)arg); 
        if (arg instanceof Map && ((HashMap<String, Vertex<Page>>)arg).containsKey("removedV"))
            removePage(((HashMap<String, Vertex<Page>>)arg).get("removedV"));
        if (arg instanceof Map && ((HashMap<String, Edge<Hyperlink, Page>>)arg).containsKey("removedE"))
            removeHyperlink(((HashMap<String,  Edge<Hyperlink, Page>>)arg).get("removedE"));
        
    }
    
    private void removeHyperlink(Edge<Hyperlink, Page> edge){
        Runnable r;
        r = () -> {
            graphEdgleListAdapter.removeHyperlink(edge.element().getText());
            graphPanel.updateAndWait();
        };

        new Thread(r).start();
    }
    
    /**
     * Removed the Page from the visual
     * 
     * @param vertex 
     */
    private void removePage(Vertex<Page> vertex){
        Runnable r;
        r = () -> {
            graphEdgleListAdapter.removePage(vertex.element().getFilename());
            graphPanel.updateAndWait();
        };

        new Thread(r).start();
    }
    
    /**
     * Updates the graph visualy
     * 
     * @param website 
     */
    private void updateWebsite(Website website){
        Runnable r;
        r = () -> {
            graphEdgleListAdapter.setWebsite(website);
            com.brunomnsilva.smartgraph.graph.Vertex<String> insertedVertex = graphEdgleListAdapter.insertVertex();
            graphEdgleListAdapter.insertEdges();
            graphPanel.updateAndWait();

            SmartStylableNode stylableVertex = graphPanel.getStylableVertex(insertedVertex);
            if (stylableVertex != null) {
                String vertexStyle = "-fx-fill: orange; -fx-stroke: red; -fx-stroke-width: 3;";
                if (isURL(insertedVertex.element())) {
                    vertexStyle = "-fx-fill: gray; -fx-stroke: darkgrey; -fx-stroke-width: 3;";
                } 
                stylableVertex.setStyle(vertexStyle);
            }
        };

        new Thread(r).start();
    }
    
    /**
     * Updates the Statistics
     * 
     * @param website 
     */
    private void updateStats(Statistics stats){
        txtInternals.setText("NO. Internals: ".concat(String.valueOf(stats.getInternals())));
        txtExternals.setText("NO. Externals: ".concat(String.valueOf(stats.getExternals())));
        txtHyperlinks.setText("NO. Hyperlinks: ".concat(String.valueOf(stats.getHyperlinks())));
        chartHyperlinks.getData().clear();
        chartHyperlinks.getData().add(stats.getHyperlinksValues());
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

    @Override
    public void setTriggers(IController controller) {
        exitDraw.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                ((Stage) scene.getWindow()).close();
            }
        });
        
        createPage.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                createPageDialog(controller, new HashMap<>());
            }
        });
        
        createHyperlink.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                createHyperlinkDialog(controller);
            }
        });
        
        deletePage.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                createRemovePageDialog(controller);
            }
        });
        
        deleteHyperlink.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                createRemoveHyperlinkDialog(controller);
            }
        });
        
        graphPanel.setVertexDoubleClickAction(vertex -> {
            
            Map<String,String> attrs = new HashMap<>();
            String element = ((SmartGraphVertexNode<String>)vertex).getUnderlyingVertex().element();
            Vertex<Page> v = ((DrawController)controller).findPage(element);
            
            attrs.put("filename", v.element().getFilename());
            attrs.put("folder", v.element().getFolder());
            attrs.put("content", v.element().getContent());
            createPageDialog(controller, attrs);
        });
        
        graphPanel.setEdgeDoubleClickAction(edge -> {
            System.out.println(edge.toString());
        });
    }

    private Dialog generateDialog(){
        Dialog dialog = new Dialog();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        
        return dialog;
    }
    
    private void createPageDialog(IController controller, Map<String, String> attrs){
        boolean isExternal = false;
        
        Dialog dialog = generateDialog();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        
        TextField txtFilename = new TextField();
        TextArea txtContent = new TextArea();
        TextField txtFolder = new TextField();
        
        if (attrs.containsKey("filename")) {
            String filename = attrs.get("filename");
            txtFilename.setText(filename);
            
            isExternal = ((DrawController)controller).isURL(filename);
        }
        
        if (isExternal) {
            txtContent.setDisable(isExternal);
            txtFolder.setDisable(isExternal);
        } else {
            if (attrs.containsKey("content")) txtContent.setText(attrs.get("content"));
            if (attrs.containsKey("folder")) txtFolder.setText(attrs.get("folder"));
        }
        
        Label lblFilename = new Label("Filename");
        Label lblContent = new Label("Content");
        Label lblFolder = new Label("Folder");
        
        gridPane.add(lblFilename, 0, 0);
        gridPane.add(lblFolder, 0, 1);
        gridPane.add(lblContent, 0, 2);
        gridPane.add(txtFilename, 1, 0);
        gridPane.add(txtFolder, 1, 1);
        gridPane.add(txtContent, 1, 2);     
        
        Button ok = new Button("OK");
        Button cancel = new Button("Cancel");
        ok.setPrefWidth(100);
        cancel.setPrefWidth(100);
        
        gridPane.add(ok, 2, 0);
        gridPane.add(cancel, 2, 1);
        
        ok.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                boolean success = ((DrawController)controller).createPage(txtFilename, txtFolder, txtContent);
                if(success) dialog.close();
            }
        });
        
        cancel.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                dialog.close();
            }
        });
        
        dialog.getDialogPane().setContent(gridPane);
        dialog.show();
    }
    
    private void createHyperlinkDialog(IController controller){
        Dialog dialog = generateDialog();
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        Label lblCombo = new Label("Choose the vertices you connect with an Hyperlink");
        Label lblHyperlink = new Label("Add the text you want to appear");
        TextField txtHyperlink = new TextField();
        
        ComboBox verticesA = new ComboBox();
        ComboBox verticesB = new ComboBox();
        
        fillCombo(verticesA, true);
        fillCombo(verticesB, false);
        
        gridPane.add(lblCombo, 0, 0, 2, 1);
        gridPane.add(lblHyperlink, 0, 1);
        gridPane.add(txtHyperlink, 1, 1);

        
        gridPane.add(verticesA, 0, 2);
        gridPane.add(verticesB, 1, 2);
        
        Button ok = new Button("OK");
        Button cancel = new Button("Cancel");
        ok.setPrefWidth(100);
        cancel.setPrefWidth(100);
        
        gridPane.add(ok, 2, 0);
        gridPane.add(cancel, 2, 1);
        
        ok.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                if(verticesA.getValue() != null && verticesB.getValue() != null && !txtHyperlink.getText().equals("")) {
                    boolean success = ((DrawController)controller)
                            .createHyperlink(verticesA.getValue().toString(), verticesB.getValue().toString(), txtHyperlink.getText());
                    if(success) dialog.close();
                }
            }
        });
        
        cancel.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                dialog.close();
            }
        });
        
        dialog.getDialogPane().setContent(gridPane);
        dialog.show();        
    }
    
    /**
     * Fills the combobox with vertices
     * 
     * @param combobox
     * @param isFrom 
     */
    private void fillCombo(ComboBox combobox, boolean isFrom) {
        List<Vertex<Page>> vertices = (List<Vertex<Page>>) website.vertices();
        for (Vertex<Page> page : vertices) {
            if(isFrom && !page.element().isExternal() || !isFrom) {
                combobox.getItems().add(page.element().getFilename());
            }
        }        
    }
    
    /**
     * Fills the combobox with edges
     * 
     * @param combobox
     * @param isFrom 
     */
    private void fillComboEdges(ComboBox combobox, boolean isFrom) {
        
        for (Edge<Hyperlink, Page> page : website.edges()) {
            combobox.getItems().add(page.element().getText());
        }        
    }
    
    
    private void createRemoveHyperlinkDialog(IController controller) {
        Dialog dialog = generateDialog();
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        Label lblCombo = new Label("Choose the Page you want to remove");
        ComboBox edges = new ComboBox();
        fillComboEdges(edges, false);
        
        gridPane.add(lblCombo, 0, 0);
        gridPane.add(edges, 0, 1);
        
        Button ok = new Button("OK");
        Button cancel = new Button("Cancel");
        ok.setPrefWidth(100);
        cancel.setPrefWidth(100);
        
        gridPane.add(ok, 2, 0);
        gridPane.add(cancel, 2, 1);
        
        ok.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
               boolean success = ((DrawController)controller).removeHyperlink(edges.getValue().toString());
               if (success) dialog.close();               
            }
        });
        
        cancel.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                dialog.close();
            }
        });
        
        dialog.getDialogPane().setContent(gridPane);
        dialog.show();
    }
    
    /**
     * Removes a Page from the Website
     * 
     * @param controller 
     */
    private void createRemovePageDialog(IController controller) {
        Dialog dialog = generateDialog();
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        Label lblCombo = new Label("Choose the Page you want to remove");
        ComboBox vertices = new ComboBox();
        fillCombo(vertices, false);
        
        gridPane.add(lblCombo, 0, 0);
        gridPane.add(vertices, 0, 1);
        
        Button ok = new Button("OK");
        Button cancel = new Button("Cancel");
        ok.setPrefWidth(100);
        cancel.setPrefWidth(100);
        
        gridPane.add(ok, 2, 0);
        gridPane.add(cancel, 2, 1);
        
        ok.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
               if (vertices.getValue().toString().equals("index.html")) showAlert("The index page cannot be removed!");
               else {
                   boolean success = ((DrawController)controller).removePage(vertices.getValue().toString());
                   if (success) dialog.close();
               }
               
            }
        });
        
        cancel.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                dialog.close();
            }
        });
        
        dialog.getDialogPane().setContent(gridPane);
        dialog.show();
    }
    
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        
        alert.showAndWait();
    }
}
