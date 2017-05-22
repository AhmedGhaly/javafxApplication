/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.function.Predicate;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;

/**
 *
 * @author AHMED GHALY, MOHAMED MOSAD
 */
public class ApplicationApp extends Application {
    
    // pans
    HBox root1 = new HBox(10); 
    BorderPane r = new BorderPane();
    GridPane root8 = new GridPane();
    TableView<Product> table = new TableView<>();
    Stage window5 = new Stage();
    // label
        Label il = new Label("");   // id lable
        Label nl = new Label("");   // name labe
        Label pl = new Label("");   // price lable
        Label ql = new Label("");   // quntity lable
        
    ObservableList<Product> item = FXCollections.observableArrayList();
    Stage primaryStage = new Stage();
    
    // method to connect database
    private void connect(){
        try{
            item.clear();
            Connection con = null;
            con = DriverManager.getConnection("jdbc:sqlite:product.db");
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery("SELECT * FROM products");
            while(rs.next()){
                
                item.add(
                        new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getFloat("price"),
                            rs.getInt("quntity")
                        )
                    );
            }
            
            // to delete the elment that quntity equal zero
            rs    = stmt.executeQuery("SELECT quntity FROM products");  // get the quntitiy form all element
            while(rs.next()){
                int d1 = rs.getInt("quntity");
                if(d1==0){
                  String r = Integer.toString(d1);  // translate the integer to string
                  ApplicationApp object = new ApplicationApp();  // make a new object frome class Allplicatio to call deleteTrem class
                  object.delereItem(Integer.parseInt(r));
              }
        
            }
            // to clse database
            con.close();
            stmt.close();
            rs.close();
             
        }
      
        catch(Exception e){
            System.out.println(e.getMessage());
            
        }
        
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        sinIn();
    }
    
    public void close(Stage primary) {
        // method to sure close stage by alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);           
       alert.setTitle("Close Project"); // make titlte for alert
       alert.setHeaderText("Are you Sure ?");   // text show in alert stage
       Optional<ButtonType> result = alert.showAndWait();
       if (result.get() == ButtonType.OK){  // if user press OK
            alert.close();  // close the alert
            primary.close();    // close the programe

       } else {
          
        }
    }

       
    // method to makr a login window   
    public void LogIn (TextField inputname,TextField pass){
   
        if( pass.getText().equals("123")&&
            ( inputname.getText().equals("Ahmed")||inputname.getText().equals("Mohamed"))){
           
        table();    // show the programe if enter the user and pass correct
        window5.close();    // close login window            
        
       } else {
            // if user enter uncorrect data
            Stage window2=new Stage();
            window2.setMinWidth(200);                     
            Label lableError=new  Label("Your Username Or Password is Wrong");
            lableError.setTextFill(Color.rgb(210, 0, 0));                        
            lableError .setFont(new Font("Arial", 15));
            Button ok = new Button("                OK             ");                       
            VBox Edit=new VBox(50); // make a vBox
            Edit.setPadding(new Insets(20,20,20,20));   // make a pading
            Edit.getChildren().addAll(lableError,ok);   // add the elment to the Pain
            Scene scen3 = new Scene( Edit); // make the scene
            window2.setScene(scen3);    //add the scene to the Stage
            window2.show(); 
            // if user enter ok
            ok.setOnAction(e->{
                window2.close(); 
                pass.clear();
            });

        }
    }
    
    
