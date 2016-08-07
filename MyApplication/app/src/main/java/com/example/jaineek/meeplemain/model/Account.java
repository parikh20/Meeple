package com.example.jaineek.meeplemain.model;

import java.util.List;

/**
 * @author Jaineek Parikh
 * @author Ananth Kuchibhotla
 */

public class Account {

    private String name;
    private String username;
    private String password;
    private String email;
    private List<Integer> userPosts;   //TODO: consider using Stack instead

    public Account(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /*GETTERS AND SETTERS*/

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
        this.email = email;
    }

    /*
    public static void main(String[] args) {
        Account exampleAccount = new Account("ananth", "ananth");
        exampleAccount.setEmail("ananth.com");
    } */
}
