/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author AHMED GHALY
 */
public class AddItem {
    
    public static void add(){
        
        Stage windowAdd = new Stage();
        GridPane root = new GridPane();
        HBox root1 = new HBox(10);
        
        // labels
        // id label
        Label idlb = new Label("ID   ");
        root.add(idlb, 0, 0);
        // name label
        Label namelb = new Label("Name   ");
        root.add(namelb, 0, 1);
        // price label
        Label pricelb = new Label("Price   ");
        root.add(pricelb, 0, 2);
        // quntity label
        Label quntitylb = new Label("Quntity   ");
        root.add(quntitylb, 0, 3);
        
        // textfields
        // id
        TextField idt = new TextField();
        idt.setPromptText("ID");
        root.add(idt, 1, 0);
        // name
        TextField namet = new TextField();
        namet.setPromptText("Name");
        root.add(namet, 1, 1);
        // price
        TextField pricet = new TextField();
        pricet.setPromptText("Price");
        root.add(pricet, 1, 2);
        // quntiy
        TextField quntityt = new TextField();
        quntityt.setPromptText("Quntity");
        root.add(quntityt, 1, 3);

        // buttons
        Image se = new Image(ApplicationApp.class.getResourceAsStream("add.png"));
        Button addb = new Button("Add", new ImageView(se));
        addb.setMinHeight(30);
 
        Image se1 = new Image(ApplicationApp.class.getResourceAsStream("cancel.png"));
        Button cancelb = new Button("Cancel", new ImageView(se1));
        cancelb.setMinHeight(30);

        
        // event in button
        cancelb.setOnAction(e-> windowAdd.close());
        
        // event on add button
        addb.setOnAction(e->{
                   
            addToData(

                idt.getText(),
                namet.getText(),
                pricet.getText(),
                quntityt.getText()
            
            );  // call the mathod addToData
            
            windowAdd.close();
        });

        // gridpane
        
        root.setAlignment(Pos.CENTER);
        root.setVgap(10);
        root.setHgap(10);
        root.add(root1, 1, 4);
        
        // vbox
        root1.getChildren().addAll(addb, cancelb);
        root1.setAlignment(Pos.CENTER);
        
        // scene
        Scene scene = new Scene(root, 300, 250);
        
        // add Css file 
        scene.getStylesheets().add(AddItem.class.getResource("App.css").toExternalForm());

        // stage
        windowAdd.getIcons().add(new Image(AddItem.class.getResourceAsStream( "add.png" )));
        windowAdd.setScene(scene);
        windowAdd.setTitle("Add Item");
        windowAdd.initModality(Modality.APPLICATION_MODAL);
        windowAdd.showAndWait();

    }
    
    // method to add to the database
    public static void addToData(String i, String n, String p, String q){
        
         int r = Integer.parseInt(i);   // convert string i(id) to integer
        // sure that the id do not exite incide the database
         if(sureId(r)){
                
            Alert alert = new Alert(Alert.AlertType.WARNING);           
            alert.setTitle("Error....");
            alert.setHeaderText("This id are already excite");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                alert.close();
                return;
            }
        }
        // if the id dont excite inside database
        // add it to datatbase
        try{
            Connection con = null;
            con = DriverManager.getConnection("jdbc:sqlite:product.db");
            Statement stmt  = con.createStatement();

            stmt.executeQuery( "INSERT INTO products VALUES (" +i+ ",'" +n+ "'," +p+ "," +q+ ")" );
            con.close();
            stmt.close();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    // TO SRUE THAT THE ID NOT REABETE
    
    public static boolean sureId(int y){
        
         try{
            Connection con = null;
            con = DriverManager.getConnection("jdbc:sqlite:product.db");
            Statement stmt  = con.createStatement();
            
            ResultSet s = stmt.executeQuery("SELECT name FROM products");
            System.out.println(s.getString("name"));
            ResultSet rs    = stmt.executeQuery("SELECT id FROM products");            
            while(rs.next()){
                if(y==rs.getInt("id"))
                    return true;
            }
            con.close();
            stmt.close();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
         return false;
        
    } 
}
