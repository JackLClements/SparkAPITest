/*
 */
package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Cart;

/**
 *
 * @author Jack L. Clements
 */
public class AccountManager {

    private final static String DBNAME = "exampleDB";
    private Connection con;

    //Only used in theory, as there is no DB link here.
    //Similarly, you should store passwords hashed, not plaintext.
    //A hashing function is on my to-do list.
    public Cart getUser(String username, String password) {
        Cart user = null;
        String statement = "SELECT * FROM " + DBNAME + "WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(statement);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            user = new Cart();
            user.setId(rs.getString("username"));
            //think about how your database is designed here, as you may need multiple calls for cart items
            
            //should autocommit
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

}
