/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javafx.stage.Modality;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author AHMED GHALY
 */
public class Order {
    
    public void order(){
     
        Stage windowOrder = new Stage();
        GridPane root = new GridPane();
        HBox root1 = new HBox(20);
        // combobox
        ComboBox<String> com = new ComboBox<>();
         root.add(com, 1, 0);
        com.setPromptText("Select the item to Order it");
        // connect to data base
        try{
                Connection con = null;
                con = DriverManager.getConnection("jdbc:sqlite:product.db");
                Statement stmt  = con.createStatement();
                ResultSet rs    = stmt.executeQuery("SELECT name FROM products");
                while(rs.next()){
                
                com.getItems().addAll( rs.getString("name") );
            }
                con.close();
                stmt.close();
                rs.close();
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        
       
        // button
        Image se = new Image(Order.class.getResourceAsStream( "order.png" ));
        Button orderb = new Button("Order", new ImageView(se));
        Image se1 = new Image(Order.class.getResourceAsStream( "cancel.png" ));
        Button c = new Button("Cancel", new ImageView(se1));
        c.setOnAction(e-> windowOrder.close());
        Button cal = new Button("Calculator");
        
        
        // textfield 
        TextField total = new TextField();
        root.add(total, 1, 2);
        total.setEditable(false);
        TextField num = new TextField();
        root.add(num, 1, 1);
        

  

        // action on buttton
        orderb.setOnAction(e->{
            
            try{
 
            Connection con = null;
            con = DriverManager.getConnection("jdbc:sqlite:product.db");
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery("SELECT quntity FROM products  WHERE name = '"+com.getValue()+"'" );
            int t = rs.getInt("quntity");
            int d1 = t;
            int n = Integer.parseInt(num.getText());
            d1 -= n;
            if(d1<0)
                error(com.getValue(), t);
              if(d1>=0){
                  String w = Integer.toString(d1);
                  System.out.println(w);
            stmt.executeQuery("UPDATE products SET quntity = ("+w+") WHERE name = '"+com.getValue()+"'");
            con.close();
            stmt.close();
            rs.close();
            }
              

        } catch(Exception e1){
            System.out.println(e1.getMessage());
        }
        
        windowOrder.close();
        });
       cal.setOnAction(e->{
           // to cal the total price
           try{
           Connection con = null;
            con = DriverManager.getConnection("jdbc:sqlite:product.db");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT price FROM products  WHERE name = '"+com.getValue()+"'" );
            float x, y, s;
            x = rs.getFloat("price");
            String r = num.getText();
            s = Float.parseFloat(r);
            y = x*s;
            r = Float.toString(y);
            total.setText(r);
               System.out.println(r);
           } catch(Exception e1){
               System.out.println(e1.getMessage());
           }
            
           
           
       });

        
        // pane
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.setVgap(20);
        root.setHgap(20);
        
        root1.getChildren().addAll(cal, orderb, c);
        root.add(root1, 1, 3);
        
        // label
        Label number = new Label("Number");
        root.add(number, 0, 1);
        Label to = new Label("Total");
        root.add(to, 0, 2);        
        
        // scene
        Scene scene = new Scene(root, 400, 300);
        // add Css file
        scene.getStylesheets().add(Order.class.getResource("App.css").toExternalForm());

        // stage
        windowOrder.setScene(scene);
        windowOrder.setTitle("Order");
        windowOrder.initModality(Modality.APPLICATION_MODAL);
        windowOrder.showAndWait();
    }
    
    //*******************************************************
    
    // method sure that the amount you want incide the database or not
    public  void error(String d, int y){

    Stage window = new Stage();
    StackPane root = new StackPane();
    Label r = new Label("ther are not enough of item '"+d+"'  Ther Are only : "+y);
    root.getChildren().add(r);
    Scene scne = new Scene(root, 350, 100);
    window.setScene(scne);
    window.show();
    }
}
