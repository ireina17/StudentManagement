package raisetech.StudentManagement.exception;

public class CourseStatusNotAllowedException extends RuntimeException {
    public CourseStatusNotAllowedException() {
        super("入力されたコースステータスは許可されていません。仮申込／本申込／受講中／受講終了を入力してください。");
    }
}
