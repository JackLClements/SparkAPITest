package com.jc.shopping.models;

/**
 * Object representing an item in a cart, includes the no. of items in the purchase
 * @author Jack L. Clements
 */
public class CartItem {

    //Fields
    private String serial;
    private String name; //plaintext name
    private int cost; //in pence, avoid rounding errors w/ float
    private int quantity;
    private boolean all;
    
    /**
     * Default constructor
     */
    public CartItem() {
        this.serial = "Placeholder";
        this.name = "Placeholder";
        this.cost = 0;
        this.quantity = 0;
        this.all = false;
    }
    
    
    /**
     * Values-based constructor
     * @param serial
     * @param name
     * @param cost
     * @param quantity 
     */
    public CartItem(String serial, String name, int cost, int quantity) {
        this.serial = serial;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.all = false;
    }
    
    /**
     * Values-based constructor
     * @param serial
     * @param name
     * @param cost
     * @param quantity 
     * @param all
     */
    public CartItem(String serial, String name, int cost, int quantity, boolean all) {
        this.serial = serial;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.all = all;
    }
    
    /**
     * Deep copy constructor, for when one item is added multiple times, any methods called would
     * cumulatively modify the same memory location, calling for a deep copy to change that. 
     * @param item the item to copy
     */
    public CartItem(CartItem item){
        this.serial = item.getSerial();
        this.name = item.getName();
        this.cost = item.getCost();
        this.quantity = item.getQuantity();
        this.all = item.isAll();
    }
    
    /**
     * Add a quantity of items (positive only) to the item batch
     * @param quantity 
     */
    public void addQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity += quantity;
        }
    }
    
    /**
     * Remove a quantity of items (positive only) to the item batch
     * If you remove more than currently exist, item count resets to zero
     * @param quantity 
     */
    public void removeQuantity(int quantity){
        if(quantity > 0){
            this.quantity -= quantity;
            if(this.quantity < 0){
                this.quantity = 0;
            }
        }
    }

    //Accessor methods
    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public boolean isAll(){
        return this.all;
    }
    
    public void setAll(boolean all){
        this.all = all;
    }
    
    /**
     * Formats cost as currency
     * @return the currency value as a Str
     */
    public String getCostStr() {
        int pounds = cost / 100;
        int pence = cost % 100;
        String costStr = "£" + pounds + "." + pence;
        return costStr;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public boolean equals(Object obj){
        //compare memory
        if(obj == this){
            return true;
        }
        //check null/class mismatch
        if(obj == null || this.getClass() != obj.getClass()){
            return false;
        }
        //Cast and compare serials
        CartItem cObj = (CartItem) obj;
        return cObj.serial.equals(this.serial);
    }
    
    @Override
    public int hashCode(){
        //31 and 17 are not the "perfect" hash no. to avoid collisions, but given there should be no negative hash values, these odd primes should be fine
        int result = 17;
        result = 31 * result+serial.hashCode();
        result = 31 * result+name.hashCode();
        result = 31 * result+cost;
        result = 31 * result+quantity;
        return result;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": ").append(serial).append(" x").append(quantity).append(" at ");
        int pounds = cost / 100;
        int pence = cost % 100;
        sb.append("£").append(pounds).append(".").append(pence).append(" per item.");
        return sb.toString();
    }

}
