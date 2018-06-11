package models;

import models.Cart;
import models.CartItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Unit tests for the Cart class
 * @author Jack L. Clements & MOO
 */
class DummyCartTest {
    
    /**
     * Test that the cart ID is stored correctly
     */
    @Test
    void shouldStoreCartId() {
        // Given

        // When
        Cart cart = new Cart("ABC");

        // Then
        assertEquals("ABC", cart.getId());
    }
    
    /**
     * Test calculating the subtotal given an item
     */
    @Test //test for addition
    void calculateSubtotal1(){
        //Given
        CartItem item = new CartItem("002", "Detergent", 50, 2);
        
        //When
        Cart cart = new Cart("ABC");
        cart.addItem(item);
        
        //Then
        assertEquals(100, cart.getSubtotal());
    }
    
    /**
     * Test multiple of the same request firing, and incorrect requests
     */
    @Test //test for non-addition
    void calculateSubtotal2(){
        //Given
        CartItem item = new CartItem("001", "Biscuits", 510, 3);
        CartItem item2 = new CartItem("001", "Biscuits", 510, 0);
        CartItem item3 = new CartItem("001", "Biscuits", 510, -2);
        
        //When
        Cart cart = new Cart("ABC");
        cart.addItem(item); //use same memory location in order to test re-firing errors
        cart.addItem(item);
        cart.addItem(item);
        cart.addItem(item2);
        cart.addItem(item3);
        //Then
        assertEquals(4590, cart.getSubtotal());
    }
    
    /**
     * Test emptying the cart
     */
    @Test
    void emptyCart(){
        //Given
        CartItem item = new CartItem("001", "Biscuits", 510, 3);
        //When
        Cart cart = new Cart("ABC");
        cart.addItem(item);
        
        //Then
        cart.emptyCart();
        assertTrue(cart.isEmpty());
    }
    
    
    
    
}