package raisetech.StudentManagement.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository sut;

    @Test
    void 受講生の全件検索が行えること() {
        List<Student> actual = sut.search();
        assertThat(actual.size()).isEqualTo(5);
    }

    @Test
    void 受講生の単体検索が行えること() {
        Student actual = sut.searchStudent("3");
        Student expected = sut.search().get(2);

        Assertions.assertEquals(actual.getId(), expected.getId());
        Assertions.assertEquals(actual.getName(), expected.getName());
        Assertions.assertEquals(actual.getKanaName(), expected.getKanaName());
        Assertions.assertEquals(actual.getNickname(), expected.getNickname());
        Assertions.assertEquals(actual.getEmail(), expected.getEmail());
        Assertions.assertEquals(actual.getArea(), expected.getArea());
        Assertions.assertEquals(actual.getAge(), expected.getAge());
        Assertions.assertEquals(actual.getSex(), expected.getSex());
        Assertions.assertEquals(actual.getRemark(), expected.getRemark());
        Assertions.assertEquals(actual.isDeleted(), expected.isDeleted());
    }

    @Test
    void 受講生コースの全件検索が行えること() {
        List<StudentCourse> actual = sut.searchStudentCourseList();
        assertThat(actual.size()).isEqualTo(10);
    }

    @Test
    void 受講生コースの単体検索が行えること() {
        List<StudentCourse> actual = sut.searchStudentCourse("3");
        List<StudentCourse> expected = List.of(sut.searchStudentCourseList().get(4), sut.searchStudentCourseList().get(5));

        Assertions.assertEquals(actual.get(0).getId(), expected.get(0).getId());
        Assertions.assertEquals(actual.get(1).getId(), expected.get(1).getId());
        Assertions.assertEquals(actual.get(0).getStudentId(), expected.get(0).getStudentId());
        Assertions.assertEquals(actual.get(1).getStudentId(), expected.get(1).getStudentId());
        Assertions.assertEquals(actual.get(0).getCourseName(), expected.get(0).getCourseName());
        Assertions.assertEquals(actual.get(1).getCourseName(), expected.get(1).getCourseName());
        Assertions.assertEquals(actual.get(0).getCourseStart(), expected.get(0).getCourseStart());
        Assertions.assertEquals(actual.get(1).getCourseStart(), expected.get(1).getCourseStart());
        Assertions.assertEquals(actual.get(0).getCourseEnd(), expected.get(0).getCourseEnd());
        Assertions.assertEquals(actual.get(1).getCourseEnd(), expected.get(1).getCourseEnd());
    }

    @Test
    void 受講生の登録が行えること() {
        Student student = new Student();
        student.setName("田中太郎");
        student.setKanaName("タナカタロウ");
        student.setNickname("タロウ");
        student.setEmail("tanaka.tariu@example.com");
        student.setArea("東京都");
        student.setAge(25);
        student.setSex("男性");
        student.setRemark("");

        sut.registerStudent(student);
        List<Student> actual = sut.search();

        assertThat(actual.size()).isEqualTo(6);
    }

    @Test
    void 受講生コースの登録が行えること() {
        LocalDateTime now = LocalDateTime.now();
        StudentCourse studentCourse = new StudentCourse(null, "11", "TESTコース", now, now.plusYears(1));

        sut.registerStudentCourse(studentCourse);

        List<StudentCourse> actual = sut.searchStudentCourseList();
        assertThat(actual.size()).isEqualTo(11);
    }

    @Test
    void 受講生を更新できること() {
        Student expected = new Student("1", "山田花子", "ヤマダハナコ", "ハナコ", "yamada.hanako@example.com", "愛知県", 20, "女性", "変更しました", true);

        sut.updateStudent(expected);
        Student actual = sut.searchStudent("1");

        Assertions.assertEquals(actual.getId(), expected.getId());
        Assertions.assertEquals(actual.getName(), expected.getName());
        Assertions.assertEquals(actual.getKanaName(), expected.getKanaName());
        Assertions.assertEquals(actual.getNickname(), expected.getNickname());
        Assertions.assertEquals(actual.getEmail(), expected.getEmail());
        Assertions.assertEquals(actual.getArea(), expected.getArea());
        Assertions.assertEquals(actual.getAge(), expected.getAge());
        Assertions.assertEquals(actual.getSex(), expected.getSex());
        Assertions.assertEquals(actual.getRemark(), expected.getRemark());
        Assertions.assertEquals(actual.isDeleted(), expected.isDeleted());
    }

    @Test
    void 受講生コースを更新できること() {
        LocalDateTime now = LocalDateTime.now();
        StudentCourse expected = new StudentCourse();
        expected.setId("1");
        expected.setStudentId("1");
        expected.setCourseName("TESTコース");

        sut.updateStudentCourse(expected);

        List<StudentCourse> studentCourseList = sut.searchStudentCourse("1");
        StudentCourse actual = studentCourseList.get(0);

        Assertions.assertEquals(actual.getId(), expected.getId());
        Assertions.assertEquals(actual.getStudentId(), expected.getStudentId());
        Assertions.assertEquals(actual.getCourseName(), expected.getCourseName());
    }
}