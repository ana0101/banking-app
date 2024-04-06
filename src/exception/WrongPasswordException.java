package exception;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("Incorrect password");
    }
}
