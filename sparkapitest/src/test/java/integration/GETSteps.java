package integration;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import cart.ApplicationMain;
import models.Cart;
import cucumber.api.java8.En;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for the /contents GET command
 * @author Jack L. Clements
 */
public class GETSteps implements En {

    private int port;

    public GETSteps() {

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

        Given("^a GET request is sent to http://localhost:(\\d+)/cart/contents$", (Integer port) -> {
            //Get request sent in Then, Given just a formal description
        });

        Then("^the cart should have a subtotal of (\\d+) and an empty list$", (Integer subtotal) -> {
            HttpResponse<String> result;
            try {
                result = Unirest.get("http://localhost:" + port + "/cart/contents").asString();
            } catch (UnirestException e) {
                result = null;
            }
            System.out.println(result.getBody());
            assertNotNull(result);
            Cart cart = new Gson().fromJson(result.getBody(), Cart.class);
            
            //assertations
            assertEquals((int) subtotal, cart.getSubtotal());
            assertEquals(true, cart.isEmpty());
        });

    }
}
