package model.user;

import model.account.Account;

import java.util.ArrayList;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String cnp;
    private String username;
    private String password;
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String cnp, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.username = username;
        this.password = password;
        this.accounts = new ArrayList<Account>();
    }

    public User(int userId, String firstName, String lastName, String cnp, String username, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.username = username;
        this.password = password;
        this.accounts = new ArrayList<Account>();
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCnp() {
        return cnp;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
