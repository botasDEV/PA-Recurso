/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import controllers.MainController;
import java.util.Observable;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Website;

/**
 *
 * @author Rafae
 */
public class DrawWindow implements IWindow {

    private Website website;
    
    private Scene scene;
    private SmartGraphPanel<String, String> graphPanel;
    private int titleX = 5;
    private int btnX1 = 15;
    private int btnX2 = 100;
    
        
    public DrawWindow(Website website) {
        Pane root = initComponents();
        scene = new Scene(root, 800, 600);
        root.setStyle("-fx-font: 12px \"Courier New\"");
        this.website = website;
    }
    
    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public Pane initComponents() {
        Graph<String, String> graph = new GraphEdgeList();   
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertVertex("F");

        graph.insertEdge("A", "B", "AB");
        graph.insertEdge("B", "A", "AB2");
        graph.insertEdge("A", "C", "AC");
        graph.insertEdge("A", "D", "AD");
        graph.insertEdge("B", "C", "BC");
        graph.insertEdge("F", "D", "DF2"); 
        
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        graphPanel = new SmartGraphPanel<>(graph, strategy);
        
        
        Pane vBox = new Pane();
        vBox.setPadding(new Insets(20, 50, 10, 0));
        
        Text txt1 = new Text("Page");
        txt1.setLayoutX(titleX);
        txt1.setLayoutY(35);
        txt1.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 25.0));
        
        Button btn1 = new Button();
        btn1.setGraphic(new ImageView("websitemaker/resources/images/add.png"));
        btn1.setLayoutX(btnX1);
        btn1.setLayoutY(50);
        
        Button btn2 = new Button();
        btn2.setGraphic(new ImageView("websitemaker/resources/images/delete.png"));
        btn2.setLayoutX(btnX2);
        btn2.setLayoutY(50);

        
        Text txt2 = new Text("Hyperlink");
        txt2.setLayoutX(titleX);
        txt2.setLayoutY(200);
        txt2.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 25.0));
                
        Button btn3 = new Button();
        btn3.setGraphic(new ImageView("websitemaker/resources/images/add.png"));
        btn3.setLayoutX(btnX1);
        btn3.setLayoutY(215);

        
        Button btn4 = new Button();
        btn4.setGraphic(new ImageView("websitemaker/resources/images/delete.png"));
        btn4.setLayoutX(btnX2);
        btn4.setLayoutY(215);
        
        Text txt3 = new Text("Website");
        txt3.setLayoutX(titleX);
        txt3.setLayoutY(970);
        txt3.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 25.0));
        
        Button btn5 = new Button();
        btn5.setGraphic(new ImageView("websitemaker/resources/images/save.png"));
        btn5.setLayoutX(btnX1);
        btn5.setLayoutY(985);

        Button btn6 = new Button();
        btn6.setGraphic(new ImageView("websitemaker/resources/images/cancel.png"));
        btn6.setLayoutX(btnX2);
        btn6.setLayoutY(985);
        btn6.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
                stage.close();
            }
        });
        
        vBox.getChildren().add(txt1);
        vBox.getChildren().add(btn1);
        vBox.getChildren().add(btn2);
        vBox.getChildren().add(txt2);
        vBox.getChildren().add(btn3);
        vBox.getChildren().add(btn4);
        vBox.getChildren().add(txt3);
        vBox.getChildren().add(btn5);
        vBox.getChildren().add(btn6);
        
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
                
        RowConstraints rowConstraints1 = new RowConstraints();
        rowConstraints1.setPercentHeight(100);
        gridPane.getRowConstraints().addAll(rowConstraints1);
        
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setPercentWidth(90);
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints2.setPercentWidth(10);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTriggers(MainController controller) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
