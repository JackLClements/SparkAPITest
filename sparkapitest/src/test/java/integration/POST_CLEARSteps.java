/*
 */
package integration;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import cart.ApplicationMain;
import models.CartItem;
import models.Cart;
import cucumber.api.java8.En;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 * Integration steps for the /clear POST command
 * @author Jack L. Clements
 */
public class POST_CLEARSteps implements En{
     private int port;
     
      public POST_CLEARSteps() {

        Before(() -> {
            port = ApplicationMain.startServer();
        });

        After(() -> {
            ApplicationMain.stopServer();
            //using template code, doesn't adequately wait for shutdown, but this makes do
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Given("^a POST /add request is sent to http://localhost:(\\d+)/cart/add followed by a POST /clear", (Integer amb1) -> {
            try {
                // Write code here that turns the phrase above into concrete actions
                Gson gson = new Gson();
                String json = gson.toJson(new CartItem("201a", "Detergent", 520, 2));
                HttpResponse<String> health = Unirest.post("http://localhost:" + port + "/cart/add").header("accept", "application/json").body(json).asString();
                health = Unirest.post("http://localhost:" + port + "/cart/clear").header("accept", "application/json").asString();
            } catch (UnirestException ex) {
                Logger.getLogger(POST_ADDSteps.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Then("^the cart should be empty", () -> {
            HttpResponse<String> result;
            try {
                result = Unirest.get("http://localhost:" + port + "/cart/contents").asString();
            } catch (UnirestException e) {
                result = null;
            }
            Cart cart = new Gson().fromJson(result.getBody(), Cart.class);
            //Assertations
            assertNotNull(result);
            assertEquals(0, cart.getSubtotal());
            assertEquals(true, cart.getCart().isEmpty());
        });

    }
}
