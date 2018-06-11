package cart;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import models.CartItem;
import models.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.ResponseTransformer;
import spark.Spark;
import java.util.Collections;

import static spark.Spark.before;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.path;

public class ApplicationMain {
    //Setup commonly used variables/objects
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationMain.class);
    private static final Gson GSON = new Gson();
    private static final ResponseTransformer JSON_TRANSFORMER = GSON::toJson;
    private static final String JSON = "application/json";

    //Use singular static obj in place of database calls. 
    //I chose to use this over a multi-user handler as I thought it would only be implementing a pseudo-DB and needless for this task
    private static Cart cart = new Cart("001");

    public static void main(String[] Args) {
        Spark.port(4567);
        startServer();
    }
    
    /**
     * Begins the Spark instance, calls initializeRoutes and writes to log
     * @return 
     */
    public static int startServer() {
        Spark.init();
        initializeRoutes();

        exception(JsonSyntaxException.class, ApplicationMain::handleInvalidInput);
        LOG.debug("Created exception handlers");

        Spark.awaitInitialization();
        LOG.debug("Ready");
        return Spark.port();
    }
    
    /**
     * Maps the REST API routes to network paths, and defines behaviour
     */
    private static void initializeRoutes() {
        // Set response type to always be JSON  
        before((request, response) -> response.type(JSON));

        path("/health", () -> get("", (req, res) -> "healthy"));

        path("/cart", () -> {
            get("/contents", (req, res) -> cart, JSON_TRANSFORMER); //return the contents
            post("/add", (req, res) -> { //add an item to the cart
                res.type("application/json");
                CartItem item = new Gson().fromJson(req.body(), CartItem.class);
                cart.addItem(item);
                return cart;
            }, JSON_TRANSFORMER);
            post("/remove", (req, res) -> {
                res.type("application/json");
                 //controller should return an item ID and int set only
                 //should there be a better way to do this with just the Item obj?
                //ToRemove removal = new Gson().fromJson(req.body(), ToRemove.class);
                CartItem item = new Gson().fromJson(req.body(), CartItem.class);
                if(item.isAll()){                   
                    cart.removeItem(item);
                }
                else{
                    cart.removeItem(item, item.getQuantity()); //change this to be entirely values and switch on method in cart class
                }
                return cart;
            }, JSON_TRANSFORMER);
            post("/clear", (req, res) -> { //empty the cart of all contents
                res.type("application/json");
                cart.emptyCart();
                return cart;
            }, JSON_TRANSFORMER);
        });
        LOG.debug("Initialised routes");
    }
    
    /**
     * Handles a 404 error
     * @param e the exception
     * @param request request payload
     * @param response response payload
     */
    private static void handleInvalidInput(Exception e, Request request, Response response) {
        response.status(400);
        errorResponse(e, request, response);
    }
    
     /**
     * Handles a specific Java exception/error
     * @param e the exception
     * @param request request payload
     * @param response response payload
     */
    private static void errorResponse(Exception e, Request request, Response response) {
        response.type(JSON);
        response.body(GSON.toJson(Collections.singletonMap("error", e.getMessage())));
    }

    /**
     * For testing, as we want to start and stop the server.
     */
    public static void stopServer() {
        LOG.debug("Asking server to stop");
        Spark.stop();
    }

}
