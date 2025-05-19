package raisetech.StudentManagement.exception;

public class ConnectionRefusedException extends RuntimeException {
    public ConnectionRefusedException(String message) {
        super(message);
    }
}
