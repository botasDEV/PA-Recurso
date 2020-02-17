/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.IController;
import controllers.MainController;
import java.util.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.Websites;

/**
 *
 * @author Rafae
 */
public final class MainWindow implements IWindow {
    
    private final Scene scene;
    private Button createBtn;
    private Button editBtn;
    private Button deleteBtn;
    private Button graphBtn;
    ListView listView;
    
    Websites websites;
    
    public MainWindow(Websites websites) {
        this.websites = websites;
        Pane root = initComponents();
        scene = new Scene(root, 500, 500);
        root.setStyle("-fx-font: 12px \"Courier New\"");
    }
    
    @Override
    public Pane initComponents() {
        VBox leftVBox = new VBox();
        leftVBox.setPadding(new Insets(10));
        leftVBox.setSpacing(8);
        
        Text title = new Text("Websites");
        title.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 25.0));
        title.getStyleClass().add("titles");
        leftVBox.getChildren().add(title);
        
        listView = new ListView();
        listView.setPrefHeight(600.0);
        
        if(websites.getWebsites().isEmpty()) {
            listView.getItems().add("-- No Projects Yet --");
            listView.disableProperty().set(true);
        } else {
            listView.disableProperty().set(false);
            websites.getWebsites().forEach(website -> {
                listView.getItems().add(website.getName());
            });
        }
        
        leftVBox.getChildren().add(listView);
        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20.0);
        gridPane.setVgap(20.0);
        gridPane.setAlignment(Pos.CENTER);
        
        createBtn = new Button();
        createBtn.setGraphic(new ImageView("websitemaker/resources/images/add.png"));
        editBtn = new Button();
        editBtn.setGraphic(new ImageView("websitemaker/resources/images/edit.png"));
        deleteBtn = new Button();
        deleteBtn.setGraphic(new ImageView("websitemaker/resources/images/delete.png"));
               
        gridPane.add(createBtn, 0, 0); 
        gridPane.add(editBtn, 0, 1);
        gridPane.add(deleteBtn, 1, 0);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(leftVBox);
        borderPane.setCenter(gridPane);
        
        return borderPane;
    }
    
    @Override
    public Scene getScene() {
        return scene;
    }
    
    @Override
    public void setTriggers(IController controller) {
        createBtn.setOnAction((ActionEvent event) -> {
            ((MainController)controller).createWebsite();
        });
    }
    
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("ATUALIZAR User Interface");
        
        Websites sites = (Websites) arg;
        listView.getItems().clear();
        if(sites.getWebsites().isEmpty()) {
            listView.getItems().add("-- No Projects Yet --");
            listView.disableProperty().set(true);
        } else {
            listView.disableProperty().set(false);
            sites.getWebsites().forEach(website -> {
                listView.getItems().add(website.getName());
            });
        }        
    }
    
}
