package Controller;

import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

     public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.get("/accounts", this::accountsHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::messagesPostHandler);
        app.delete("/messages/{message_id}", this::deletionHandler);
        app.get("/messages", this::messagesGetAllHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUser);
        app.patch("/messages/{message_id}", this::messagesUpdateHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.register(account);
        if(addedAccount != null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }

    private void accountsHandler(Context ctx) {
        List<Account> accounts = accountService.getAllAccounts();
        ctx.json(accounts);
    }

    private void loginHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.login(account);
        if(addedAccount != null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(401);
        }
    }

    private void messagesPostHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        if(addedMessage != null) {
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }

    private void deletionHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String delete = ctx.pathParam("message_id");
        Integer messageid = Integer.parseInt(delete);
        Message deletedMessage = messageService.deleteMessage(messageid);
        if (deletedMessage != null) {
            ctx.json(mapper.writeValueAsString(deletedMessage));
        }else{
            // message does not exist/already deleted
            ctx.status(200); 
        }
        
    }

    private void messagesGetAllHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageByIDHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String messageID = ctx.pathParam("message_id");
        Integer messageid = Integer.parseInt(messageID);
        Message retrievedMessage = messageService.deleteMessage(messageid);
        if (retrievedMessage != null) {
            ctx.json(mapper.writeValueAsString(retrievedMessage));
        }else{
            // message does not exist
            ctx.status(200); 
        }
        
    }

    private void getAllMessagesFromUser(Context ctx) {
        String accountID = ctx.pathParam("account_id");
        Integer accountid = Integer.parseInt(accountID);
        List<Message> retrievedMessages = messageService.getAllMessagesFromUser(accountid);
        if (retrievedMessages != null) {
            ctx.json(retrievedMessages);
        }else{
            // there are no messages
            ctx.status(200); 
        }
    }

    private void messagesUpdateHandler(Context ctx) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        String messageID = ctx.pathParam("message_id");
        Integer messageid = Integer.parseInt(messageID);
        Message message = mapper.readValue(ctx.body(), Message.class);
        String newText = message.getMessage_text();
        Message updatedMessage = messageService.updateMessage(messageid, newText);
        if(updatedMessage != null) {
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }else{
            ctx.status(400);
        }
    }
}