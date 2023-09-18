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

    public Message deleteMessage(int message) {
        return messageDAO.deleteMessage(message);
    }
}
