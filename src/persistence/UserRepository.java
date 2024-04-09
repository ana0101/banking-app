package persistence;

import model.user.User;

import java.util.ArrayList;

public class UserRepository implements GenericRepository<User> {
    private ArrayList<User> users = new ArrayList<User>();

    public int add(User user) {
        if (!users.contains(user)) {
            users.add(user);
            return user.getUserId();
        }
        return 0;
    }

    public User get(int id) {
        return users.stream().filter(user -> user.getUserId() == id).findFirst().orElse(null);
    }

    public User getByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    public ArrayList<User> getAll() {
        return users;
    }

    public void update(User user) {

    }

    public void delete(User user) {
        users.remove(user);
    }
}
