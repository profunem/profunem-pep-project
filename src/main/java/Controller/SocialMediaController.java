package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    public SocialMediaController()
    {
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
        
        //Account endpoints
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::loginAccountHandler);

        //Message endpoints
        app.post("/messages", this::postNewMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountIDHandler);

        //Testing endpoints
        app.get("/users", this::getAllAccountsHandler);

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
     * Handler to register new user.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin. It will
     *            be available to this method automatically thanks to the app.post method.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object.
     */
    private void registerAccountHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
    }
    
    private void loginAccountHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.loginAccount(account);
        if(loginAccount==null){
            ctx.status(401);
        }else{
            ctx.json(mapper.writeValueAsString(loginAccount));
        }
    } 

    private void postNewMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedMessage));
        }
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException
    {
        ctx.json(messageService.getAllMessages());
    } 

    private void getMessageByIDHandler(Context ctx) throws JsonProcessingException
    {
        Message message = messageService.getMessageById(Integer.valueOf(ctx.pathParam("message_id")));
        if(message != null)
        {
            ctx.json(message);
        }        
    } 

    private void deleteMessageByIDHandler(Context ctx) throws JsonProcessingException
    {
        Message message = messageService.deleteMessageById(Integer.valueOf(ctx.pathParam("message_id")));
        if(message != null)
        {
            ctx.json(message);
        }   
    } 

    private void updateMessageByIDHandler(Context ctx) throws JsonProcessingException
    {

    } 

    private void getAllMessagesByAccountIDHandler(Context ctx) throws JsonProcessingException
    {

    } 

    private void getAllAccountsHandler(Context ctx)
    {
        ctx.json(accountService.getAllAccounts());
    } 


}