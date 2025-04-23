package raisetech.StudentManagement.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String id) {
        super("指定されたIDの受講生が見つかりません: " + id);
    }
}
