package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService()
    {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public Message addMessage(Message message)
    {
        String messageText = message.getMessage_text();
        int postBy = message.getPosted_by();

        if(messageText.isEmpty() || messageText.length() < 255 || accountDAO.getAccountById(postBy) == null)
        {
            return null;
        }

        return messageDAO.insertMessage(message);
    }
    
}
