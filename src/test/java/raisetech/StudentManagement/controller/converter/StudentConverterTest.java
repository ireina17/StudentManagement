package raisetech.StudentManagement.controller.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class StudentConverterTest {

    private StudentConverter converter = new StudentConverter();

    @Test
    void 受講生コース情報のマッピングが実行できて値が正常に返ってくること() {
        //データの準備
        LocalDateTime now = LocalDateTime.now();
        Student student1 = new Student("999", "田中太郎", "タナカタロウ", "タロウ", "test@example.com", "東京都", 20, "男性", "", false);
        Student student2 = new Student("998", "佐藤一郎", "サトウイチロウ", "イチロウ", "test@example.com", "埼玉県", 22, "男性", "", false);
        StudentCourse studentCourse1 = new StudentCourse("999", "999", "JAVAコース", now, now.plusYears(1));
        StudentCourse studentCourse2 = new StudentCourse("998", "998", "JAVAコース", now, now.plusYears(1));
        StudentCourse studentCourse3 = new StudentCourse("1000", "998", "AWSコース", now, now.plusYears(1));

        //実行前の準備
        List<Student> students = List.of(student1, student2);
        List<StudentCourse> studentCourseList = List.of(studentCourse1, studentCourse2, studentCourse3);

        //実行
        List<StudentDetail> actual = converter.convertStudentDetails(students, studentCourseList);

        //期待されるデータの準備
        StudentDetail studentDetail1 = new StudentDetail(student1, List.of(studentCourse1));
        StudentDetail studentDetail2 = new StudentDetail(student2, List.of(studentCourse2, studentCourse3));
        List<StudentDetail> expected = new ArrayList<>();
        expected.add(studentDetail1);
        expected.add(studentDetail2);

        //検証
        Assertions.assertEquals(expected, actual);
    }
}