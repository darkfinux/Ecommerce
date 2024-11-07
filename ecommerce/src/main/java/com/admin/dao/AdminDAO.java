package com.admin.dao;

import java.sql.*;

import com.admin.model.Admin;
import com.product.dao.ProductDAO;
import com.product.model.Product;

public class AdminDAO {
	private String jdbcURL="jdbc:mysql://localhost:3306/ecommerce_db";
	private String jdbcUserName="root";
	private String jdbcPassword="yash2801";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
    }

    // Method to add an admin
    public boolean addAdmin(Admin admin) {
        String sql = "INSERT INTO admins (username, password) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, admin.getUsername());
            statement.setString(2, admin.getPassword());
            return statement.executeUpdate() > 0; // Returns true if the admin was added
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Error occurred
    }

    // Method to get an admin by username
    public Admin getAdminByUsername(String username) {
        String sql = "SELECT * FROM admins WHERE username = ?";
        Admin admin = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                admin = new Admin(
                        
                        resultSet.getString("username"),
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    // Method to validate admin credentials
    public boolean validateAdmin(String username, String password) {
        Admin admin = getAdminByUsername(username);
        return admin != null && admin.getPassword().equals(password);
    }
    public static void main(String[] args) throws SQLException {
        AdminDAO dao = new AdminDAO();
        if (dao.getConnection() != null) {
            System.out.println("Successfully connected to the database!!");
        } else {
            System.out.println("Problem in database connection!!");
        }
        Admin prod = new Admin("Yash","12yas" );
        dao.addAdmin(prod);
}
}