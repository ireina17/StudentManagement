package raisetech.StudentManagement.repository;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.CourseStatus;
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
        List<Student> actual = sut.findStudentsByAll();

        assertThat(actual.size()).isEqualTo(5);
        assertEquals(expected, actual);
    }

    @Test
    void 受講生の単体でid検索が行えること() {
        Student actual = sut.searchStudentById("3");
        Student expected = studentTestDataList().get(2);

        assertEquals(expected, actual);
    }

    @Test
    void 名前が完全一致する受講生が検索されること() {
        List<Student> actual = sut.findStudentsByName("高橋一郎");
        List<Student> expected = List.of(studentTestDataList().get(2));

        assertEquals(expected, actual);
    }

    @Test
    void 名前が完全一致する受講生が複数件ヒットすること() {
        List<Student> actual = sut.findStudentsByName("佐藤太郎");
        List<Student> expected = List.of(studentTestDataList().get(0), studentTestDataList().get(4));

        assertEquals(expected, actual);
    }

    @Test
    void カナ名が完全一致する受講生が検索されること() {
        List<Student> actual = sut.findStudentsByKanaName("タカハシイチロウ");
        List<Student> expected = List.of(studentTestDataList().get(2));

        assertEquals(expected, actual);
    }

    @Test
    void カナ名が完全一致する受講生が複数件ヒットすること() {
        List<Student> actual = sut.findStudentsByKanaName("サトウタロウ");
        List<Student> expected = List.of(studentTestDataList().get(0), studentTestDataList().get(4));

        assertEquals(expected, actual);
    }

    @Test
    void ニックネームが完全一致する受講生が検索されること() {
        List<Student> actual = sut.findStudentsByNickname("イチ");
        List<Student> expected = List.of(studentTestDataList().get(2));

        assertEquals(expected, actual);
    }

    @Test
    void ニックネームが完全一致する受講生が複数件ヒットすること() {
        List<Student> actual = sut.findStudentsByNickname("タロウ");
        List<Student> expected = List.of(studentTestDataList().get(0), studentTestDataList().get(4));

        assertEquals(expected, actual);
    }

    @Test
    void メールが完全一致する受講生が検索されること() {
        List<Student> actual = sut.findStudentsByEmail("ichiro.takahashi@example.com");
        List<Student> expected = List.of(studentTestDataList().get(2));

        assertEquals(expected, actual);
    }

    @Test
    void メールが完全一致する受講生が複数件ヒットすること() {
        List<Student> actual = sut.findStudentsByEmail("taro.sato@example.com");
        List<Student> expected = List.of(studentTestDataList().get(0), studentTestDataList().get(4));

        assertEquals(expected, actual);
    }

    @Test
    void 地域が完全一致する受講生が検索されること() {
        List<Student> actual = sut.findStudentsByArea("愛知");
        List<Student> expected = List.of(studentTestDataList().get(2));

        assertEquals(expected, actual);
    }

    @Test
    void 地域が完全一致する受講生が複数件ヒットすること() {
        List<Student> actual = sut.findStudentsByArea("東京");
        List<Student> expected = List.of(studentTestDataList().get(0), studentTestDataList().get(4));

        assertEquals(expected, actual);
    }

    @Test
    void 年齢が完全一致する受講生が検索されること() {
        List<Student> actual = sut.findStudentsByAge("22");
        List<Student> expected = List.of(studentTestDataList().get(2));

        assertEquals(expected, actual);
    }

    @Test
    void 年齢が完全一致する受講生が複数件ヒットすること() {
        List<Student> actual = sut.findStudentsByAge("20");
        List<Student> expected = List.of(studentTestDataList().get(0), studentTestDataList().get(4));

        assertEquals(expected, actual);
    }

    @Test
    void 性別が完全一致する受講生が検索されること() {
        List<Student> actual = sut.findStudentsBySex("その他");
        List<Student> expected = List.of(studentTestDataList().get(2));

        assertEquals(expected, actual);
    }

    @Test
    void 性別が完全一致する受講生が複数件ヒットすること() {
        List<Student> actual = sut.findStudentsBySex("男性");
        List<Student> expected = List.of(studentTestDataList().get(0), studentTestDataList().get(4));

        assertEquals(expected, actual);
    }

    @Test
    void 備考が完全一致する受講生が検索されること() {
        List<Student> actual = sut.findStudentsByRemark("その他");
        List<Student> expected = List.of(studentTestDataList().get(2));

        assertEquals(expected, actual);
    }

    @Test
    void 備考が完全一致する受講生が複数件ヒットすること() {
        List<Student> actual = sut.findStudentsByRemark("テストです。");
        List<Student> expected = List.of(studentTestDataList().get(0), studentTestDataList().get(4));

        assertEquals(expected, actual);
    }

    @Test
    void 削除フラグが完全一致する受講生が複数件ヒットすること() {
        List<Student> actual = sut.findStudentsByIsDeleted("true");
        List<Student> expected = List.of(studentTestDataList().get(0), studentTestDataList().get(4));

        assertEquals(expected, actual);
    }

    @Test
    void 受講生コースの全件検索が行えること() {
        List<StudentCourse> actual = sut.findStudentCoursesByAll();
        List<StudentCourse> expected = studentCoursesTestDataList();

        assertThat(actual.size()).isEqualTo(10);
        assertEquals(expected, actual);
    }

    @Test
    void 受講生コースの単体検索が行えること() {
        List<StudentCourse> actual = sut.searchStudentCourseById("3");
        List<StudentCourse> expected = List.of(sut.findStudentCoursesByAll().get(4), sut.findStudentCoursesByAll().get(5));

        assertThat(actual.size()).isEqualTo(2);
        assertEquals(expected, actual);
    }

    @Test
    void コース申し込み状況を全件検索が行えること() {
        List<CourseStatus> actual = sut.findCourseStatusByAll();
        List<CourseStatus> expected = courseStatusTestDataList();

        assertThat(actual.size()).isEqualTo(10);
        assertEquals(expected, actual);
    }

    @Test
    void コース申し込み状況を検索が行えること() {
        CourseStatus actual = sut.searchCourseStatus("2");
        CourseStatus expected = courseStatusTestDataList().get(1);

        assertEquals(expected, actual);
    }

    @Test
    void 受講生の登録が行えること() {
        Student student = new Student(null, "田中太郎", "タナカタロウ", "タロウ", "tanaka.tariu@example.com", "東京都", 25, "男性", "", false);

        sut.registerStudent(student);
        List<Student> actualList = sut.findStudentsByAll();
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

        List<StudentCourse> actualList = sut.findStudentCoursesByAll();
        assertThat(actualList.size()).isEqualTo(11);
        StudentCourse actual = actualList.get(10);
        StudentCourse expected = new StudentCourse("11", "11", "TESTコース", now, now.plusYears(1));

        assertEquals(expected, actual);
    }

    @Test
    void コースの申し込み状況の登録が行えること() {
        CourseStatus courseStatus = new CourseStatus(null, "11", "仮申込");

        sut.registerCourseStatus(courseStatus);
        List<CourseStatus> actualList = sut.findCourseStatusByAll();
        CourseStatus actual = actualList.get(10);
        CourseStatus expected = new CourseStatus("11", "11", "仮申込");

        assertThat(actualList.size()).isEqualTo(11);
        assertEquals(expected, actual);
    }

    @Test
    void 受講生を更新できること() {
        Student expected = new Student("1", "山田花子", "ヤマダハナコ", "ハナコ", "yamada.hanako@example.com", "愛知県", 20, "女性", "変更しました", true);

        sut.updateStudent(expected);
        Student actual = sut.searchStudentById("1");

        assertEquals(expected, actual);
    }

    @Test
    void 受講生コースを更新できること() {
        StudentCourse studentCourse = new StudentCourse("1", "1", "TESTコース", null, null);

        sut.updateStudentCourse(studentCourse);

        List<StudentCourse> studentCourseList = sut.searchStudentCourseById("1");
        StudentCourse actual = studentCourseList.get(0);
        StudentCourse expected = new StudentCourse("1", "1", "TESTコース", studentCoursesTestDataList().get(0).getCourseStart(), studentCoursesTestDataList().get(0).getCourseEnd());

        assertEquals(expected, actual);
    }

    @Test
    void コース申し込み状況を更新できること() {
        CourseStatus expected = new CourseStatus("2", "2", "受講終了");

        sut.updateCourseStatus(expected);
        CourseStatus actual = sut.findCourseStatusByAll().get(1);

        assertEquals(expected, actual);
    }

    List<Student> studentTestDataList() {
        Student student1 = new Student("1", "佐藤太郎", "サトウタロウ", "タロウ", "taro.sato@example.com", "東京", 20, "男性", "テストです。", true);
        Student student2 = new Student("2", "鈴木花子", "スズキハナコ", "ハナコ", "hanako.suzuki@example.com", "大阪", 32, "女性", "", false);
        Student student3 = new Student("3", "高橋一郎", "タカハシイチロウ", "イチ", "ichiro.takahashi@example.com", "愛知", 22, "その他", "その他", false);
        Student student4 = new Student("4", "田中美咲", "タナカミサキ", "ミサキ", "misaki.tanaka@example.com", "福岡", 19, "女性", "", false);
        Student student5 = new Student("5", "佐藤太郎", "サトウタロウ", "タロウ", "taro.sato@example.com", "東京", 20, "男性", "テストです。", true);
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

    List<CourseStatus> courseStatusTestDataList() {
        CourseStatus courseStatus1 = new CourseStatus("1", "1", "受講終了");
        CourseStatus courseStatus2 = new CourseStatus("2", "2", "本申込");
        CourseStatus courseStatus3 = new CourseStatus("3", "3", "受講終了");
        CourseStatus courseStatus4 = new CourseStatus("4", "4", "受講中");
        CourseStatus courseStatus5 = new CourseStatus("5", "5", "仮申込");
        CourseStatus courseStatus6 = new CourseStatus("6", "6", "仮申込");
        CourseStatus courseStatus7 = new CourseStatus("7", "7", "受講終了");
        CourseStatus courseStatus8 = new CourseStatus("8", "8", "本申込");
        CourseStatus courseStatus9 = new CourseStatus("9", "9", "受講中");
        CourseStatus courseStatus10 = new CourseStatus("10", "10", "仮申込");
        return List.of(courseStatus1, courseStatus2, courseStatus3, courseStatus4, courseStatus5, courseStatus6, courseStatus7, courseStatus8, courseStatus9, courseStatus10);
    }
}