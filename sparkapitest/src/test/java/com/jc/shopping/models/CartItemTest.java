/*
 */
package com.jc.shopping.models;

import com.jc.shopping.models.CartItem;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for CartItem class
 * @author Jack L. Clements
 */
public class CartItemTest {
    /**
     * Test of addQuantity method, of class CartItem.
     */
    @Test
    public void testAddQuantity() {
        //Given
        CartItem item = new CartItem("004", "Bread", 205, 1);
        
        //When
        item.addQuantity(3);
        
        //Then
        assertEquals(4, item.getQuantity());
    }
    
    /**
     * Test of cost to string (currency) format
     */
    @Test
    public void testGetCostStr() {
        //Given
        CartItem item = new CartItem("004", "Bread", 205, 1);
        
        //When
        //n/a
        
        //Then
        assertEquals("£2.05", item.getCostStr());
    }
    
}