public void table(){
      
    connect(); // to connect to database

    // add column
    // id column
    TableColumn<Product, Integer> id = new TableColumn<>("ID");
    id.setMaxWidth(50);
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    // name coulnm
    TableColumn<Product, String> name = new TableColumn<>("Name");
    name.setMinWidth(120);
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    // price coulmn
    TableColumn<Product, Double> price = new TableColumn<>("Price");
    price.setMinWidth(80);
    price.setCellValueFactory(new PropertyValueFactory<>("price"));

    //quntity coulmn
    TableColumn<Product, Integer> quntity = new TableColumn<>("Quntity");
    quntity.setMinWidth(40);
    quntity.setCellValueFactory(new PropertyValueFactory<>("quntity"));

    // buttons
    // add button
    Image se4 = new Image(ApplicationApp.class.getResourceAsStream( "add.png" ));
    Button add = new Button("Add", new ImageView(se4));
    add.setMinWidth(100);

    // delete button
    Image se1 = new Image(ApplicationApp.class.getResourceAsStream( "delete.png" ));
    Button del = new Button("Delete", new ImageView(se1));
    del.setMinWidth(100);
    // search button
    Image se = new Image(ApplicationApp.class.getResourceAsStream( "search.png" ));
    Button search = new Button("Search", new ImageView(se));       
    search.setMinWidth(100);
    // order button
    Image se2 = new Image(ApplicationApp.class.getResourceAsStream( "order.png" ));
    Button order = new Button("Order", new ImageView(se2));
    order.setMinWidth(100); 
    // show button
    Button show = new Button("Show");

    //**************************


        // events in button

        // event in show button
        show.setOnAction(e-> {
            
            try{
           Product select = table.getSelectionModel().getSelectedItem();    // get the selected item
            int y = select.getId(); //get id of tje selected item
            search(y);  // search by id for selected item
            }
            // if user click the button but do not select any item
            catch(Exception f){
       
                Alert alert = new Alert(Alert.AlertType.WARNING);           
     
                // alert.getDialogPane();
                alert.getDialogPane().setStyle("-fx-border-color: black; -fx-cursor: hand; -fx-border-width: 5; -fx-border-radius:10;-fx-font-family: \"Arial Black\";"
                        + ""
                        + " -fx-padding: 0.5em 0.5em 0.5em 0.5em;\n" +
                        "   -fx-background-color:tomato;\n" +
                        "   -fx-text-fill:black;\n" 
                        );        
                alert.initStyle(StageStyle.UNDECORATED);
                 //  ((Node)(alert.getSource())).getScene().getWindow().hide();
                alert.getDialogPane().getScene().setFill(Color.GREEN);
                alert.setTitle("WARNING");
                alert.setHeaderText("If You Want to Show Item information \n"
               + "you Must Select a Item from Table \n"+"Which You want to Show it \n"+"Then click Show. ");
        
                Optional<ButtonType> result = alert.showAndWait();
 
               if (result.get() == ButtonType.OK){  // if user click OK
                    alert.close();
   
                } else {
           
                     alert .setOnCloseRequest(a-> {
                     System.out.println(f.getMessage());
                    alert
                    .close();});
            
 
  
                }
            }
              
    });
        // evet in delete button
        del.setOnAction(e-> {
            
            try{
            Product select = table.getSelectionModel().getSelectedItem();   // to fit the date of selected item
            int y = select.getId(); // to get id of the that item
            delereItem(y);  // clall delete class to delete the selcted item
            connect();  // refreh the table
            }
            // if user click delete button but do not select item
             catch(Exception f){ 
                System.out.println(f.getMessage());
                Alert alert = new Alert(Alert.AlertType.WARNING);   // make alert           

                // make a style for alert
                alert.getDialogPane().setStyle("-fx-border-color: black; -fx-cursor: hand; -fx-border-width: 5; -fx-border-radius:10;-fx-font-family: \"Arial Black\";"
                        + ""
                        + " -fx-padding: 0.5em 0.5em 0.5em 0.5em;\n" +
                        "   -fx-background-color:tomato;\n" +
                        "   -fx-text-fill:black;\n" 
                        );    alert.initStyle(StageStyle.UNDECORATED);
                alert.getDialogPane().getScene().setFill(Color.GREEN);
                alert.setTitle("WARNING");
                 alert.setHeaderText("If You Want to Delete Element \n"
                    + "you Must Select a Row in Table \n"+"Which You want to Delete it \n"+"Then click delete. ");
       
 
                Optional<ButtonType> result = alert.showAndWait();
                // if user click ok
                if (result.get() == ButtonType.OK){
                     alert.close();

                } else {
                    alert.close();
                    System.out.println(f.getMessage());
        
                }
            }
              
        });
        // event in search button
        search.setOnAction(e-> {

            TextField Search =new TextField();
            Search.setPromptText("Search with Id Or Name ");
            Search.setMaxWidth(200);
            HBox e1 = new HBox(10); // make a hbox pane
            Button ok = new Button("ok");
            // event in ok button
            ok.setOnAction(e3-> {
                Search.clear(); // to clear the textfield
                r.setBottom(root1); // make the botton in boderpane root1
                    });
            e1.getChildren().addAll(Search, ok);    // add the element to the pane
            r.setBottom(e1);    // make the botton in boderpane r
            r.setPadding(new Insets(20));   // make a pading for porderpane
            esrc(Search);   // to search for the element

            
        });
        // event in order buttin
        order.setOnAction(e-> {
            Order s = new Order();  // make a object form order class
            s.order();  // call the method order from order class
            connect();  // referh the database 
                });
        // wait if the user enter the close button and do not close the stage
        primaryStage.setOnCloseRequest(e-> {
            e.consume();
            close(primaryStage);
                });

    // add coulomns to the table
    table.setItems(item);
    table.getColumns().addAll(id, name, price, quntity);    //add the item to the satge table

    // labels
    // id lable
    Label idLable = new Label("ID  ");
    // name lable
    Label nameLable = new Label("Name  ");
    // price labele
    Label priceLable = new Label("Price  ");
    // quntoty lable
    Label quntityLable = new Label("Quntity  ");



    // borderPane
    BorderPane root5 = new BorderPane();
    root5.setCenter(table); // add table to the center of the boderpane
    table.setMaxWidth(400);
   
    // event in button
    // event in add button
    add.setOnAction(e->{

        // labels
        // id lable
        Label idL = new Label("ID");
        // name lable
        Label nameL = new Label("Name");
        // price label
        Label priceL= new Label("Price");
        // quntity lable
        Label quntityL = new Label("quntity");
       // textfield
       // id textfield
       TextField idT = new TextField();
       idT.setPromptText("Enter ID Number");
       idT.setMaxWidth(300);
       // name textfield
       TextField nameT = new TextField();
       nameT.setPromptText("Enter Name");
       nameT.setMaxWidth(300);
       // price textfield
       TextField priceT = new TextField();
       priceT.setPromptText("Enter Price");
       priceT.setMaxWidth(300);
       // quntity textfield
       TextField quntityT = new TextField();
       quntityT.setPromptText("Enter Quntity");
       quntityT.setMaxWidth(300);
       // buttons
       // add button
       Button addB = new Button("ADD");
       // cancle button
       Button cancelB = new Button("Cancel");

        addB.setOnAction(e1-> {

            try{
           AddItem.addToData(idT.getText(), nameT.getText(), priceT.getText(), quntityT.getText());
           connect(); 
           idT.clear();
           nameT.clear();
           quntityT.clear();
           priceT.clear();
            }
            catch(Exception f){
  
       
              Alert alert = new Alert(Alert.AlertType.WARNING);   
     
                // alert.getDialogPane();
                alert.getDialogPane().setStyle("-fx-border-color: black; -fx-cursor: hand; -fx-border-width: 5; -fx-border-radius:10;-fx-font-family: \"Arial Black\";"
                        + ""
                        + " -fx-padding: 0.5em 0.5em 0.5em 0.5em;\n" +
                        "   -fx-background-color:tomato;\n" +
                        "   -fx-text-fill:black;\n" 
                        ); alert.initStyle(StageStyle.UNDECORATED);
                
                // alert.getDialogPane().getScene().setFill(Color.GREEN);
                alert.setTitle("WARNING");
                alert.setHeaderText("You must Enter All data as :\n"+
               "Id must be number,\n"+
               "Name   as  you  like,\n"+
               "Price   must  be  number,\n "+
               "quantity   must  be   number.\n"         
                       
              );

       Optional<ButtonType> result = alert.showAndWait();
 
       if (result.get() == ButtonType.OK){
            alert.close();
   
       } else {
           
      alert .setOnCloseRequest(a-> {
              System.out.println(f.getMessage());
              alert
              .close();});
            
 
  
        }
            }
        });
        // event in cancel button
        cancelB.setOnAction(e2->r.setCenter(root8));
       GridPane t = new GridPane();
       t.add(idL, 0, 0);    // add id lable
       t.add(nameL, 0, 1);  // add name labe
       t.add(priceL, 0, 2); // add price lable
       t.add(quntityL, 0, 3);   // add quntity lable

       t.add(idT, 1, 0);    // add id textfield
       t.add(nameT, 1, 1);  // add name textfield
       t.add(priceT, 1, 2); // add price textfield
       t.add(quntityT, 1, 3);   // add quntity textfield

       t.add(addB, 0, 4);   // add add button
       t.add(cancelB, 1, 4);    // add cancel button

       t.setPadding(new Insets(30));    // make padding for gridpane
       t.setVgap(20);
       t.setHgap(20);

       r.setCenter(t);  // add the gridpane to the center of borderpane
    });
    

    Label productInfo = new Label("Product Information");
    productInfo.setId("k");
    HBox HBox1 = new HBox(10);
    HBox1.getChildren().add(productInfo);   // add the productInfo label to the HBox1 pane
    HBox1.setAlignment(Pos.CENTER); //  set the HBox1 in the center

    // gridPane
    root8.setPadding(new Insets(20));
    root8.setVgap(20);
    root8.setHgap(20);

    // add the item to the gridpane
    root8.add(idLable, 0, 0);
    root8.add(nameLable, 0, 1);
    root8.add(priceLable, 0, 2);
    root8.add(quntityLable, 0, 3);

    root8.add(il, 1, 0);
    root8.add(nl, 1, 1);
    root8.add(pl, 1, 2);
    root8.add(ql, 1, 3);

    
    VBox x1;
    x1 = new VBox(10);
    x1.getChildren().addAll(add, del);  // add the item to the VBox  x1
    
    VBox x3 = new VBox(10);
    x3.getChildren().addAll(show);  // add the item to the VBox  x3
    x3.setAlignment(Pos.CENTER);
    
    VBox x2;
    x2 = new VBox(10);
    x2.getChildren().addAll(order, search); // add the item to the VBox  x2
    // box pane
    
    // hbox pane
    VBox root = new VBox();
    root.getChildren().addAll(root8, root1);
    r.setCenter(root8);
    r.setBottom(root1);
    r.setTop(HBox1);

    // add Padding
    root1.setPadding(new Insets(10, 10, 10, 10));
    // add Children to hbox
    root1.getChildren().addAll(x1, x2, x3);
    root1.setMinWidth(200);
    root5.setRight(r);
    // add Children to vbox


    // scene
    Scene scene = new Scene(root5, 600, 400);

    // add Css file
    scene.getStylesheets().add(ApplicationApp.class.getResource("App.css").toExternalForm());
    // stage
    primaryStage.getIcons().add(new Image(ApplicationApp.class.getResourceAsStream( "logo.png" ))); // to chang logo
    primaryStage.setTitle("My first app");
    primaryStage.setScene(scene);
    primaryStage.show();


 }
      
    // search in dataabse
    private void search(int y){
        
        try{

            Connection con = null;
            con = DriverManager.getConnection("jdbc:sqlite:product.db");    // conect to database
            Statement stmt  = con.createStatement();
            ResultSet rs    = stmt.executeQuery("SELECT * FROM products WHERE id = "+y);    // git all thing in the database
            il.setText(Integer.toString(rs.getInt("id")));
            nl.setText(rs.getString("name"));
            pl.setText(Float.toString(rs.getFloat("price")));
            ql.setText(Integer.toString(rs.getInt("quntity")));
            con.close();
            stmt.close();
            rs.close();
        
        }
        catch(Exception e1){
            System.out.println(e1.getMessage());
            
        }
        
        
    }
    // Method to delete item
    public void delereItem(int e){
        
     try{
        Connection con = null;
        con = DriverManager.getConnection("jdbc:sqlite:product.db");
        Statement stmt  = con.createStatement();
        ResultSet rs    = stmt.executeQuery("DELETE FROM products WHERE id = "+e);
        con.close();
        stmt.close();
        rs.close();
        
        } catch(Exception e1){
            System.out.println(e1.getMessage());
        }
        
    }
    
    // method to search of an item
    private void esrc(TextField Search){
       
        FilteredList<Product>filter=new FilteredList<>(item,e->true);
        Search.setOnKeyReleased(e->{
         Search.textProperty().addListener((ObservableValue,OldValue,newValue )->{
         filter.setPredicate((Predicate < ? super Product > )Product->{
              String lowercaseFilter = newValue.toLowerCase();
         if((Integer.toString(Product.getId()).indexOf(newValue) !=-1)||newValue ==null || newValue.isEmpty()){
         return true ;  
         }
       
       if(Product.getName().toLowerCase().contains( lowercaseFilter)){
         return true ;
        
        }
         return false ;        
        }); 
        });       
        SortedList<Product> sort =new SortedList<> (filter);
        sort.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sort );
        });
     
    }
    
    public void sinIn(){
         window5.setTitle("Login Page");
        GridPane g=new GridPane();  
        g.setId("q");
        Image se1 = new Image(ApplicationApp.class.getResourceAsStream("people.png"));
        Label l=new Label ("user name  ", new ImageView(se1));
         l .setFont(new Font("Arial", 15)); 
        l.setTextFill(Color.rgb(255,255,0));
        Image se2 = new Image(ApplicationApp.class.getResourceAsStream("log_in.png"));
        Label p=new Label ("Password", new ImageView(se2));
        p .setFont(new Font("Arial", 15)); 
        p.setTextFill(Color.rgb(255,255,0));  g.setConstraints(l, 0, 2);
        g.setConstraints(p, 0, 4);                 

        TextField inputname;
        inputname  =new TextField();
        g.setConstraints(inputname, 1,2);      
        Label  massage;
        massage  =new Label ("Welcome to my Programe");
        massage.setTextFill(Color.rgb(255, 128, 0));
        massage .setFont(new Font("Arial", 17));                 
        g.setConstraints(massage, 1,0);              
        PasswordField pass = new PasswordField(); 

        g.setConstraints(pass, 1,4);
        g.setPadding(new Insets(20,20,20,20));
         Button btn5 = new Button("login");
        g.setConstraints(btn5, 1,6);
        Label  space1;
        space1  =new Label (" ");
        g.setConstraints(space1, 1,1);

        Label  space2;
        space2  =new Label (" ");
        g.setConstraints(space2, 1,3);                 
        Label  space;
        space  =new Label (" ");
        g.setConstraints(space, 1,5);

            btn5.setOnAction(e->{;
            LogIn(inputname,pass);
        });
            
     window5.setOnCloseRequest(e->{
                     
        close(window5);
        e.consume();
                 
    });
    g.getChildren().addAll(btn5 ,pass,inputname,l,p,massage,space,space1,space2);
 
    g.setAlignment(Pos.CENTER );
    Scene scen4 = new Scene( g,450,250);
             // add Css file
    scen4.getStylesheets().add(ApplicationApp.class.getResource("App.css").toExternalForm());
    window5.getIcons().add(new Image(ApplicationApp.class.getResourceAsStream( "login.png" ))); 
    window5.setScene(scen4);
    window5.show();
    
    } 
}

