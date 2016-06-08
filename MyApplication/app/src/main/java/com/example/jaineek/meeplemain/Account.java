package com.example.jaineek.meeplemain;
import android.content.Context;
import android.widget.Toast;

import java.util.List;

/**
 * @author Jaineek Parikh
 * @author Ananth Kuchibhotla
 * @author Rohan Upponi
 */

public class Account {

    private String username;
    private String password;
    private String email;
    private List<Integer> userPosts;   //TODO: consider using Stack instead

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
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
        assert email.contains("@"); //TODO: make a better email check
        this.email = email;
    }

    /*
    public static void main(String[] args) {
        Account exampleAccount = new Account("ananth", "ananth");
        exampleAccount.setEmail("ananth.com");
    } */
}
