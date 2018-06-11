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
 * Integration tests for the /add POST command
 * @author Jack L. Clements
 */
public class POST_ADDSteps implements En {

    private int port;

    public POST_ADDSteps() {

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

        Given("^a POST request is sent to http://localhost:(\\d+)/cart/add$", (Integer amb1) -> {
            try {
                Gson gson = new Gson();
                String json = gson.toJson(new CartItem("201a", "Detergent", 520, 2));
                HttpResponse<String> health = Unirest.post("http://localhost:" + port + "/cart/add").header("accept", "application/json").body(json).asString();
            } catch (UnirestException ex) {
                Logger.getLogger(POST_ADDSteps.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Then("^the cart should have one item included with correct values", () -> {
            HttpResponse<String> result;
            try {
                result = Unirest.get("http://localhost:" + port + "/cart/contents").asString();
            } catch (UnirestException e) {
                result = null;
            }
            Cart cart = new Gson().fromJson(result.getBody(), Cart.class);
            CartItem item = cart.getCart().get(0);
            //assertations
            assertNotNull(result);
            assertEquals(1040, cart.getSubtotal());
            assertEquals("Detergent", item.getName());
            assertEquals("201a", item.getSerial());
            assertEquals(520, item.getCost());
            assertEquals(2, item.getQuantity());
        });

    }

}
