package com.example.jaineek.meeplemain;
import android.content.Context;
import android.widget.Toast;

import java.util.List;

/**
 * @author Jaineek Parikh
 * @author Johnny Test
 * @author Ananth Kuchibhotla
 * @author Rohan Upponi
 */

public class Account {

    private static Context context;
    private String username;
    private String password;
    private String email;
    private List<Integer> userPosts;   //TODO: consider using Stack instead

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // set the Account context to display Toasts on screen.
    // this is called in MeepleMain onCreate to access MeepleMain activity context
    public static void setContext(Context mainContext) {
        context = mainContext;
    }

    /*GETTERS AND SETTERS*/

    public String getUsername() {
        return username;
    }

    //TODO: add security layer for getting/changing password
    public String getPassword() {
        return password;
    }

    public String getEmail(){
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        try {
            assert email.contains("@"); //TODO: make a better email check
            this.email = email;
        } catch (AssertionError e) {
            // makes popup message if invalid email is inputted
            Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    public static void main(String[] args) {
        Account exampleAccount = new Account("ananth", "ananth");
        exampleAccount.setEmail("ananth.com");
    } */
}
