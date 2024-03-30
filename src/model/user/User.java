package model.user;

import model.account.Account;

import java.util.ArrayList;

public class User {
    private static int userIdCount = 0;
    private int userId;
    private String firstName;
    private String lastName;
    private String cnp;
    private String username;
    private String password;
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String cnp, String username, String password) {
        userIdCount ++;
        this.userId = userIdCount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.username = username;
        this.password = password;
        this.accounts = new ArrayList<Account>();
    }
}
