package com.jc.shopping.models;

import java.util.ArrayList;

/**
 * Simple class representing a cart with an id, list of items w/ quantities and a subtotal value
 * @author Jack L. Clements
 */
public class Cart {

    private String id;
    private ArrayList<CartItem> cart; //wanted to use a hashmap, but memory constraints vs small time access
    private int subtotal;
    
    
    public Cart(){
        id = "";
        cart = new ArrayList<>();
        subtotal = 0;
    }
    
    /**
     * Regular constructor, intialises new cart with given id
     * @param id 
     */
    public Cart(String id) {
        this.id = id;
        this.cart = new ArrayList<>();
        this.subtotal = 0;
    }
    
    /**
     * Values-based constructor
     * @param id
     * @param cart
     * @param subtotal 
     */
    public Cart(String id, ArrayList<CartItem> cart, int subtotal){
        this.id = id;
        this.cart = cart;
        this.subtotal = subtotal;
    }
    
    /**
     * Goes through list of items and calculates the subtotal.
     * Not done when adding objects for time-complexity, but could equally be
     * done in this instance due to the average customer having a small basket
     */
    public void calculateSubtotal(){
        this.subtotal = 0;
        for(CartItem item : cart){
            this.subtotal += item.getCost()*item.getQuantity();
        }
    }
    
    /**
     * Adds a new item to the cart.
     * If the item is already in the cart, it adds a new quantity amount.
     * @param newItem the item to add
     * @return cart object for the API
     */
    public Cart addItem(CartItem newItem){
        boolean exists = false;
        for(CartItem item : cart){ //Check for serial no. equality
            if(item.getSerial().equals(newItem.getSerial())){
                exists = true;
                item.addQuantity(newItem.getQuantity()); //update item listing
            }
        }
        if(!exists){
            cart.add(new CartItem(newItem));
            //If a request with the same memory location fires multiple times in error, it will keep adding to its own quantity
            //A deep copy avoids this problem by seperating out the memory locations
        }
        //Torn between recalculating the price each time or by using extra memory and doing it on the fly
        if(newItem.getQuantity() > 0){
            this.subtotal += (newItem.getCost() * newItem.getQuantity());
        }
        //calculateSubtotal();
        return this;
    }
    
    /**
     * Removes all instances of an item from the cart
     * @param item the item to remove
     * @return cart object for API
     */
    public Cart removeItem(CartItem item){
        boolean found = false;
        cart.remove(item);
        return this;
    }
    
    /**
     * Removes specified no. of instance of item ID.
     * If total removed leaves the item at 0 units, it is removed.
     * @param item
     * @param quantity
     * @return cart object for API
     */
    public Cart removeItem(CartItem item, int quantity){
        if(cart.contains(item)){
            CartItem mItem = cart.get(cart.indexOf(item));
            mItem.removeQuantity(quantity);
            if(mItem.getQuantity() == 0){
                cart.remove(mItem);
            }
        }
        return this;
    }
    
    /**
     * Removes all items from the cart
     * @return cart obj. for the API
     */
    public Cart emptyCart(){
        this.subtotal = 0;
        cart.clear();
        return this;
    }
    
    /**
     * Accessor variant that checks if the cart is empty or not
     * @return 
     */
    public boolean isEmpty(){
        return this.cart.isEmpty();
    }
    
    //Accessor methods

    public String getId() {
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public ArrayList<CartItem> getCart(){
        return this.cart;
    }
    
    public void setCart(ArrayList<CartItem> cart){
        this.cart = cart;
    }
    
    public int getSubtotal(){
        return this.subtotal;
    }
    
    public void setSubtotal(int subtotal){
        this.subtotal = subtotal;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Cart with ID ").append(this.id).append(" with items: \n");
        for(CartItem item : cart){
            sb.append(item.getName()).append(" x").append(item.getQuantity()).append(" at ").append(item.getCostStr()).append(" per item.\n");
        }
        int cost = this.subtotal;
        int pounds = cost / 100;
        int pence = cost % 100;
        sb.append("Total: £").append(pounds).append(".").append(pence);
        return sb.toString();
    }
}
