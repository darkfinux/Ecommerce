package com.user.dao;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.product.dao.ProductDAO;
import com.user.model.User;
public class UserDAO {
	
	private String jdbcURL="jdbc:mysql://localhost:3306/ecommerce_db";
	private String jdbcUserName="root";
	private String jdbcPassword="yash2801";
	
    private static final String INSERT_USER_SQL = "INSERT INTO users (uname, email, country, passwd, role, address) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?;";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users;";
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE id = ?;";
    private static final String UPDATE_USER_SQL = "UPDATE users SET uname = ?, email = ?, country = ?, passwd = ?, role = ?, address = ? WHERE id = ?;";

    public UserDAO() {

}

    // Database connection
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    public void insertUser(User user) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getrole()); // Added role
            preparedStatement.setString(6, user.getaddress()); // Added address
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public User selectUser(int id) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setName(resultSet.getString("uname"));
                user.setEmail(resultSet.getString("email"));
                user.setCountry(resultSet.getString("country"));
                user.setPassword(resultSet.getString("passwd"));
                user.setrole(resultSet.getString("role")); // Added role
                user.setaddress(resultSet.getString("address")); // Added address
            } else {
                System.out.println("No user found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("uname"));
                user.setEmail(resultSet.getString("email"));
                user.setCountry(resultSet.getString("country"));
                user.setPassword(resultSet.getString("passwd"));
                user.setrole(resultSet.getString("role")); // Added role
                user.setaddress(resultSet.getString("address")); // Added address
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public boolean deleteUser(int id) {
        boolean status = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {
            preparedStatement.setInt(1, id);
            status = preparedStatement.executeUpdate() > 0; // Updated to return success status
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
    public boolean updateUser(User user) {
        boolean status = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getrole()); // Added role
            preparedStatement.setString(6, user.getaddress()); // Added address
            preparedStatement.setInt(7, user.getId()); // Added ID for the WHERE clause
            
            status = preparedStatement.executeUpdate() > 0; // Updated to return success status
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        if (dao.getConnection() != null) {
            System.out.println("Successfully connected to the database!!");
        } else {
            System.out.println("Problem in database connection!!");
        }
        User user = new User("test", "test@abc.com", "India", "abc@123", "customer", "123 Test St");
        dao.insertUser(user);
        User user1 = dao.selectUser(2);
        if (user1 != null) {
            System.out.println(user1);
        }
      

      
    }
}
  


    
    
 
