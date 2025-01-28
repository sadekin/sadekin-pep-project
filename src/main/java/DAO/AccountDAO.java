package DAO;

import Model.Account; 
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList; 
import java.util.List; 

public class AccountDAO {
    
    public Account addAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection(); 

        try {
            // if (getAccountByUsername(account.getUsername()) == null) return null; 

            String sql = "INSERT INTO account (username, password) VALUES (?, ?)"; 

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
            preparedStatement.setString(1, account.getUsername()); 
            preparedStatement.setString(2, account.getPassword()); 
            preparedStatement.executeUpdate(); 

            ResultSet pkResultSet = preparedStatement.getGeneratedKeys(); 
            if (pkResultSet.next()) {
                int generatedAccountID = (int) pkResultSet.getLong(1); 
                return new Account(generatedAccountID, account.getUsername(), account.getPassword()); 
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        }

        return null; 
    }

    // public Account getAccountByUsername(String username) {
    //     Connection connection = ConnectionUtil.getConnection();

    //     try {
    //         String sql = "SELECT * FROM account WHERE usernamae = ?"; 

    //         PreparedStatement preparedStatement = connection.prepareStatement(sql); 
    //         preparedStatement.setString(1, username); 

    //         ResultSet resultSet = preparedStatement.executeQuery(); 
    //         while (resultSet.next()) {
    //             return new Account(
    //                 resultSet.getInt("account_id"), 
    //                 resultSet.getString("username"), 
    //                 resultSet.getString("password")
    //             ); 
    //         }

    //     } catch (SQLException e) {
    //         System.out.println(e.getMessage()); 
    //     }

    //     return null; 
    // }
    
    public Account loginAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection(); 

        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?"; 

            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword()); 

            ResultSet resultSet = preparedStatement.executeQuery(); 
            while (resultSet.next()) {
                return new Account(
                    (int) resultSet.getLong("account_id"), 
                    resultSet.getString("username"), 
                    resultSet.getString("password")
                ); 
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        }

        return null; 
    }
}
