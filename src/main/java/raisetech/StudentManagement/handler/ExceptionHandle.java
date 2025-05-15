package raisetech.StudentManagement.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raisetech.StudentManagement.exception.AccessNotFoundException;
import raisetech.StudentManagement.exception.StudentNotFoundException;

@RestControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("エラーが発生しました: " + ex.getMessage());
    }

    @ExceptionHandler(AccessNotFoundException.class)
    public ResponseEntity<String> handleAccessNotFoundException(AccessNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
