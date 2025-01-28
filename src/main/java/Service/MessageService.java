package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List; 

public class MessageService {
    MessageDAO messageDAO; 

    public MessageService() {
        messageDAO = new MessageDAO(); 
    }

    public Message createMessage(Message message) {
        return messageDAO.createMessage(message); 
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages(); 
    }

    public Message getMessageByID(int id) {
        return messageDAO.getMessageByID(id); 
    }

    public Message deleteMessageByID(int id) {
        Message messageToDelete = messageDAO.getMessageByID(id); 
        if (messageToDelete != null) {
            messageDAO.deleteMessageByID(id); 
        }
        return messageToDelete; 
    }

    public Message updateMessageTextByID(int id, String text) {
        Message messageToUpdate = messageDAO.getMessageByID(id); 
        if (messageToUpdate != null) {
            messageToUpdate = messageDAO.updateMessageTextByID(id, text); 
        }
        return messageToUpdate; 
    }

    public List<Message> getMessagesByAccountID(int accountID) {
        return messageDAO.getMessagesByAccountID(accountID); 
    }
}
