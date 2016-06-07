package com.example.jaineek.meeplemain;
import java.util.List;

/**
 * @author Jaineek Parikh
 * @author Johnny Test
 * @author Ananth Kuchibhotla
 * @author Rohan Upponi
 */

public class Account {

    private String username;
    private String password;
    private List<Integer> userPosts;   //TODO: consider using Stack instead

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    //TODO: add security layer for getting/changing password
    public String getPassword() {
        return password;
    }
}
