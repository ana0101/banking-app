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

    public static int getUserIdCount() {
        return userIdCount;
    }

    public static void setUserIdCount(int userIdCount) {
        User.userIdCount = userIdCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
}
