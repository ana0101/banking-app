package view;

import exception.InvalidDataException;
import model.account.Account;
import model.user.User;
import service.AccountService;
import service.UserService;

import java.util.Scanner;

public class ConsoleApp {
    private Scanner scanner = new Scanner(System.in);
    private UserService userService = new UserService();
    private AccountService accountService = new AccountService();

    public static void main(String args[]) throws InvalidDataException {
        ConsoleApp app = new ConsoleApp();
        app.showUserMenu();
    }

    private void showUserMenu() throws InvalidDataException {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("4. See users");
        int option = readOption(1, 4);
        executeUserOption(option);
    }

    private void showAccountMenu(int userId) throws InvalidDataException {
        System.out.println("1. View accounts");
        System.out.println("2. Manage current account");
        System.out.println("3. Manage deposit accounts");
        System.out.println("4. Back");
        int option = readOption(1, 4);
        executeAccountOption(option, userId);
    }

    private void showCurrentAccountMenu(int userId, boolean hasCurrentAccount) throws InvalidDataException {
        int option;
        if (hasCurrentAccount) {
            System.out.println("1. View current account");
            System.out.println("3. Manage cards");
            System.out.println("4. Back");
            option = readOption2(new int[]{1, 3, 4});
        }
        else {
            System.out.println("2. Add current account");
            System.out.println("4. Back");
            option = readOption2(new int[]{2, 4});
        }
        executeCurrentAccountOption(option, userId);
    }

    private void executeUserOption(int option) throws InvalidDataException {
        switch (option) {
            case 1:
                // login
                int userId = 0;
                try {
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    userId = userService.loginUser(username, password).getUserId();
                    showAccountMenu(userId);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    showUserMenu();
                }
                break;
            case 2:
                // register
                try {
                    System.out.println("Enter first name: ");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter last name: ");
                    String lastName = scanner.nextLine();
                    System.out.println("Enter cnp: ");
                    String cnp = scanner.nextLine();
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    userService.registerUser(firstName, lastName, cnp, username, password);
                    showUserMenu();
                }
                catch (InvalidDataException e) {
                    System.out.println(e.getMessage());
                    showUserMenu();
                }
                break;
            case 3:
                // exit
                System.exit(0);
            case 4:
                for (User user : userService.getAllUsers()) {
                    System.out.println(user);
                }
                showUserMenu();
                break;
        }
    }

    private void executeAccountOption(int option, int userId) throws InvalidDataException {
        switch (option) {
            case 1:
                // see accounts
                for (Account account : accountService.getUserAccounts(userId)) {
                    System.out.println(account);
                }
                showAccountMenu(userId);
                break;
            case 2:
                // manage current account
                boolean hasCurrentAccount = accountService.getUserAccounts(userId).size() > 0;
                showCurrentAccountMenu(userId, hasCurrentAccount);
                break;
            case 3:
                // manage deposit accounts
                break;
            case 4:
                // back
                showUserMenu();
                break;
        }
    }

    private void executeCurrentAccountOption(int option, int userId) throws InvalidDataException {
        switch (option) {
            case 1:
                // view current account
                System.out.println(accountService.getUserCurrentAccount(userId));
                showCurrentAccountMenu(userId, true);
                break;
            case 2:
                // add current account
                try {
                    System.out.println("Enter balance: ");
                    double balance = readDouble();
                    accountService.addCurrentAccount(userId, balance);
                    showCurrentAccountMenu(userId, true);
                }
                catch (InvalidDataException e) {
                    System.out.println(e.getMessage());
                    showCurrentAccountMenu(userId, false);
                }
                break;
            case 3:
                // manage cards
                break;
            case 4:
                // back
                showAccountMenu(userId);
                break;
        }
    }

    private int readOption(int a, int b) {
        try {
            int option = readInt();
            if (option >= a && option <= b) {
                return option;
            }
        }
        catch (InvalidDataException invalid) {}
        System.out.println("Invalid option. Try again");
        return readOption(a, b);
    }

    private int readOption2(int[] validOptions) {
        try {
            int option = readInt();
            for (int validOption : validOptions) {
                if (option == validOption) {
                    return option;
                }
            }
        }
        catch (InvalidDataException invalid) {}
        System.out.println("Invalid option. Try again");
        return readOption2(validOptions);
    }

    private int readInt() throws InvalidDataException {
        String line = scanner.nextLine();
        if (line.matches("^\\d+$")) {
            return Integer.parseInt(line);
        }
        else {
            throw new InvalidDataException("Invalid number");
        }
    }

    private double readDouble() throws InvalidDataException {
        String line = scanner.nextLine();
        if (line.matches("^-?\\d*\\.?\\d+$")) {
            return Double.parseDouble(line);
        }
        else {
            throw new InvalidDataException("Invalid number");
        }
    }
}
