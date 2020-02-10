/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import controllers.IController;
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
import models.Hyperlink;
import models.Page;
import models.Website;

/**
 *
 * @author Rafae
 */
public final class DrawWindow implements IWindow {

    private Website website;
    
    private Scene scene;
    private SmartGraphPanel<Page, Hyperlink> graphPanel;
    private int titleX = 5;
    private int btnX1 = 15;
    private int btnX2 = 100;
    private Button createPage;
    private Button deletePage;
    private Button createHyperlink;
    private Button deleteHyperlink;
    private Button saveWebsite;
    private Button exitDraw;
    
    public DrawWindow(Website website) {
        this.website = website;
        Pane root = initComponents();
        scene = new Scene(root, 800, 600);
        root.setStyle("-fx-font: 12px \"Courier New\"");
    }
    
    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public Pane initComponents() {
        
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        graphPanel = new SmartGraphPanel<>(website.toGraphEdgeList(), strategy);
        
        
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
        txt2.setLayoutY(200);
        txt2.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 25.0));
                
        createHyperlink = new Button();
        createHyperlink.setGraphic(new ImageView("websitemaker/resources/images/add.png"));
        createHyperlink.setLayoutX(btnX1);
        createHyperlink.setLayoutY(215);

        
        deleteHyperlink = new Button();
        deleteHyperlink.setGraphic(new ImageView("websitemaker/resources/images/delete.png"));
        deleteHyperlink.setLayoutX(btnX2);
        deleteHyperlink.setLayoutY(215);
        
        Text txt3 = new Text("Website");
        txt3.setLayoutX(titleX);
        txt3.setLayoutY(970);
        txt3.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 25.0));
        
        saveWebsite = new Button();
        saveWebsite.setGraphic(new ImageView("websitemaker/resources/images/save.png"));
        saveWebsite.setLayoutX(btnX1);
        saveWebsite.setLayoutY(985);

        exitDraw = new Button();
        exitDraw.setGraphic(new ImageView("websitemaker/resources/images/cancel.png"));
        exitDraw.setLayoutX(btnX2);
        exitDraw.setLayoutY(985);
        
        
        vBox.getChildren().add(txt1);
        vBox.getChildren().add(createPage);
        vBox.getChildren().add(deletePage);
        vBox.getChildren().add(txt2);
        vBox.getChildren().add(createHyperlink);
        vBox.getChildren().add(deleteHyperlink);
        vBox.getChildren().add(txt3);
        vBox.getChildren().add(saveWebsite);
        vBox.getChildren().add(exitDraw);
        
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
    public void setTriggers(IController controller) {
        exitDraw.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
                stage.close();
            }
        });
    }
    
}
