package service;

import exception.InvalidDataException;
import exception.UsernameException;
import exception.WrongPasswordException;
import model.user.User;
import persistence.UserRepository;

import java.util.ArrayList;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    public void registerUser(String firstName, String lastName, String cnp, String username, String password) throws InvalidDataException {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new InvalidDataException("Invalid first name");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new InvalidDataException("Invalid last name");
        }
        if (cnp == null || cnp.trim().isEmpty()) {
            throw new InvalidDataException("Invalid cnp");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new InvalidDataException("Invalid username");
        }
        if (userRepository.getByUsername(username) != null) {
            throw new InvalidDataException("This username is already taken");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidDataException("Invalid password");
        }
        User user = new User(firstName, lastName, cnp, username, password);
        userRepository.add(user);
    }

    public User loginUser(String username, String password) throws WrongPasswordException, UsernameException {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameException();
        }
        if (!password.equals(user.getPassword())) {
            throw new WrongPasswordException();
        }
        return user;
    }

    public ArrayList<User> getAllUsers() {
        return userRepository.getAll();
    }
}
