/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import adapter.GraphEdgleListAdapter;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import controllers.DrawController;
import controllers.IController;
import digraph.Vertex;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import models.Page;
import models.Website;

/**
 *
 * @author Rafae
 */
public final class DrawWindow implements IWindow {

    private Website website;
    
    private Scene scene;
    private GraphEdgleListAdapter graphEdgleListAdapter;
    private SmartGraphPanel graphPanel;
    private int titleX = 5;
    private int btnX1 = 10;
    private int btnX2 = 95;
    private Button createPage;
    private Button deletePage;
    private Button createHyperlink;
    private Button deleteHyperlink;
    private Button saveWebsite;
    private Button exitDraw;
    
    public DrawWindow(Website website) {
        this.website = website;
        graphEdgleListAdapter = new GraphEdgleListAdapter(website);
        Pane root = initComponents();
        scene = new Scene(root, 1200, 900);
        root.setStyle("-fx-font: 12px \"Courier New\"");
    }
    
    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public Pane initComponents() {
        graphPanel = new SmartGraphPanel<>(graphEdgleListAdapter);
        
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
        txt3.setLayoutY(770);
        txt3.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 25.0));
        
        saveWebsite = new Button();
        saveWebsite.setGraphic(new ImageView("websitemaker/resources/images/save.png"));
        saveWebsite.setLayoutX(btnX1);
        saveWebsite.setLayoutY(785);

        exitDraw = new Button();
        exitDraw.setGraphic(new ImageView("websitemaker/resources/images/cancel.png"));
        exitDraw.setLayoutX(btnX2);
        exitDraw.setLayoutY(785);
        
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
        columnConstraints1.setPercentWidth(85);
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints2.setPercentWidth(15);
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
        graphEdgleListAdapter.setWebsite((Website)arg);
        graphEdgleListAdapter.insertVertices();
        graphEdgleListAdapter.insertEdges();
        graphPanel.update();
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
                Dialog dialog = createPageDialog(controller);
            }
        });
        
        createHyperlink.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                Dialog dialog = createHyperlinkDialog(controller);
            }
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
    
    private Dialog createPageDialog(IController controller){
        Dialog dialog = generateDialog();
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        TextField txtFilename = new TextField();
        TextArea txtContent = new TextArea();
        TextField txtFolder = new TextField();
        
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
        return dialog;
    }
    
    private Dialog createHyperlinkDialog(IController controller){
        Dialog dialog = generateDialog();
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        Label lblCombo = new Label("Choose the vertices you connect with an Hyperlink");
        Label lblHyperlink = new Label("Add the text you want to appear");
        TextField txtHyperlink = new TextField();
        
        ComboBox verticesA = new ComboBox();
        ComboBox verticesB = new ComboBox();
        
        fillCombo(verticesA);
        fillCombo(verticesB);
        
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
        return dialog;
    }
    
    private void fillCombo(ComboBox combobox) {
        List<Vertex<Page>> vertices = (List<Vertex<Page>>) website.vertices();
        for (Vertex<Page> page : vertices) {
            combobox.getItems().add(page.element().getFilename());
        }        
    }
}
