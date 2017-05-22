/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

/**
 *
 * @author AHMED GHALY
 */
public class Product {
    // class Product
    
    // prooerties
    private int id;
    private String name;
    private double price;
    private int quntity;
    
    // constructors
    public Product(){
        this.id = 0;
        this.name = "";
        this.price = 0.0;
        this.quntity = 0;
    }
    
    public Product(int id, String name, double price, int quntity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quntity = quntity;
    }

      // methods

    //    geter and seter mathod
    // getid
    public int getId() {
        return id;
    }
    //gitname
    public String getName() {
        return name;
    }
    //getprice
    public double getPrice() {
        return price;
    }
    //getquntity
    public int getQuntity() {
        return quntity;
    }
    //getid
    public void setId(int id) {
        this.id = id;
    }
    //setname
    public void setName(String name) {
        this.name = name;
    }
    //setprice
    public void setPeice(double price) {
        this.price = price;
    }
    //setquntity
    public void setQuntity(int quntity) {
        this.quntity = quntity;
    }
    
}
