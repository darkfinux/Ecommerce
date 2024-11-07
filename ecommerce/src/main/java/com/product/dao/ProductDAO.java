package com.product.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.product.model.Product;
import com.user.dao.UserDAO;
import com.user.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
	private String jdbcURL="jdbc:mysql://localhost:3306/ecommerce_db";
	private String jdbcUserName="root";
	private String jdbcPassword="yash2801";
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
    }

    // Method to add a product
    public boolean addProduct(Product product) {
        if (isProductExists(product.getName())) {
            System.out.println("Product with name '" + product.getName() + "' already exists.");
            return false; // Product already exists
        }

        String sql = "INSERT INTO products (name, description, price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getPrice()); // Changed to setInt for price
            statement.setInt(4, product.getQuantity());
            statement.executeUpdate();
            return true; // Product added successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error occurred
        }
    }

    // Method to check if a product exists by name
    private boolean isProductExists(String productName) {
        String sql = "SELECT COUNT(*) FROM products WHERE name = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, productName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Returns true if product exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Product does not exist
    }

    // Method to update a product
    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, quantity = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getPrice()); // Changed to setInt for price
            statement.setInt(4, product.getQuantity());
            statement.setInt(5, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a product
    public void deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get a product by ID
    public Product getProductById(int productId) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Product product = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                product = new Product(
                        
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("price"), // Changed to getInt for price
                        resultSet.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
    public static void main(String[] args) throws SQLException {
        ProductDAO dao = new ProductDAO();
        if (dao.getConnection() != null) {
            System.out.println("Successfully connected to the database!!");
        } else {
            System.out.println("Problem in database connection!!");
        }
        Product prod = new Product("test", "test@abc.com", 10,120);
        dao.addProduct(prod);
      
      

      
    }
    


}

               

