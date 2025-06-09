package raisetech.StudentManagement.service.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;

class StudentEntityConverterTest {

    private StudentRepository repository;

    private StudentEntityConverter converter;

    @BeforeEach
    void before() {
        repository = Mockito.mock(StudentRepository.class);
        converter = new StudentEntityConverter(repository);
    }

    @Test
    void 受講生コース情報をDBから取得しマッピングが実行できて正常に返ってくること() {
        LocalDateTime now = LocalDateTime.now();
        Student student1 = new Student("999", "田中太郎", "タナカタロウ", "タロウ", "test@example.com", "東京都", 20, "男性", "", false);
        Student student2 = new Student("998", "佐藤一郎", "サトウイチロウ", "イチロウ", "test@example.com", "埼玉県", 22, "男性", "", false);
        StudentCourse studentCourse1 = new StudentCourse("999", "999", "JAVAコース", now, now.plusYears(1));
        StudentCourse studentCourse2 = new StudentCourse("998", "998", "JAVAコース", now, now.plusYears(1));
        StudentCourse studentCourse3 = new StudentCourse("1000", "998", "AWSコース", now, now.plusYears(1));

        when(repository.searchStudentCourseById("999")).thenReturn(List.of(studentCourse1));
        when(repository.searchStudentCourseById("998")).thenReturn(List.of(studentCourse2, studentCourse3));

        List<Student> studentList = List.of(student1, student2);

        List<StudentDetail> actual = converter.convertStudentDetail(studentList);

        StudentDetail studentDetail1 = new StudentDetail(student1, List.of(studentCourse1));
        StudentDetail studentDetail2 = new StudentDetail(student2, List.of(studentCourse2, studentCourse3));
        List<StudentDetail> expected = List.of(studentDetail1, studentDetail2);

        Assertions.assertEquals(expected, actual);
    }
}