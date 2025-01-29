package DAO;

import Model.Account; 
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {
    
    /**
     * Adds new account to database. 
     * @param account the account to add.
     * @return the account's Account object representation with its generated key or null if insertion unsuccessful.
     */
    public Account addAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection(); 

        try {
            // insert new account
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
            preparedStatement.setString(1, account.getUsername()); 
            preparedStatement.setString(2, account.getPassword()); 
            preparedStatement.executeUpdate(); 

            // return Account object with its generated PK (successful registration)
            ResultSet pkResultSet = preparedStatement.getGeneratedKeys(); 
            if (pkResultSet.next()) {
                int generatedAccountID = (int) pkResultSet.getLong(1); 
                return new Account(
                    generatedAccountID, 
                    account.getUsername(),
                    account.getPassword()
                ); 
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        }

        // unsuccessul registration 
        return null; 
    }

    /** 
     * Gets an account by username. Helper function for ensuring that we 
     * don't create duplicate accounts during registration.
     * @param username 
     * @return the Account object with the already existing username or null if not found.
     */
    public Account getAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            // select account by username
            String sql = "SELECT * FROM account WHERE usernamae = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setString(1, username); 

            // return the Account object (username already exists)
            ResultSet resultSet = preparedStatement.executeQuery(); 
            while (resultSet.next()) {
                return new Account(
                    resultSet.getInt("account_id"), 
                    resultSet.getString("username"), 
                    resultSet.getString("password")
                ); 
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        }

        // username does not exist
        return null; 
    }
    
    /**
     * Simulates logging into an account by checking if the username and password combination exists in the database.
     * @param account object with provided username and password. 
     * @return the Account object if login successful, else null.
     */
    public Account loginToAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection(); 

        try {
            // select account by username and ID 
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword()); 

            // return the account if found (successful login)
            ResultSet resultSet = preparedStatement.executeQuery(); 
            while (resultSet.next()) {
                return new Account(
                    resultSet.getInt("account_id"), 
                    resultSet.getString("username"), 
                    resultSet.getString("password")
                ); 
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        }

        // unsuccessful login
        return null; 
    }

    /**
     * Looks for an account via a given account ID.
     * @param id 
     * @return the Account object if found, else null.
     */
    public Account getAccountByID(int id) {
        Connection connection = ConnectionUtil.getConnection(); 

        try {
            // look for the account by ID
            String sql = "SELECT * FROM account WHERE account_id = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setInt(1, id);

            // return the successfuly found account if it exists
            ResultSet resultSet = preparedStatement.executeQuery(); 
            while (resultSet.next()) {
                return new Account(
                    id, 
                    resultSet.getString("username"), 
                    resultSet.getString("password")
                ); 
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        }
        
        // unsuccessful search
        return null; 
    }
}
