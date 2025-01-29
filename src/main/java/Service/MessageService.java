package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List; 

public class MessageService {
    MessageDAO messageDAO; 

    /**
     * No-args contructor for MessageService instantianes a plain MessageDAO.
     */
    public MessageService() {
        messageDAO = new MessageDAO(); 
    }

    /**
     * Uses MessageDAO to add a valid new message to the database.
     * @param message
     * @return created message or null if invalid message.
     */
    public Message createMessage(Message message) {
        // note that we do not need to explicitly check if the user referred to
        // by the Message object's posted_by field exists since it's a SQL error 
        // to try to add a foreign key that references a PK that doesn't exist.
        String messageText = message.getMessage_text(); 
        if (messageText.isEmpty() || messageText.length() > 255) 
            return null; 
        return messageDAO.createMessage(message); 
    }

    /**
     * Uses MessageDAO to return a list of all existing messages in the database. 
     * @return a list of all existing messages or an empty list if there are none. 
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages(); 
    }

    /**
     * Uses messageDAO to get a message by its ID from the database. 
     * @param id message_id
     * @return message with matching ID or null if the message doesn't exist.
     */
    public Message getMessageByID(int id) {
        return messageDAO.getMessageByID(id); 
    }

    /**
     * Uses messageDAO to delete a message by its ID from the database. 
     * @param id message_id
     * @return deleted message with matching ID or null if the message doesn't exist.
     */
    public Message deleteMessageByID(int id) {
        Message messageToDelete = messageDAO.getMessageByID(id); 
        if (messageToDelete != null) {
            messageDAO.deleteMessageByID(id); 
        }
        return messageToDelete; 
    }

    /**
     * Uses messageDAO to delete a message by its ID from the database. 
     * @param id message_id
     * @param text new message_text
     * @return updated message with matching ID and new text or null if the message doesn't exist.
     */
    public Message updateMessageTextByID(int id, String text) {
        if (text.isEmpty() || text.length() > 255 || messageDAO.getMessageByID(id) == null) 
            return null; 
        return messageDAO.updateMessageTextByID(id, text); 
    }

    /**
     * Uses messageDAO to retrieve all messages posted by the user with the matching account ID. 
     * @param accountID 
     * @return a list containing all the messages posted by the user or an empty list if they don't exist.
     */
    public List<Message> getMessagesByAccountID(int accountID) {
        return messageDAO.getMessagesByAccountID(accountID); 
    }
}
