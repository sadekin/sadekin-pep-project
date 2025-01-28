package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList; 
import java.util.List;

public class MessageDAO {
    
    public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection(); 

        try {
            // insert new message
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch()); 
            preparedStatement.executeUpdate(); 

            // return inserted message
            ResultSet pkResultSet = preparedStatement.getGeneratedKeys(); 
            if (pkResultSet.next()) {
                int generatedMessageID = (int) pkResultSet.getLong("message_id"); 
                return new Message(
                    generatedMessageID, 
                    message.getPosted_by(), 
                    message.getMessage_text(), 
                    message.getTime_posted_epoch()
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
         
        return null; 
    }

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection(); 
        List<Message> messages = new ArrayList<>(); 

        try {
            // select all messages
            String sql = "SELECT * FROM message"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 

            // return them in a list
            ResultSet resultSet = preparedStatement.executeQuery();   
            while (resultSet.next()) {
                Message message = new Message(
                    (int) resultSet.getLong("message_id"), 
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"), 
                    resultSet.getLong("time_posted_epoch")
                );
                messages.add(message); 
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        }

        return messages; 
    }

    public Message getMessageByID(int id) {
        Connection connection = ConnectionUtil.getConnection(); 

        try {
            // select message by ID
            String sql = "SELECT * FROM message WHERE message_id = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setInt(1, id);

            // return the message
            ResultSet resultSet = preparedStatement.executeQuery(); 
            if (resultSet.next()) {
                return new Message(
                    id, 
                    resultSet.getInt("posted_by"), 
                    resultSet.getString("message_text"), 
                    resultSet.getLong("time_posted_epoch")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        }

        return null; 
    }

    public void deleteMessageByID(int id) {
        Connection connection = ConnectionUtil.getConnection(); 
        try {
            // straightforward
            String sql = "DELETE * FROM message WHERE message_id = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate(); 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Message updateMessageTextByID(int id, String text) {
        Connection connection = ConnectionUtil.getConnection(); 

        try {
            // update message
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setString(1, text);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate(); 

            // select newly updated message
            sql = "SELECT * FROM message WHERE message_id = ?"; 
            preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setInt(1, id);

            // ... and return it
            ResultSet resultSet = preparedStatement.executeQuery(); 
            if (resultSet.next()) {
                return new Message(
                    id, 
                    resultSet.getInt("posted_by"), 
                    text, 
                    resultSet.getLong("time_posted_epoch")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        }

        return null; 
    }

    public List<Message> getMessagesByAccountID(int account_id) {
        Connection connection = ConnectionUtil.getConnection(); 
        List<Message> messages = new ArrayList<>(); 

        try {
            // select messages by account ID
            String sql = "SELECT * FROM message WHERE posted_by = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setInt(1, account_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // ... and add them to the to-be-returned list
            while (resultSet.next()) {
                Message message = new Message(
                    resultSet.getInt("message_id"), 
                    account_id, 
                    resultSet.getString("message_text"), 
                    resultSet.getLong("time_posted_epoch")
                ); 
                messages.add(message); 
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        }

        return messages;
    }
}
