package raisetech.StudentManagement.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository sut;

    @Test
    void 受講生の全件検索が行えること() {
        List<Student> expected = studentTestDataList();
        List<Student> actual = sut.search();

        assertThat(actual.size()).isEqualTo(5);
        assertEquals(expected, actual);
    }

    @Test
    void 受講生の単体検索が行えること() {
        Student actual = sut.searchStudent("3");
        Student expected = studentTestDataList().get(2);

        assertEquals(expected, actual);
    }

    @Test
    void 受講生コースの全件検索が行えること() {
        List<StudentCourse> actual = sut.searchStudentCourseList();
        List<StudentCourse> expected = studentCoursesTestDataList();

        assertThat(actual.size()).isEqualTo(10);
        assertEquals(expected, actual);
    }

    @Test
    void 受講生コースの単体検索が行えること() {
        List<StudentCourse> actual = sut.searchStudentCourse("3");
        List<StudentCourse> expected = List.of(sut.searchStudentCourseList().get(4), sut.searchStudentCourseList().get(5));

        assertThat(actual.size()).isEqualTo(2);
        assertEquals(expected, actual);
    }

    @Test
    void 受講生の登録が行えること() {
        Student student = new Student(null, "田中太郎", "タナカタロウ", "タロウ", "tanaka.tariu@example.com", "東京都", 25, "男性", "", false);

        sut.registerStudent(student);
        List<Student> actualList = sut.search();
        Student actual = actualList.get(5);
        Student expected = new Student("6", "田中太郎", "タナカタロウ", "タロウ", "tanaka.tariu@example.com", "東京都", 25, "男性", "", false);

        assertThat(actualList.size()).isEqualTo(6);
        assertEquals(expected, actual);
    }

    @Test
    void 受講生コースの登録が行えること() {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        StudentCourse studentCourse = new StudentCourse(null, "11", "TESTコース", now, now.plusYears(1));

        sut.registerStudentCourse(studentCourse);

        List<StudentCourse> actualList = sut.searchStudentCourseList();
        assertThat(actualList.size()).isEqualTo(11);
        StudentCourse actual = actualList.get(10);
        StudentCourse expected = new StudentCourse("11", "11", "TESTコース", now, now.plusYears(1));

        assertEquals(expected, actual);
    }

    @Test
    void 受講生を更新できること() {
        Student expected = new Student("1", "山田花子", "ヤマダハナコ", "ハナコ", "yamada.hanako@example.com", "愛知県", 20, "女性", "変更しました", true);

        sut.updateStudent(expected);
        Student actual = sut.searchStudent("1");

        assertEquals(expected, actual);
    }

    @Test
    void 受講生コースを更新できること() {
        StudentCourse studentCourse = new StudentCourse("1", "1", "TESTコース", null, null);

        sut.updateStudentCourse(studentCourse);

        List<StudentCourse> studentCourseList = sut.searchStudentCourse("1");
        StudentCourse actual = studentCourseList.get(0);
        StudentCourse expected = new StudentCourse("1", "1", "TESTコース", studentCoursesTestDataList().get(0).getCourseStart(), studentCoursesTestDataList().get(0).getCourseEnd());

        assertEquals(expected, actual);
    }

    List<Student> studentTestDataList() {
        Student student1 = new Student("1", "佐藤太郎", "サトウタロウ", "たろう", "taro.sato@example.com", "東京", 20, "男性", "", false);
        Student student2 = new Student("2", "鈴木花子", "スズキハナコ", "ハナコ", "hanako.suzuki@example.com", "大阪", 32, "女性", "", false);
        Student student3 = new Student("3", "高橋一郎", "タカハシイチロウ", "イチ", "ichiro.takahashi@example.com", "愛知", 22, "男性", "", false);
        Student student4 = new Student("4", "田中美咲", "タナカミサキ", "ミサキ", "misaki.tanaka@example.com", "福岡", 19, "女性", "", false);
        Student student5 = new Student("5", "伊藤健", "イトウケン", "ケン", "ken.ito@example.com", "北海道", 40, "その他", "", false);
        return List.of(student1, student2, student3, student4, student5);
    }

    List<StudentCourse> studentCoursesTestDataList() {
        return List.of(
                studentCoursesTestDataConversion("1", "1", "JAVAコース", 2024, 4, 1, 9, 0, 0),
                studentCoursesTestDataConversion("2", "1", "AWSコース", 2024, 4, 1, 10, 0, 0),
                studentCoursesTestDataConversion("3", "2", "JAVAコース", 2024, 5, 1, 9, 30, 0),
                studentCoursesTestDataConversion("4", "2", "デザインコース", 2024, 5, 1, 11, 0, 0),
                studentCoursesTestDataConversion("5", "3", "AWSコース", 2024, 6, 1, 9, 0, 0),
                studentCoursesTestDataConversion("6", "3", "デザインコース", 2024, 6, 1, 10, 30, 0),
                studentCoursesTestDataConversion("7", "4", "AWSコース", 2024, 7, 1, 9, 0, 0),
                studentCoursesTestDataConversion("8", "4", "JAVAコース", 2024, 7, 1, 10, 0, 0),
                studentCoursesTestDataConversion("9", "5", "デザインコース", 2024, 8, 1, 9, 0, 0),
                studentCoursesTestDataConversion("10", "5", "JAVAコース", 2024, 8, 1, 10, 0, 0)
        );
    }

    StudentCourse studentCoursesTestDataConversion(String id, String studentId, String courseName, int year, int month, int dayOfMonth, int hour, int minute, int second) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
        return new StudentCourse(id, studentId, courseName, localDateTime, localDateTime.plusYears(1));
    }
}