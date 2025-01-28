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
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)"; 

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch()); 
            preparedStatement.executeUpdate(); 

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
            String sql = "SELECT * FROM message"; 

            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
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
            String sql = "SELECT * FROM message WHERE message_id = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            preparedStatement.setInt(1, id);

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
}
