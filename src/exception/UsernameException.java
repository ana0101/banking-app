package exception;

public class UsernameException extends Exception {
    public UsernameException() {
        super("No user with this username");
    }
}
