package com.product.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private int price;  // Changed from double to int
    private int quantity;

    // Constructor
    public Product( String name, String description, int price, int quantity) {
        
        this.name = name;
        this.description = description;
        this.price = price;  // Now an integer
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;  // Now returns an integer
    }

    public void setPrice(int price) {  // Now accepts an integer
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}