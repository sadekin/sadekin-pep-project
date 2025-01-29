package Controller;

import Model.Account;
import Service.AccountService;
import Service.MessageService;
import Model.Message; 
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService(); 
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.get("example-endpoint", this::exampleHandler);
        
        app.post("/register", this::postRegisterHandler); 
        app.post("/login", this::postLoginHandler); 
        app.post("/messages", this::postMessagesHandler); 
        app.get("/messages", this::getAllMessages); 
        app.get("/messages/{message_id}", this::getMessageByID); 
        app.delete("/messages/{message_id}", this::deleteMessageByID); 
        app.patch("/messages/{message_id}", this::updateMessageTextByID); 
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountID); 

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handles new user registration. 
     * @param ctx
     * @throws JsonProcessingException
     */
    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); 
        String jsonString = ctx.body(); 
        Account account = mapper.readValue(jsonString, Account.class); 
        Account addedAccount = accountService.addAccount(account); 
        if (addedAccount == null) {
            ctx.status(400); 
        } else {
            ctx.json(mapper.writeValueAsString(addedAccount)); 
        }
    }

    /**
     * Handlers user login.
     * @param ctx
     * @throws JsonProcessingException
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); 
        String jsonString = ctx.body(); 
        Account account = mapper.readValue(jsonString, Account.class); 
        Account loggedInAccount = accountService.loginToAccount(account); 
        if (loggedInAccount == null) {
            ctx.status(401); 
        } else {
            ctx.json(mapper.writeValueAsString(loggedInAccount)); 
        }

    }

    /**
     * Handles posting new messages. 
     * @param ctx
     * @throws JsonProcessingException
     */
    private void postMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); 
        String jsonString = ctx.body(); 
        Message message = mapper.readValue(jsonString, Message.class); 
        Message createdMessage = messageService.createMessage(message); 
        if (createdMessage == null) {
            ctx.status(400); 
        } else {
            ctx.json(mapper.writeValueAsString(createdMessage)); 
        }
    }

    /**
     * Handles retrieval of all existing messages. 
     */
    private void getAllMessages(Context ctx) {
        ctx.json(messageService.getAllMessages()); 
    }

    /**
     * Handles retrieval of a message by its ID. 
     * @param ctx
     */
    private void getMessageByID(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id")); 
        Message message = messageService.getMessageByID(id);
        if (message != null) { // notFound test only passes with null check
            ctx.json(message); 
        }
    }
    
    /**
     * Handles deletion of a message by its ID. 
     * @param ctx
     */
    private void deleteMessageByID(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id")); 
        Message deletedMessage = messageService.deleteMessageByID(id); 
        if (deletedMessage != null) {
            ctx.json(deletedMessage); 
        }
    }

    /**
     * Handles updates to an existing message's content. 
     * @param ctx
     */
    private void updateMessageTextByID(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); 
        String jsonString = ctx.body(); 
        Message message = mapper.readValue(jsonString, Message.class); 
        String newMessageText = message.getMessage_text(); 
        int id = Integer.parseInt(ctx.pathParam("message_id")); 
        Message updatedMessage = messageService.updateMessageTextByID(id, newMessageText); 
        if (updatedMessage == null) {
            ctx.status(400); 
        } else {
            ctx.json(updatedMessage); 
        }
    }

    /**
     * Handles retrieval of a particular user's messages. 
     * @param ctx
     */
    public void getMessagesByAccountID(Context ctx) {
        int accountID = Integer.parseInt(ctx.pathParam("account_id")); 
        ctx.json(messageService.getMessagesByAccountID(accountID)); 
    }
}