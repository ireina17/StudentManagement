package raisetech.StudentManagement.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import raisetech.StudentManagement.exception.StudentNotFoundException;
import raisetech.StudentManagement.exception.UnavailableApiVersionException;

@RestControllerAdvice
public class ExceptionHandle {
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("エラーが発生しました: " + ex.getMessage());
    }

    @ExceptionHandler(UnavailableApiVersionException.class)
    public ResponseEntity<String> handleUnavailableApiVersionException(UnavailableApiVersionException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}
