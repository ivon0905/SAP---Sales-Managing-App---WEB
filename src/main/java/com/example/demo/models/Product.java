package com.example.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "Code")
    private int code;
    @Column(name = "Type")
    private String type;
    @Column(name = "Brand")
    private String brand;
    @Column(name = "Color")
    private String color;
    @Column(name = "Price")
    private double price;
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "Section")
    private String section;

    public Product(){

    }

    public Product(int id, int code, String type, String brand, String color, double price, int quantity, String section) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.brand = brand;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
        this.section = section;
    }

    public int getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
