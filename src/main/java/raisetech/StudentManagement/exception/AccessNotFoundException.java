package raisetech.StudentManagement.exception;

public class AccessNotFoundException extends RuntimeException {
    public AccessNotFoundException(String message) {
        super(message);
    }
}
