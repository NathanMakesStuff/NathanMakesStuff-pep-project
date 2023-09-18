package Service;

import java.sql.SQLException;
import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }
    
    public Message createMessage(Message message) {
        return messageDAO.createMessage(message);
    }

    public Message deleteMessage(int messageID) {
        return messageDAO.deleteMessage(messageID);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessage(int messageID) {
        return messageDAO.getMessageByID(messageID);
    }

    public List<Message> getAllMessagesFromUser(int account_id) {
        return messageDAO.getAllMessagesFromUser(account_id);
    }

    public Message updateMessage(int message_id, String newText) {
        return messageDAO.updateMessageByID(message_id, newText);
    }
}
