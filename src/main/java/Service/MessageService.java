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
}
